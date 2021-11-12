package com.zalocoders.redditapp.ui.posts.list

import gandroid.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.zalocoders.redditapp.data.models.post.Children
import com.zalocoders.redditapp.databinding.PostItemLayoutBinding

class PostsAdapter : PagingDataAdapter<Children, PostsAdapter.PostsViewHolder>(diffUtil){
	inner class  PostsViewHolder(private val binding: PostItemLayoutBinding) :
			RecyclerView.ViewHolder(binding.root) {
		
		@SuppressLint("SetTextI18n")
		fun bind(item: Children) {
			with(binding) {
			postTitle.text = item.child_data.title
				postDescription.text = item.child_data.description
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
	