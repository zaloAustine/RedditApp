package com.zalocoders.redditapp.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zalocoders.redditapp.data.models.post.FavouritePostEntity
import io.reactivex.Single


@Dao
interface FavouritePostsDao {
	
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	 fun insertFavourite(post: FavouritePostEntity):Long
	
	@Query("SELECT * FROM favourite_posts")
	fun getAllFavouritePost(): PagingSource<Int, FavouritePostEntity>
	
	@Query("DELETE FROM favourite_posts")
	 fun deleteAllPosts():Single<Int>
	
	@Query("DELETE FROM favourite_posts WHERE favourite_id = :id")
	fun deletePost(id:Int):Single<Int>
}
