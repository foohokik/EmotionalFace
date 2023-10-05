package com.example.emotionalface

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View


class EmoFace @JvmOverloads constructor (
    context: Context, attrs: AttributeSet?, defStyleAttr: Int = 0, defStyleRes: Int = 0
) : View(context, attrs, defStyleAttr) {

    // Paint object for coloring and styling
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    // Some colors for the face background, eyes and mouth.
    private var faceColor = DEFAULT_FACE_COLOR
    private var eyesColor = DEFAULT_EYES_COLOR
    private var mouthColor = DEFAULT_MOUTH_COLOR
    private var borderColor = DEFAULT_BORDER_COLOR

    // Face border width in pixels
    private var borderWidth = DEFAULT_BORDER_WIDTH
    // View size in pixels
    private var size = 0
    private val mouthPath = Path()

    var happinessState = HAPPY
        set(state) {
            field = state
            invalidate()
        }

    init {
        paint.isAntiAlias = true
        setupAttributes(attrs)
    }

    private fun setupAttributes(attrs: AttributeSet?) {

        val typedArray =context.theme.obtainStyledAttributes(attrs, R.styleable.EmoFace, 0,0)
        happinessState = typedArray.getInt(R.styleable.EmoFace_state, HAPPY.toInt()).toLong()
        faceColor = typedArray.getColor(R.styleable.EmoFace_faceColor, DEFAULT_FACE_COLOR)
        eyesColor = typedArray.getColor(R.styleable.EmoFace_eyesColor, DEFAULT_EYES_COLOR)
        mouthColor = typedArray.getColor(R.styleable.EmoFace_mouthColor, DEFAULT_MOUTH_COLOR)
        borderColor = typedArray.getColor(R.styleable.EmoFace_borderColor, DEFAULT_BORDER_COLOR)
        borderWidth = typedArray.getDimension(R.styleable.EmoFace_borderWidth, DEFAULT_BORDER_WIDTH)

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        drawFaceBackground(canvas)
        drawEyes(canvas)
        drawMouth(canvas)

    }

    private fun drawFaceBackground (canvas: Canvas?) {

        paint.color = faceColor
        paint.style = Paint.Style.FILL

        val radius = size/2f
        canvas?.drawCircle(radius, radius, radius, paint)

        paint.color = borderColor
        paint.style = Paint.Style.STROKE

        canvas?.drawCircle(radius, radius, radius - borderWidth/2, paint)

    }

    private fun drawEyes (canvas: Canvas?) {

        paint.color = eyesColor
        paint.style = Paint.Style.FILL

        val leftRect = RectF( size*0.25f,size*0.5f, size*0.4f, size*0.2f)
        canvas?.drawOval(leftRect,paint)

        val rightRect = RectF( size*0.75f,size*0.5f, size*0.6f, size*0.2f)
        canvas?.drawOval(rightRect,paint)
    }

    private fun drawMouth (canvas: Canvas?) {

        mouthPath.reset()
        mouthPath.moveTo(size*0.22f, size*0.6f)

        if (happinessState== HAPPY) {
            mouthPath.quadTo(size * 0.5f, size * 0.8f, size * 0.78f, size * 0.6f)
            mouthPath.quadTo(size * 0.5f, size * 0.9f, size * 0.22f, size * 0.6f)
        } else {
            mouthPath.quadTo(size * 0.5f, size * 0.4f, size * 0.78f, size * 0.6f)
            mouthPath.quadTo(size * 0.5f, size * 0.5f, size * 0.22f, size * 0.6f)
        }
        paint.color = mouthColor
        paint.style = Paint.Style.FILL

        canvas?.drawPath(mouthPath, paint)

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        size = Math.min(measuredWidth, measuredHeight)
        setMeasuredDimension(size, size)
    }

    companion object {
        private const val DEFAULT_FACE_COLOR = Color.YELLOW
        private const val DEFAULT_EYES_COLOR = Color.BLACK
        private const val DEFAULT_MOUTH_COLOR = Color.BLACK
        private const val DEFAULT_BORDER_COLOR = Color.BLACK
        private const val DEFAULT_BORDER_WIDTH = 4.0f

        const val HAPPY = 0L
        const val SAD = 1L
    }

    override fun onSaveInstanceState(): Parcelable? {

        val bundle = Bundle()
        bundle.putLong("happystate", happinessState)
        bundle.putParcelable("superState", super.onSaveInstanceState())
        return bundle
    }

    override fun onRestoreInstanceState(state: Parcelable?) {

        var viewS = state
        if (viewS is Bundle) {
            happinessState = viewS.getLong("happystate", HAPPY)
            viewS = viewS.getParcelable("superState")
        }
        super.onRestoreInstanceState(viewS)
    }

}
