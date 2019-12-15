package com.example.bottomnavigation.kodein

import com.google.gson.Gson
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

/**
 * description
 * @date 2019/12/12
 * @author:TaoSi
 */
//注入Gson实例
const val TAG_GSON_MODULE = "GsonModule"

val gsonModule = Kodein.Module(TAG_GSON_MODULE){
    bind<Gson>() with singleton {
        Gson()
    }
}