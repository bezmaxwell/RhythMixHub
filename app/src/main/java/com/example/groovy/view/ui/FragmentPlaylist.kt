package com.example.groovy.view.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.groovy.R
import com.example.groovy.model.Playlist
import com.example.groovy.viewModel.MyPlaylistRecyclerViewAdapter
import com.example.groovy.viewModel.PlaylistViewModel
import com.example.groovy.viewModel.PlaylistViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FragmentPlaylist : Fragment() {

    lateinit var viewModel: PlaylistViewModel

    @Inject
    lateinit var viewModelFactory: PlaylistViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_playlist_item, container, false)

        val getLoader = view.findViewById<ProgressBar>(R.id.loader)

        setupViewModel()
        observeLoader(getLoader)
        observePlaylist(view)

        return view
    }

    private fun observeLoader(getLoader: ProgressBar) {
        viewModel.loader.observe(this as LifecycleOwner) { loading ->
            when (loading) {
                true -> getLoader.visibility = View.VISIBLE
                else -> getLoader.visibility = View.GONE
            }
        }
    }

    private fun observePlaylist(view: View) {
        viewModel.playlists.observe(this as LifecycleOwner) { playlist ->
            if (playlist.getOrNull() != null) {
                val recyclerView = view.findViewById<RecyclerView>(R.id.playlist_list)
                setupList(recyclerView, playlist.getOrNull()!!)
            } else {
                Log.e("Error", "Nothing to show")
            }
        }
    }

    private fun setupList(
        view: View?,
        playlists: List<Playlist>
    ) {
        with(view as RecyclerView) {
            layoutManager = LinearLayoutManager(context)

            adapter = MyPlaylistRecyclerViewAdapter(playlists) { id ->
                val action: NavDirections =
                    FragmentPlaylistDirections.actionFragmentPlaylistToPlaylistDetailFragment(id)

                findNavController().navigate(action)
            }

        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory)[PlaylistViewModel::class.java]
    }

    companion object {

        @JvmStatic
        fun newInstance() = FragmentPlaylist().apply {}
    }
}