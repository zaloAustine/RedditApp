package com.zalocoders.redditapp.ui.posts.detail

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import com.zalocoders.redditapp.data.models.post.Children
import com.zalocoders.redditapp.data.models.post.MediaType
import com.zalocoders.redditapp.data.models.post.getMediaType
import com.zalocoders.redditapp.data.models.post.getMediaUrl
import com.zalocoders.redditapp.data.models.post.toFavouritePostEntity
import com.zalocoders.redditapp.databinding.FragmentPostDetailsBinding
import com.zalocoders.redditapp.utils.show
import com.zalocoders.redditapp.utils.showErrorSnackbar
import com.zalocoders.redditapp.utils.showSnackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostDetailsFragment : Fragment() {
	private lateinit var binding:FragmentPostDetailsBinding
	
	private val navArgs:PostDetailsFragmentArgs by navArgs()
	
	private val viewModel:PostDetailViewModel by viewModels()
	
	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
							  savedInstanceState: Bundle?): View {
		binding = FragmentPostDetailsBinding.inflate(layoutInflater)
		return binding.root
	}
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		setupViews(navArgs.post)
		observeViewModel()
	}
	
	@SuppressLint("SetTextI18n")
	fun setupViews(item: Children) {
		val data = item.child_data
		with(binding) {
			
			postTitle.text = data.title
			upvote.text = "${data.ups} ups"
			downVotes.text = "${data.downs} downs"
			
			if (data.likes.isNullOrEmpty()) {
				likes.text = "0"
			} else {
				likes.text = "${data.likes} likes"
			}
			comments.text = data.num_comments.toString()
			
			
			addToFavourite.setOnFavoriteChangeListener { _, favorite ->
				if (favorite) {
					viewModel.saveFavouriteToDb(item.toFavouritePostEntity())
				} else {
					viewModel.deleteFavourite(item.child_data.id)
				}
			}
			
			when (getMediaType(item.child_data.media, item.child_data.is_video)) {
				MediaType.IMAGE, MediaType.GIF -> {
					image.show()
					
					val options = RequestOptions()
					options.centerCrop()
					
					Glide.with(binding.root.context)
							.load(getMediaUrl(item.child_data.media, item.child_data.is_video, item))
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
		private fun observeViewModel(){
			viewModel.isSaved.observe(viewLifecycleOwner,{
				when(it){
					true -> view?.showSnackbar("Added to Favourites", Snackbar.LENGTH_SHORT)
					false -> view?.showErrorSnackbar("Failed Adding", Snackbar.LENGTH_SHORT)
				}
			})
			
			viewModel.isDeleted.observe(viewLifecycleOwner,{
				when(it){
					true -> view?.showErrorSnackbar("Removed from Favourites", Snackbar.LENGTH_SHORT)
					false -> view?.showErrorSnackbar("Failed removing", Snackbar.LENGTH_SHORT)
				}
			})
	}
	
}