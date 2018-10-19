package com.visally.hamburgertogglemenu

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.support.v4.content.res.ResourcesCompat
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import android.view.animation.OvershootInterpolator

/**
 * Created by Mahdi_ZareTahghighDoost(ZTD)
 *  on 10/12/2018.
 */

class HamburgerToggleMenu(context: Context, var attrs: AttributeSet?) : View(context, attrs) {

    private var mWidth: Float = -1f
    private var mHeight: Float = -1f
    private var mCenterX: Float = -1f
    private var mCenterY: Float = -1f

    private var factor: Float = 0f
    private var factor1: Float = 0f
    private var factor2: Float = 0f
    private var factor3: Float = 0f
    private var factor4: Float = 0f

    private var mMenuColor: Int = -1
    private var mStrokeWidth: Float = -1f
    private var mDuration : Int = -1

    private var animationFlag = true


    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.HamburgerToggleMenu)
        mMenuColor = typedArray.getColor(R.styleable.HamburgerToggleMenu_hti_icon_color,
                ResourcesCompat.getColor(resources, android.R.color.black, null))
        mStrokeWidth = typedArray.getDimension(R.styleable.HamburgerToggleMenu_hti_stroke_width, context.dpToPx(5))
        mDuration = typedArray.getInt(R.styleable.HamburgerToggleMenu_hti_duration, 500)
        typedArray.recycle()

//        setHamBurgerMenuAnimator()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        mWidth = when {
            MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.UNSPECIFIED -> getDesiredWidth()
            MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.EXACTLY -> MeasureSpec.getSize(widthMeasureSpec).toFloat()
            MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.AT_MOST -> Math.min(getDesiredWidth(), MeasureSpec.getSize(widthMeasureSpec).toFloat())
            else -> getDesiredWidth()
        }
        mHeight = when {
            MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.UNSPECIFIED -> getDesiredHeight()
            MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.EXACTLY -> MeasureSpec.getSize(heightMeasureSpec).toFloat()
            MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.AT_MOST -> Math.min(getDesiredHeight(), MeasureSpec.getSize(heightMeasureSpec).toFloat())
            else -> getDesiredHeight()
        }
        mCenterX = mWidth / 2
        mCenterY = mHeight / 2
        setMeasuredDimension(mWidth.toInt(), mHeight.toInt())
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if(factor==0f)
        {
            factor = mWidth-paddingRight
            factor1 = paddingTop.toFloat()
            factor2 = mHeight-paddingTop
            factor3 = paddingTop.toFloat()
            factor4 = mHeight-paddingBottom
        }
        drawHamburgerMenu(canvas)
    }

    private fun setHamBurgerMenuAnimator() {

        var mAnimator =
                if (animationFlag)
                    ValueAnimator.ofFloat(1f, 0f)
                else
                    ValueAnimator.ofFloat(0f, 1f)
        animationFlag=!animationFlag

        mAnimator.interpolator = OvershootInterpolator()
        mAnimator.duration = mDuration.toLong()
        mAnimator.addUpdateListener {
            factor1 = it.animatedValue as Float
            invalidate()
        }
        mAnimator.start()
    }

    private fun setHamBurgerMenuAnimator2() {

        var mAnimator =
                if (animationFlag)
                    ObjectAnimator.ofFloat(mWidth-paddingRight, mCenterX)
                else
                    ObjectAnimator.ofFloat(mCenterX, mWidth-paddingRight)

        var mAnimator1 =
                if (animationFlag)
                    ObjectAnimator.ofFloat(paddingTop.toFloat(), mCenterY)
                else
                    ObjectAnimator.ofFloat(mCenterY, paddingTop.toFloat())

        var mAnimator2 =
                if (animationFlag)
                    ObjectAnimator.ofFloat(mHeight-paddingBottom, mCenterY)
                else
                    ObjectAnimator.ofFloat(mCenterY, mHeight-paddingBottom)

        var mAnimator3 =
                if (animationFlag)
                    ObjectAnimator.ofFloat(paddingTop.toFloat(), mCenterY)
                else
                    ObjectAnimator.ofFloat(mCenterY, paddingTop.toFloat())

        var mAnimator4 =
                if (animationFlag)
                    ObjectAnimator.ofFloat(mHeight-paddingBottom, mCenterY)
                else
                    ObjectAnimator.ofFloat(mCenterY, mHeight-paddingBottom)

        animationFlag=!animationFlag
        var animatorSet= AnimatorSet()
        animatorSet.playSequentially(mAnimator1,mAnimator3,mAnimator4,mAnimator2,mAnimator)

        mAnimator.interpolator = OvershootInterpolator()
        mAnimator.duration = mDuration.toLong()
        mAnimator.addUpdateListener {
            factor = it.animatedValue as Float
            invalidate()
        }
        mAnimator1.interpolator = LinearInterpolator()
        mAnimator1.duration = mDuration.toLong()
        mAnimator1.addUpdateListener {
            factor1 = it.animatedValue as Float
            invalidate()
        }
        mAnimator2.interpolator = LinearInterpolator()
        mAnimator2.duration = mDuration.toLong()
        mAnimator2.addUpdateListener {
            factor2 = it.animatedValue as Float
            invalidate()
        }

        mAnimator3.interpolator = LinearInterpolator()
        mAnimator3.duration = mDuration.toLong()
        mAnimator3.addUpdateListener {
            factor3 = it.animatedValue as Float
            invalidate()
        }

        mAnimator4.interpolator = LinearInterpolator()
        mAnimator4.duration = mDuration.toLong()
        mAnimator4.addUpdateListener {
            factor4 = it.animatedValue as Float
            invalidate()
        }

        animatorSet.start()
    }


    private fun drawCloseHamburgerMenu(canvas: Canvas?) {
        val paint: Paint = getPaint()
        if (paddingTop + (mCenterY * factor1) <= mCenterY) {
            canvas?.drawLine(paddingLeft.toFloat(), paddingTop.toFloat(), mCenterX, paddingTop + (mCenterY * factor1), paint)
            canvas?.drawLine(mWidth - paddingRight, paddingTop.toFloat(), mCenterX, paddingTop + (mCenterY * factor1), paint)
        } else {
            canvas?.drawLine(paddingLeft.toFloat(), paddingTop.toFloat(), mCenterX, mCenterY, paint)
            canvas?.drawLine(mWidth - paddingRight, paddingTop.toFloat(), mCenterX, mCenterY, paint)
        }

        if (paddingLeft + (mCenterX * factor1) <= mCenterX)
            canvas?.drawLine((paddingLeft) + (mCenterX * factor1), mCenterY, (mWidth - paddingRight) - (mCenterX * factor1), mCenterY, paint)

        if ((mHeight - paddingBottom) - (mCenterY * factor1) >= mCenterY) {
            canvas?.drawLine(paddingLeft.toFloat(), mHeight - paddingBottom, mCenterX, (mHeight - paddingBottom) - (mCenterY * factor1), paint)
            canvas?.drawLine(mWidth - paddingRight, mHeight - paddingBottom, mCenterX, (mHeight - paddingBottom) - (mCenterY * factor1), paint)
        } else {
            canvas?.drawLine(paddingLeft.toFloat(), mHeight - paddingBottom, mCenterX, mCenterY, paint)
            canvas?.drawLine(mWidth - paddingRight, mHeight - paddingBottom, mCenterX, mCenterY, paint)
        }
    }

    private fun drawHamburgerMenu(canvas: Canvas?){
        val paint = getPaint()
        canvas?.drawLine(paddingLeft.toFloat(),paddingTop.toFloat(),mCenterX,factor1,paint)
        canvas?.drawLine(mWidth-paddingRight,paddingTop.toFloat(),mCenterX,factor3,paint)

        canvas?.drawLine(mCenterX,mCenterY,mWidth-factor,mCenterY,paint)
        canvas?.drawLine(mCenterX,mCenterY,factor,mCenterY,paint)

        canvas?.drawLine(paddingRight.toFloat(),mHeight-paddingBottom,mCenterX,factor2,paint)
        canvas?.drawLine(mWidth-paddingRight,mHeight-paddingBottom,mCenterX,factor4,paint)
    }

    private fun getPaint(): Paint {
        val paint = Paint()
        paint.color = mMenuColor
        paint.strokeWidth = mStrokeWidth
        paint.isAntiAlias = true
        paint.style = Paint.Style.FILL_AND_STROKE
        paint.strokeCap = Paint.Cap.ROUND
        return paint
    }

    private fun getDesiredWidth(): Float {
        return context.dpToPx(40)
    }

    private fun getDesiredHeight(): Float {
        return context.dpToPx(40)
    }

    fun toggleMenu() {
        setHamBurgerMenuAnimator2()
    }
}
