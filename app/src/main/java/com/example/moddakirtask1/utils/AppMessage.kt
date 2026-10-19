package com.example.moddakirtask1.utils

import android.app.Activity
import android.view.View
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.example.moddakirtask1.R
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar

enum class AppMessageType(
    @ColorRes val backgroundColor: Int,
    @ColorRes val contentColor: Int
) {
    SUCCESS(R.color.message_success, R.color.message_on_color),
    WARNING(R.color.message_warning, R.color.message_on_warning),
    ERROR(R.color.message_error, R.color.message_on_color)
}

fun View.showAppMessage(
    message: CharSequence,
    type: AppMessageType,
    longDuration: Boolean = false
) {
    val duration = if (longDuration) Snackbar.LENGTH_LONG else Snackbar.LENGTH_SHORT
    Snackbar.make(this, message, duration).apply {
        animationMode = BaseTransientBottomBar.ANIMATION_MODE_SLIDE
        setBackgroundTint(ContextCompat.getColor(context, type.backgroundColor))
        setTextColor(ContextCompat.getColor(context, type.contentColor))
        view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)?.maxLines = 3
    }.show()
}

fun Fragment.showSuccessMessage(message: CharSequence, longDuration: Boolean = false) =
    messageHost().showAppMessage(message, AppMessageType.SUCCESS, longDuration)

fun Fragment.showWarningMessage(message: CharSequence, longDuration: Boolean = false) =
    messageHost().showAppMessage(message, AppMessageType.WARNING, longDuration)

fun Fragment.showErrorMessage(message: CharSequence, longDuration: Boolean = false) =
    messageHost().showAppMessage(message, AppMessageType.ERROR, longDuration)

fun Fragment.showWarningMessage(@StringRes message: Int, longDuration: Boolean = false) =
    showWarningMessage(getString(message), longDuration)

fun Activity.showSuccessMessage(message: CharSequence, longDuration: Boolean = false) =
    contentView().showAppMessage(message, AppMessageType.SUCCESS, longDuration)

fun Activity.showWarningMessage(message: CharSequence, longDuration: Boolean = false) =
    contentView().showAppMessage(message, AppMessageType.WARNING, longDuration)

fun Activity.showErrorMessage(message: CharSequence, longDuration: Boolean = false) =
    contentView().showAppMessage(message, AppMessageType.ERROR, longDuration)

fun Activity.showWarningMessage(@StringRes message: Int, longDuration: Boolean = false) =
    showWarningMessage(getString(message), longDuration)

private fun Activity.contentView(): View = findViewById(android.R.id.content)

private fun Fragment.messageHost(): View =
    if (this is DialogFragment) requireActivity().contentView() else requireView()
