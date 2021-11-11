package com.zalocoders.redditapp.data.models.post

import com.squareup.moshi.Json

data class Children(
		@Json(name = "data")
		val child_data: ChildrenData,
		val kind: String
)