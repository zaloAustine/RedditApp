package com.zalocoders.redditapp.ui.posts.list

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.zalocoders.redditapp.data.models.post.Children
import com.zalocoders.redditapp.data.models.post.toFavouritePostEntity
import com.zalocoders.redditapp.databinding.FragmentPostsListBinding
import com.zalocoders.redditapp.utils.showErrorSnackbar
import com.zalocoders.redditapp.utils.showSnackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

@AndroidEntryPoint
class PostsListFragment : Fragment(),OnClickListener{
	private lateinit var binding:FragmentPostsListBinding
	private lateinit var postAdapter:PostsAdapter
	private var ids = mutableListOf<String>()
	
	private val viewModel:PostListViewModel by viewModels()
	
	
	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
							  savedInstanceState: Bundle?): View {
		binding = FragmentPostsListBinding.inflate(layoutInflater)
		return binding.root
	}
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		observeViewModel()
		setUpRecyclerView()
		setUpViews()
		getPost()
		setUpLoading()
		searchCloseListener()
		setUpSearch()
	}
	
	private fun setUpViews(){
		binding.favourites.setOnClickListener {
			val action = PostsListFragmentDirections.actionPostsListFragmentToFavouriteListFragment()
			findNavController().navigate(action)
		}
	}
	
	private fun observeViewModel(){
		viewModel.isSaved.observe(viewLifecycleOwner,{
			when(it){
				true -> view?.showSnackbar("Added to Favourites",Snackbar.LENGTH_SHORT)
				false -> view?.showErrorSnackbar("Failed Adding",Snackbar.LENGTH_SHORT)
			}
		})
		
		viewModel.isDeleted.observe(viewLifecycleOwner,{
			when(it){
				true -> view?.showErrorSnackbar("Removed from Favourites",Snackbar.LENGTH_SHORT)
				false -> view?.showErrorSnackbar("Failed removing",Snackbar.LENGTH_SHORT)
			}
		})
	}
	
	private fun setUpRecyclerView() {
		postAdapter = PostsAdapter(this,ids)
		binding.postRecyclerview.apply {
			layoutManager = LinearLayoutManager(context)
			setHasFixedSize(true)
			adapter = postAdapter
		}
		
		viewModel.getFavourite().observe(viewLifecycleOwner,{ item ->
			ids.clear()
			ids.addAll(item.map { it.favourite_id })
		})
		
	}
	
	private fun getPost(){
			lifecycleScope.launchWhenStarted{
			viewModel.getTopPosts().collectLatest {
				postAdapter.submitData(it)
			}
		}
	}
	
	private fun setUpSearchData(query:String){
		lifecycleScope.launchWhenStarted{
			viewModel.getSearchedItems(query).collectLatest {
				postAdapter.submitData(it)
			}
		}
	}
	
	private fun setUpLoading(){
		postAdapter.addLoadStateListener { loadState ->
		
			if (loadState.refresh is LoadState.Loading){
				binding.progressBar.visibility = View.VISIBLE
			}
			else{
				binding.progressBar.visibility = View.GONE
				val error = when {
					loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
					loadState.append is LoadState.Error -> loadState.append as LoadState.Error
					loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
					else -> null
				}
				error?.let {
					Toast.makeText(context, it.error.message, Toast.LENGTH_LONG).show()
					Timber.e("Error ${it.error.localizedMessage}")
				}
			}
		}
	}
	
	private fun setUpSearch() {
		
		binding.multiSearchView.isIconified = true
		
		binding.multiSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
			
			override fun onQueryTextChange(newText: String): Boolean {
				return false
			}
			
			override fun onQueryTextSubmit(query: String): Boolean {
				setUpSearchData(query)
				closeKeyBoard()
				return true
			}
		})
	}
	
	private fun searchCloseListener() {
		binding.multiSearchView.setOnCloseListener {
			closeKeyBoard()
			getPost()
			true
		}
	}
	
	private fun closeKeyBoard() {
		val imm =
				activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
		imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
	}
	
	override fun addFavourite(item: Children) {
		viewModel.saveFavouriteToDb(item.toFavouritePostEntity())
	}
	
	override fun deleteFavourite(item: Children) {
		viewModel.deleteFavourite(item.child_data.id)
	}
	
	override fun favouriteDetails(item: Children) {
		val action = PostsListFragmentDirections.actionPostsListFragmentToPostDetailsFragment(item)
		findNavController().navigate(action)
	}
}