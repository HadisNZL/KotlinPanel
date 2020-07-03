package com.kotlin.panel.mvp.model

import com.kotlin.panel.base.BaseModel
import com.kotlin.panel.http.RetrofitHelper
import com.kotlin.panel.mvp.contract.MainContract
import com.kotlin.panel.mvp.model.bean.HttpResult
import com.kotlin.panel.mvp.model.bean.UserInfoBody
import io.reactivex.rxjava3.core.Observable

class MainModel : BaseModel(), MainContract.Model {

    override fun logout(): Observable<HttpResult<Any>> {
        return RetrofitHelper.service.logout()
    }

    override fun getUserInfo(): Observable<HttpResult<UserInfoBody>> {
        return RetrofitHelper.service.getUserInfo()
    }

}