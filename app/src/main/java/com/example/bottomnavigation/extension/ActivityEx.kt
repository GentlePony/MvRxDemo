package com.example.bottomnavigation.extension

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.Point
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.annotation.ColorInt

/**
 * description
 * @date 2019/12/12
 * @author:TaoSi
 */

/** 上下文(方便使用)*/
val Activity.mContext: Context
    get() {
        return this
    }

/**Activity本身(弹窗,权限请求等需要用到Activity)*/
val Activity.mActivity: Activity
    get() {
        return this
    }

/**屏幕宽度*/
val Activity.mScreenWidth: Int
    get() {
        return resources.displayMetrics.widthPixels
    }

/**屏幕高度(包含状态栏高度但不包含底部虚拟按键高度)*/
val Activity.mScreenHeight: Int
    get() {
        return resources.displayMetrics.heightPixels
    }

/**ContentView*/
val Activity.mContentView: FrameLayout
    get() {
        return this.findViewById(android.R.id.content)
    }

//设置状态栏颜色
fun Activity.setStatusColor(@ColorInt color: Int) {
    window.statusBarColor = color
}


/**状态栏高度*/
val Activity.mStatusBarHeight: Int
    get() {
        return Resources.getSystem()
            .getDimensionPixelSize(resources.getIdentifier("status_bar_height", "dimen", "android"))
    }

/**判断是否存在虚拟导航栏*/
const val NAVIGATION = "navigationBarBackground"
fun Activity.isNavigationBarExist(): Boolean {
    val vp = window.decorView as? ViewGroup
    vp?.let {
        for (i in 0 until it.childCount) {
            it.getChildAt(i).context.packageName
            if (it.getChildAt(i).id != View.NO_ID && NAVIGATION.equals(
                    resources.getResourceEntryName(
                        it.getChildAt(
                            i
                        ).id
                    )
                )
            ) {
                return true
            }
        }
    }
    return false
}

/**判断是否为全面屏设备*/
fun Activity.isAllScreenDevice(): Boolean {
    var mIsAllScreenDevice = false
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
        return false
    }
    val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
    if (windowManager != null) {
        val display = windowManager.getDefaultDisplay()
        var point = Point()
        display.getRealSize(point)
        var width: Float
        var height: Float
        if (point.x < point.y) {
            width = point.x.toFloat()
            height = point.y.toFloat()
        } else {
            width = point.y.toFloat()
            height = point.x.toFloat()
        }
        if (height / width >= 1.97f) {
            mIsAllScreenDevice = true
        }
    }
    return mIsAllScreenDevice
}

/**状态栏低电量模式*/
private const val UI_LOW_POWER = View.SYSTEM_UI_FLAG_LOW_PROFILE
/**填充状态栏*/
private const val UI_FILL_TOP = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
/**填充底部导航栏*/
private const val UI_FILL_BOTTOM = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
/**固定操作*/
private const val UI_STICKY = View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
/**常规*/
private const val UI_STABLE = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
/**隐藏底部导航栏*/
private const val UI_HIDE_NAI = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
/**正常显示:只填充顶部*/
private const val UI_MERGE_NORMAL_TOP = UI_STABLE or
        UI_STICKY or
        UI_LOW_POWER or
        UI_FILL_TOP
/**状态栏操作简化*/
var Activity.mUiSystem: Int
    get() = window.decorView.systemUiVisibility
    set(value) {
        window.decorView.systemUiVisibility = value
    }
/**填充顶部不填充底部*/
fun Activity.uiModeNormal() {
    mUiSystem = UI_MERGE_NORMAL_TOP
    mContentView.setPadding(0, mStatusBarHeight, 0, 0)
}

/**设置状态栏字体为白色*/
fun Activity.setStatusBarWhiteText() {
    //原生
    window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
    //小米
    setMiUIStatusBarTextColor(this, false)
    //魅族
    setFlymeStatusBarTextColor(this, false)
}

/**设置状态栏字体为黑色*/
fun Activity.setStatusBarBlackText() {
    //原生
    window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    //小米
    setMiUIStatusBarTextColor(this, true)
    //魅族
    setFlymeStatusBarTextColor(this, true)
}

/**
 * 小米设置状态栏字体颜色
 *
 * @param isBlack
 */
private fun setMiUIStatusBarTextColor(activity: Activity, isBlack: Boolean) {
    val clazz = activity.window::class.java
    try {
        var darkModeFlag = 0
        val layoutParams = Class.forName("android.view.MiuiWindowManager\$LayoutParams")
        val field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE")
        darkModeFlag = field.getInt(layoutParams)
        val extraFlagField = clazz.getMethod("setExtraFlags", Int::class.javaPrimitiveType, Int::class.javaPrimitiveType)
        extraFlagField.invoke(activity.window, if (isBlack) darkModeFlag else 0, darkModeFlag)
    } catch (e: Exception) {
        //            Logger.e("不是MIUI");
    }

}

/**
 * 魅族设置状态栏字体颜色
 *
 * @param isBlack
 * @return
 */
private fun setFlymeStatusBarTextColor(activity: Activity, isBlack: Boolean): Boolean {
    var result = false
    if (activity.window != null) {
        try {
            val lp = activity.window.attributes
            val darkFlag = WindowManager.LayoutParams::class.java.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON")
            val meizuFlags = WindowManager.LayoutParams::class.java.getDeclaredField("meizuFlags")
            darkFlag.isAccessible = true
            meizuFlags.isAccessible = true
            val bit = darkFlag.getInt(null)
            var value = meizuFlags.getInt(lp)
            if (isBlack) {
                value = value or bit
            } else {
                value = value and bit.inv()
            }
            meizuFlags.setInt(lp, value)
            activity.window.attributes = lp
            result = true
        } catch (e: Exception) {
            //                Logger.e("不是Flyme");
        }
    }
    return result
}