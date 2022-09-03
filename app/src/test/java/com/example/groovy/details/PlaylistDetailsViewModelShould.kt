package com.example.groovy.details

import com.example.groovy.PlaylistDetailsService
import com.example.groovy.utils.BaseUnitTest
import com.example.groovy.utils.captureValues
import com.example.groovy.utils.getValueForTest
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.times
import org.mockito.Mockito.verify

class PlaylistDetailsViewModelShould : BaseUnitTest() {

    private lateinit var viewModel: PlaylistDetailsViewModel
    private val id = "1"
    private val service: PlaylistDetailsService = mock()
    private val playlistDetails: PlaylistDetails = mock()
    private val expected = Result.success(playlistDetails)
    private val exception = RuntimeException("Something went wrong")
    private val error = Result.failure<PlaylistDetails>(exception)

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getPlaylistDetailsFromService() = runTest {

        mockSuccessfulCase()
        viewModel.getPlaylistDetails(id)
        viewModel.playlistDetails.getValueForTest()

        verify(service, times(1)).fetchPlaylistDetails(id)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun emitPlaylistDetailsFromService() = runTest {
        mockSuccessfulCase()
        viewModel.getPlaylistDetails(id)
        assertEquals(expected, viewModel.playlistDetails.getValueForTest())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun emitErrorWhenServiceFails() = runTest {
        mockErrorCase()
        assertEquals(error, viewModel.playlistDetails.getValueForTest())
    }

    @Test
    fun showLoaderWhileLoading() = runBlocking {
        mockSuccessfulCase()
            viewModel.loader.captureValues {
                viewModel.getPlaylistDetails(id)
                viewModel.playlistDetails.getValueForTest()

                assertEquals(true, values[0])
            }

        }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun closeLoaderAfterPlaylistDetailsLoad() = runTest {
        mockSuccessfulCase()

        viewModel.loader.captureValues {
            viewModel.getPlaylistDetails(id)
            viewModel.playlistDetails.getValueForTest()

            assertEquals(false, values.last())
        }
    }

    private suspend fun mockErrorCase() {
        whenever(service.fetchPlaylistDetails(id)).thenReturn(
            flow {
                emit(error)
            }
        )
        viewModel = PlaylistDetailsViewModel(service)
        viewModel.getPlaylistDetails(id)
    }

    private suspend fun mockSuccessfulCase() {
        whenever(service.fetchPlaylistDetails(id))
            .thenReturn(flow { emit(expected) })

        viewModel = PlaylistDetailsViewModel(service)
        viewModel.getPlaylistDetails(id)
    }
}