package com.zalocoders.redditapp.ui.posts.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.zalocoders.redditapp.data.models.post.FavouritePostEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


@HiltViewModel
class PostListViewModel @Inject constructor(
		private var repository: PostListRepository
):ViewModel() {
	
	fun getTopPosts() = repository.getTopPosts().cachedIn(viewModelScope)
	
	private fun insertFavourite(post:FavouritePostEntity){
		Single.fromCallable { repository.saveFavouriteToDb(post) }
				.subscribeOn(Schedulers.io())
				.subscribe()
	}
	
	fun deleteFavourite(id:Int){
		Single.fromCallable {repository.deleteFavourite(id) }
				.subscribeOn(Schedulers.io())
				.subscribe()
	}
	
	fun deleteAllFavourites(){
		Single.fromCallable {repository.deleteAllFavourite() }
				.subscribeOn(Schedulers.io())
				.subscribe()
		
	}
}