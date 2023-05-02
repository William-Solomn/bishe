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
#include "gpio.h"
#include <drv_gpio.h>
#define PLL0_OUTPUT_FREQ 800000000UL
#define PLL1_OUTPUT_FREQ 400000000UL

/****************************
 * please update this
 **************************/
#include "lcd.h"
#include "rt_ai.h"

#define MODEL_INPUT_W 28
#define MODEL_INPUT_H 28
#define LCD_WIDTH 320
#define LCD_HEIGHT 240

#define LED_PIN 0
#define BUTTON_PIN 1

#define MY_MODEL_NAME           RT_AI_MNIST_MODEL_NAME
#define MY_MODEL_IN_1_SIZE      RT_AI_MNIST_IN_1_SIZE
#define MY_MODEL_OUT_1_SIZE     RT_AI_MNIST_OUT_1_SIZE
                                                                                                                                                                            char *label[]={"A95GE9","AQ590S","AC9Q78"};
//"AQE386","AXQ852","A43J91","A95GE9","AQ590S","AC9Q78"
extern int32_t enter_python(uint32_t delay_s);

//相机参数初始化:g_dvp_finish_flag
volatile uint32_t g_dvp_finish_flag;
rt_err_t camera_cb(rt_device_t dev, size_t size)
{
    g_dvp_finish_flag = 1;
    return RT_EOK;
}

int main(void)
{
    /* Set CPU clock */
    sysctl_clock_enable(SYSCTL_CLOCK_AI);  // 使能系统时钟（系统时钟初始化）
    io_mux_init();  // 硬件引脚初始化
    io_set_power();  // 设置IO口电压

    rt_hw_pin_init();
    rt_kprintf("gpio init successful\n");
    gpio_init();
    fpioa_set_function(30 , FUNC_GPIO0 );//LED pin定义
    fpioa_set_function(3 , FUNC_GPIO1 );//Button pin定义
    gpio_set_drive_mode(LED_PIN, GPIO_DM_OUTPUT);
    gpio_set_drive_mode(BUTTON_PIN, GPIO_DM_INPUT_PULL_UP);

    gpio_set_pin(0, GPIO_PV_LOW);
    sleep(3);
//    gpio_set_drive_mode(BUTTON_PIN, GPIO_DM_INPUT_PULL_UP);
    rt_kprintf("___________aaaaa_______________\n ");

    rt_kprintf("___________aaaaa_______________ \n");
    //    gpio_init();  // 初始化GPIO
    //    gpio_set_drive_mode(BUTTON_PIN, GPIO_DM_INPUT_PULL_UP);  // 设置按钮为输入模式并启用内部上拉电阻
    //    gpio_set_pin(BUTTON_PIN, GPIO_PE_BOTH);

    /* LCD init */
    lcd_init();
    rt_kprintf("LCD屏幕初始化成功\n");

    lcd_draw_string(40, 40, "Init success!", WHITE);

    lcd_draw_string(40, 80, "Hello teacher, this is my info:", RED);
    lcd_draw_string(43, 100, "Name : Qiu Shi Bo!", GRED);
    lcd_draw_string(43, 120, "Student-ID : 1907040649", YELLOW);
    lcd_draw_string(43, 140, "Teacher : Chai Rui", YELLOW);


    lcd_draw_string(40, 200, "AI-Model will start 3s later...", WHITE);
    sleep(2);
    lcd_clear(BLACK);
    rt_kprintf("AI Model start\n");

    static uint8_t *display_image;

    /* read一帧图像,显示与AI图像会按顺序连续排放在buffer中 */
    display_image = rt_malloc((240 * 320 * 2)+(240 * 320 * 3));
    //查找摄像头设备,CAMERA="ov2640" OR "ov5640" OR "gc0308"
    rt_device_t camera_dev = rt_device_find(CAMERA);

    //判断是否存在摄像头设备
    if(!camera_dev) {
        rt_kprintf("find camera err!\n");
        return -1;
    }else{
        rt_kprintf("find camera succ");
    }
    rt_device_init(camera_dev); //初始化摄像头
    rt_device_open(camera_dev,RT_DEVICE_OFLAG_RDWR); //打开摄像头,读写模式
    rt_device_set_rx_indicate(camera_dev,camera_cb); //设置read回调函数

    sysctl_enable_irq();
    while(1){
        g_dvp_finish_flag = 0;
        rt_device_read(camera_dev,0,display_image,0);
        while (g_dvp_finish_flag == 0) {};

        gpio_pin_value_t button_state = gpio_get_pin(BUTTON_PIN);
                if (button_state == GPIO_PV_LOW)
                {
                    rt_kprintf("___________aaaaa_______________ ");
                    gpio_set_pin(LED_PIN, GPIO_PV_HIGH);
                }
                else
                {
                    rt_kprintf("___________aaaaa_______________ ");
                    gpio_set_pin(LED_PIN, GPIO_PV_LOW);
                }

//        for(int i=0; i<3;i++){
//            rt_kprintf("The prediction is : %s\n",label[i]);
//            lcd_draw_picture(0, 0, 320, 240, (uint32_t *) display_image);
//            lcd_draw_string(320/2 - 16, 20, "-", GREEN);
//            sleep(2);
//        }

                lcd_draw_picture(0, 0, 320, 240, (uint32_t *) display_image);
//        // 检测按钮状态
//        if (gpio_get_pin(BUTTON_PIN) == GPIO_PV_LOW) {
//            rt_kprintf("clicked\n");
//        } else {
//            rt_kprintf("not click\n");
//        }

        sleep(2);
    }

    /* enter msh or python, default python if enable mpy, type msh to msh */
    //enter_python(3);
    return 0;
}

