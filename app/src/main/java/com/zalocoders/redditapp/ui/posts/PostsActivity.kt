package com.zalocoders.redditapp.ui.posts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.zalocoders.redditapp.R
import com.zalocoders.redditapp.databinding.ActivityPostsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostsActivity : AppCompatActivity() {
	
	private val binding:ActivityPostsBinding by lazy{
		ActivityPostsBinding.inflate(layoutInflater)
	}
	private lateinit var appBarConfiguration: AppBarConfiguration
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(binding.root)
		
		val navHostFragment =
				supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
		val navController = navHostFragment.navController
		
		appBarConfiguration = AppBarConfiguration(navController.graph)
		binding.toolbar.setupWithNavController(navController, appBarConfiguration)
	}
}