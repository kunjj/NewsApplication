package com.example.newsapplication.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class News(
    val articles: MutableSet<Article>,
    val status: String,
    val totalResults: Int
) : Parcelable