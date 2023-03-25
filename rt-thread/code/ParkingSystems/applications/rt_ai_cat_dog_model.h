#ifndef __RT_AI_CAT_DOG_MODEL_H
#define __RT_AI_CAT_DOG_MODEL_H

/* model info ... */

// model name
#define RT_AI_CAT_DOG_MODEL_NAME			"cat_dog"

#define RT_AI_CAT_DOG_WORK_BUFFER_BYTES	(469520)

#define AI_CAT_DOG_DATA_WEIGHTS_SIZE		(1493120) //unused

#define RT_AI_CAT_DOG_BUFFER_ALIGNMENT		(4)

#define RT_AI_CAT_DOG_IN_NUM				(1)

#define RT_AI_CAT_DOG_IN_1_SIZE			(1 * 3 * 150 * 150)
#define RT_AI_CAT_DOG_IN_1_SIZE_BYTES		((1 * 3 * 150 * 150) * 4)
#define RT_AI_CAT_DOG_IN_SIZE_BYTES		{	\
	((1 * 3 * 150 * 150) * 4) ,	\
}

#define RT_AI_CAT_DOG_IN_TOTAL_SIZE_BYTES	((1 * 3 * 150 * 150) * 4)


#define RT_AI_CAT_DOG_OUT_NUM				(1)

#define RT_AI_CAT_DOG_OUT_1_SIZE			(1 * 1)
#define RT_AI_CAT_DOG_OUT_1_SIZE_BYTES		((1 * 1) * 4)
#define RT_AI_CAT_DOG_OUT_SIZE_BYTES		{	\
	((1 * 1) * 4) ,	\
}

#define RT_AI_CAT_DOG_OUT_TOTAL_SIZE_BYTES	((1 * 1) * 4)



#define RT_AI_CAT_DOG_TOTAL_BUFFER_SIZE		//unused

#endif	//end
