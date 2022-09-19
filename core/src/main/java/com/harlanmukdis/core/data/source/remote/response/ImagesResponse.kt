package com.harlanmukdis.core.data.source.remote.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ImagesResponse(
    @field:SerializedName("backdrops")
    var backdrops: List<VideoResponse> = arrayListOf(),
    @field:SerializedName("logos")
    var logos: List<VideoResponse> = arrayListOf(),
    @field:SerializedName("posters")
    var posters: List<VideoResponse> = arrayListOf(),
    @field:SerializedName("id")
    var id: Int
): Serializable
