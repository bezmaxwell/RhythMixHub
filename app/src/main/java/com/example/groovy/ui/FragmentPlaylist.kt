package com.example.groovy.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.groovy.R
import com.example.groovy.viewModel.PlaylistViewModel

class FragmentPlaylist : Fragment() {

    lateinit var viewModel: PlaylistViewModel
    lateinit var viewModelFactory:PlaylistViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_playlist_item, container, false)

        viewModel.playlist.observe(this as LifecycleOwner, { playlist ->
            with(view as RecyclerView) {
                layoutManager = LinearLayoutManager(context)

                adapter = MyPlaylistRecyclerViewAdapter(playlist)
            }
        })

        return view
    }

    companion object {

        @JvmStatic
        fun newInstance() = FragmentPlaylist().apply {}
    }
}