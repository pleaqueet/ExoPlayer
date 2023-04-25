package com.example.exoplayer.data.repository

import com.example.exoplayer.data.local.ChannelDatabase
import com.example.exoplayer.data.local.fromModelToEntity
import com.example.exoplayer.data.local.toModel
import com.example.exoplayer.data.remote.PlayerService
import com.example.exoplayer.domain.common.Resource
import com.example.exoplayer.domain.model.Channel
import com.example.exoplayer.domain.repository.PlayerRepository
import javax.inject.Inject

class PlayerRepositoryImpl @Inject constructor(
    private val remoteService: PlayerService,
    database: ChannelDatabase
) : PlayerRepository {
    private val channelDao = database.channelDao()
    override suspend fun getMedia(): Resource<List<Channel>> {
        try {
            val cachedChannels = channelDao.getAllChannels().map { it }
            if (cachedChannels.isNotEmpty()) {
                return Resource.Success(cachedChannels.map { it.toModel() })
            }
            val newChannels = remoteService.getMedia().media
            channelDao.insertChannels(newChannels.map { it.fromModelToEntity() })
            return Resource.Success(newChannels)
        } catch (e: Exception) {
            return Resource.Error(message = e.message.toString())
        }
    }
}