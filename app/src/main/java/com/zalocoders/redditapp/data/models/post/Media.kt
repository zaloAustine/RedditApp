package com.zalocoders.redditapp.data.models.post

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Media(
		val reddit_video: RedditVideo?,
		val oembed: Oembed?
):Parcelable
