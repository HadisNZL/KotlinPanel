package com.kotlin.panel.widget

import android.annotation.TargetApi
import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import com.kotlin.panel.app.App

/**
 * 更新因为系统变化的设置 Configuration
 */
class ContextUtil {
    companion object {
        fun attachBaseContext(context: Context?): Context? {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                createConfigurationResources(context)
            } else {
                setConfiguration()
                context
            }
        }


        @TargetApi(Build.VERSION_CODES.N)
        fun createConfigurationResources(context: Context?): Context? {
            val resources = context?.resources
            val configuration = resources?.configuration
            return context?.createConfigurationContext(configuration!!)
        }


        fun setConfiguration() {
            val configuration: Configuration = App.context.resources.configuration
            val resources: Resources = App.context.resources
            val dm = resources.displayMetrics
            resources.updateConfiguration(configuration, dm) //语言更换生效的代码!
        }


    }
}