package com.kotlin.panel.mvp.contract

import com.kotlin.panel.base.IModel
import com.kotlin.panel.base.IPresenter
import com.kotlin.panel.base.IView
import com.kotlin.panel.mvp.model.bean.BaseBean
import com.kotlin.panel.mvp.model.bean.HttpResult
import com.kotlin.panel.mvp.model.bean.UserInfoBody
import io.reactivex.rxjava3.core.Observable

interface MainContract {

    interface Model : IModel {
        fun logout(): Observable<HttpResult<Any>>
        fun getUserInfo(): Observable<HttpResult<UserInfoBody>>
    }

    interface View : IView {
        fun showLogoutSuccess(success: Boolean)
        fun showUserInfo(bean: UserInfoBody)
    }

    interface Presenter : IPresenter<View> {
        fun logout()
        fun getUserInfo()
    }
}