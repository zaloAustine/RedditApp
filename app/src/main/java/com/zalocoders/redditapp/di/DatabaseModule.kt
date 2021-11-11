package com.zalocoders.redditapp.di

import android.content.Context
import androidx.room.Room
import com.zalocoders.redditapp.db.RedditDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
	
	@Provides
	fun provideWeatherDatabase(@ApplicationContext context: Context) =
			Room.databaseBuilder(context, RedditDatabase::class.java, "reddit_db")
					.fallbackToDestructiveMigration()
					.build()
	
	@Provides
	fun provideFavouriteDao(database: RedditDatabase) = database.favouriteDao()

}
