package com.kotlin.panel.widget

import android.content.Context
import android.util.AttributeSet
import androidx.preference.Preference
import androidx.preference.PreferenceViewHolder
import com.kotlin.panel.R
import com.kotlin.panel.utils.SettingUtil

class IconPreference(context: Context, attrs: AttributeSet) : Preference(context, attrs) {

    private var circleImageView: CirecleColorView? = null

    init {
        widgetLayoutResource = R.layout.item_icon_preference_preview
    }

    override fun onBindViewHolder(holder: PreferenceViewHolder?) {
        super.onBindViewHolder(holder)
        val color = SettingUtil.getColor()
        val view = holder?.itemView
        circleImageView = view?.findViewById(R.id.iv_preview)
        circleImageView?.setBackgroundColor(color)
    }


    fun setView() {
        val color = SettingUtil.getColor()
        circleImageView!!.setBackgroundColor(color)
    }
}