package com.example.exoplayer.data.remote

import retrofit2.http.GET

interface PlayerService {
    @GET(PATH_MEDIA)
    suspend fun getMedia(): PlayerMediaResponse

    companion object {
        const val PATH_MEDIA = "downloads/media.json"
        const val API_URL = "http://stand.netup.tv/"
    }
}