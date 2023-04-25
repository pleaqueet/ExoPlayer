package com.example.exoplayer.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.exoplayer.domain.model.Channel

@Entity(tableName = "channels")
data class ChannelEntity(
    @PrimaryKey val name: String,
    val url: String
)

fun Channel.fromModelToEntity() = ChannelEntity(name, url)

fun ChannelEntity.toModel() = Channel(name, url)