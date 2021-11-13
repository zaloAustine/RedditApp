package com.zalocoders.redditapp.ui.posts.list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zalocoders.redditapp.data.models.post.Children
import com.zalocoders.redditapp.data.models.post.MediaType
import com.zalocoders.redditapp.data.models.post.getMediaType
import com.zalocoders.redditapp.data.models.post.getMediaUrl
import com.zalocoders.redditapp.databinding.PostItemLayoutBinding
import com.zalocoders.redditapp.utils.show
import com.bumptech.glide.request.RequestOptions
import android.webkit.WebChromeClient


class PostsAdapter(private val onClickListener: OnClickListener,private val favouriteIds:List<String>) : PagingDataAdapter<Children, PostsAdapter.PostsViewHolder>(diffUtil){
	inner class  PostsViewHolder(private val binding: PostItemLayoutBinding) :
			RecyclerView.ViewHolder(binding.root) {
		
		@SuppressLint("SetTextI18n")
		fun bind(item: Children) {
			val data = item.child_data
			with(binding) {
				
				postTitle.text = data.title
				upvote.text = "${data.ups} ups"
				downVotes.text = "${data.downs} downs"
				
				if (data.likes.isNullOrEmpty()){
					likes.text = "0 likes"
				}else{
					likes.text = "${data.likes} likes"
				}
				comments.text = data.num_comments.toString()
				
				if(favouriteIds.contains(data.id)){
					addToFavourite.isFavorite = true
				}
				
				postCard.setOnClickListener {
					onClickListener.favouriteDetails(item)
					
				}
				
				addToFavourite.setOnFavoriteChangeListener { _, favorite ->
					if (favorite) {
						onClickListener.addFavourite(item)
						addToFavourite.isFavorite = true
						
					} else {
						onClickListener.deleteFavourite(item)
						addToFavourite.isFavorite = false
						
					}
				}
				
				when(getMediaType(item.child_data.media,item.child_data.is_video)){
					MediaType.IMAGE,MediaType.GIF  -> {
						image.show()
						
						val options = RequestOptions()
						options.centerCrop()
						
						Glide.with(binding.root.context)
								.load(getMediaUrl(item.child_data.media,item.child_data.is_video,item))
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
					PostItemLayoutBinding.inflate(
							LayoutInflater.from(parent.context), parent, false
					)
			)
	
	override fun onBindViewHolder(holder: PostsViewHolder, position: Int) {
		holder.bind(getItem(position)!!)
	}
}

val diffUtil = object : DiffUtil.ItemCallback<Children>() {
	override fun areItemsTheSame(oldItem: Children, newItem: Children): Boolean {
		return oldItem.child_data.id == newItem.child_data.id
	}
	
	override fun areContentsTheSame(oldItem: Children, newItem: Children): Boolean {
		return oldItem == newItem
	}
}

interface OnClickListener {
	fun addFavourite(item:Children)
	fun deleteFavourite(item:Children)
	fun favouriteDetails(item:Children)
	
}