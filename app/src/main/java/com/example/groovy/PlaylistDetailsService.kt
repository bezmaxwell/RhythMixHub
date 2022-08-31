package com.example.groovy

import com.example.groovy.details.PlayDetailsAPI
import com.example.groovy.details.PlaylistDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PlaylistDetailsService @Inject constructor(
    private val api: PlayDetailsAPI
) {

    suspend fun fetchPlaylistDetails(id: String): Flow<Result<PlaylistDetails>> {
        return flow {
            emit(Result.success( api.fetchPlaylistDetails(id)))
        }
    }
}
