package com.zalocoders.redditapp.ui.posts.list

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.zalocoders.redditapp.data.models.post.Children
import com.zalocoders.redditapp.data.models.post.FavouritePostEntity
import com.zalocoders.redditapp.db.FavouritePostsDao
import com.zalocoders.redditapp.network.ApiService
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow


class PostListRepository @Inject constructor(
		private val apiService: ApiService,
		private val favouritePostsDao: FavouritePostsDao
) {
	 fun getTopPosts(): Flow<PagingData<Children>> {
	 	return Pager(PagingConfig(pageSize = 25)) {
			PostPagingSource(apiService)
		}.flow
	 }
	
	fun getSearchedItems(query: String):Flow<PagingData<Children>> {
			return Pager(PagingConfig(pageSize = 25)) {
				SearchDataSource(apiService,query)
			}.flow
	}
	
	@SuppressLint("CheckResult")
	fun saveFavouriteToDb(post:FavouritePostEntity): Completable {
		return favouritePostsDao.insertFavourite(post)
	}
	
	fun deleteFavourite(id:String): Single<Int> {
		return favouritePostsDao.deletePost(id)
	}
	
	fun getAllFavourites(): LiveData<List<FavouritePostEntity>> {
		return favouritePostsDao.getAllFavouritePost()
	}
}

class PostPagingSource(
		private val apiService: ApiService
) : PagingSource<Int, Children>() {
	
	override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Children> {
		return try {
			val nextPage = params.key ?: 1
			val response = apiService.getTopPosts("all",nextPage.toString(),"poo")
			
			LoadResult.Page(
					data = response.data.children,
					prevKey = if (nextPage == 1) null else nextPage - 1,
					nextKey = response.data.dist + 1
			)
		} catch (e: Exception) {
			LoadResult.Error(e)
		}
	}
}


class SearchDataSource(
		private val apiService: ApiService,
		private val query:String
) : PagingSource<Int, Children>() {
	
	override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Children> {
		return try {
			val nextPage = params.key ?: 1
			val response = apiService.searchPosts(query,"all",nextPage.toString())
			
			LoadResult.Page(
					data = response.data.children,
					prevKey = if (nextPage == 1) null else nextPage - 1,
					nextKey = response.data.dist + 1
			)
		} catch (e: Exception) {
			LoadResult.Error(e)
		}
	}
}