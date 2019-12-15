package com.example.bottomnavigation.common.interceptor

import com.blankj.utilcode.util.LogUtils
import okhttp3.Interceptor
import okhttp3.Response

/**
 * description
 * @date 2019/12/12
 * @author:TaoSi
 */
class HttpInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
//        builder.addHeader("co")
        LogUtils.e("HTTPS请求${builder.build().url()}\n${builder.build().headers()}\n${builder.build().body()}")

        return chain.proceed(builder.build())
    }

}