package com.kotlin.panel.ui.fragment

import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.preference.PreferenceFragmentCompat
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.color.colorChooser
import com.kotlin.panel.R
import com.kotlin.panel.app.App
import com.kotlin.panel.base.BaseActivity
import com.kotlin.panel.ui.activity.MainActivity
import com.kotlin.panel.utils.SettingUtil
import com.kotlin.panel.utils.StatusBarUtil
import com.kotlin.panel.widget.IconPreference

class MineFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var cirecleColorView: IconPreference

    companion object {
        fun getInstance(): MineFragment = MineFragment()
    }


    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.pref_setting)
        cirecleColorView = findPreference<IconPreference>("color")!!
        doSomething()
    }

    private fun doSomething() {
        findPreference<IconPreference>("color")?.setOnPreferenceClickListener {
            val colors = intArrayOf(
                Color.RED,
                Color.BLUE,
                ContextCompat.getColor(App.context, R.color.Amber),
                ContextCompat.getColor(App.context, R.color.Green),
                ContextCompat.getColor(App.context, R.color.Black),
                ContextCompat.getColor(App.context, R.color.Purple),
                ContextCompat.getColor(App.context, R.color.light_red),
                ContextCompat.getColor(App.context, R.color.Brown)
            )
            context?.let { it1 ->
                MaterialDialog(it1).show {
                    title(R.string.color_dialog_title)
                    colorChooser(
                        colors,
                        initialSelection = SettingUtil.getColor()
//                        allowCustomArgb = true,
//                        showAlphaSelector = true
                    ) { dialog, color ->
                        SettingUtil.setColor(color)
                    }
                    positiveButton(R.string.color_dialog_ok_select)

                }
            }
            false
        }
    }

    private fun initColor() {
        (activity as BaseActivity).initColor()
        (activity as BaseActivity).setAboutSettingStatusbarColor(true)
    }


    override fun setDivider(divider: Drawable?) {
        super.setDivider(ColorDrawable(Color.TRANSPARENT))
    }

    override fun setDividerHeight(height: Int) {
        super.setDividerHeight(0)
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        when (key) {
            "color" -> {
                cirecleColorView.setView()
                initColor()
            }
            "nav_bar" -> {
                initColor()
            }
            "nav_night_mode" -> {
                (activity as MainActivity).setLightStatusBar(!SettingUtil.getIsNightMode())
                (activity as MainActivity).changeNightOrDay()
            }
        }

    }

}