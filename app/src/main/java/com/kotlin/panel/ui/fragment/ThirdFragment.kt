package com.kotlin.panel.ui.fragment

import android.view.View
import com.kotlin.panel.R
import com.kotlin.panel.base.BaseFragment

class ThirdFragment : BaseFragment() {

    companion object {
        fun getInstance(): ThirdFragment = ThirdFragment()
    }

    override fun attachLayoutRes(): Int = R.layout.fragment_third

    override fun initView(view: View) {
    }

    override fun lazyLoad() {
    }
}