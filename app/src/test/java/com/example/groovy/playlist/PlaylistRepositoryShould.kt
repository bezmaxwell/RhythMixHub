package com.example.groovy.playlist

import com.example.groovy.PlaylistService
import com.example.groovy.model.Playlist
import com.example.groovy.model.PlaylistRepository
import com.example.groovy.utils.BaseUnitTest
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class PlaylistRepositoryShould : BaseUnitTest() {

    private val service: PlaylistService = mock()
    private val playlists = mock<List<Playlist>>()

    private val exception = RuntimeException("Something went wrong")

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getPlaylistFromService() = runTest {
        val repository = PlaylistRepository(service)
        repository.getPlaylists()

        verify(service, times(1)).fetchPlaylists()
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun emitPlaylistsFromService() = runTest {
        val repository = mockSuccessfulCase()

        assertEquals(playlists, repository.getPlaylists().first().getOrNull())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun propagateErrors() = runTest {
        val repository = mockFailureCase()

        assertEquals(exception, repository.getPlaylists().first().exceptionOrNull())
    }

    private suspend fun mockFailureCase(): PlaylistRepository {
        whenever(service.fetchPlaylists()).thenReturn(
            flow {
                emit(Result.failure<List<Playlist>>(exception))
            }
        )
        return PlaylistRepository(service)
    }

    private suspend fun mockSuccessfulCase(): PlaylistRepository {
        whenever(service.fetchPlaylists()).thenReturn(
            flow {
                emit(Result.success(playlists))
            }
        )

        return PlaylistRepository(service)
    }


}