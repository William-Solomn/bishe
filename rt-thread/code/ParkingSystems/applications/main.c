#include <stdio.h>
#include <string.h>
#include <unistd.h>
#include <stdlib.h>
#include "fpioa.h"
#include "board_config.h"
#include "plic.h"
#include "sysctl.h"
#include "lcd.h"
#include "kpu.h"
#include "nt35310.h"
#include "st7789.h"
#include <rtthread.h>
#include <rt_ai.h>
#define PLL0_OUTPUT_FREQ 800000000UL
#define PLL1_OUTPUT_FREQ 400000000UL

/****************************
 * please update this
 **************************/
#include <rt_ai_cat_dog_model.h>                                   //RT-AK生成文件
#define MY_MODEL_NAME           RT_AI_CAT_DOG_MODEL_NAME
#define MY_MODEL_OUT_1_SIZE     RT_AI_CAT_DOG_OUT_1_SIZE
char *label[]={"0 cat","1 dog"};
/********************************************************/


static int ai_done(void *ctx)
{
    *((uint32_t*)ctx)= 1;
    return 0;
}
volatile uint32_t g_dvp_finish_flag;
rt_err_t camera_cb(rt_device_t dev, size_t size)
{
    g_dvp_finish_flag = 1;
    return RT_EOK;
}

int main(void)
{
    /* Set CPU and dvp clk */
    sysctl_clock_enable(SYSCTL_CLOCK_AI);
    io_set_power();
    io_mux_init();
    lcd_init();
    lcd_clear(BLACK);
    lcd_draw_string(40, 10, "Hello RT-Thread!", RED);
    lcd_draw_string(40, 30, "Demo: Hand Detection ", BLUE);

    rt_ai_t mymodel = NULL;
    volatile uint32_t g_ai_done_flag;
    static uint8_t *kpu_image;
    static uint8_t *display_image;
    rt_kprintf("LCD init\n");                              //LCD初始化完成
    /* DVP init */
    rt_kprintf("DVP init\n");


    /* read一帧图像,显示与AI图像会按顺序连续排放在buffer中 */
    display_image = rt_malloc((240 * 320 * 2)+(240 * 320 * 3));
    kpu_image = display_image + (240 * 320 * 2); //接着显示之后存放,注意地址偏移

    rt_device_t camera_dev = rt_device_find(CAMERA); //查找摄像头设备,CAMERA="ov2640" OR "ov5640" OR "gc0308"
    if(!camera_dev) {
        rt_kprintf("find camera err!\n");
        return -1;
    };
    rt_device_init(camera_dev); //初始化摄像头
    rt_device_open(camera_dev,RT_DEVICE_OFLAG_RDWR); //打开摄像头,读写模式
    rt_device_set_rx_indicate(camera_dev,camera_cb); //设置read回调函数

    mymodel = rt_ai_find(MY_MODEL_NAME);                       //查找模型
    if(!mymodel){
        rt_kprintf("\nmodel find error\n");
        while (1) {};
    }

    if (rt_ai_init(mymodel,(rt_ai_buffer_t*)kpu_image) != 0)     //模型初始化
    {
        rt_kprintf("\nmodel init error\n");
        while (1) {};
    }

    /* 使能系统全局中断 */
    sysctl_enable_irq();

    rt_kprintf("rt_ai_init complete..\n");


    //至此，所有运行前准备工作完成。
    
    while (1){
        g_dvp_finish_flag = 0;
        rt_device_read(camera_dev,0,display_image,0);
        while (g_dvp_finish_flag == 0) {};

        g_ai_done_flag= 0;
        if(rt_ai_run(mymodel,ai_done,&g_ai_done_flag) != 0){
            rt_kprintf("rtak run fail!\n");
            while (1);
        }
        while(!g_ai_done_flag);
        int pred = 0;
        float *output;
        output = rt_ai_output(mymodel,0);                        //输出kpu推理结果
        if (output[0] >= 0.5)                                              //输出最大值作为推理结果
                pred = 1;
            else
                pred = 0;

        //lcd_clear(BLACK);
        rt_kprintf("The prediction is : %s\n",label[pred]);

        lcd_draw_picture(0, 0, 320, 240, (uint32_t *) display_image);
        lcd_draw_string(320/2 - 16, 20, label[pred], GREEN);
        ;
    }
}
