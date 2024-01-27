package com.matanel.soundmanager

import android.content.Context
import android.media.MediaPlayer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class BackgroundSoundLoop(private val context: Context, private val id: Int) {

    fun makeSoundInLoop(): MediaPlayer {
        val player = MediaPlayer.create(context, id)
        player.isLooping = true
        player.setVolume(1.0f, 1.0f)

        GlobalScope.launch(Dispatchers.IO) {
            player.start()
        }

        return player
    }
}