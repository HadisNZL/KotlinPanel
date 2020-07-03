package com.kotlin.panel.widget

import android.R
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.graphics.drawable.RippleDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.StateListDrawable
import android.graphics.drawable.shapes.OvalShape
import android.os.Build
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.FloatRange
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import kotlin.math.roundToInt

class CirecleColorView(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {

    private var borderWidthSmall = 0
    private var borderWidthLarge = 0

    private var outerPaint: Paint
    private var whitePaint: Paint
    private var innerPaint: Paint
    private var selected = false

    init {
        val r = resources
        borderWidthSmall = TypedValue
            .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3f, r.displayMetrics).toInt()
        borderWidthLarge = TypedValue
            .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5f, r.displayMetrics).toInt()

        whitePaint = Paint()
        whitePaint.isAntiAlias = true
        whitePaint.color = Color.WHITE
        innerPaint = Paint()
        innerPaint.isAntiAlias = true
        outerPaint = Paint()
        outerPaint.isAntiAlias = true

        update(Color.DKGRAY)
        setWillNotDraw(false)
    }

    @ColorInt
    private fun translucentColor(color: Int): Int {
        val factor = 0.7f
        val alpha = (Color.alpha(color) * factor).roundToInt()
        val red = Color.red(color)
        val green = Color.green(color)
        val blue = Color.blue(color)
        return Color.argb(alpha, red, green, blue)
    }

    @ColorInt
    fun shiftColor(
        @ColorInt color: Int, @FloatRange(from = 0.0, to = 2.0) by: Float
    ): Int {
        if (by == 1f) {
            return color
        }
        val hsv = FloatArray(3)
        Color.colorToHSV(color, hsv)
        hsv[2] *= by // value component
        return Color.HSVToColor(hsv)
    }

    private fun shiftColorDown(@ColorInt color: Int): Int {
        return shiftColor(color, 0.9F)
    }

    private fun shiftColorUp(@ColorInt color: Int): Int {
        return shiftColor(color, 1.1F)
    }

    private fun update(@ColorInt color: Int) {
        innerPaint.color = color
        outerPaint.color = shiftColorDown(color)
        val selector: Drawable = createSelector(color)
        foreground = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val states =
                arrayOf(intArrayOf(R.attr.state_pressed))
            val colors =
                intArrayOf(shiftColorUp(color))
            val rippleColors = ColorStateList(states, colors)
            RippleDrawable(rippleColors, selector, null)
        } else {
            selector
        }
    }

    override fun setBackgroundColor(color: Int) {
        update(color)
        requestLayout()
        invalidate()
    }

    override fun setBackgroundResource(@ColorRes resid: Int) {
        setBackgroundColor(ContextCompat.getColor(context, resid))
    }

    override fun setBackground(background: Drawable?) {
        throw IllegalStateException("Cannot use setBackground() on CircleView.")
    }

    override fun setBackgroundDrawable(background: Drawable?) {
        throw IllegalStateException("Cannot use setBackgroundDrawable() on CircleView.")
    }

    override fun setActivated(activated: Boolean) {
        throw IllegalStateException("Cannot use setActivated() on CircleView.")
    }

    override fun setSelected(selected: Boolean) {
        this.selected = selected
        requestLayout()
        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(measuredWidth, measuredWidth)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val outerRadius = measuredWidth / 2
        if (selected) {
            val whiteRadius = outerRadius - borderWidthLarge
            val innerRadius = whiteRadius - borderWidthSmall
            canvas.drawCircle(
                measuredWidth / 2.toFloat(),
                measuredHeight / 2.toFloat(),
                outerRadius.toFloat(),
                outerPaint
            )
            canvas.drawCircle(
                measuredWidth / 2.toFloat(),
                measuredHeight / 2.toFloat(),
                whiteRadius.toFloat(),
                whitePaint
            )
            canvas.drawCircle(
                measuredWidth / 2.toFloat(),
                measuredHeight / 2.toFloat(),
                innerRadius.toFloat(),
                innerPaint
            )
        } else {
            canvas.drawCircle(
                measuredWidth / 2.toFloat(),
                measuredHeight / 2.toFloat(),
                outerRadius.toFloat(),
                innerPaint
            )
        }
    }

    private fun createSelector(@ColorInt color: Int): Drawable {
        val darkerCircle = ShapeDrawable(OvalShape())
        darkerCircle.paint.color = translucentColor(
            shiftColorUp(color)
        )
        val stateListDrawable = StateListDrawable()
        stateListDrawable.addState(intArrayOf(R.attr.state_pressed), darkerCircle)
        return stateListDrawable
    }

    fun showHint(color: Int) {
        val screenPos = IntArray(2)
        val displayFrame = Rect()
        getLocationOnScreen(screenPos)
        getWindowVisibleDisplayFrame(displayFrame)
        val context = context
        val width = width
        val height = height
        val midy = screenPos[1] + height / 2
        var referenceX = screenPos[0] + width / 2
        if (ViewCompat.LAYOUT_DIRECTION_LTR == ViewCompat.getLayoutDirection(this)) {
            val screenWidth = context.resources.displayMetrics.widthPixels
            referenceX = screenWidth - referenceX // mirror
        }
        val cheatSheet = Toast
            .makeText(
                context,
                String.format("#%06X", 0xFFFFFF and color),
                Toast.LENGTH_SHORT
            )
        if (midy < displayFrame.height()) {
            // Show along the top; follow action buttons
            cheatSheet.setGravity(
                Gravity.TOP or GravityCompat.END, referenceX,
                screenPos[1] + height - displayFrame.top
            )
        } else {
            // Show along the bottom center
            cheatSheet.setGravity(Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL, 0, height)
        }
        cheatSheet.show()
    }
}