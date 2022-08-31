package com.example.groovy.playlist

import com.example.groovy.model.Playlist
import com.example.groovy.model.PlaylistRepository
import com.example.groovy.utils.BaseUnitTest
import com.example.groovy.utils.captureValues
import com.example.groovy.utils.getValueForTest
import com.example.groovy.viewModel.PlaylistViewModel
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test


class PlaylistViewModelShould : BaseUnitTest() {

    private val repository: PlaylistRepository = mock()
    private val playlist = mock<List<Playlist>>()
    private val expected = Result.success(playlist)

    private val exception = RuntimeException("Something went wrong !!!")

    @Test
    fun getPlaylistFromRepository(): Unit =

        runBlocking {
            val viewModel = mockSuccessfulCase()
            viewModel.playlists.getValueForTest()

            verify(repository, times(1)).getPlaylists()
        }

    @Test
    fun emitPlaylistFromRepository() {
        val viewModel = mockSuccessfulCase()

        assertEquals(expected, viewModel.playlists.getValueForTest())
    }


    @Test
    fun emitErrorWhenReceiveError() {
        val viewModel = mockErrorCase()
        assertEquals(exception, viewModel.playlists.getValueForTest()!!.exceptionOrNull())
    }

    @Test
    fun showSpinnerWhileLoading() =
        runBlocking {
            val viewModel = mockSuccessfulCase()

            viewModel.loader.captureValues {
                viewModel.playlists.getValueForTest()

                assertEquals(true, values[0])
            }

        }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun closeLoaderAfterPlaylistLoad() = runTest {
        val viewModel = mockSuccessfulCase()

        viewModel.loader.captureValues {
            viewModel.playlists.getValueForTest()

            assertEquals(false, values.last())
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun closeLoaderAfterError() = runTest {
        val viewModel = mockErrorCase()

        viewModel.loader.captureValues {
            viewModel.playlists.getValueForTest()

            assertEquals(false, values.last())
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun mockSuccessfulCase(): PlaylistViewModel {
        runTest {
            whenever(repository.getPlaylists()).thenReturn(
                flow {
                    emit(expected)
                }
            )
        }
        return PlaylistViewModel(repository)
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    private fun mockErrorCase(): PlaylistViewModel {
        runTest {
            whenever(repository.getPlaylists()).thenReturn(
                flow {
                    emit(Result.failure<List<Playlist>>(exception = exception))
                }
            )
        }
        return PlaylistViewModel(repository)
    }
}