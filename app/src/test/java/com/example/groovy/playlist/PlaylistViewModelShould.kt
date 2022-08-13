package com.example.groovy.playlist

import com.example.groovy.model.PlaylistRepository
import com.example.groovy.model.Playlist
import com.example.groovy.utils.BaseUnitTest
import com.example.groovy.utils.getValueForTest
import com.example.groovy.viewModel.PlaylistViewModel
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.lang.RuntimeException


class PlaylistViewModelShould : BaseUnitTest() {

    private val repository: PlaylistRepository = mock()
    private val playlist = mock<List<Playlist>>()
    private val expected = Result.success(playlist)

    private val exception = RuntimeException("Something went wrong !!!")

    @Test
    @OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
    fun getPlaylistFromRepository(): Unit =

        runBlocking {
            whenever(repository.getPlaylists()).thenReturn(
                flow {
                    emit(expected)
                }
            )
            val viewModel = PlaylistViewModel(repository)
            viewModel.playlists.getValueForTest()
            verify(repository, times(1)).getPlaylists()
        }

    @Test
    @OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
    fun emitPlaylistFromRepository() =
        runBlocking {
            whenever(repository.getPlaylists()).thenReturn(
                flow {
                    emit(expected)
                }
            )

            val viewModel = PlaylistViewModel(repository)
            assertEquals(expected, viewModel.playlists.getValueForTest())
        }


    @Test
    fun emitErrorWhenReceiveError() {
        runTest {
            whenever(repository.getPlaylists()).thenReturn(
                flow {
                    emit(Result.failure<List<Playlist>>(exception =exception))
                }
            )
        }
        val viewModel = PlaylistViewModel(repository)

        assertEquals(exception, viewModel.playlists.getValueForTest()!!.exceptionOrNull())
    }
}