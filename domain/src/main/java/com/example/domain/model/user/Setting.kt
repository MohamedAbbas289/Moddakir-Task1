package com.example.domain.model.user

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Setting(
    val enableVideoRecording: Boolean? = null,
    val freezeStartDate: String? = null,
    val appName: String? = null,
    val enableVoiceRecording: Boolean? = null,
    val currency: String? = null,
    val freezeEndDate: String? = null,
    val freezed: Boolean? = null
) : Parcelable