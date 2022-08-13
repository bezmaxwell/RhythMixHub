package com.example.groovy.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.groovy.model.PlaylistRepository

class PlaylistViewModelFactory(
    private val repository: PlaylistRepository
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PlaylistViewModel(repository) as T
    }

}
