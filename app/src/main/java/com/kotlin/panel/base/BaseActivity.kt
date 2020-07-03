@file:Suppress("DEPRECATION")

package com.kotlin.panel.base

import android.annotation.SuppressLint
import android.content.IntentFilter
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.TypedValue
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kotlin.panel.R
import com.kotlin.panel.app.App
import com.kotlin.panel.event.NetworkChangeEvent
import com.kotlin.panel.receiver.NetworkChangeReceiver
import com.kotlin.panel.utils.CommonUtil
import com.kotlin.panel.utils.LogTools
import com.kotlin.panel.utils.SettingUtil
import com.kotlin.panel.utils.StatusBarUtil
import kotlinx.android.synthetic.main.common_toolbar.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.lang.reflect.Field

abstract class BaseActivity : AppCompatActivity() {


    private val connectivityAction = "android.net.conn.CONNECTIVITY_CHANGE"

    /**
     * theme color
     */
    protected var mThemeColor: Int = SettingUtil.getColor()

    /**
     * 网络状态变化的广播
     */
    private var mNetworkChangeReceiver: NetworkChangeReceiver? = null

    /**
     * 布局Id
     */
    protected abstract fun attachLayoutRes(): Int

    /**
     * 初始化view
     */
    abstract fun initView()

    /**
     * 初始化数据
     */
    abstract fun initData()


    /**
     * 是否使用 EventBus
     */
    open fun useEventBus(): Boolean = true


    override fun onCreate(savedInstanceState: Bundle?) {
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        super.onCreate(savedInstanceState)
        setContentView(attachLayoutRes())
        if (useEventBus()) {
            EventBus.getDefault().register(this)
        }
        initView()
        initData()

    }

    /**
     * Network Change
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onNetworkChangeEvent(event: NetworkChangeEvent) {

    }


    override fun onResume() {
        val filter = IntentFilter()
        filter.addAction(connectivityAction)
        mNetworkChangeReceiver = NetworkChangeReceiver()
        registerReceiver(mNetworkChangeReceiver, filter)
        super.onResume()
        //导航栏着色
        initColor()
    }

    open fun initColor() {
        mThemeColor = if (SettingUtil.getIsNightMode()) {
            ContextCompat.getColor(this, R.color.colorPrimary)
        } else {
            SettingUtil.getColor()
        }
        LogTools.dTag("sdduudi", "上下文", App.context, "这个this$this")
        StatusBarUtil.setColor(this, mThemeColor, 0)
        if (this.comm_toolbar != null) {
            this.comm_toolbar?.setBackgroundDrawable(ColorDrawable(mThemeColor))
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            window.statusBarColor = CircleView.shiftColorDown(mThemeColor)
//            // 最近任务栏上色
//            val tDesc = ActivityManager.TaskDescription(
//                    getString(R.string.app_name),
//                    BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher),
//                    mThemeColor)
//            setTaskDescription(tDesc)
            if (SettingUtil.getNavBar()) {
                window.navigationBarColor = mThemeColor
//                window.navigationBarColor = CircleView.shiftColorDown(mThemeColor)
            } else {
                window.navigationBarColor = Color.BLACK
            }
        }
    }

    override fun onPause() {
        if (mNetworkChangeReceiver != null) {
            unregisterReceiver(mNetworkChangeReceiver)
            mNetworkChangeReceiver = null
        }
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (useEventBus()) {
            EventBus.getDefault().unregister(this)
        }
        CommonUtil.fixInputMethodManagerLeak(this)
    }


    @SuppressLint("RestrictedApi")
    fun closeAnimation(view: BottomNavigationView) {
        val mMenuView = view.getChildAt(0) as BottomNavigationMenuView
        for (i in 0 until mMenuView.childCount) {
            val button =
                mMenuView.getChildAt(i) as BottomNavigationItemView
            val mLargeLabel = getField<TextView>(button.javaClass, button, "largeLabel")!!
            val mSmallLabel = getField<TextView>(button.javaClass, button, "smallLabel")!!
            val mSmallLabelSize = mSmallLabel.textSize
            setField(button.javaClass, button, "shiftAmount", 0f)
            setField(button.javaClass, button, "scaleUpFactor", 1f)
            setField(button.javaClass, button, "scaleDownFactor", 1f)
            mLargeLabel.setTextSize(TypedValue.COMPLEX_UNIT_PX, mSmallLabelSize)
        }
        mMenuView.updateMenuView()
    }


    private fun <T> getField(
        targetClass: Class<*>,
        instance: Any,
        fieldName: String
    ): T? {
        try {
            val field: Field = targetClass.getDeclaredField(fieldName)
            field.isAccessible = true
            return field.get(instance) as T?
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
        return null
    }


    private fun setField(
        targetClass: Class<*>,
        instance: Any,
        fieldName: String,
        value: Any
    ) {
        try {
            val field: Field = targetClass.getDeclaredField(fieldName)
            field.isAccessible = true
            field.set(instance, value)
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
    }

    fun setAboutSettingStatusbarColor(isLastPage: Boolean) {
        setLightStatusBar(isLastPage)
        StatusBarUtil.setColor(
            this,
            if (isLastPage) ContextCompat.getColor(
                this,
                R.color.transparent
            ) else mThemeColor,
            0
        )
    }

    fun setLightStatusBar(isDarkColor: Boolean) {
        StatusBarUtil.setLightStatusBar(this, isDarkColor)
    }

}