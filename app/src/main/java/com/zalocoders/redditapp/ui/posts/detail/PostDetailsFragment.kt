package com.zalocoders.redditapp.ui.posts.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zalocoders.redditapp.databinding.FragmentPostsListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostDetailsFragment : Fragment() {
	private lateinit var binding:FragmentPostsListBinding
	
	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
							  savedInstanceState: Bundle?): View {
		binding = FragmentPostsListBinding.inflate(layoutInflater)
		return binding.root
	}
	
}