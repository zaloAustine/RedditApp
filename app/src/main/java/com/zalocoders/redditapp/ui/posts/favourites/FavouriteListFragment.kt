package com.zalocoders.redditapp.ui.posts.favourites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zalocoders.redditapp.databinding.FragmentFavouriteListBinding


class FavouriteListFragment : Fragment() {
	private lateinit var binding:FragmentFavouriteListBinding
	
	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
							  savedInstanceState: Bundle?): View {
		binding = FragmentFavouriteListBinding.inflate(layoutInflater)
		return binding.root
	}
	
}