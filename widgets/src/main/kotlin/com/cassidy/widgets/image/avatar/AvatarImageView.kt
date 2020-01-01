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
import com.gocantar.cassidy.tools.extensions.color
import com.gocantar.cassidy.tools.extensions.dimension
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

    private var label: String? = ""

    private var masking: Boolean = false

    private var textColor: Int = color(R.color.white)
    private var backgroundAvatarColor: Int = color(R.color.grey)

    private var labelFontSize: Float = dimension(R.dimen.default_avatar_text_size)

    private val labelPaint: Paint
    private val backgroundPaint: Paint
    private val maskPaint: Paint

    private val backgroundBounds: RectF = RectF()
    private val maskBounds: RectF = RectF()

    private val labelBounds: Rect = Rect()

    init {
        attrs?.let { initializeAttributes(it, defStyleAttr) }
        backgroundPaint = configureBackgroundPaint()
        maskPaint = configureMaskPaint()
        labelPaint = configureTextPaint()
        updateTextBounds()
    }

    private fun initializeAttributes(attributes: AttributeSet, defStyleAttr: Int) {
        val styles = context.obtainStyledAttributes(
            attributes, R.styleable.AvatarImageView, defStyleAttr, 0
        )
        with(styles) {
            val avatarStyle = getInt(R.styleable.AvatarImageView_avatarStyle, 0)
            val backgroundType = getInt(R.styleable.AvatarImageView_backgroundBehaviour, 0)
            labelFontSize = getDimension(R.styleable.AvatarImageView_labelSize, labelFontSize)
            textColor = getColor(R.styleable.AvatarImageView_labelColor, textColor)
            masking = getBoolean(R.styleable.AvatarImageView_mask, masking)
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
        updateViewBounds(maskBounds)
    }

    override fun onDraw(canvas: Canvas) {
        label?.run {
            drawBackground(canvas)
            drawMask(canvas)
            drawText(canvas)
            drawHighlight(canvas)
            drawStroke(canvas)
            return
        }
        super.onDraw(canvas)
    }

    fun setLabel(avatar: Avatar) {
        viewModel.setAvatar(avatar)
    }

    private fun drawBackground(canvas: Canvas) {
        canvas.drawOval(backgroundBounds, backgroundPaint)
    }

    private fun drawMask(canvas: Canvas) {
        runIf(masking) {
            canvas.drawArc(backgroundBounds, 315F, 180F, false, maskPaint)
        }
    }

    private fun drawText(canvas: Canvas) {
        val textBottom = backgroundBounds.centerY() - labelBounds.exactCenterY()
        canvas.drawText(label!!, backgroundBounds.centerX(), textBottom, labelPaint)
    }

    private fun updateBackground(color: Int) {
        runIf(color != AvatarColorPickerDelegate.FIXED) {
            backgroundAvatarColor = color(R.array.avatar_colors, color)
            backgroundPaint.color = backgroundAvatarColor
        }
    }

    private fun updateText(value: String?) {
        label = value
        updateTextBounds()
    }

    private fun updateTextBounds() {
        val initials = label ?: return
        labelPaint.getTextBounds(initials, 0, initials.length, labelBounds)
    }

    private fun configureTextPaint(): Paint {
        return Paint(Paint.ANTI_ALIAS_FLAG).apply {
            textAlign = Paint.Align.CENTER
            textSize = labelFontSize
            color = textColor
        }
    }

    private fun configureBackgroundPaint(): Paint {
        return Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.FILL
            color = backgroundAvatarColor
        }
    }

    private fun configureMaskPaint(): Paint {
        return Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.FILL
            color = color(R.color.mask)
        }
    }
}