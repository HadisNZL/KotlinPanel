package com.kotlin.panel.widget

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.preference.PreferenceCategory
import androidx.preference.PreferenceViewHolder
import com.kotlin.panel.R
import com.kotlin.panel.utils.CommonUtil
import org.jetbrains.anko.singleLine


class CustomPreferenceCategory(context: Context, attributeSet: AttributeSet) :
    PreferenceCategory(context, attributeSet) {

    override fun onBindViewHolder(holder: PreferenceViewHolder) {
        super.onBindViewHolder(holder)
        val view: View = holder.itemView
//        view.layoutParams = LinearLayout.LayoutParams(
//            LinearLayout.LayoutParams.MATCH_PARENT, CommonUtil.dp2px(context, 32F)
//        )
        val tv: TextView = holder.findViewById(android.R.id.title) as TextView
        view.setBackgroundColor(ContextCompat.getColor(context, R.color.preference_category_color))
        tv.setTextColor(ContextCompat.getColor(context, R.color.item_desc))
        tv.height = CommonUtil.dp2px(context, 10F)
        tv.singleLine = true
//        tv.setPadding(view.paddingLeft, 0, view.paddingLeft, 0);
        tv.gravity = Gravity.CENTER_VERTICAL
        tv.setTypeface(Typeface.SANS_SERIF, Typeface.NORMAL)
        tv.textAlignment = View.TEXT_ALIGNMENT_VIEW_START
    }
}