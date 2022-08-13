package com.example.groovy

import com.example.groovy.model.Playlist
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class PlaylistService(
    private val api:PlaylistApi
) {

    suspend fun fetchPlaylists(): Flow<Result<List<Playlist>>> {

        return flow {
            emit(Result.success(api.fetchAllPlaylists()))
        }.catch {
            emit(Result.failure(RuntimeException("Something went wrong")))
        }
    }
}
