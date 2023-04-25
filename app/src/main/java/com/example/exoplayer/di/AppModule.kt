package com.example.exoplayer.di

import android.app.Application
import androidx.room.Room
import com.example.exoplayer.data.local.ChannelDatabase
import com.example.exoplayer.data.remote.PlayerService
import com.example.exoplayer.data.repository.PlayerRepositoryImpl
import com.example.exoplayer.domain.repository.PlayerRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun providePlayerService(): PlayerService {
        return Retrofit.Builder()
            .baseUrl(PlayerService.API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PlayerService::class.java)
    }

    @Singleton
    @Provides
    fun providePlayerRepository(
        remoteService: PlayerService,
        database: ChannelDatabase
    ): PlayerRepository = PlayerRepositoryImpl(remoteService = remoteService, database = database)

    @Provides
    @Singleton
    fun provideDatabase(app: Application): ChannelDatabase =
        Room.databaseBuilder(app, ChannelDatabase::class.java, "channel_database").build()
}