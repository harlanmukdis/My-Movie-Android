package com.harlanmukdis.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @field:SerializedName("error_code")
    val error_code: Int,
    @field:SerializedName("status_message")
    val status_message: String,
    @field:SerializedName("success")
    val success: Boolean = false
)
