package com.example.groovy.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.groovy.PlaylistDetailsService
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class PlaylistDetailsViewModel(
    private val service: PlaylistDetailsService
) : ViewModel() {

    val playlistDetails: MutableLiveData<Result<PlaylistDetails>> = MutableLiveData()
    val loader = MutableLiveData<Boolean>()

    fun getPlaylistDetails(id: String) {
        viewModelScope.launch {
            loader.postValue(true)
            service.fetchPlaylistDetails(id)
                .onEach {
                    loader.postValue(false)
                }
                .collect {
                    playlistDetails.postValue(it)
                }
        }
    }
}
