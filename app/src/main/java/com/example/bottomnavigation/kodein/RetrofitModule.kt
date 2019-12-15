package com.example.bottomnavigation.kodein

import com.example.bottomnavigation.common.interceptor.HttpInterceptor
import com.example.bottomnavigation.constants.ApiConstants
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * description
 * @date 2019/12/12
 * @author:TaoSi
 */
const val TAG_RETROFIT_MODULE = "RetrofitModule"
const val Time_OUT = 20
val retrofitModule = Kodein.Module(TAG_RETROFIT_MODULE) {
    bind<Retrofit.Builder>() with singleton {
        Retrofit.Builder()
    }
    bind<OkHttpClient.Builder>() with singleton {
        OkHttpClient.Builder()
    }
    bind<OkHttpClient>() with singleton {
        instance<OkHttpClient.Builder>().connectTimeout(Time_OUT.toLong(),TimeUnit.SECONDS)
            .callTimeout(Time_OUT.toLong(),TimeUnit.SECONDS)
            .addInterceptor(HttpInterceptor()).build()
    }
    bind<Retrofit>() with singleton {
        instance<Retrofit.Builder>().baseUrl(ApiConstants.BASE_URL)
            .client(instance())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}