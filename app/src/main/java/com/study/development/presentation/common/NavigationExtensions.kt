package com.study.development.presentation.common

import android.app.Activity
import android.content.Intent
import androidx.core.app.ActivityOptionsCompat
import com.study.development.R

fun Activity.navigateTo(
    intent: Intent,
    direction: NavDirection = NavDirection.RIGHT,
    finishCurrent: Boolean = false
) {
    val (enterAnim, exitAnim) = when (direction) {
        NavDirection.RIGHT -> R.anim.slide_in_right to R.anim.slide_out_left
        NavDirection.LEFT -> R.anim.slide_in_left to R.anim.slide_out_right
    }
    val options = ActivityOptionsCompat.makeCustomAnimation(this, enterAnim, exitAnim)
    startActivity(intent, options.toBundle())
    if (finishCurrent) finish()
}

enum class NavDirection { LEFT, RIGHT }
