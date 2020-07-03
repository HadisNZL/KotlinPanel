package com.kotlin.panel.widget

import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.kotlin.panel.R
import com.kotlin.panel.utils.CommonUtil

class CustomToast {

    private var toast: Toast
    private var textView: TextView

    constructor(context: Context?, message: String) : this(context, message, Toast.LENGTH_SHORT)

    constructor(context: Context?, message: String, duration: Int) {
        toast = Toast(context)
        toast.duration = duration
        val view = View.inflate(context, R.layout.toast_custom, null)
        textView = view.findViewById(R.id.tv_prompt)
        textView.text = message
        toast.view = view
//        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.setGravity(Gravity.TOP, 0, CommonUtil.dp2px(context!!, 90f))
    }

    fun show() {
        toast.show()
    }

}