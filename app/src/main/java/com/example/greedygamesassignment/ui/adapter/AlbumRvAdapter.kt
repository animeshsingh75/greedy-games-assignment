package com.example.greedygamesassignment.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.greedygamesassignment.R
import com.example.greedygamesassignment.databinding.ItemLayoutAlbumBinding
import com.example.greedygamesassignment.datamodels.Album
import com.squareup.picasso.Picasso

class AlbumRvAdapter(
    private val data: List<Album>,
    private val itemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<AlbumRvAdapter.AlbumListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumListViewHolder {
        val binding =
            ItemLayoutAlbumBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return AlbumListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlbumListViewHolder, position: Int) {
        holder.bind(data[position], itemClickListener)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    interface OnItemClickListener {
        fun onItemClicked(album: Album)
    }

    class AlbumListViewHolder(
        private val binding: ItemLayoutAlbumBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(album: Album, clickListener: OnItemClickListener) {
            Picasso.get()
                .load(album.image?.get(album.image.size - 1)?.text)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(binding.albumIv)

            binding.albumNameTv.text = album.name
            binding.artistNameTv.text = album.artist?.name
            itemView.setOnClickListener {
                clickListener.onItemClicked(album)
            }
        }
    }
}