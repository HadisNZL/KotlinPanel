package com.kotlin.panel.ui.activity

import android.content.Intent
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import androidx.core.content.ContextCompat
import com.blankj.utilcode.constant.PermissionConstants
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.PermissionUtils
import com.kotlin.panel.R
import com.kotlin.panel.app.App
import com.kotlin.panel.base.BaseActivity
import com.kotlin.panel.utils.LogTools
import com.kotlin.panel.utils.SettingUtil
import kotlinx.android.synthetic.main.activity_splash.*
import org.jetbrains.anko.backgroundColor

class SplashActivity : BaseActivity() {

    private var alphaAnimation: AlphaAnimation? = null

    override fun useEventBus(): Boolean = false

    override fun attachLayoutRes(): Int = R.layout.activity_splash

    override fun initView() {
        requestPermission()
    }

    private fun requestPermission() {
        PermissionUtils.permission(PermissionConstants.STORAGE)
            .callback(object : PermissionUtils.FullCallback {
                override fun onGranted(permissionsGranted: List<String>) {
                    initAmin()
                }

                override fun onDenied(
                    permissionsDeniedForever: List<String>,
                    permissionsDenied: List<String>
                ) {
                    LogUtils.d(permissionsDeniedForever, permissionsDenied)
                    if (permissionsDeniedForever.isNotEmpty()) {
                        LogTools.e(" denied forever")
                    } else {
                        LogTools.e(" is denied")
                        finish()
                    }
                }
            })
            .request()
    }


    private fun initAmin() {
        alphaAnimation = AlphaAnimation(1.0F, 1.0F)
        alphaAnimation?.run {
            duration = 1500
            setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationRepeat(p0: Animation?) {
                }

                override fun onAnimationEnd(p0: Animation?) {
                    jumpToMain()
                }

                override fun onAnimationStart(p0: Animation?) {
                }
            })
        }
        layout_splash.startAnimation(alphaAnimation)
    }

    fun jumpToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    override fun initData() {
//        showToast("闪屏页")
    }

    override fun initColor() {
        super.initColor()
        layout_splash.setBackgroundColor(mThemeColor)
    }
}
