package com.example.groovy.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.compose.material.Snackbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.groovy.R
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PlaylistDetailsFragment : Fragment() {

    lateinit var viewModel: PlaylistDetailsViewModel

    @Inject
    lateinit var viewModelFactory: PlaylistDetailsViewModelFactory

    private val args: PlaylistDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_playlist_detail, container, false)
        val id = args.playlistId
        val detailsLoader = view.findViewById<ProgressBar>(R.id.details_loader)

        setupViewModel()
        viewModel.getPlaylistDetails(id)

        observePlaylistDetails(view)
        observeLoader(detailsLoader)
        return view
    }

    private fun observeLoader(detailsLoader:ProgressBar) {
        viewModel.loader.observe(this as LifecycleOwner) { loading ->
            when (loading) {
                true -> detailsLoader.visibility = View.VISIBLE
                else -> detailsLoader.visibility = View.GONE
            }
        }
    }

    private fun observePlaylistDetails(view: View) {
        viewModel.playlistDetails.observe(this as LifecycleOwner) { playlistDetails ->
            if (playlistDetails.getOrNull() != null) {
                val (getPlaylistName, getPlaylistDetails) = setupUI(view)

                playlistDetails.getOrNull()?.let {
                    getPlaylistName.text = it.name
                    getPlaylistDetails.text = it.details
                }
            } else {
//                com.google.android.material.snackbar.Snackbar
//                    .make(R.layout.playlists_details_root,
//                R.string.generic_error,
//                        com.google.android.material.snackbar.Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun setupViewModel() {
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(PlaylistDetailsViewModel::class.java)
    }

    private fun setupUI(view: View): Pair<TextView, TextView> {
        val getPlaylistName = view.findViewById<TextView>(R.id.playlist_name)
        val getPlaylistDetails = view.findViewById<TextView>(R.id.playlist_details)
        return Pair(getPlaylistName, getPlaylistDetails)
    }

    companion object {
        @JvmStatic
        fun newInstance() = PlaylistDetailsFragment()
    }
}