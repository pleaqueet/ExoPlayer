package com.example.exoplayer.domain.use_cases

import com.example.exoplayer.data.repository.PlayerRepositoryImpl
import com.example.exoplayer.domain.common.Resource
import com.example.exoplayer.domain.model.Channel
import javax.inject.Inject

class GetMediaUseCase @Inject constructor(private val repository: PlayerRepositoryImpl) {
    suspend operator fun invoke(): Resource<List<Channel>> = repository.getMedia()
}