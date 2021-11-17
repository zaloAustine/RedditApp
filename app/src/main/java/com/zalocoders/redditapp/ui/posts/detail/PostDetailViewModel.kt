package com.zalocoders.redditapp.ui.posts.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zalocoders.redditapp.data.models.post.FavouritePostEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


@HiltViewModel
class PostDetailViewModel @Inject constructor(
		private var repository: PostDetailRepository
):ViewModel() {
	
	private val disposable = CompositeDisposable()
	
	private val _isSaved = MutableLiveData<Boolean>()
	val isSaved: LiveData<Boolean>
		get() = _isSaved
	
	private val _isDeleted = MutableLiveData<Boolean>()
	val isDeleted: LiveData<Boolean>
		get() = _isDeleted
	
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
	
	override fun onCleared() {
		super.onCleared()
		disposable.dispose()
	}
}