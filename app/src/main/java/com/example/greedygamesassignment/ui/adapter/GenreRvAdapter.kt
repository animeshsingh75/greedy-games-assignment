package com.example.greedygamesassignment.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.greedygamesassignment.databinding.ItemLayoutGenreBinding
import com.example.greedygamesassignment.datamodels.Tag

class GenreRvAdapter(
    private val data: List<Tag>,
    private val itemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<GenreRvAdapter.GenreListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreListViewHolder {
        val binding =
            ItemLayoutGenreBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return GenreListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GenreListViewHolder, position: Int) {
        holder.bind(data[position], itemClickListener)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    interface OnItemClickListener {
        fun onItemClicked(genreName: String)
    }

    class GenreListViewHolder(
        private val binding: ItemLayoutGenreBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(genre: Tag, clickListener: OnItemClickListener) {
            binding.genreNameTv.text = genre.name?.replaceFirstChar(Char::titlecase)
            itemView.setOnClickListener {
                genre.name?.let { genreName -> clickListener.onItemClicked(genreName) }
            }
        }
    }
}