package com.zalocoders.redditapp.ui.posts.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.zalocoders.redditapp.databinding.FragmentPostsListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

@AndroidEntryPoint
class PostsListFragment : Fragment() {
	private lateinit var binding:FragmentPostsListBinding
	private lateinit var postAdapter:PostsAdapter
	
	private val viewModel:PostListViewModel by viewModels()
	
	
	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
							  savedInstanceState: Bundle?): View {
		binding = FragmentPostsListBinding.inflate(layoutInflater)
		return binding.root
	}
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		setUpRecyclerView()
		getPost()
		setUpLoading()
		
		lifecycleScope.launchWhenCreated {
			viewModel.ins()
		}
		
	}
	
	private fun setUpRecyclerView() {
		 postAdapter = PostsAdapter()
			binding.postRecyclerview.apply {
				layoutManager = LinearLayoutManager(context)
				setHasFixedSize(true)
				adapter = postAdapter
			}
	}
	
	private fun getPost(){
			lifecycleScope.launchWhenStarted{
			viewModel.getTopPosts().collectLatest {
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
}