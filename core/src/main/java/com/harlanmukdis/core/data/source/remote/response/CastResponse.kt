package com.harlanmukdis.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class CastResponse (
    @field:SerializedName("id")
    var id: Int,
    @field:SerializedName("cast")
    var cast: List<Cast> = arrayListOf(),
) {
    data class Cast(
        @field:SerializedName("adult")
        var adult: Boolean,
        @field:SerializedName("gender")
        var gender: Int,
        @field:SerializedName("id")
        var id: Int,
        @field:SerializedName("known_for_department")
        var known_for_department: String,
        @field:SerializedName("name")
        var name: String,
        @field:SerializedName("original_name")
        var original_name: String,
        @field:SerializedName("popularity")
        var popularity: Double,
        @field:SerializedName("profile_path")
        var profile_path: String,
        @field:SerializedName("cast_id")
        var cast_id: Int,
        @field:SerializedName("character")
        var character: String,
        @field:SerializedName("credit_id")
        var credit_id: String,
        @field:SerializedName("order")
        var order: Int
    )
}
