package com.example.domain.model.user

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CategorizeItem(
    val createdAt: String? = null,
    val deletedAt: String? = null,
    val deleted: Boolean? = null,
    val categoryNameEnglishLower: String? = null,
    val categoryNameLower: String? = null,
    val appType: String? = null,
    val id: String? = null,
    val isActive: Boolean? = null,
    val categoryName: String? = null,
    val updatedAt: String? = null
) : Parcelable