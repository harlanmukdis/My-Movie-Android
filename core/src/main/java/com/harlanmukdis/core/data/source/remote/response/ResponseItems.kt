package com.harlanmukdis.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ResponseItems<T>(
    @field:SerializedName("results") val results: List<T>
)
