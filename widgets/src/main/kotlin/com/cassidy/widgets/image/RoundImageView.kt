package com.cassidy.widgets.image

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapShader
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Outline
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.Shader
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewOutlineProvider
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatImageView
import com.cassidy.widgets.R
import com.gocantar.cassidy.tools.extensions.color
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * @author Gonzalo Cantarero Pérez
 */

open class RoundImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    private var isInitialize: Boolean = false
    private var isHighLightEnable: Boolean = false
    private var isTapped: Boolean = false

    private var highLightColor: Int = color(R.color.highlight)
    private var strokeColor: Int = color(R.color.transparent)

    private var strokeWidth: Float = 0F

    private var bitmap: Bitmap? = null

    private var bitmapShader: Shader? = null
    private val shaderMatrix: Matrix = Matrix()

    private val bitmapBounds: RectF = RectF()
    private val strokeBounds: RectF = RectF()

    private val tapPain: Paint
    private val strokePaint: Paint
    private val bitmapPaint: Paint

    init {
        isInitialize = true
        attrs?.let { initializeAttributes(it, defStyleAttr) }
        bitmapPaint = configureBitmapPaint()
        strokePaint = configureStrokePaint()
        tapPain = configureTappedPaint()
        configureBitmap()
    }

    private fun initializeAttributes(attributes: AttributeSet, defStyleAttr: Int) {
        val styles = context.obtainStyledAttributes(
            attributes, R.styleable.RoundImageView, defStyleAttr, 0
        )
        with(styles) {
            strokeColor = getColor(R.styleable.RoundImageView_strokeColor, strokeColor)
            strokeWidth = getDimension(R.styleable.RoundImageView_strokeWidth, strokeWidth)
            highLightColor = getColor(R.styleable.RoundImageView_highlightColor, highLightColor)
            isHighLightEnable = getBoolean(R.styleable.RoundImageView_isHighlightEnable, false)
            recycle()
        }
    }

    override fun setImageResource(@DrawableRes resId: Int) {
        super.setImageResource(resId)
        configureBitmap()
    }

    override fun setImageDrawable(drawable: Drawable?) {
        super.setImageDrawable(drawable)
        configureBitmap()
    }

    override fun setImageBitmap(bm: Bitmap?) {
        super.setImageBitmap(bm)
        configureBitmap()
    }

    override fun setImageURI(uri: Uri?) {
        super.setImageURI(uri)
        configureBitmap()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        updateViewBounds(bitmapBounds)
        updateStrokeBounds(bitmapBounds)
        updateBitmapSize()
    }

    override fun onDraw(canvas: Canvas) {
        drawBitmap(canvas)
        drawStroke(canvas)
        drawHighlight(canvas)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        super.onTouchEvent(event)
        return when (event.action) {
            MotionEvent.ACTION_DOWN -> event.handleDown()
            MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> event.handleUp()
            else -> false
        }
    }

    protected fun drawHighlight(canvas: Canvas) {
        if (isHighLightEnable && isTapped) {
            canvas.drawOval(bitmapBounds, tapPain)
        }
    }

    protected fun drawStroke(canvas: Canvas) {
        if (strokePaint.strokeWidth > 0F) {
            canvas.drawOval(strokeBounds, strokePaint)
        }
    }

    private fun drawBitmap(canvas: Canvas) {
        canvas.drawOval(bitmapBounds, bitmapPaint)
    }

    protected fun updateViewBounds(bounds: RectF) {
        val contentWidth = width - paddingLeft - paddingRight
        val contentHeight = height - paddingLeft - paddingRight
        val diameter = min(contentWidth, contentHeight)
        val (left, top) = getCircleBoundsOrigin(contentWidth, contentHeight)
        bounds.set(left, top, left + diameter, top + diameter)
    }

    private fun configureBitmap() {
        if (isInitialize.not()) return
        bitmap = drawable.bitmap()
        bitmap?.let {
            bitmapShader = BitmapShader(it, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
            bitmapPaint.shader = bitmapShader
        }
        updateBitmapSize()
    }

    private fun configureBitmapPaint(): Paint {
        return Paint(Paint.ANTI_ALIAS_FLAG)
    }

    private fun configureStrokePaint(): Paint {
        return Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
            strokeWidth = this@RoundImageView.strokeWidth
            color = strokeColor
        }
    }

    private fun configureTappedPaint(): Paint {
        return Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.FILL
            color = highLightColor
        }
    }

    private fun updateStrokeBounds(bounds: RectF) {
        val halfStrokeWidth = strokeWidth / 2
        strokeBounds.set(bounds)
        strokeBounds.inset(halfStrokeWidth, halfStrokeWidth)
        outlineProvider = RoundImageViewOutlineProvider(strokeBounds)
    }

    private fun getCircleBoundsOrigin(width: Int, height: Int): Pair<Float, Float> {
        var left = paddingLeft.toFloat()
        var top = paddingTop.toFloat()
        if (width > height) left += (width - height) / 2F else top += (height - width) / 2F
        return Pair(left, top)
    }

    private fun updateBitmapSize() {
        var dx: Float
        var dy: Float
        var scale: Float
        bitmap?.let {
            if (it.width < it.height) {
                scale = bitmapBounds.width() / it.width.toFloat()
                dx = bitmapBounds.left
                dy = bitmapBounds.top - (it.height * scale / 2f) + bitmapBounds.width() / 2f
            } else {
                scale = bitmapBounds.height() / it.height.toFloat()
                dx = bitmapBounds.left - (it.width * scale / 2f) + bitmapBounds.width() / 2f
                dy = bitmapBounds.top
            }
            shaderMatrix.setScale(scale, scale)
            shaderMatrix.postTranslate(dx, dy)
            bitmapShader?.setLocalMatrix(shaderMatrix)
        }
    }

    private fun Drawable?.bitmap(): Bitmap? {
        val drawable = this ?: return null
        (drawable as? BitmapDrawable)?.run { return bitmap }
        return with(drawable) {
            val bitmapConfiguration = Bitmap.Config.ARGB_8888
            val bitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, bitmapConfiguration)
            val canvas = Canvas(bitmap)
            setBounds(0, 0, canvas.width, canvas.height)
            draw(canvas)
            bitmap
        }
    }

    private fun MotionEvent.handleDown(): Boolean {
        if (isInCircle().not()) return false
        isTapped = true
        invalidate()
        return isClickable
    }

    private fun MotionEvent.handleUp(): Boolean {
        isTapped = false
        invalidate()
        return isInCircle()
    }

    private fun MotionEvent.isInCircle(): Boolean {
        val centerX = bitmapBounds.centerX().toDouble()
        val centerY = bitmapBounds.centerY().toDouble()
        val distanceToCenter = sqrt((centerX - x).pow(2.0) + (centerY - y).pow(2.0))
        return distanceToCenter <= bitmapBounds.width() / 2
    }

    inner class RoundImageViewOutlineProvider(rect: RectF) : ViewOutlineProvider() {

        private val mRect: Rect = Rect(
            rect.left.toInt(),
            rect.top.toInt(),
            rect.right.toInt(),
            rect.bottom.toInt()
        )

        override fun getOutline(view: View?, outline: Outline) {
            outline.setOval(mRect)
        }
    }
}