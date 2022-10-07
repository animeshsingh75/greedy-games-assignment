package com.example.greedygamesassignment.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.greedygamesassignment.R
import com.example.greedygamesassignment.databinding.ItemLayoutAlbumBinding
import com.example.greedygamesassignment.datamodels.Track
import com.squareup.picasso.Picasso

class TrackRvAdapter(
    private val data: List<Track>
) :
    RecyclerView.Adapter<TrackRvAdapter.TrackListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackListViewHolder {
        val binding =
            ItemLayoutAlbumBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return TrackListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TrackListViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class TrackListViewHolder(
        private val binding: ItemLayoutAlbumBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(track: Track) {
            Picasso.get()
                .load(track.image?.get(track.image.size - 1)?.text)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(binding.albumIv)
            binding.artistNameTv.setTextColor(Color.BLACK)
            binding.albumNameTv.setTextColor(Color.BLACK)
            binding.albumNameTv.text = track.name
            binding.artistNameTv.text = track.artist?.name
        }
    }
}