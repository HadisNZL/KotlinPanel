package com.kotlin.panel.ext

import com.kotlin.panel.app.App
import com.kotlin.panel.base.IModel
import com.kotlin.panel.base.IView
import com.kotlin.panel.http.exception.ErrorStatus
import com.kotlin.panel.http.exception.ExceptionHandle
import com.kotlin.panel.http.function.RetryWithDelay
import com.kotlin.panel.mvp.model.bean.BaseBean
import com.kotlin.panel.rx.SchedulerUtils
import com.kotlin.panel.utils.LogTools
import com.kotlin.panel.utils.NetWorkUtil
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable

fun <T : BaseBean> Observable<T>.ss(
    model: IModel?,
    view: IView?,
    isShowLoading: Boolean = true,
    onSuccess: (T) -> Unit// 这里Unit 相当于Java里面的object
) {
    this.compose(SchedulerUtils.ioToMain())
        .retryWhen(RetryWithDelay())
        .subscribe(object : Observer<T> {
            override fun onComplete() {
                view?.hideLoading()
            }

            override fun onSubscribe(d: Disposable?) {
                if (isShowLoading) view?.showLoading()
                model?.addDisposable(d)
                if (!NetWorkUtil.isNetworkConnected(App.instance)) {
                    view?.showDefaultMsg("网络连接不可用,请检查网络设置!")
                    onComplete()
                }
            }

            override fun onNext(t: T) {
                when {
                    t.errorCode == ErrorStatus.SUCCESS -> onSuccess.invoke(t)
                    t.errorCode == ErrorStatus.TOKEN_INVALID -> {
                        //token过期，重新登录
                    }
                    else -> view?.showDefaultMsg(t.errorMsg)
                }
            }

            override fun onError(e: Throwable) {
                view?.hideLoading()
                view?.showError(ExceptionHandle.handleException(e))
            }

        })
}


fun <T : BaseBean> Observable<T>.sss(
    view: IView?,
    isShowLoading: Boolean = true,
    onSuccess: (T) -> Unit,
    onError: ((T) -> Unit)? = null
): Disposable {
    LogTools.e("走了")
    if (isShowLoading) view?.showLoading()
    return this.compose(SchedulerUtils.ioToMain())
        .retryWhen(RetryWithDelay())
        .subscribe({
            LogTools.e("subscribe", it.errorCode)
            when {
                it.errorCode == ErrorStatus.SUCCESS -> onSuccess.invoke(it)
                it.errorCode == ErrorStatus.TOKEN_INVALID -> {
                    // Token 过期，重新登录
                }
                else -> {
                    if (onError != null) {
                        onError.invoke(it)
                    } else {
                        if (it.errorMsg.isNotEmpty())
                            view?.showDefaultMsg(it.errorMsg)
                    }
                }
            }
            view?.hideLoading()
        }, {
            LogTools.e("Throwable", it)
            view?.hideLoading()
            view?.showError(ExceptionHandle.handleException(it))
        })
}