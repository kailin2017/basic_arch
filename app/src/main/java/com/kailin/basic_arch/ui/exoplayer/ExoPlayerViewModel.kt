package com.kailin.basic_arch.ui.exoplayer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.Player
import com.kailin.basic_arch.app.DataStateViewModel

class ExoPlayerViewModel : DataStateViewModel(), Player.Listener {

    private val _isPlayerLoading = MutableLiveData<Boolean>()
    val isPlayerLoading: LiveData<Boolean> = _isPlayerLoading

    override fun onPlaybackStateChanged(playbackState: Int) {
        super.onPlaybackStateChanged(playbackState)
        when (playbackState) {
            ExoPlayer.STATE_BUFFERING -> {
                _isPlayerLoading.value = true
            }
            else -> {
                _isPlayerLoading.value = false
            }
        }
    }
}