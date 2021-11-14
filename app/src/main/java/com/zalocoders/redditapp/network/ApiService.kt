package com.zalocoders.redditapp.network

import com.zalocoders.redditapp.data.models.post.PostsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
	
	@GET("top.json")
	suspend fun getTopPosts(
			@Query("t") t: String,
			@Query("limit") limit: String,
			@Query("after") after: String,
			):PostsResponse
	
	
	@GET("search.json")
	suspend fun searchPosts(
			@Query("q") query: String,
			@Query("after") t: String,
			@Query("limit") limit: String,
	): PostsResponse
}
