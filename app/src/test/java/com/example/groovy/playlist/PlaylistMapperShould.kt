package com.example.groovy.playlist

import com.example.groovy.R
import com.example.groovy.PlaylistMapper
import com.example.groovy.PlaylistRaw
import com.example.groovy.utils.BaseUnitTest
import org.junit.Assert.assertEquals
import org.junit.Test

class PlaylistMapperShould : BaseUnitTest() {

    private val playlistRaw = PlaylistRaw("1", "da name", "da category")

    private val playlistRawRock = PlaylistRaw("1", "name rock", "rock")

    private val mapper = PlaylistMapper()

    private val playlists = mapper(listOf(playlistRaw))

    private val playlist = playlists[0]
    private val playlistRock = mapper(listOf(playlistRawRock))[0]

    @Test
    fun keepSameId() {
        assertEquals(playlistRaw.id, playlist.id)
    }

   @Test
   fun keepSameName(){
       assertEquals(playlistRaw.name, playlist.name)
   }

   @Test
   fun keepSameCategory() {
        assertEquals(playlistRaw.category, playlist.category)
   }

    @Test
    fun mapDefaultImageWhenNotRock() {
        assertEquals(R.mipmap.playlist, playlist.image)
    }

    @Test
    fun mapRockImageWhenRockCategory(){
        assertEquals(R.mipmap.rock, playlistRock.image)
    }

}