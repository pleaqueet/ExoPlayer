package com.example.exoplayer.presentation

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.exoplayer.databinding.ActivityMainBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), Player.Listener {
    private val viewModel by viewModels<PlayerViewModel>()
    private lateinit var binding: ActivityMainBinding
    private lateinit var player: ExoPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupPlayer()

        savedInstanceState?.getInt(KEY_MEDIA_ITEM_INDEX)?.let { mediaItemIndex ->
            val currentTimePosition = savedInstanceState.getLong(KEY_CURRENT_TIME_POSITION)
            player.seekTo(mediaItemIndex, currentTimePosition)
            player.play()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putLong(KEY_CURRENT_TIME_POSITION, player.currentPosition)
        outState.putInt(KEY_MEDIA_ITEM_INDEX, player.currentMediaItemIndex)
    }

    private fun setupPlayer() {
        player = ExoPlayer.Builder(this).build()
        viewModel.currentChannel.observe(this) { channel ->
            player.setMediaItem(MediaItem.fromUri(channel.url))
            binding.title.text = channel.name
        }
        binding.nextButton.setOnClickListener { viewModel.nextChannel() }
        binding.previousButton.setOnClickListener { viewModel.previousChannel() }

        binding.playerView.player = player
        player.addListener(this)
    }

    override fun onStop() {
        super.onStop()
        player.release()
    }

    override fun onPlaybackStateChanged(playbackState: Int) {
        super.onPlaybackStateChanged(playbackState)
        when (playbackState) {
            Player.STATE_BUFFERING -> {
                binding.progressBar.visibility = View.VISIBLE
            }
            Player.STATE_READY -> {
                binding.progressBar.visibility = View.INVISIBLE
            }
        }
    }

    companion object {
        private const val KEY_CURRENT_TIME_POSITION = "CurrentTimePosition"
        private const val KEY_MEDIA_ITEM_INDEX = "MediaItem"
    }
}