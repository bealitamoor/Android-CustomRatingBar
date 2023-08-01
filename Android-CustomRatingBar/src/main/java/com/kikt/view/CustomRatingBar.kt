package com.kikt.view

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewGroup
import android.widget.ImageView

/**
 * Updated by @bealitamoor on 2023/08/01.
 * Simple Custom Rating bar
 */
class CustomRatingBar @JvmOverloads constructor(
    private val mContext: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ViewGroup(
    mContext, attrs, defStyleAttr
) {
    private var max = 0
    private var padding = 0
    private var starWidth = 0
    private var starHeight = 0

    private var childParams: LayoutParams? = null

    private var stars = 0f
    private var lastStars = 0f
    private var minStar = 0f

    private  var mEmptyStar = 0
    private  var mFullStar = 0
    private  var mHalfStar = 0

    private fun initView(a: TypedArray) {

        max = a.getInteger(R.styleable.CustomRatingBar_maxStar, 5)
        padding = a.getDimension(R.styleable.CustomRatingBar_padding, 10f).toInt()
        starWidth = a.getDimension(R.styleable.CustomRatingBar_starWidth, 40f).toInt()
        starHeight = a.getDimension(R.styleable.CustomRatingBar_starHeight, 40f).toInt()
        minStar = a.getFloat(R.styleable.CustomRatingBar_minStar, 0f)
        stars = a.getFloat(R.styleable.CustomRatingBar_currentStar, 0f) * 2
        mEmptyStar = a.getResourceId(R.styleable.CustomRatingBar_emptyStar, STAR_EMPTY)
        mHalfStar = a.getResourceId(R.styleable.CustomRatingBar_halfStar, STAR_HALF)
        mFullStar = a.getResourceId(R.styleable.CustomRatingBar_fullStar, STAR_FULL)
        isCanChange = a.getBoolean(R.styleable.CustomRatingBar_canChange, true)

        for (i in 0 until max) {
            val child = createChild()
            addView(child)
            list.add(child)
        }

    }

    private var list: MutableList<ImageView> = ArrayList()
    private fun createChild(): ImageView {
        val imageView = ImageView(mContext)
        imageView.setImageResource(R.drawable.star_empty)

        childParams = generateDefaultLayoutParams()
        childParams?.width = starWidth
        childParams?.height = starHeight
        imageView.layoutParams = childParams

        return imageView
    }

    private var isCanChange = false
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> return if (isCanChange) {
                val x = event.x
                val current = checkX(x)
                stars = fixStars(current)
                checkState()
                true
            } else {
                false
            }
        }
        return true
    }

    interface OnStarChangeListener {
        fun onStarChange(ratingBar: CustomRatingBar?, star: Float)
    }

    var onStarChangeListener: OnStarChangeListener? = null

    init {
        val a = mContext.obtainStyledAttributes(attrs, R.styleable.CustomRatingBar)
        initView(a)
        a.recycle()
    }

    private fun checkState() {
        if (lastStars != stars) {
            lastStars = stars
            if (onStarChangeListener != null) {
                onStarChangeListener!!.onStarChange(this, stars / 2)
            }
            setView()
        }
    }

    private fun setView() {
        if (stars < minStar * 2) {
            stars = minStar * 2
        }
        val stars = stars.toInt()
        if (stars % 2 == 0) {
            for (i in 0 until max) {
                if (i < stars / 2) {
                    setFullView(list[i])
                } else {
                    setEmptyView(list[i])
                }
            }
        } else {
            for (i in 0 until max) {
                if (i < stars / 2) {
                    setFullView(list[i])
                } else if (i == stars / 2) {
                    setHalfView(list[i])
                } else {
                    setEmptyView(list[i])
                }
            }
        }
    }

    private fun setEmptyView(view: ImageView) {
        view.setImageResource(mEmptyStar)
    }

    private fun setHalfView(view: ImageView) {
        view.setImageResource(mHalfStar)
    }

    private fun setFullView(view: ImageView) {
        view.setImageResource(mFullStar)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        setView()
    }

    private fun fixStars(current: Int): Float {
        if (current > max * 2) {
            return (max * 2).toFloat()
        } else if (current < minStar * 2) {
            return minStar * 2
        }
        return current.toFloat()
    }

    private fun checkX(x: Float): Int {
        val width = width
        val per = width / max / 2
        return (x / per).toInt() + 1
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        measureChildren(widthMeasureSpec, heightMeasureSpec)
        val childCount = childCount
        var width = 0
        var height = 0
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            val measuredWidth = child.measuredWidth
            val measuredHeight = child.measuredHeight
            width += measuredWidth
            height = measuredHeight
            if (i != childCount - 1) {
                width += padding
            }
        }
        setMeasuredDimension(width, height)
    }

    override fun generateLayoutParams(attrs: AttributeSet): MarginLayoutParams {
        return MarginLayoutParams(context, attrs)
    }

    override fun onLayout(changed: Boolean, paraml: Int, paramt: Int, paramr: Int, paramb: Int) {

        var l = paraml
        var t = paramt
        var r = paramr
        var b = paramb

        t = 0
        l = 0

        val childCount = childCount
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            r = l + child.measuredWidth
            b = t + child.measuredHeight
            child.layout(l, t, r, b)
            l = r + padding
        }
    }

    companion object {
        val STAR_EMPTY = R.drawable.star_empty
        val STAR_HALF = R.drawable.star_half
        val STAR_FULL = R.drawable.star_full
    }
}