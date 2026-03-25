package com.study.development.presentation.splash

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

class WaveView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    private val wavePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val path = Path()
    private var animOffset = 0f

    private val animator = ValueAnimator.ofFloat(0f, 2f * PI.toFloat()).apply {
        duration = 3000
        repeatCount = ValueAnimator.INFINITE
        repeatMode = ValueAnimator.RESTART
        interpolator = LinearInterpolator()
        addUpdateListener {
            animOffset = it.animatedValue as Float
            invalidate()
        }
    }

    data class Wave(
        val color: Int,
        val amplitude: Float,
        val frequency: Float,
        val phaseShift: Float,
        val speedMultiplier: Float,
        val yOffsetFraction: Float
    )

    private val waves = listOf(
        Wave(Color.parseColor("#1A0033"), 60f, 1.2f, 0f,   1.0f, 0.45f),
        Wave(Color.parseColor("#6A0DAD"), 50f, 1.5f, 1.0f, 1.3f, 0.50f),
        Wave(Color.parseColor("#9B30FF"), 40f, 1.8f, 2.0f, 0.8f, 0.55f),
        Wave(Color.parseColor("#2D006B"), 55f, 1.1f, 0.5f, 1.5f, 0.48f),
        Wave(Color.parseColor("#000000"), 45f, 2.0f, 1.5f, 1.1f, 0.52f)
    )

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        animator.start()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        animator.cancel()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val w = width.toFloat()
        val h = height.toFloat()

        waves.forEach { wave ->
            path.reset()
            wavePaint.color = wave.color
            wavePaint.alpha = 180

            val yBase = h * wave.yOffsetFraction
            val phase = animOffset * wave.speedMultiplier + wave.phaseShift

            path.moveTo(0f, h)
            path.lineTo(0f, yBase)

            for (i in 0..300) {
                val x = i / 300f * w
                val y = yBase +
                        wave.amplitude * sin(phase + wave.frequency * x / w * 2 * PI).toFloat() +
                        (wave.amplitude * 0.3f) * cos(phase * 1.3f + wave.frequency * x / w * PI).toFloat()
                path.lineTo(x, y)
            }

            path.lineTo(w, h)
            path.close()
            canvas.drawPath(path, wavePaint)
        }
    }
}
