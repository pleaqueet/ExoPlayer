package com.example.exoplayer.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exoplayer.domain.common.Resource
import com.example.exoplayer.domain.model.Channel
import com.example.exoplayer.domain.use_cases.GetMediaUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(
    getMediaUseCase: GetMediaUseCase
) : ViewModel() {
    private val _currentChannel = MutableLiveData<Channel>()
    val currentChannel: LiveData<Channel> = _currentChannel

    private var channels = emptyList<Channel>()
    private var numberOfCurrentChannel = 0
    private lateinit var playerMedia: Resource<List<Channel>>

    fun nextChannel() {
        if (numberOfCurrentChannel < channels.size - 1) {
            _currentChannel.value = channels[++numberOfCurrentChannel]
        }
    }

    fun previousChannel() {
        if (numberOfCurrentChannel > 0) {
            _currentChannel.value = channels[--numberOfCurrentChannel]
        }
    }

    init {

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                playerMedia = getMediaUseCase()
            }
            when (playerMedia) {
                is Resource.Success -> {
                    channels = playerMedia.data ?: emptyList()
                    _currentChannel.value = playerMedia.data?.get(numberOfCurrentChannel)
                }
                is Resource.Error -> {
                    throw RuntimeException(playerMedia.message)
                }
            }
        }
    }
}
