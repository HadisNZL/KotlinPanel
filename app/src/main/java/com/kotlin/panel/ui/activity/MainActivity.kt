package com.kotlin.panel.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomnavigation.LabelVisibilityMode.LABEL_VISIBILITY_LABELED
import com.kotlin.panel.R
import com.kotlin.panel.app.App
import com.kotlin.panel.base.BaseMvpActivity
import com.kotlin.panel.ext.showToast
import com.kotlin.panel.mvp.contract.MainContract
import com.kotlin.panel.mvp.model.bean.UserInfoBody
import com.kotlin.panel.mvp.presenter.MainPresenter
import com.kotlin.panel.ui.fragment.HomeFragment
import com.kotlin.panel.ui.fragment.MineFragment
import com.kotlin.panel.ui.fragment.SecondFragment
import com.kotlin.panel.ui.fragment.ThirdFragment
import com.kotlin.panel.utils.LogTools
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.common_toolbar.*
import kotlinx.android.synthetic.main.im_badge.*


class MainActivity : BaseMvpActivity<MainContract.View, MainContract.Presenter>(),
    MainContract.View {

    private val BOTTOM_INDEX: String = "bottom_index"
    private var mExitTime: Long = 0

    private val FRAGMENT_HOME = 0x01
    private val FRAGMENT_SECOND = 0x02
    private val FRAGMENT_THIRD = 0x03
    private val FRAGMENT_MINE = 0x04
    private var mIndex = FRAGMENT_HOME

    private var mHomeFragment: HomeFragment? = null
    private var mSecondFragment: SecondFragment? = null
    private var mThirdFragment: ThirdFragment? = null
    private var mMineFragment: MineFragment? = null

    /**
     * kotlin 配合伴生对象 静态可赋值变量 相当于 java static    如果 private const val   相当于java static final
     */
    companion object {
        private var isFromChangeNightOrDay: Boolean = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            mIndex = savedInstanceState.getInt(BOTTOM_INDEX)
        }
        super.onCreate(savedInstanceState)
    }

    override fun createPresenter(): MainContract.Presenter = MainPresenter()

    override fun attachLayoutRes(): Int = R.layout.activity_main

    @SuppressLint("WrongConstant")
    override fun initView() {
        super.initView()
        tv_title.text = getString(R.string.app_name)
//        closeAnimation(bottom_navigation)//反射关闭BottomNavigationView动画
        setNavigationBadge(bottom_navigation, 2, true)
        bottom_navigation.run {
            // 以前使用 BottomNavigationViewHelper.disableShiftMode(this) 方法来设置底部图标和字体都显示并去掉点击动画
            // 升级到 28.0.0 之后，官方重构了 BottomNavigationView ，目前可以使用 labelVisibilityMode = 1 来替代
            // BottomNavigationViewHelper.disableShiftMode(this)
            labelVisibilityMode = LABEL_VISIBILITY_LABELED
            setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        }
        bottom_navigation.getChildAt(0).findViewById<View>(R.id.action_home)
            .setOnLongClickListener { return@setOnLongClickListener true }
        bottom_navigation.getChildAt(0).findViewById<View>(R.id.action_second)
            .setOnLongClickListener { return@setOnLongClickListener true }
        bottom_navigation.getChildAt(0).findViewById<View>(R.id.action_third)
            .setOnLongClickListener { return@setOnLongClickListener true }
        bottom_navigation.getChildAt(0).findViewById<View>(R.id.action_mine)
            .setOnLongClickListener { return@setOnLongClickListener true }
        showFragment(mIndex)
//        bottom_navigation.selectedItemId = R.id.action_mine
    }

    override fun initData() {
        mPresenter?.getUserInfo()
    }

    override fun onResume() {
        super.onResume()
        setAboutSettingStatusbarColor(mIndex == FRAGMENT_MINE)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(BOTTOM_INDEX, mIndex)
    }


    override fun showDefaultMsg(msg: String) {
        super.showDefaultMsg(msg)
        LogTools.e("showDefaultMsg", "-->$msg")
    }


    override fun showLogoutSuccess(success: Boolean) {
    }

    override fun showUserInfo(bean: UserInfoBody) {
        LogTools.d("bean", "-->$bean")
    }


    override fun showError(errorMsg: String) {
        super.showError(errorMsg)
        LogTools.e("showError", "-->$errorMsg")
    }

    override fun hideLoading() {
    }

    /**
     * NavigationItemSelect监听
     */
    private val onNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            return@OnNavigationItemSelectedListener when (item.itemId) {
                R.id.action_home -> {
                    showFragment(FRAGMENT_HOME)
                    true
                }
                R.id.action_second -> {
                    showFragment(FRAGMENT_SECOND)
                    true
                }
                R.id.action_third -> {
                    showFragment(FRAGMENT_THIRD)
                    true
                }
                R.id.action_mine -> {
                    showFragment(FRAGMENT_MINE)
                    true
                }
                else -> {
                    false
                }
            }
        }

    /**
     * 展示Fragment
     * @param index
     */
    private fun showFragment(index: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        hideFragments(transaction)
        mIndex = index
        setAboutSettingStatusbarColor(mIndex == FRAGMENT_MINE)
        when (index) {
            FRAGMENT_HOME // 首页
            -> {
                comm_toolbar.visibility = VISIBLE
                tv_title.text = getString(R.string.app_name)
                if (mHomeFragment == null) {
                    mHomeFragment = HomeFragment.getInstance()
                    transaction.add(R.id.container, mHomeFragment!!, "home")
                } else {
                    transaction.show(mHomeFragment!!)
                }
            }
            FRAGMENT_SECOND  //第二页
            -> {
                comm_toolbar.visibility = VISIBLE
                tv_title.text = getString(R.string.second_name)
                if (mSecondFragment == null) {
                    mSecondFragment = SecondFragment.getInstance()
                    transaction.add(R.id.container, mSecondFragment!!, "second")
                } else {
                    transaction.show(mSecondFragment!!)
                }
            }
            FRAGMENT_THIRD////第三页
            -> {
                comm_toolbar.visibility = VISIBLE
                tv_title.text = getString(R.string.third_name)
                if (mThirdFragment == null) {
                    mThirdFragment = ThirdFragment.getInstance()
                    transaction.add(R.id.container, mThirdFragment!!, "third")
                } else {
                    transaction.show(mThirdFragment!!)
                }
            }
            FRAGMENT_MINE // 我的
            -> {
                comm_toolbar.visibility = GONE
                tv_title.text = getString(R.string.mine_name)
                if (mMineFragment == null) {
                    mMineFragment = MineFragment.getInstance()
                    transaction.add(R.id.container, mMineFragment!!, "project")
                } else {
                    transaction.show(mMineFragment!!)
                }
            }
        }
        transaction.commit()
    }


    /**
     * 隐藏所有的Fragment
     */
    private fun hideFragments(transaction: FragmentTransaction) {
        mHomeFragment?.let { transaction.hide(it) }
        mSecondFragment?.let { transaction.hide(it) }
        mThirdFragment?.let { transaction.hide(it) }
        mMineFragment?.let { transaction.hide(it) }
    }


    private fun setNavigationBadge(view: BottomNavigationView, index: Int, isShowBadge: Boolean) {
        val menuView: BottomNavigationMenuView = view.getChildAt(0) as BottomNavigationMenuView
        val itemView = menuView.getChildAt(index) as BottomNavigationItemView
        var customView = LayoutInflater.from(this).inflate(R.layout.im_badge, menuView, false)
        itemView.addView(customView)
        num_badge.visibility = if (isShowBadge) VISIBLE else GONE
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis().minus(mExitTime) <= 2000) {
                finish()
            } else {
                mExitTime = System.currentTimeMillis()
                showToast(getString(R.string.exit_tip))
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun recreate() {
        removeFragment()
        super.recreate()
    }

    private fun removeFragment() {
        try {
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            if (mHomeFragment != null) {
                fragmentTransaction.remove(mHomeFragment!!)
            }
            if (mSecondFragment != null) {
                fragmentTransaction.remove(mSecondFragment!!)
            }
            if (mThirdFragment != null) {
                fragmentTransaction.remove(mThirdFragment!!)
            }
            if (mMineFragment != null) {
                fragmentTransaction.remove(mMineFragment!!)
            }
            fragmentTransaction.commitAllowingStateLoss()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun changeNightOrDay() {
        removeFragment()
        App.setNightOrDateFlag()
        this.window.setWindowAnimations(R.style.WindowAnimationFadeInOut)
        recreate()
    }
}
