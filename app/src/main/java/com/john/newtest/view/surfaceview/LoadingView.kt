package com.john.newtest.view.surfaceview

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.RelativeLayout
import com.john.newtest.R

/**
 * Created by JohnZh on 2020/11/24
 *
 *
 *
 */
class LoadingView(context: Context?, attrs: AttributeSet?) : RelativeLayout(context, attrs) {

    companion object {
        private const val DEFAULT_ANIMATION_WIDTH_DP = 100f
        private const val DEFAULT_ANIMATION_HEIGHT_DP = 100f
    }

    private val frameSurfaceView = FrameSurfaceView(context)
    private val layoutParams = LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT).apply {
        addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE)
    }

    private var drawWidth = 0
    private var drawHeight = 0
    private var frameDurationMillis = 15
    private var repeat = true
    private lateinit var resArray: IntArray

    init {
        val typedArray = context?.obtainStyledAttributes(attrs, R.styleable.LoadingView)

        typedArray?.let {
            drawWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_ANIMATION_WIDTH_DP,
                    resources.displayMetrics).toInt()
            drawHeight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_ANIMATION_HEIGHT_DP,
                    resources.displayMetrics).toInt()

            frameDurationMillis = typedArray.getInt(R.styleable.LoadingView_frameDurationMills, frameDurationMillis)
            drawWidth = typedArray.getDimensionPixelOffset(R.styleable.LoadingView_drawWidth, drawWidth)
            drawHeight = typedArray.getDimensionPixelOffset(R.styleable.LoadingView_drawWidth, drawHeight)
            repeat = typedArray.getBoolean(R.styleable.LoadingView_repeat, repeat)

            val resArrayId = typedArray.getResourceId(R.styleable.LoadingView_frameResArray, -1)
            if (resArrayId != -1) {
                val typeArray = resources.obtainTypedArray(resArrayId)
                resArray = IntArray(typeArray.length()) {
                    typeArray.getResourceId(it, 0)
                }
                frameSurfaceView.setBitmaps(resArray.toCollection(ArrayList()))
            }

            frameSurfaceView.setFrameDuration(frameDurationMillis)
            frameSurfaceView.setDrawWidth(drawWidth)
            frameSurfaceView.setDrawHeight(drawHeight)
            frameSurfaceView.setRepeat(repeat)

            addView(frameSurfaceView, layoutParams)
        }

        typedArray?.recycle()
    }

    fun show() {
        visibility = VISIBLE
        frameSurfaceView.start()
    }

    fun hide() {
        visibility = GONE
        frameSurfaceView.stop()
    }
}