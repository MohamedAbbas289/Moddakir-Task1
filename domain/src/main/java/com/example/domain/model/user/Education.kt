package com.example.domain.model.user

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Education(
    val paths: List<String?>? = null
) : Parcelable