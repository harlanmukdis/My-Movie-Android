package com.harlanmukdis.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import java.io.Serializable

@Parcelize
data class Review (
    var id: String,
    var author: String,
    var content: String,
    var created_at: String,
    var updated_at: String,
    var url: String,
    var author_details: @RawValue Author,
): Parcelable, Serializable {
    data class Author(
        var name: String,
        var username: String,
        var avatar_path: String? = null,
        var rating: Double? = null,
    )
}