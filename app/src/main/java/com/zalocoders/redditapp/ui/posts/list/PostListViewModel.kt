package com.zalocoders.redditapp.ui.posts.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.zalocoders.redditapp.data.models.post.FavouritePostEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import timber.log.Timber


@HiltViewModel
class PostListViewModel @Inject constructor(
		private var repository: PostListRepository
):ViewModel() {
	private val disposable = CompositeDisposable()
	
	private val _isSaved = MutableLiveData<Boolean>()
	val isSaved: LiveData<Boolean>
		get() = _isSaved
	
	private val _isDeleted = MutableLiveData<Boolean>()
	val isDeleted: LiveData<Boolean>
		get() = _isDeleted
	
	private val _favouritesLiveData = MutableLiveData<List<FavouritePostEntity>>()
	val favouritesLiveData: LiveData<List<FavouritePostEntity>>
		get() = _favouritesLiveData
	
	fun getTopPosts() = repository.getTopPosts().cachedIn(viewModelScope)
	
	fun saveFavouriteToDb(favouritePostEntity: FavouritePostEntity){
		disposable.add(repository.saveFavouriteToDb(favouritePostEntity)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe({
					_isSaved.postValue(true)
				},{
					_isSaved.postValue(false)
				}))
	}
	
	fun deleteFavourite(id:String){
		disposable.add(repository.deleteFavourite(id)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe({
					_isDeleted.postValue(true)
				},{
					_isDeleted.postValue(false)
				}))
	}
	
	fun getFavourite(){
		disposable.add(repository.getAllFavourites()
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe({ _favouritesLiveData.postValue(it) },
						{ error -> Timber.e(error.localizedMessage) }))
	}
	
	
	override fun onCleared() {
		super.onCleared()
		disposable.clear()
	}
}