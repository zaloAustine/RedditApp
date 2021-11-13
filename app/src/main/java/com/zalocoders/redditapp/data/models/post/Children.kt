package com.zalocoders.redditapp.data.models.post

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class Children(
		@Json(name = "data")
		val child_data: ChildrenData,
		val kind: String
):Parcelable