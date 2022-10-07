package com.example.greedygamesassignment.ui.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.greedygamesassignment.Constants
import com.example.greedygamesassignment.ui.AlbumFragment
import com.example.greedygamesassignment.ui.ArtistFragment
import com.example.greedygamesassignment.ui.TrackFragment

class PagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    private val genre: String
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return AlbumFragment()
            1 -> {
                val artistFragment = ArtistFragment()
                artistFragment.arguments = Bundle().apply {
                    putString(Constants.TAG, genre)
                }
                return artistFragment
            }
        }
        val trackFragment = TrackFragment()
        trackFragment.arguments = Bundle().apply {
            putString(Constants.TAG, genre)
        }
        return trackFragment
    }

    override fun getItemCount(): Int {
        return 3
    }
}