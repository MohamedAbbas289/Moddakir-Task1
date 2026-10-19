package com.example.domain.model.user

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class EducationLevel(
    val name: String? = null,
    val id: String? = null
) : Parcelable