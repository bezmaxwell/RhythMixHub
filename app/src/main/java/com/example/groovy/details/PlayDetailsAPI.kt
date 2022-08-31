package com.example.groovy.details

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PlayDetailsAPI @Inject constructor(){

    suspend fun fetchPlaylistDetails(id: String) : PlaylistDetails {
        TODO("Not yet implemented")
    }
}
