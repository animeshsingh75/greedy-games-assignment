package com.example.greedygamesassignment.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.greedygamesassignment.Constants
import com.example.greedygamesassignment.databinding.FragmentTrackBinding
import com.example.greedygamesassignment.network.Client
import com.example.greedygamesassignment.network.Resource
import com.example.greedygamesassignment.repo.Repo
import com.example.greedygamesassignment.ui.adapter.TrackRvAdapter
import com.example.greedygamesassignment.viewmodel.GenreDetailViewModel

class TrackFragment : Fragment() {

    private lateinit var binding: FragmentTrackBinding
    private lateinit var viewModel: GenreDetailViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTrackBinding.inflate(layoutInflater)
        val apiService = Client.api
        val repo = Repo(apiService)
        val factory = GenreDetailViewModel.GenreDetailViewModelFactory(repo)
        viewModel = ViewModelProvider(requireActivity(), factory)[GenreDetailViewModel::class.java]
        arguments?.getString(Constants.TAG)?.let { viewModel.getTrackList(it) }
        getObserver()
        return binding.root
    }

    private fun getObserver() {
        viewModel.trackList.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Error -> {
                    Toast.makeText(requireActivity(), it.message, Toast.LENGTH_LONG).show()
                }
                is Resource.Loading -> {
                }
                is Resource.Success -> {
                    val adapter =
                        it.data?.tracks?.track?.let { trackData ->
                            TrackRvAdapter(
                                trackData
                            )
                        }
                    Log.d("TrackFragment", "getObserver: ${it.data}")
                    binding.trackRv.layoutManager = GridLayoutManager(requireContext(), 2)
                    binding.trackRv.adapter = adapter
                }
            }
        }
    }

}
