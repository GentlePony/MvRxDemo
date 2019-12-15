package com.example.bottomnavigation.application

import android.app.Application
import android.content.Context
import com.example.bottomnavigation.kodein.gsonModule
import com.example.bottomnavigation.kodein.retrofitModule
import com.tencent.mmkv.MMKV
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.androidModule
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

/**
 * description
 * @date 2019/12/12
 * @author:TaoSi
 */
class App : Application(), KodeinAware {
    override val kodein: Kodein = Kodein.lazy {
        bind<Context>() with singleton {
            this@App
        }
        import(androidXModule(this@App))
        import(androidModule(this@App))
        import(gsonModule)
        import(retrofitModule)
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        initMMKV()
    }

    private fun initMMKV() {
        MMKV.initialize(this)
    }

    companion object {
        lateinit var INSTANCE: App
    }
}