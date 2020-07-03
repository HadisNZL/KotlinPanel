package com.kotlin.panel.app

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.kotlin.panel.utils.DisplayManager
import com.kotlin.panel.utils.LogTools
import com.kotlin.panel.utils.SettingUtil
import kotlin.properties.Delegates

/**
 * create by niuzilin
 */
open class App : Application() {

    companion object {
        var context: Context by Delegates.notNull()
            private set

        lateinit var instance: Application

        fun setNightOrDateFlag() {
            AppCompatDelegate.setDefaultNightMode(
                if (SettingUtil.getIsNightMode()) {
                    AppCompatDelegate.MODE_NIGHT_YES
                } else {
                    AppCompatDelegate.MODE_NIGHT_NO
                }
            )
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        context = applicationContext
        initTheme()
        DisplayManager.init(this)
        LogTools.initLogTools(this)
        registerActivityLifecycleCallbacks(mActivityLifecycleCallbacks)

    }


    private fun initTheme() {
        setNightOrDateFlag()
    }

    private val mActivityLifecycleCallbacks = object : ActivityLifecycleCallbacks {
        override fun onActivityPaused(activity: Activity) {
        }

        override fun onActivityStarted(activity: Activity) {
        }

        override fun onActivityDestroyed(activity: Activity) {
        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        }

        override fun onActivityStopped(activity: Activity) {
        }

        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        }

        override fun onActivityResumed(activity: Activity) {
        }

    }

}