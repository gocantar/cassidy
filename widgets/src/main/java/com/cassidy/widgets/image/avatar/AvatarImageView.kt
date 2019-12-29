package com.cassidy.widgets.image.avatar

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.util.AttributeSet
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.cassidy.widgets.R
import com.cassidy.widgets.extensions.color
import com.cassidy.widgets.extensions.dimension
import com.cassidy.widgets.image.CustomRoundImageView
import com.cassidy.widgets.image.avatar.models.Avatar

class AvatarImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CustomRoundImageView(context, attrs, defStyleAttr) {

    private val viewModel = AvatarImageViewModel()

    private var text: String? = ""

    private var textColor: Int = color(R.color.white)
    private var backgroundAvatarColor: Int = color(R.color.grey)

    private var textFontSize: Float = dimension(R.dimen.default_avatar_text_size)

    private val textPaint: Paint
    private val backgroundPaint: Paint

    private val backgroundBounds: RectF = RectF()

    private val textBounds: Rect = Rect()

    init {
        attrs?.let { initializeAttributes(it, defStyleAttr) }
        textPaint = configureTextPaint()
        backgroundPaint = configureBackgroundPaint()
        updateTextBounds()
    }

    private fun initializeAttributes(attributes: AttributeSet, defStyleAttr: Int) {
        val styles = context.obtainStyledAttributes(
            attributes, R.styleable.AvatarImageView, defStyleAttr, 0
        )
        with(styles) {
            textFontSize = getDimension(R.styleable.AvatarImageView_textSize, textFontSize)
            textColor = getColor(R.styleable.AvatarImageView_textColor, textColor)
            backgroundAvatarColor =
                getColor(R.styleable.AvatarImageView_backgroundAvatarColor, backgroundAvatarColor)
            viewModel.configure(getInt(R.styleable.AvatarImageView_avatarStyle, 0))
            recycle()
        }
    }

    fun setAvatar(avatar: Avatar) {
        viewModel.setAvatar(avatar)
    }

    override fun onLifecycleOwnerAttached(lifecycleOwner: LifecycleOwner) {
        viewModel.text.observe(lifecycleOwner, Observer {
            text = it
            updateTextBounds()
            invalidate()
        })
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        updateViewBounds(backgroundBounds)
    }

    override fun onDraw(canvas: Canvas) {
        text?.run {
            drawBackground(canvas)
            drawText(canvas)
            drawHighlight(canvas)
            drawStroke(canvas)
            return
        }
        super.onDraw(canvas)
    }

    private fun drawBackground(canvas: Canvas) {
        canvas.drawOval(backgroundBounds, backgroundPaint)
    }

    private fun drawText(canvas: Canvas) {
        val textBottom = backgroundBounds.centerY() - textBounds.exactCenterY()
        canvas.drawText(text!!, backgroundBounds.centerX(), textBottom, textPaint)
    }

    private fun configureTextPaint(): Paint {
        return Paint(Paint.ANTI_ALIAS_FLAG).apply {
            textAlign = Paint.Align.CENTER
            textSize = textFontSize
            color = textColor
        }
    }

    private fun configureBackgroundPaint(): Paint {
        return Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.FILL
            color = backgroundAvatarColor
        }
    }

    private fun updateTextBounds() {
        textPaint.getTextBounds(text, 0, text?.length ?: 0, textBounds)
    }
}