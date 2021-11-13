package com.zalocoders.redditapp.ui.posts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.zalocoders.redditapp.databinding.ActivityPostsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostsActivity : AppCompatActivity() {
	
	private val binding:ActivityPostsBinding by lazy{
		ActivityPostsBinding.inflate(layoutInflater)
	}
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(binding.root)

	}
}