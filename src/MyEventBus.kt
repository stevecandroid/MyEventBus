package com.example.studyanko

import android.util.Log
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.async
import org.greenrobot.eventbus.EventBus
import org.jetbrains.anko.doAsync
import java.lang.reflect.Method

/**
 * Created by 铖哥 on 2017/8/9.
 */
object MyEventBus{

    val subMap : MutableMap<Triple<Any,Method,String>,Boolean> = mutableMapOf()
//    val msg : MutableList<Triple<Any,Any,MutableMap<>>> = mutableListOf()

    @Synchronized fun register( subscriber : Any){
        val clazz = subscriber::class.java
            for( m in clazz.declaredMethods){
                 val a = m.getAnnotation(MySubscribe::class.java)
                    if(a!=null){
                        subMap.put(Triple(subscriber,m,m.parameterTypes[0].toString()),a.isAsync)
                    }
            }
    }

    fun post( msg : Any){
        for( ( trible , isAsync) in subMap ){
           if(trible.third.substring(6,trible.third.length) == (msg::class.java.name)){
               if(isAsync){
                   async(CommonPool) {
                       trible.second.invoke(trible.first,msg)
                   }
               }else{
                   trible.second.invoke(trible.first,msg)
               }
           }
        }
    }

     @Synchronized fun unregister(subscriber: Any){
        for ( (sub , _ ) in subMap){
            if(sub.first === subscriber)
            subMap.remove(sub)
        }
    }
}