package com.example.groovy

import com.example.groovy.model.Playlist

interface PlaylistApi {

    suspend fun fetchAllPlaylists(): List<Playlist> {
        TODO("Not yet implemented")
    }

}
