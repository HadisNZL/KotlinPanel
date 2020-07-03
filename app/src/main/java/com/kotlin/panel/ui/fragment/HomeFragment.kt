package com.kotlin.panel.ui.fragment

import android.view.View
import com.kotlin.panel.R
import com.kotlin.panel.base.BaseFragment
import com.kotlin.panel.utils.SettingUtil
import com.kotlin.panel.utils.StatusBarUtil

class HomeFragment : BaseFragment() {

    companion object {
        fun getInstance(): HomeFragment = HomeFragment()
    }

    override fun attachLayoutRes(): Int = R.layout.fragment_home

    override fun initView(view: View) {
//        activity?.let { StatusBarUtil.setColorNoTranslucent(it, SettingUtil.getColor()) }
    }

    override fun lazyLoad() {
    }
}