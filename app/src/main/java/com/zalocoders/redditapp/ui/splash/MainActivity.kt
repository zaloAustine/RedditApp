package com.zalocoders.redditapp.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.zalocoders.redditapp.databinding.ActivityMainBinding
import com.zalocoders.redditapp.ui.posts.PostsActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
	
	private val binding: ActivityMainBinding by lazy{
		ActivityMainBinding.inflate(layoutInflater)
	}
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(binding.root)
		
		Handler().postDelayed({
			val i = Intent(this, PostsActivity::class.java)
			startActivity(i)
			finish()
		}, 3000)
		
	}
}