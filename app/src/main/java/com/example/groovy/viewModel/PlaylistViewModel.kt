package com.example.groovy.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.example.groovy.model.PlaylistRepository
import kotlinx.coroutines.flow.onEach

class PlaylistViewModel(repository: PlaylistRepository) : ViewModel() {

    val loader = MutableLiveData<Boolean>()

    val playlists = liveData {
        loader.postValue(true)

        emitSource(repository.getPlaylists()
            .onEach {
                loader.postValue(false)
            }.asLiveData())


    }

}
