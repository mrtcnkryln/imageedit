package com.mrtcnkryln.image_edit.ui.view.custom

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.util.AttributeSet
import android.graphics.drawable.Drawable
import com.mrtcnkryln.image_edit.util.MultiTouchGestureDetector
import android.view.MotionEvent
import android.view.View
import com.mrtcnkryln.image_edit.R
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.Color


class CustomImageView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private var mMultiTouchGestureDetector: MultiTouchGestureDetector? = null
    private var mIcon: Drawable? = null

    private var mScaleFactor = 1.0f
    private var mOffsetX = 0.0f
    private var mOffsetY = 0.0f
    private var mRotation = 0.0f

    init {
        mMultiTouchGestureDetector =
            MultiTouchGestureDetector(context!!, MultiTouchGestureDetectorListener())
        iconClear()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        mMultiTouchGestureDetector!!.onTouchEvent(event)
        return true
    }

    fun setIcon(icon: Bitmap) {
        val drawable = BitmapDrawable(resources, icon)
        this.mIcon = drawable
        this.mIcon!!.setBounds(0, 0, mIcon!!.intrinsicWidth, mIcon!!.intrinsicWidth)
        invalidate()

    }

    fun iconClear() {
        val transparentDrawable = ColorDrawable(Color.TRANSPARENT)
        this.mIcon = transparentDrawable
        this.mIcon!!.setBounds(0, 0, mIcon!!.intrinsicWidth, mIcon!!.intrinsicWidth)
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        canvas.save()
        // move to center
        canvas.translate(
            ((measuredWidth - mIcon!!.intrinsicWidth) / 2).toFloat(),
            ((measuredHeight - mIcon!!.intrinsicWidth) / 2).toFloat()
        )

        // transform
        canvas.save()
        canvas.translate(mOffsetX, mOffsetY)
        canvas.scale(
            mScaleFactor,
            mScaleFactor,
            (mIcon!!.intrinsicWidth / 2).toFloat(),
            (mIcon!!.intrinsicWidth / 2).toFloat()
        )
        canvas.rotate(
            mRotation,
            (mIcon!!.intrinsicWidth / 2).toFloat(),
            (mIcon!!.intrinsicWidth / 2).toFloat()
        )
        mIcon!!.draw(canvas)
        canvas.restore()

        canvas.restore()
    }


    private inner class MultiTouchGestureDetectorListener :
        MultiTouchGestureDetector.SimpleOnMultiTouchGestureListener() {

        override fun onScale(detector: MultiTouchGestureDetector) {
            mScaleFactor *= detector.getScale()
            mScaleFactor = Math.max(1.0f, Math.min(mScaleFactor, 5.0f))

            invalidate()
        }

        override fun onMove(detector: MultiTouchGestureDetector) {
            mOffsetX += detector.getMoveX()
            mOffsetY += detector.getMoveY()

            invalidate()
        }

        override fun onRotate(detector: MultiTouchGestureDetector) {
            mRotation += detector.getRotation()

            invalidate()
        }
    }

}