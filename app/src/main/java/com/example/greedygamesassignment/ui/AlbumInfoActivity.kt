package com.example.greedygamesassignment.ui

import android.content.Intent
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.greedygamesassignment.Constants
import com.example.greedygamesassignment.Constants.ALBUM_NAME
import com.example.greedygamesassignment.Constants.ARTIST_NAME
import com.example.greedygamesassignment.R
import com.example.greedygamesassignment.Utils.setMessageWithClickableLink
import com.example.greedygamesassignment.databinding.ActivityAlbumInfoBinding
import com.example.greedygamesassignment.datamodels.Tag
import com.example.greedygamesassignment.network.Client
import com.example.greedygamesassignment.network.Resource
import com.example.greedygamesassignment.repo.Repo
import com.example.greedygamesassignment.ui.adapter.GenreRvAdapter
import com.example.greedygamesassignment.viewmodel.AlbumInfoViewModel
import com.squareup.picasso.Picasso

class AlbumInfoActivity : AppCompatActivity(), GenreRvAdapter.OnItemClickListener {
    lateinit var binding: ActivityAlbumInfoBinding
    private lateinit var viewModel: AlbumInfoViewModel
    private val genreList = mutableListOf<Tag>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlbumInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val apiService = Client.api
        val repo = Repo(apiService)
        val factory = AlbumInfoViewModel.AlbumInfoViewModelFactory(repo)
        viewModel = ViewModelProvider(this, factory)[AlbumInfoViewModel::class.java]
        val artist = intent.getStringExtra(ARTIST_NAME)
        val album = intent.getStringExtra(ALBUM_NAME)
        if (artist != null && album != null)
            viewModel.getAlbumInfo(artist, album)
        binding.backIv.setOnClickListener {
            onBackPressed()
        }
        getObserver()
    }

    private fun getObserver() {
        viewModel.albumInfo.observe(this) {
            when (it) {
                is Resource.Error -> {
                    it.message?.let { message ->
                        binding.loadingPb.visibility = View.GONE
                        Toast.makeText(this, message, Toast.LENGTH_LONG).show()

                    }
                }
                is Resource.Loading -> {
                    binding.loadingPb.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.loadingPb.visibility = View.GONE
                    it.data?.albumInfo?.apply {
                        Picasso.get()
                            .load(this.image?.get(this.image.size - 1)?.text)
                            .placeholder(R.drawable.ic_launcher_background)
                            .error(R.drawable.ic_launcher_background)
                            .into(binding.albumIv)
                        binding.albumNameTv.text = this.name
                        binding.artistNameTv.text = this.artist
                        binding.albumDescriptionTv.setMessageWithClickableLink(
                            this.wiki?.summary
                        )
                        binding.albumDescriptionTv.movementMethod = LinkMovementMethod.getInstance()
                        val adapter = this.tags?.tag?.let { genreListData ->
                            GenreRvAdapter(
                                genreListData,
                                this@AlbumInfoActivity
                            )
                        }
                        binding.genreRv.layoutManager = LinearLayoutManager(
                            this@AlbumInfoActivity,
                            LinearLayoutManager.HORIZONTAL,
                            false
                        )
                        binding.genreRv.adapter = adapter

                    }
                }
            }
        }
    }

    override fun onItemClicked(genreName: String) {
        val intent = Intent(this, GenreDetailActivity::class.java)
        intent.putExtra(Constants.TAG, genreName)
        startActivity(intent)
    }
}