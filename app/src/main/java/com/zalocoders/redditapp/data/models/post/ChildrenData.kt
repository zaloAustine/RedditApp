package com.zalocoders.redditapp.data.models.post

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class ChildrenData(
		val archived: Boolean,
		val author: String,
		val author_fullname: String,
		val downs: Int,
		val hide_score: Boolean,
		val id: String,
		val title:String,
		val is_video: Boolean,
		val likes: String?,
		val media: Media?,
		val name: String,
		val num_comments: Int,
		val over_18: Boolean,
		val saved: Boolean,
		val score: Int,
		val thumbnail: String,
		val ups: Int,
		val upvote_ratio: Double,
		@Json(name = "url_overridden_by_dest")
		val url: String,
		):Parcelable