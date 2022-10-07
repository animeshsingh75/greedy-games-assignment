package com.example.greedygamesassignment.ui

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.greedygamesassignment.Constants
import com.example.greedygamesassignment.R
import com.example.greedygamesassignment.Utils.setMessageWithClickableLink
import com.example.greedygamesassignment.databinding.ActivityArtistInfoBinding
import com.example.greedygamesassignment.datamodels.Album
import com.example.greedygamesassignment.network.Client
import com.example.greedygamesassignment.network.Resource
import com.example.greedygamesassignment.repo.Repo
import com.example.greedygamesassignment.ui.adapter.AlbumRvAdapter
import com.example.greedygamesassignment.ui.adapter.GenreRvAdapter
import com.example.greedygamesassignment.ui.adapter.TrackRvAdapter
import com.example.greedygamesassignment.viewmodel.ArtistInfoViewModel
import com.squareup.picasso.Picasso
import kotlin.math.ln
import kotlin.math.pow

class ArtistInfoActivity : AppCompatActivity(), GenreRvAdapter.OnItemClickListener,
    AlbumRvAdapter.OnItemClickListener {
    lateinit var binding: ActivityArtistInfoBinding
    private lateinit var viewModel: ArtistInfoViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArtistInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val apiService = Client.api
        val repo = Repo(apiService)
        val factory = ArtistInfoViewModel.ArtistInfoViewModelFactory(repo)
        viewModel = ViewModelProvider(this, factory)[ArtistInfoViewModel::class.java]
        val artist = intent.getStringExtra(Constants.ARTIST_NAME)
        Log.d("ArtistInfoActivity", "onCreate: $artist")
        artist?.let { viewModel.getData(it) }
        getObserver()
    }

    private fun getObserver() {
        viewModel.artistInfo.observe(this) {
            when (it) {
                is Resource.Error -> {
                    it.message?.let { message ->
                        binding.loadingPb.visibility = View.GONE
                        Toast.makeText(this, message, Toast.LENGTH_LONG).show()

                    }
                }
                is Resource.Loading -> {
                    binding.group.visibility=View.GONE
                    binding.loadingPb.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.group.visibility=View.VISIBLE
                    binding.loadingPb.visibility = View.GONE
                    it.data?.artistInfo?.apply {
                        Picasso.get()
                            .load(this.image?.get(this.image.size - 1)?.text)
                            .placeholder(R.drawable.ic_launcher_background)
                            .error(R.drawable.ic_launcher_background)
                            .into(binding.artistIv)
                        binding.artistNameTv.text = this.name
                        binding.albumDescriptionTv.setMessageWithClickableLink(this.bio?.summary)

                        binding.followersNoTv.text = getFormattedNumber(this.stats?.listeners)
                        binding.playCountNoTv.text = getFormattedNumber(this.stats?.playcount)
                        binding.albumDescriptionTv.movementMethod = LinkMovementMethod.getInstance()
                        val adapter = this.tags?.tag?.let { genreListData ->
                            GenreRvAdapter(
                                genreListData,
                                this@ArtistInfoActivity
                            )
                        }
                        binding.genreRv.layoutManager = LinearLayoutManager(
                            this@ArtistInfoActivity,
                            LinearLayoutManager.HORIZONTAL,
                            false
                        )
                        binding.genreRv.adapter = adapter

                    }
                }
            }
        }

        viewModel.trackList.observe(this) {
            when (it) {
                is Resource.Error -> {
                }
                is Resource.Loading -> {
                }
                is Resource.Success -> {
                    val adapter =
                        it.data?.tracks?.track?.let { trackData -> TrackRvAdapter(trackData) }
                    binding.trackRv.layoutManager =
                        LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                    binding.trackRv.adapter = adapter
                }
            }
        }

        viewModel.albumList.observe(this) {
            when (it) {
                is Resource.Error -> {
                }
                is Resource.Loading -> {
                }
                is Resource.Success -> {
                    val adapter =
                        it.data?.albums?.album?.let { albumData -> AlbumRvAdapter(albumData, this) }
                    binding.albumRv.layoutManager =
                        LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                    binding.albumRv.adapter = adapter
                }
            }
        }
    }

    private fun getFormattedNumber(count: Int?): String? {
        if (count != null) {
            if (count < 1000) return "" + count
            val exp = (ln(count.toDouble()) / ln(1000.0)).toInt()
            return String.format("%.1f %c", count / 1000.0.pow(exp.toDouble()), "kMGTPE"[exp - 1])
        }
        return null
    }

    override fun onItemClicked(genreName: String) {
        val intent = Intent(this, GenreDetailActivity::class.java)
        intent.putExtra(Constants.TAG, genreName)
        startActivity(intent)
    }

    override fun onItemClicked(album: Album) {
        val intent = Intent(this, AlbumInfoActivity::class.java)
        intent.putExtra(Constants.ALBUM_NAME, album.name)
        intent.putExtra(Constants.ARTIST_NAME, album.artist?.name)
        startActivity(intent)
    }
}