package com.zalocoders.redditapp.data.models.post

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite_posts")
data class FavouritePostEntity(
		@PrimaryKey(autoGenerate = false) val favourite_id: String,
		val archived: Boolean,
		val author: String,
		val author_full_name: String,
		val downs: Int,
		val edited: Boolean,
		val is_video: Boolean,
		val likes: String?,
		val media_url:String,
		val media_type:MediaType,
		val name: String,
		val num_comments: Int,
		val over_18: Boolean,
		val saved: Boolean,
		val score: Int,
		val post_hint:String,
		val thumbnail: String,
		val url: String,
)
