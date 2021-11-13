package com.zalocoders.redditapp.ui.posts.favourites


import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import com.zalocoders.redditapp.data.models.post.FavouritePostEntity
import com.zalocoders.redditapp.db.FavouritePostsDao
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject


class FavouriteListRepository @Inject constructor(
		private val favouritePostsDao: FavouritePostsDao
) {
	
	fun getAllFavourites():LiveData<List<FavouritePostEntity>>{
	return favouritePostsDao.getAllFavouritePost()
	}
	
	fun deleteFavourite(id:String):Single<Int>{
		return favouritePostsDao.deletePost(id)
	}
	
	fun saveFavouriteToDb(post:FavouritePostEntity): Completable {
		return favouritePostsDao.insertFavourite(post)
	}
	
	fun deleteAllFavourite():Single<Int>{
		return favouritePostsDao.deleteAllPosts()
	}
}


