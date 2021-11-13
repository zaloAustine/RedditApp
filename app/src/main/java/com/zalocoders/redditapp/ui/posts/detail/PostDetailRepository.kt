package com.zalocoders.redditapp.ui.posts.detail


import android.annotation.SuppressLint
import com.zalocoders.redditapp.data.models.post.FavouritePostEntity
import com.zalocoders.redditapp.db.FavouritePostsDao
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject


class PostDetailRepository @Inject constructor(
		private val favouritePostsDao: FavouritePostsDao
) {
	
	fun getAllFavourites():Single<List<FavouritePostEntity>>{
	return favouritePostsDao.getAllFavouritePost()
	}
	
	fun deleteFavourite(id:String):Single<Int>{
		return favouritePostsDao.deletePost(id)
	}
	
	@SuppressLint("CheckResult")
	fun saveFavouriteToDb(post:FavouritePostEntity): Completable {
		return favouritePostsDao.insertFavourite(post)
	}
}


