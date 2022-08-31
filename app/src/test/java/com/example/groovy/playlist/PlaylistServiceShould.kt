package com.example.groovy.playlist

import com.example.groovy.PlaylistApi
import com.example.groovy.PlaylistRaw
import com.example.groovy.PlaylistService
import com.example.groovy.model.Playlist
import com.example.groovy.utils.BaseUnitTest
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class PlaylistServiceShould : BaseUnitTest() {

    private lateinit var service: PlaylistService
    private val api: PlaylistApi = mock()
    private val playlists: List<PlaylistRaw> = mock()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun fetchPlaylistsFromAPI() = runTest {
        service = PlaylistService(api)
        service.fetchPlaylists().first()

        verify(api, times(1)).fetchAllPlaylists()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun convertValuesToFlowResultAndEmitsThem() = runTest {

        mockSuccessfulCase()

        assertEquals(Result.success(playlists), service.fetchPlaylists().first())
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun emitsErrorResultWhenNetworkFails() = runTest {
        mockErrorCase()

        assertEquals(
            "Something went wrong",
            service.fetchPlaylists().first().exceptionOrNull()?.message
        )
    }

    private suspend fun mockSuccessfulCase() {
        whenever(api.fetchAllPlaylists()).thenReturn(playlists)

        service = PlaylistService(api)
    }

    private suspend fun mockErrorCase() {
        whenever(api.fetchAllPlaylists()).thenThrow(RuntimeException("Problem when code backend developer response !!!"))

        service = PlaylistService(api)
    }
}