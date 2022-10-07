package com.example.greedygamesassignment.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.greedygamesassignment.R
import com.example.greedygamesassignment.databinding.ItemLayoutAlbumBinding
import com.example.greedygamesassignment.datamodels.ArtistData
import com.squareup.picasso.Picasso

class ArtistRvAdapter(
    private val data: List<ArtistData>,
    private val itemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<ArtistRvAdapter.ArtistListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistListViewHolder {
        val binding =
            ItemLayoutAlbumBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ArtistListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArtistListViewHolder, position: Int) {
        holder.bind(data[position], itemClickListener)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    interface OnItemClickListener {
        fun onItemClicked(artist: ArtistData)
    }

    class ArtistListViewHolder(
        private val binding: ItemLayoutAlbumBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(artist: ArtistData, clickListener: OnItemClickListener) {
            Picasso.get()
                .load(artist.image?.get(artist.image.size-1)?.text)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(binding.albumIv)
            binding.albumNameTv.text = artist.name
            binding.artistNameTv.visibility = View.GONE
            binding.albumNameTv.setTextColor(Color.BLACK)
            itemView.setOnClickListener {
                clickListener.onItemClicked(artist)
            }
        }
    }
}