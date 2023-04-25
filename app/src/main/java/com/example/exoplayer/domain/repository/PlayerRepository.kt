package com.example.exoplayer.domain.repository

import com.example.exoplayer.domain.common.Resource
import com.example.exoplayer.domain.model.Channel

interface PlayerRepository {
    suspend fun getMedia(): Resource<List<Channel>>
}