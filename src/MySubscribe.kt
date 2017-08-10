package com.example.studyanko

/**
 * Created by 铖哥 on 2017/8/9.
 */

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class MySubscribe ( val isAsync : Boolean = false)



@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class test ( val isAsync : Boolean = false)