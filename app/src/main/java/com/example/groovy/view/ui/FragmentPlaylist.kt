package com.example.groovy.view.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.groovy.viewModel.MyPlaylistRecyclerViewAdapter
import com.example.groovy.model.PlaylistRepository
import com.example.groovy.viewModel.PlaylistViewModelFactory
import com.example.groovy.R
import com.example.groovy.model.Playlist
import com.example.groovy.viewModel.PlaylistViewModel

class FragmentPlaylist : Fragment() {

    lateinit var viewModel: PlaylistViewModel
    lateinit var viewModelFactory: PlaylistViewModelFactory
    private var repository = PlaylistRepository()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_playlist_item, container, false)

        setupViewModel()

        viewModel.playlists.observe(this as LifecycleOwner) { playlist ->
            if(playlist.getOrNull() != null){
                playlist.getOrNull()?.let {
                     setupList(view, it)
                }
            }else {
                //TODO
            }
        }


        return view
    }

    private fun setupList(
        view: View?,
        playlist: List<Playlist>
    ) {
        with(view as RecyclerView) {
            layoutManager = LinearLayoutManager(context)

            adapter = MyPlaylistRecyclerViewAdapter(playlist)
        }
    }

    private fun setupViewModel() {
        viewModelFactory = PlaylistViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[PlaylistViewModel::class.java]
    }

    companion object {

        @JvmStatic
        fun newInstance() = FragmentPlaylist().apply {}
    }
}