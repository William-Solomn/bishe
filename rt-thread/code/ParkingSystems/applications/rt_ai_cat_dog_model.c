#include <rt_ai.h>
#include <backend_k210_kpu.h>
#include <rt_ai_cat_dog_model.h>
#include <kpu.h>

extern unsigned char cd_kmodel[];

/* based on k210 */
#define RT_AI_CAT_DOG_INFO    {       \
    RT_AI_CAT_DOG_IN_NUM,             \
    RT_AI_CAT_DOG_OUT_NUM,            \
    RT_AI_CAT_DOG_IN_SIZE_BYTES,      \
    RT_AI_CAT_DOG_OUT_SIZE_BYTES,     \
    RT_AI_CAT_DOG_WORK_BUFFER_BYTES,  \
    ALLOC_INPUT_BUFFER_FLAG                 \
}

#define RT_AI_CAT_DOG_HANDLE  {         \
    .info   =     RT_AI_CAT_DOG_INFO    \
}

#define RT_K210_AI_CAT_DOG   {   \
    .parent         = RT_AI_CAT_DOG_HANDLE,   \
    .model          = cd_kmodel, \
    .dmac           = DMAC_CHANNEL5,        \
}

static struct k210_kpu rt_k210_ai_cat_dog = RT_K210_AI_CAT_DOG;

static int rt_k210_ai_cat_dog_init(){
    rt_ai_register(RT_AI_T(&rt_k210_ai_cat_dog),RT_AI_CAT_DOG_MODEL_NAME,0,backend_k210_kpu,&rt_k210_ai_cat_dog);
    return 0;
}

INIT_APP_EXPORT(rt_k210_ai_cat_dog_init);
