package com.example.groovy.viewModel

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.groovy.R
import com.example.groovy.databinding.FragmentPlaylistItemBinding
import com.example.groovy.model.Playlist

class MyPlaylistRecyclerViewAdapter(
    private val values: List<Playlist>,
    private val listener: (String) -> Unit

    )
    : RecyclerView.Adapter<MyPlaylistRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            FragmentPlaylistItemBinding.inflate(LayoutInflater.from(parent.context),
                parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]

        holder.playlistName.text = item.name
        holder.playlistCategory.text = item.category
        holder.imagePlaylist.setImageResource(item.image)
        holder.root.setOnClickListener { listener(item.id) }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentPlaylistItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val playlistName: TextView = binding.playlistName
        val playlistCategory: TextView = binding.playlistCategory
        val imagePlaylist:ImageView = binding.playlistImage
        val root = binding.playlistItemRoot
    }

}