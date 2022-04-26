package com.kailin.basic_arch.ui.exoplayer

import androidx.fragment.app.viewModels
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.kailin.basic_arch.BR
import com.kailin.basic_arch.R
import com.kailin.basic_arch.app.DataBindingConfig
import com.kailin.basic_arch.app.DataBindingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExoPlayerFragment : DataBindingFragment() {

    private val viewModel: ExoPlayerViewModel by viewModels()
    private var player: ExoPlayer? = null
    private var playWhenReady = true
    private var currentWindow = 0
    private var playbackPosition = 0L

    override fun onCreateDataBindingConfig(): DataBindingConfig =
        DataBindingConfig(R.layout.fragment_exoplayer)
            .setDataStateViewModel(BR.viewModel, viewModel)

    override fun onStart() {
        super.onStart()
        val items = listOf(
            MediaItem.fromUri(getString(R.string.media_url_mp3)),
            MediaItem.fromUri(getString(R.string.media_url_mp4))
        )
        player = ExoPlayer.Builder(requireContext())
            .build()
            .also {
                it.playWhenReady = playWhenReady
                it.seekTo(currentWindow, playbackPosition)
                it.addListener(viewModel)
                it.prepare()
                it.addMediaItems(items)
            }
        setBindVariable(BR.player, player)
    }

    override fun onStop() {
        super.onStop()
        player?.run {
            playbackPosition = this.currentPosition
            currentWindow = this.currentMediaItemIndex
            playWhenReady = this.playWhenReady
            removeListener(viewModel)
            release()
        }
        player = null
    }
}