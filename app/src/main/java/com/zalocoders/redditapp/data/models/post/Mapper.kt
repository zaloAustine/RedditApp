package com.zalocoders.redditapp.data.models.post


fun Children.toFavouritePostEntity():FavouritePostEntity{
	return FavouritePostEntity(
	archived = this.child_data.archived,
	author = this.child_data.author,
	author_full_name = this.child_data.author_fullname,
	downs = this.child_data.downs,
	is_video = this.child_data.is_video,
	likes = this.child_data.likes,
	name = this.child_data.name,
	num_comments = this.child_data.num_comments,
	over_18 = this.child_data.over_18,
	saved = this.child_data.saved,
	score = this.child_data.score,
	thumbnail = this.child_data.thumbnail,
	url = this.child_data.url,
	media_url = getMediaUrl(this.child_data.media,this.child_data.is_video,this),
	media_type = getMediaType(this.child_data.media,this.child_data.is_video),
	favourite_id = this.child_data.id,
	ups = this.child_data.ups.toString(),
	title = this.child_data.title
	)
	}


fun getMediaType(media: Media?, isVideo: Boolean): MediaType {
	if(isVideo){
			return MediaType.VIDEO
		}else if(!isVideo && media?.oembed != null){
			return MediaType.GIF
		}
	return MediaType.IMAGE
	}

fun getMediaUrl(media: Media?, isVideo: Boolean,children: Children): String {
	if(isVideo){
		return media?.reddit_video!!.fallback_url
	}else if(!isVideo && media?.oembed != null){
		return media.oembed.thumbnail_url
	}
	return children.child_data.url
}
