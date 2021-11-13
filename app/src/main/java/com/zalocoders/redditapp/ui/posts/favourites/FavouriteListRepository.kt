package com.zalocoders.redditapp.ui.posts.favourites

import android.annotation.SuppressLint
import androidx.paging.PagingData
import com.zalocoders.redditapp.data.models.post.Children
import com.zalocoders.redditapp.data.models.post.FavouritePostEntity
import com.zalocoders.redditapp.db.FavouritePostsDao
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject


class FavouriteListRepository @Inject constructor(
		private val favouritePostsDao: FavouritePostsDao
) {
	
	fun getAllFavourites():Single<List<FavouritePostEntity>>{
	return favouritePostsDao.getAllFavouritePost()
	}
	
	@SuppressLint("CheckResult")
	fun saveFavouriteToDb(post:FavouritePostEntity): Completable {
		return favouritePostsDao.insertFavourite(post)
	}
	
	fun deleteFavourite(id:Int):Single<Int>{
		return favouritePostsDao.deletePost(id)
	}
	
	
	fun deleteAllFavourite():Single<Int>{
		return favouritePostsDao.deleteAllPosts()
	}
}


