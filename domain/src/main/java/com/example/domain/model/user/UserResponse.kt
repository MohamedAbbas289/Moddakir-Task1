package com.example.domain.model.user

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserResponse(
    val country: String? = null,
    val consumerPreference: ConsumerPreference? = null,
    val education: Education? = null,
    val gender: String? = null,
    val city: String? = null,
    val isChildDependent: Boolean? = null,
    val certificate: String? = null,
    val childPathId: String? = null,
    val type: String? = null,
    val setting: Setting? = null,
    val createdAt: String? = null,
    val rate: String? = null,
    val educationLevel: EducationLevel? = null,
    val childLinesPerSession: Int? = null,
    val isRegistrationCompleted: Boolean? = null,
    val id: String? = null,
    val email: String? = null,
    val categorize: List<CategorizeItem?>? = null,
    val profession: String? = null,
    val identifier: String? = null,
    val address: String? = null,
    val avatarUrl: String? = null,
    val childEducationPlanId: String? = null,
    val fullName: String? = null,
    val birthDate: String? = null,
    val phone: String? = null,
    val childSessionsPerWeekId: String? = null,
    val assignmentValue: String? = null,
    val region: Region? = null,
    val childAgePreferenceForEdit: String? = null,
    val username: String? = null
) : Parcelable