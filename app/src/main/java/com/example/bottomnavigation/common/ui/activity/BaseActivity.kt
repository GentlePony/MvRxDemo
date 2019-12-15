package com.example.bottomnavigation.common.ui.activity

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bottomnavigation.extension.setStatusBarBlackText
import com.example.bottomnavigation.extension.setStatusColor
import com.example.bottomnavigation.extension.uiModeNormal

/**
 * description
 * @date 2019/12/12
 * @author:TaoSi
 */
abstract class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        onCreateBefore()
        initStatus()
        super.onCreate(savedInstanceState)
        if (layoutResId() != 0) {
            setContentView(layoutResId())
        }
        initView()
        initData()
    }

    protected open fun initStatus() {
        setStatusColor(Color.TRANSPARENT)
        uiModeNormal()
        setStatusBarBlackText()
    }

    protected open fun onCreateBefore() {}

    protected abstract fun layoutResId(): Int

    protected abstract fun initView()

    protected abstract fun initData()
}