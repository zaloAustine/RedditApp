package com.zalocoders.redditapp.ui.posts.list

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.zalocoders.redditapp.data.models.post.Children
import com.zalocoders.redditapp.data.models.post.FavouritePostEntity
import com.zalocoders.redditapp.db.FavouritePostsDao
import com.zalocoders.redditapp.network.ApiService
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import timber.log.Timber


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
					nextKey = nextPage + 2
			)
		} catch (e: Exception) {
			LoadResult.Error(e)
		}
	}
	
	// The refresh key is used for subsequent refresh calls to PagingSource.load after the initial load
	@ExperimentalPagingApi
	override fun getRefreshKey(state: PagingState<Int, Children>): Int? {
		// We need to get the previous key (or next key if previous is null) of the page
		// that was closest to the most recently accessed index.
		// Anchor position is the most recently accessed index
		return state.anchorPosition?.let { anchorPosition ->
			state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
					?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
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
					nextKey = nextPage + 2
			)
		} catch (e: Exception) {
			LoadResult.Error(e)
		}
	}
}