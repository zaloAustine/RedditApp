package com.zalocoders.redditapp.ui.posts.favourites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zalocoders.redditapp.data.models.post.FavouritePostEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import timber.log.Timber


@HiltViewModel
class FavouriteListViewModel @Inject constructor(
		private var repository: FavouriteListRepository
):ViewModel() {
	
	private val disposable = CompositeDisposable()
	
	private val _isSaved = MutableLiveData<Boolean>()
	val isSaved: LiveData<Boolean>
		get() = _isSaved
	
	private val _isDeleted = MutableLiveData<Boolean>()
	val isDeleted: LiveData<Boolean>
		get() = _isDeleted
	
	private val _isCleared = MutableLiveData<Boolean>()
	val isCleared: LiveData<Boolean>
		get() = _isCleared
	
	fun getFavourite():LiveData<List<FavouritePostEntity>>{
		return repository.getAllFavourites()
	}
	
	
	fun deleteAllFavourites(){
		disposable.add(repository.deleteAllFavourite()
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe({
					_isCleared.value = true
				},{
					_isCleared.value = false
				}))
	}
	
	
	fun deleteFavourite(id:String){
		disposable.add(repository.deleteFavourite(id)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe({
					_isDeleted.value = true
				},{
					_isDeleted.value = false
				}))
	}
	
	fun saveFavouriteToDb(favouritePostEntity: FavouritePostEntity){
		disposable.add(repository.saveFavouriteToDb(favouritePostEntity)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe({
					_isCleared.value = true
				},{
					_isSaved.value = false
				}))
	}
	
	override fun onCleared() {
		super.onCleared()
		disposable.clear()
	}
}