package com.zalocoders.redditapp.ui.posts.favourites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.zalocoders.redditapp.data.models.post.FavouritePostEntity
import com.zalocoders.redditapp.databinding.FragmentFavouriteListBinding
import com.zalocoders.redditapp.utils.show
import com.zalocoders.redditapp.utils.showErrorSnackbar
import com.zalocoders.redditapp.utils.showSnackbar
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class FavouriteListFragment : Fragment() ,FavouritesOnClickListener{
	
	private lateinit var binding:FragmentFavouriteListBinding
	private lateinit var favouritePostsAdapter:FavouritePostsAdapter
	
	private val viewModel:FavouriteListViewModel by viewModels()
	
	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
							  savedInstanceState: Bundle?): View {
		binding = FragmentFavouriteListBinding.inflate(layoutInflater)
		return binding.root
	}
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		
		setUpRecyclerview()
		getFavouritePosts()
		observeViewModel()
		getFavourites()
		setUpViews()
	}
	
	private fun setUpViews(){
		binding.clearFavourites.setOnClickListener {
			viewModel.deleteAllFavourites()
		}
	}
	
	private fun getFavouritePosts(){
		viewModel.getFavourite()
	}
	
	private fun setUpRecyclerview(){
		favouritePostsAdapter = FavouritePostsAdapter(this)
		
		binding.recyclerview.apply {
			layoutManager = LinearLayoutManager(context)
			setHasFixedSize(true)
			adapter = favouritePostsAdapter
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
				true -> {
					view?.showErrorSnackbar("Removed from Favourites", Snackbar.LENGTH_SHORT)
				}
				false -> view?.showErrorSnackbar("Failed removing", Snackbar.LENGTH_SHORT)
			}
		})
		
		viewModel.isCleared.observe(viewLifecycleOwner,{
			when(it){
				true -> {
					view?.showErrorSnackbar("Cleared Favourites", Snackbar.LENGTH_SHORT)
				}
				false -> view?.showErrorSnackbar("Failed Clearing", Snackbar.LENGTH_SHORT)
			}
		})
		
	}
	
	private fun getFavourites(){
		viewModel.getFavourite().observe(viewLifecycleOwner,{
			if(it.isNotEmpty()){
				Timber.e("local posts $it")
				favouritePostsAdapter.submitList(it)
			}else{
				favouritePostsAdapter.submitList(it)
				binding.errorTextView.show()
			}
		})
	}
	
	override fun addFavourite(item: FavouritePostEntity) {
		viewModel.saveFavouriteToDb(item)
	}
	
	override fun deleteFavourite(item: FavouritePostEntity) {
		viewModel.deleteFavourite(item.favourite_id)
	}
	
	override fun favouriteDetails(item: FavouritePostEntity) {
	}
}