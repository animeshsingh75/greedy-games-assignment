package com.example.greedygamesassignment.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.greedygamesassignment.Constants.TAG
import com.example.greedygamesassignment.R
import com.example.greedygamesassignment.databinding.ActivityMainBinding
import com.example.greedygamesassignment.datamodels.Tag
import com.example.greedygamesassignment.network.Client
import com.example.greedygamesassignment.network.Resource
import com.example.greedygamesassignment.repo.Repo
import com.example.greedygamesassignment.ui.adapter.GenreRvAdapter
import com.example.greedygamesassignment.viewmodel.MainActivityViewModel

class MainActivity : AppCompatActivity(), GenreRvAdapter.OnItemClickListener {
    private lateinit var binding: ActivityMainBinding
    private var showAll = false
    private val genreList = mutableListOf<Tag>()
    private val adapterList = mutableListOf<Tag>()
    private lateinit var adapter: GenreRvAdapter
    private lateinit var mainActivityViewModel: MainActivityViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val apiService = Client.api
        val repo = Repo(apiService)
        val factory = MainActivityViewModel.MainActivityViewModelFactory(repo)
        mainActivityViewModel = ViewModelProvider(this, factory)[MainActivityViewModel::class.java]
        mainActivityViewModel.getGenreList()
        getObserver()
        binding.genreChooseIv.setOnClickListener {
            if (showAll) {
                adapterList.clear()
                adapterList.addAll(genreList.subList(0,10))
                binding.genreChooseIv.setImageResource(R.drawable.ic_arrow_down_circle)
                showAll=false
            } else {
                adapterList.clear()
                adapterList.addAll(genreList)
                binding.genreChooseIv.setImageResource(R.drawable.ic_arrow_up_circle)
                showAll=true
            }
            adapter.notifyDataSetChanged()
        }
    }

    private fun getObserver() {
        mainActivityViewModel.genreList.observe(this) {
            when (it) {
                is Resource.Error -> {
                    it.message?.let { message ->
                        binding.loadingPb.visibility = View.GONE
                        binding.genreChooseIv.isEnabled = true
                        Toast.makeText(this, message, Toast.LENGTH_LONG).show()

                    }
                }
                is Resource.Loading -> {
                    binding.genreChooseIv.isEnabled = false
                    binding.loadingPb.visibility = View.VISIBLE
                    binding.genreRv.visibility = View.GONE
                }
                is Resource.Success -> {
                    binding.loadingPb.visibility = View.GONE
                    binding.genreChooseIv.isEnabled = true
                    binding.genreRv.visibility = View.VISIBLE
                    it.data?.topTags?.tag?.let { genreListData -> genreList.addAll(genreListData) }
                    adapterList.addAll(genreList.subList(0, 10))
                    adapter = GenreRvAdapter(adapterList, this)
                    binding.genreRv.layoutManager = GridLayoutManager(this, 3)
                    binding.genreRv.adapter = adapter
                }
            }
        }
    }

    override fun onItemClicked(genreName: String) {
        val intent=Intent(this,GenreDetailActivity::class.java)
        intent.putExtra(TAG,genreName)
        startActivity(intent)
    }
}