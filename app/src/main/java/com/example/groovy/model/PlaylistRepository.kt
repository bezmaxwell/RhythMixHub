package com.example.groovy.model

import com.example.groovy.PlaylistService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PlaylistRepository(
    private val service: PlaylistService
) {
    suspend fun getPlaylists(): Flow<Result<List<Playlist>>> =
         service.fetchPlaylists()

}