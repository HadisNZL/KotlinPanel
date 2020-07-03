@file:Suppress("DEPRECATION")

package com.kotlin.panel.utils

import android.graphics.Color
import android.preference.PreferenceManager
import androidx.core.content.ContextCompat
import com.kotlin.panel.R
import com.kotlin.panel.app.App

object SettingUtil {
    private val setting = PreferenceManager.getDefaultSharedPreferences(App.context)


    /**
     * 获取主题颜色
     */
    fun getColor(): Int {
        val defaultColor = ContextCompat.getColor(App.context, R.color.colorPrimary)
//        val defaultColor = App.context.resources.getColor(R.color.colorPrimary)
        val color = setting.getInt("color", defaultColor)
        return if (color != 0 && Color.alpha(color) != 255) {
            defaultColor
        } else color
    }

    /**
     * 设置主题颜色
     */
    fun setColor(color: Int) {
        setting.edit().putInt("color", color).apply()
    }


    /**
     * 设置夜间模式
     */
    fun setIsNightMode(flag: Boolean) {
        setting.edit().putBoolean("nav_night_mode", flag).apply()
    }

    /**
     * 获取是否开启夜间模式
     */
    fun getIsNightMode(): Boolean {
        return setting.getBoolean("nav_night_mode", false)
    }


    /**
     * 获取是否开启导航栏上色
     */
    fun getNavBar(): Boolean {
        return setting.getBoolean("nav_bar", false)
    }

}