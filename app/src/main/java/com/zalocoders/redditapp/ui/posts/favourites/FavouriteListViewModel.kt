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
	
	private val _favouritesLiveData = MutableLiveData<List<FavouritePostEntity>>()
	
	val favouritesLiveData: LiveData<List<FavouritePostEntity>>
	get() = _favouritesLiveData
	
	fun getFavourite(){
		disposable.add(repository.getAllFavourites()
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe({ _favouritesLiveData.postValue(it) },
						{ error -> Timber.e(error.localizedMessage) }))
	}
	
	
	fun deleteFavourite(id:String){
		disposable.add(repository.deleteFavourite(id)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe())
	}
	
	fun deleteAllFavourites(){
		disposable.add(repository.deleteAllFavourite()
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe())
		
	}
	
	override fun onCleared() {
		super.onCleared()
		disposable.clear()
	}
}