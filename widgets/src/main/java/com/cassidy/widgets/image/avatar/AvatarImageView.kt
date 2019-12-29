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
import com.cassidy.widgets.image.avatar.delegates.AvatarColorPickerDelegate
import com.cassidy.widgets.image.avatar.models.Avatar
import com.gocantar.cassidy.tools.extensions.runIf

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
            val avatarStyle = getInt(R.styleable.AvatarImageView_avatarStyle, 0)
            val backgroundType = getInt(R.styleable.AvatarImageView_backgroundBehaviour, 0)
            textFontSize = getDimension(R.styleable.AvatarImageView_textSize, textFontSize)
            textColor = getColor(R.styleable.AvatarImageView_textColor, textColor)
            backgroundAvatarColor =
                getColor(R.styleable.AvatarImageView_backgroundAvatarColor, backgroundAvatarColor)
            viewModel.configure(avatarStyle, backgroundType)
            recycle()
        }
    }

    override fun onLifecycleOwnerAttached(lifecycleOwner: LifecycleOwner) {
        viewModel.avatar.observe(lifecycleOwner, Observer {
            updateText(it.first)
            updateBackground(it.second)
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

    fun setAvatar(avatar: Avatar) {
        viewModel.setAvatar(avatar)
    }

    private fun drawBackground(canvas: Canvas) {
        canvas.drawOval(backgroundBounds, backgroundPaint)
    }

    private fun drawText(canvas: Canvas) {
        val textBottom = backgroundBounds.centerY() - textBounds.exactCenterY()
        canvas.drawText(text!!, backgroundBounds.centerX(), textBottom, textPaint)
    }

    private fun updateBackground(color: Int) {
        runIf(color != AvatarColorPickerDelegate.FIXED) {
            backgroundAvatarColor = color(R.array.avatar_colors, color)
            backgroundPaint.color = backgroundAvatarColor
        }
    }

    private fun updateText(value: String?) {
        text = value
        updateTextBounds()
    }

    private fun updateTextBounds() {
        textPaint.getTextBounds(text, 0, text?.length ?: 0, textBounds)
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
}