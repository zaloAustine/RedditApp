package com.zalocoders.redditapp.ui.posts.favourites

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zalocoders.redditapp.data.models.post.MediaType
import com.zalocoders.redditapp.utils.show
import com.bumptech.glide.request.RequestOptions
import android.webkit.WebChromeClient
import androidx.recyclerview.widget.ListAdapter
import com.zalocoders.redditapp.data.models.post.FavouritePostEntity
import com.zalocoders.redditapp.databinding.FavouritePostItemLayoutBinding


class FavouritePostsAdapter(private val onClickListener: FavouritesOnClickListener) : ListAdapter<FavouritePostEntity, FavouritePostsAdapter.PostsViewHolder>(favouritePostsDiffUtil){
	inner class  PostsViewHolder(private val binding: FavouritePostItemLayoutBinding) :
			RecyclerView.ViewHolder(binding.root) {
		
		@SuppressLint("SetTextI18n")
		fun bind(item: FavouritePostEntity) {
			with(binding) {
				
				postTitle.text = item.title
				upvote.text = "${item.ups} ups"
				downVotes.text = "${item.downs} downs"
				
				if (item.likes.isNullOrEmpty()) {
					likes.text = "0 likes"
				} else {
					likes.text = "${item.likes} likes"
				}
				comments.text = item.num_comments.toString()
				
				
				postCard.setOnClickListener {
					onClickListener.favouriteDetails(item)
					
				}
				
				removeFavourite.setOnClickListener {
					onClickListener.deleteFavourite(item)
					
				}
				
				when (item.media_type) {
					MediaType.IMAGE, MediaType.GIF -> {
						image.show()
						
						val options = RequestOptions()
						options.centerCrop()
						
						Glide.with(binding.root.context)
								.load(item.media_url)
								.apply(options)
								.into(binding.image)
					}
					
					MediaType.VIDEO -> {
						videoView.show()
						videoView.webChromeClient = WebChromeClient()
						videoView.settings.mediaPlaybackRequiresUserGesture = false
						videoView.loadUrl("https://v.redd.it/ovfd4wayf0f71/DASH_720.mp4?source=fallback")
					}
				}
			}
		}
	}
	
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsViewHolder =
			PostsViewHolder(
					FavouritePostItemLayoutBinding.inflate(
							LayoutInflater.from(parent.context), parent, false
					)
			)
	
	override fun onBindViewHolder(holder: PostsViewHolder, position: Int) {
		holder.bind(getItem(position)!!)
	}
}

val favouritePostsDiffUtil = object : DiffUtil.ItemCallback<FavouritePostEntity>() {
	override fun areItemsTheSame(oldItem: FavouritePostEntity, newItem: FavouritePostEntity): Boolean {
		return oldItem.favourite_id == newItem.favourite_id
	}
	
	override fun areContentsTheSame(oldItem: FavouritePostEntity, newItem: FavouritePostEntity): Boolean {
		return oldItem == newItem
	}
}

interface FavouritesOnClickListener {
	fun addFavourite(item:FavouritePostEntity)
	fun deleteFavourite(item:FavouritePostEntity)
	fun favouriteDetails(item:FavouritePostEntity)
	
}