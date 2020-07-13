package com.code_remote.venuedemo.network

import com.code_remote.venuedemo.data.remote.ErrorDTO
import com.code_remote.venuedemo.network.Result.Companion.Status.*
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import retrofit2.HttpException
import java.net.HttpURLConnection
import java.net.SocketTimeoutException


data class Result<out T>(
    val status: Status,
    val data: T?,
    val message: String?
) {

    companion object {

        enum class Status {
            SUCCESS,
            ERROR,
            LOADING
        }

        fun <T> success(data: T?): Result<T> {
            return Result(
                SUCCESS,
                data,
                null
            )
        }

        fun <T> error(msg: String, data: T?): Result<T> {
            return Result(
                ERROR,
                data,
                msg
            )
        }

        fun <T> loading(data: T?): Result<T> {
            return Result(
                LOADING,
                data,
                null
            )
        }

        fun <T : Any> handleSuccess(data: T): Result<T> {
            return success(data)
        }

        fun <T : Any> handleException(e: Exception): Result<T> {
            return when (e) {
                is HttpException -> error(getErrorMessage(e.code(), e), null)
                is SocketTimeoutException -> error(
                    getErrorMessage(HttpURLConnection.HTTP_CLIENT_TIMEOUT),
                    null
                )
                else -> error(getErrorMessage(Int.MAX_VALUE), null)
            }
        }


        private fun getErrorMessage(code: Int, e: HttpException? = null): String {
            return when (code) {
                HttpURLConnection.HTTP_UNAUTHORIZED -> "Unauthorised"
                HttpURLConnection.HTTP_NOT_FOUND -> "Not found"
                HttpURLConnection.HTTP_CLIENT_TIMEOUT -> "Timeout"
                HttpURLConnection.HTTP_BAD_REQUEST -> e.mapError()
                else -> "Something went wrong"
            }
        }

        private fun HttpException?.mapError(): String {
            var result = "Something went wrong"
            this?.let {
                val moshi = Moshi.Builder().build()
                val jsonAdapter: JsonAdapter<ErrorDTO> = moshi.adapter<ErrorDTO>(ErrorDTO::class.java)
                val meta = jsonAdapter.fromJson(it.response()?.errorBody()?.source()!!)
                result = meta?.meta?.errorDetail ?: result
            }
            return result
        }
    }
}
