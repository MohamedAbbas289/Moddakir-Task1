package com.example.data.model

import com.example.domain.model.GoogleLoginData
import com.example.domain.model.GoogleLoginResponse
import com.google.gson.annotations.SerializedName

data class GoogleLoginResponseDto(
    @SerializedName("result") val result: String? = null,
    @SerializedName("data") val data: GoogleLoginData? = null,
    @SerializedName("error") val error: Any? = null,
    @SerializedName("status") val status: String? = null
) {
    fun toGoogleLoginResponse(): GoogleLoginResponse {
        return GoogleLoginResponse(
            result = result.orEmpty(),
            data = data,
            error = error?.toString(),
            status = status.orEmpty()
        )
    }
}
