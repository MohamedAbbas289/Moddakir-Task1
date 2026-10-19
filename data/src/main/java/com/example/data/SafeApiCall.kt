package com.example.data

import com.example.domain.common.ResultWrapper
import com.example.domain.exceptions.NetworkException
import com.example.domain.exceptions.ParsingException
import com.example.domain.exceptions.ServerError
import com.example.domain.exceptions.ServerTimeOutException
import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

fun <T> safeApiCall(
    apiCall: suspend () -> T
): Flow<ResultWrapper<T>> = flow {
    emit(ResultWrapper.Loading)
    emit(ResultWrapper.Success(apiCall()))
}.catch { error ->
    if (error is CancellationException) throw error
    val result = when (error) {
        is SocketTimeoutException -> ResultWrapper.Error(
            ServerTimeOutException("The request timed out. Please try again.", error)
        )

        is IOException -> ResultWrapper.Error(NetworkException(error))
        is HttpException -> ResultWrapper.ServerError(error.toServerError())
        is JsonParseException -> ResultWrapper.Error(ParsingException(error))
        else -> ResultWrapper.Error(error)
    }
    emit(result)
}.flowOn(Dispatchers.IO)

private fun HttpException.toServerError(): ServerError {
    val errorBody = response()?.errorBody()?.string()
    val errorResponse = errorBody.parseOrNull<ErrorResponseDto>()

    val status = errorResponse?.status
        ?: errorResponse?.error?.code
        ?: code().toString()

    val message = errorResponse?.error?.message
        ?: errorResponse?.error?.description
        ?: message().takeIf { it.isNotBlank() }
        ?: "The server could not complete the request"

    return ServerError(status, message, code(), this)
}

private inline fun <reified T> String?.parseOrNull(): T? {
    if (isNullOrBlank()) return null
    return runCatching { Gson().fromJson(this, T::class.java) }.getOrNull()
}

data class ErrorResponseDto(
    @SerializedName("status") val status: String? = null,
    @SerializedName("result") val result: String? = null,
    @SerializedName("error") val error: ErrorDetailsDto? = null
)

data class ErrorDetailsDto(
    @SerializedName("code") val code: String? = null,
    @SerializedName("message") val message: String? = null,
    @SerializedName("description") val description: String? = null
)