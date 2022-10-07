package com.example.greedygamesassignment.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.greedygamesassignment.Constants.TAG
import com.example.greedygamesassignment.R
import com.example.greedygamesassignment.Utils.setMessageWithClickableLink
import com.example.greedygamesassignment.databinding.ActivityGenreDetailBinding
import com.example.greedygamesassignment.network.Client
import com.example.greedygamesassignment.network.Resource
import com.example.greedygamesassignment.repo.Repo
import com.example.greedygamesassignment.ui.adapter.PagerAdapter
import com.example.greedygamesassignment.viewmodel.GenreDetailViewModel
import com.google.android.material.tabs.TabLayoutMediator

class GenreDetailActivity : AppCompatActivity() {
    private lateinit var viewModel: GenreDetailViewModel
    private lateinit var binding: ActivityGenreDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGenreDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val apiService = Client.api
        val repo = Repo(apiService)
        val factory = GenreDetailViewModel.GenreDetailViewModelFactory(repo)
        viewModel = ViewModelProvider(this, factory)[GenreDetailViewModel::class.java]
        val genre = intent.getStringExtra(TAG)
        genre?.let {
            viewModel.getInitialData(it)
        }
        val pagerAdapter = PagerAdapter(supportFragmentManager, lifecycle, genre!!)
        binding.viewPager.adapter = pagerAdapter
        binding.viewPager.offscreenPageLimit = 1
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            val tabNames = listOf(
                getString(R.string.albums), getString(R.string.artists), getString(
                    R.string.tracks
                )
            )
            tab.text = tabNames[position]
        }.attach()
        getObserver()

        binding.backIv.setOnClickListener {
            onBackPressed()
        }
    }

    private fun getObserver() {
        viewModel.genreInfo.observe(this) {
            when (it) {
                is Resource.Error -> {
                    it.message?.let { message ->
                        binding.loadingPb.visibility = View.GONE
                        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                    }
                }
                is Resource.Loading -> {
                    binding.loadingPb.visibility = View.VISIBLE
                    binding.tabLayout.visibility = View.GONE
                    binding.viewPager.visibility = View.GONE
                }
                is Resource.Success -> {
                    binding.loadingPb.visibility = View.GONE
                    binding.tabLayout.visibility = View.VISIBLE
                    binding.viewPager.visibility = View.VISIBLE
                    binding.genreNameTv.text = it.data?.tag?.name?.replaceFirstChar(Char::titlecase)
                    binding.descriptionTv.setMessageWithClickableLink(
                        it.data?.tag?.wiki?.summary
                    )
                }
            }
        }
    }
}