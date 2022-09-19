package com.harlanmukdis.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ReviewResponse (
    @field:SerializedName("id")
    var id: String,

    @field:SerializedName("author")
    var author: String,

    @field:SerializedName("content")
    var content: String,

    @field:SerializedName("created_at")
    var created_at: String,

    @field:SerializedName("updated_at")
    var updated_at: String,

    @field:SerializedName("url")
    var url: String,

    @field:SerializedName("author_details")
    var author_details: Author,
) {
    data class Author(
        @field:SerializedName("name")
        var name: String,
        @field:SerializedName("username")
        var username: String,
        @field:SerializedName("avatar_path")
        var avatar_path: String? = null,
        @field:SerializedName("rating")
        var rating: Double? = null,
    )
}