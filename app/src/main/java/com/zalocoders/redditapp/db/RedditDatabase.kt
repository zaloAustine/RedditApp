package com.zalocoders.redditapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zalocoders.redditapp.data.models.post.FavouritePostEntity

@Database(entities = [FavouritePostEntity::class], version = 1, exportSchema = false)
abstract class RedditDatabase:RoomDatabase() {
	abstract fun favouriteDao():FavouritePostsDao
}