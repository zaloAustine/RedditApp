package com.zalocoders.redditapp.data.models.post

import android.os.Parcelable
import kotlinx.parcelize.Parceler
import kotlinx.parcelize.Parcelize

@Parcelize
data class Media(
		val reddit_video: RedditVideo?,
	//caries along gif
		val oembed: Oembed?
):Parcelable
