package com.zalocoders.redditapp.ui.posts.favourites


import com.zalocoders.redditapp.data.models.post.FavouritePostEntity
import com.zalocoders.redditapp.db.FavouritePostsDao
import io.reactivex.Single
import javax.inject.Inject


class FavouriteListRepository @Inject constructor(
		private val favouritePostsDao: FavouritePostsDao
) {
	
	fun getAllFavourites():Single<List<FavouritePostEntity>>{
	return favouritePostsDao.getAllFavouritePost()
	}
	
	fun deleteFavourite(id:String):Single<Int>{
		return favouritePostsDao.deletePost(id)
	}
	
	
	fun deleteAllFavourite():Single<Int>{
		return favouritePostsDao.deleteAllPosts()
	}
}


