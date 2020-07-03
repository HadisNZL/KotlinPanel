package com.kotlin.panel.mvp.presenter

import com.kotlin.panel.base.BasePresenter
import com.kotlin.panel.ext.ss
import com.kotlin.panel.ext.sss
import com.kotlin.panel.mvp.contract.MainContract
import com.kotlin.panel.mvp.model.MainModel
import com.kotlin.panel.utils.LogTools

class MainPresenter : BasePresenter<MainContract.Model, MainContract.View>(),
    MainContract.Presenter {

    override fun createModel(): MainContract.Model? = MainModel()

    override fun logout() {
        mModel?.logout()?.ss(mModel, mView) {
            mView?.showLogoutSuccess(success = true)
        }
    }

    override fun getUserInfo() {
        mModel?.getUserInfo()?.sss(mView, false, {
            mView?.showUserInfo(it.data)
        }, null)
    }

}