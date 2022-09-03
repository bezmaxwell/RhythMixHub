package com.example.groovy.details

import com.example.groovy.PlaylistDetailsService
import com.example.groovy.utils.BaseUnitTest
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import java.lang.RuntimeException

class PlaylistDetailsServiceShould : BaseUnitTest() {

    lateinit var service: PlaylistDetailsService
    private val id = "12"
    private val api: PlaylistDetailsAPI = mock()
    private val playlistDetails: PlaylistDetails = mock()
    private val exception = RuntimeException("Damn backend developers again 500!!!")

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun fetchPlaylistDetailsFromAPI() = runTest {
        mockSuccessfulCase()
        service.fetchPlaylistDetails(id).single()

        verify(api, times(1)).fetchPlaylistDetails(id)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun convertValuesToFlowAndEmitThen() = runTest {
        mockSuccessfulCase()
        assertEquals(Result.success(playlistDetails), service.fetchPlaylistDetails(id).single())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun emitErrorResultWhenNetworkFails() = runTest {
        mockErrorCase()

        assertEquals("Something went wrong",
        service.fetchPlaylistDetails(id).single().exceptionOrNull()?.message
        )
    }

    private suspend fun mockErrorCase() {
        whenever(api.fetchPlaylistDetails(id)).thenThrow(exception)

        service = PlaylistDetailsService(api)
    }

    private suspend fun mockSuccessfulCase() {
        whenever(api.fetchPlaylistDetails(id)).thenReturn(playlistDetails)
        service = PlaylistDetailsService(api)
    }
}