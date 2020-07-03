package com.kotlin.panel.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.Checkable
import com.airbnb.lottie.LottieAnimationView

/**
 * 利用Lottie实现下导航动画
 */
class AnimationRadioView(context: Context, attributeSet: AttributeSet) :
    LottieAnimationView(context, attributeSet), Checkable {

    private var checked: Boolean = false

    override fun isChecked(): Boolean {
        return this.checked
    }

    override fun toggle() {
        isChecked = !checked
    }

    override fun setChecked(checked: Boolean) {
        try {
            if (this.checked != checked) {
                this.checked = checked
                if (isAnimating) {
                    cancelAnimation()
                }
                if (checked) {
                    if (speed < 0.0f) {
                        reverseAnimationSpeed()
                    }
                } else {
                    if (speed > 0.0f) {
                        reverseAnimationSpeed()
                    }
                }
                playAnimation()
            }
        } catch (ex: Exception) {

        }
    }

}