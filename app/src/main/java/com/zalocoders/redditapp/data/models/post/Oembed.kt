package com.zalocoders.redditapp.data.models.post

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Oembed(
    val author_name: String,
    val html: String,
    val provider_name: String,
    val provider_url: String,
    val thumbnail_height: Int,
    val thumbnail_url: String,
    val thumbnail_width: Int,
    val title: String,
    val type: String,
    val version: String,
    val width: Int
):Parcelable