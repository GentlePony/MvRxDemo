package com.example.bottomnavigation.kodein

import com.example.bottomnavigation.api.UserApi
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import retrofit2.Retrofit

/**
 * description
 * @date 2019/12/12
 * @author:TaoSi
 */
val TAG_API_MODULE = "ApiModule"
val apiModule = Kodein.Module(TAG_API_MODULE) {
    bind<UserApi>() with singleton {
        instance<Retrofit>().create(UserApi::class.java)
    }
}

data class ServiceManager(val userApi: UserApi)