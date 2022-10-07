package com.example.greedygamesassignment.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.greedygamesassignment.Constants.ALBUM_NAME
import com.example.greedygamesassignment.Constants.ARTIST_NAME
import com.example.greedygamesassignment.databinding.FragmentAlbumBinding
import com.example.greedygamesassignment.datamodels.Album
import com.example.greedygamesassignment.network.Client
import com.example.greedygamesassignment.network.Resource
import com.example.greedygamesassignment.repo.Repo
import com.example.greedygamesassignment.ui.adapter.AlbumRvAdapter
import com.example.greedygamesassignment.viewmodel.GenreDetailViewModel

class AlbumFragment : Fragment(), AlbumRvAdapter.OnItemClickListener {

    private lateinit var binding: FragmentAlbumBinding
    private lateinit var viewModel: GenreDetailViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val apiService = Client.api
        val repo = Repo(apiService)
        val factory = GenreDetailViewModel.GenreDetailViewModelFactory(repo)
        viewModel = ViewModelProvider(requireActivity(), factory)[GenreDetailViewModel::class.java]
        getObserver()
        binding = FragmentAlbumBinding.inflate(layoutInflater)
        return binding.root
    }

    private fun getObserver() {
        viewModel.albumList.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Error -> {
                }
                is Resource.Loading -> {
                }
                is Resource.Success -> {
                    val adapter =

                        it.data?.albums?.album?.let { albumData -> AlbumRvAdapter(albumData, this) }
                    binding.albumRv.layoutManager = GridLayoutManager(requireContext(), 2)
                    binding.albumRv.adapter = adapter
                }
            }
        }
    }

    override fun onItemClicked(album: Album) {
        val intent = Intent(requireActivity(), AlbumInfoActivity::class.java)
        intent.putExtra(ALBUM_NAME, album.name)
        intent.putExtra(ARTIST_NAME, album.artist?.name)
        startActivity(intent)
    }
}