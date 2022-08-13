package com.example.groovy.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.example.groovy.model.Playlist
import com.example.groovy.model.PlaylistRepository

class PlaylistViewModel(
    repository: PlaylistRepository
):ViewModel() {

    val playlists = liveData {
        emitSource(repository.getPlaylists().asLiveData())
    }

}
