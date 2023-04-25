package com.example.exoplayer.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ChannelEntity::class], version = 1)
abstract class ChannelDatabase : RoomDatabase() {
    abstract fun channelDao(): ChannelDao
}