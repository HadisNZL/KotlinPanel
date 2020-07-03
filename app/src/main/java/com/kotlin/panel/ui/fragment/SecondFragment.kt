package com.kotlin.panel.ui.fragment

import android.view.View
import com.kotlin.panel.R
import com.kotlin.panel.base.BaseFragment

class SecondFragment : BaseFragment() {

    companion object {
        fun getInstance(): SecondFragment = SecondFragment()
    }
    override fun attachLayoutRes(): Int = R.layout.fragment_second

    override fun initView(view: View) {
    }

    override fun lazyLoad() {
    }
}