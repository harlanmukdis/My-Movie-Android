package com.harlanmukdis.core.data.source.remote.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class VideoResponse(
    @field:SerializedName("iso_639_1")
    var iso_639_1: String,
    @field:SerializedName("iso_3166_1")
    var iso_3166_1: String,
    @field:SerializedName("name")
    var name: String,
    @field:SerializedName("key")
    var key: String,
    @field:SerializedName("site")
    var site: String,
    @field:SerializedName("size")
    var size: Int,
    @field:SerializedName("type")
    var type: String,
    @field:SerializedName("official")
    var official: Boolean,
    @field:SerializedName("published_at")
    var published_at: String,
    @field:SerializedName("id")
    var id: String
): Serializable
