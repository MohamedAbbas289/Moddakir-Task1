package com.example.domain.model.user

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ConsumerPreference(
    val languagePreference: List<String?>? = null,
    val lastUpdated: String? = null,
    val agePreference: String? = null,
    val pathPreference: String? = null,
    val useAppFor: String? = null,
    val partsMemorized: Int? = null,
    val id: String? = null,
    val educationLevelPreference: String? = null
) : Parcelable