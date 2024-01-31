package com.example.newsapplication.models

import android.os.Parcelable
import androidx.navigation.NavArgs
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Article(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val source: Source?,
    val title: String?,
    val url: String?,
    val urlToImage: String?
) : Parcelable, NavArgs {
    override fun hashCode(): Int {
        var result = id.hashCode()
        if (url.isNullOrEmpty()) {
            result = 31 * result + url.hashCode()
        }
        return result
    }
}