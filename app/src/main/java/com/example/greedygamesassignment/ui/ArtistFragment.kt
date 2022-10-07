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
import com.example.greedygamesassignment.Constants
import com.example.greedygamesassignment.Constants.TAG
import com.example.greedygamesassignment.databinding.FragmentArtistBinding
import com.example.greedygamesassignment.datamodels.ArtistData
import com.example.greedygamesassignment.network.Client
import com.example.greedygamesassignment.network.Resource
import com.example.greedygamesassignment.repo.Repo
import com.example.greedygamesassignment.ui.adapter.ArtistRvAdapter
import com.example.greedygamesassignment.viewmodel.GenreDetailViewModel

class ArtistFragment : Fragment(), ArtistRvAdapter.OnItemClickListener {

    private lateinit var binding: FragmentArtistBinding
    private lateinit var viewModel: GenreDetailViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentArtistBinding.inflate(layoutInflater)
        val apiService = Client.api
        val repo = Repo(apiService)
        val factory = GenreDetailViewModel.GenreDetailViewModelFactory(repo)
        viewModel = ViewModelProvider(requireActivity(), factory)[GenreDetailViewModel::class.java]
        arguments?.getString(TAG)?.let { viewModel.getArtistList(it) }
        getObserver()
        return binding.root
    }

    private fun getObserver() {
        viewModel.artistList.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Error -> {
                    Toast.makeText(requireActivity(), it.message, Toast.LENGTH_LONG).show()
                }
                is Resource.Loading -> {
                }
                is Resource.Success -> {
                    val adapter =
                        it.data?.topArtists?.artist?.let { artistData ->
                            ArtistRvAdapter(
                                artistData,
                                this
                            )
                        }
                    binding.artistRv.layoutManager = GridLayoutManager(requireContext(), 2)
                    binding.artistRv.adapter = adapter
                }
            }
        }
    }

    override fun onItemClicked(artist: ArtistData) {
        val intent = Intent(requireActivity(), ArtistInfoActivity::class.java)
        intent.putExtra(Constants.ARTIST_NAME, artist.name)
        startActivity(intent)
    }
}