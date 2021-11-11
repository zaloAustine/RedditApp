package com.zalocoders.redditapp.data.models.post

import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class PostsResponse(
		val data: Data,
		val kind: String
)