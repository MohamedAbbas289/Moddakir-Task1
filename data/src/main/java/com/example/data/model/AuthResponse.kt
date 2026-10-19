package com.example.data.model


import com.example.domain.model.user.UserResponse
import com.google.gson.annotations.SerializedName

data class AuthResponse(

    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("username")
    val username: String? = null,

    @field:SerializedName("gender")
    val gender: String? = null,

    @field:SerializedName("email")
    val email: String? = null,

    @field:SerializedName("fullName")
    val fullName: String? = null,

    @field:SerializedName("identifier")
    val identifier: String? = null,

    @field:SerializedName("phone")
    val phone: String? = null,

    @field:SerializedName("rate")
    val rate: String? = null,

    @field:SerializedName("createdAt")
    val createdAt: String? = null,

    @field:SerializedName("avatarUrl")
    val avatarUrl: String? = null,

    @field:SerializedName("country")
    val country: String? = null,

    @field:SerializedName("city")
    val city: String? = null,

    @field:SerializedName("address")
    val address: String? = null,

    @field:SerializedName("certificate")
    val certificate: String? = null,

    @field:SerializedName("profession")
    val profession: String? = null,

    @field:SerializedName("assignmentValue")
    val assignmentValue: String? = null,

    @field:SerializedName("type")
    val type: String? = null,


    @field:SerializedName("isChildDependent")
    val isChildDependent: Boolean? = null,

    @field:SerializedName("childAgePreferenceForEdit")
    val childAgePreferenceForEdit: String? = null,

    @field:SerializedName("childSessionsPerWeekId")
    val childSessionsPerWeekId: String? = null,

    @field:SerializedName("childPathId")
    val childPathId: String? = null,

    @field:SerializedName("childLinesPerSession")
    val childLinesPerSession: Int? = null,

    @field:SerializedName("childEducationPlanId")
    val childEducationPlanId: String? = null,

    @field:SerializedName("birthDate")
    val birthDate: String? = null,

    @field:SerializedName("isRegistrationCompleted")
    val isRegistrationCompleted: Boolean? = null,

    @field:SerializedName("status")
    val status: String? = null,

    @field:SerializedName("result")
    val result: String? = null,

    ) {
    fun toUser(): UserResponse {
        return UserResponse(
            id = id,
            fullName = fullName ?: username,
            username = username,
            email = email,
            phone = phone,
            avatarUrl = avatarUrl
        )
    }
}

