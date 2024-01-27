package com.matanel.soundmanager

import android.content.Context
import android.media.MediaPlayer

class SoundManager {

    private val map = mutableMapOf<Int, MediaPlayer>()
    private val volMap = mutableMapOf<Int, Float>()
    private var mainSoundId = 1
    private var isMuted = false

    companion object {
        private var counter = 0
        private var instance: SoundManager? = null

        @Synchronized
        fun init() {
            if (instance == null) {
                instance = SoundManager()
            }
        }

        @Synchronized
        fun getInstance(): SoundManager {
            return instance ?: throw IllegalStateException("SoundManager not initialized")
        }


    }

    fun isMuted(): Boolean = isMuted

    fun playInLoop(context: Context, id: Int): Int {
        counter += 1

        val sound = BackgroundSoundLoop(context, id)
        val mp = sound.makeSoundInLoop()
        map[counter] = mp
        volMap[counter] = 1.0f
        setVolume(counter)

        return counter
    }

    fun playOnce(context: Context, id: Int): Int {
        counter += 1

        val sound = BackgroundSoundNoLoop(context, id)
        val mp = sound.makeSoundNoLoop()
        map[counter] = mp
        volMap[counter] = 1.0f
        setVolume(counter)

        return counter
    }

    fun stopSound(num: Int) {
        map[num]?.let { mediaPlayer ->
            if (mediaPlayer.isPlaying) {
                mediaPlayer.stop()
                mediaPlayer.release()
                map.remove(num)
            }
        }
    }

    fun stopAllSounds() {
        map.keys.toList().forEach { stopSound(it) }
    }

    fun stopMainSound() = stopSound(mainSoundId)

    fun startMainSound(context: Context, id: Int) {
        mainSoundId = playInLoop(context, id)
    }

    fun pauseSound(num: Int) {
        map[num]?.takeIf { it.isPlaying }?.pause()
    }

    fun resumeSound(num: Int) {
        map[num]?.takeIf { !it.isPlaying }?.start()
    }

    fun forward(num: Int, seconds: Float) {
        map[num]?.let { mediaPlayer ->
            val currentPosition = mediaPlayer.currentPosition
            val duration = mediaPlayer.duration
            val forwardPosition = (currentPosition + (seconds * 1000).toInt()).coerceAtMost(duration)
            mediaPlayer.seekTo(forwardPosition)
        }
    }

    fun rewind(num: Int, seconds: Float) {
        map[num]?.let { mediaPlayer ->
            val currentPosition = mediaPlayer.currentPosition
            val rewindPosition = (currentPosition - (seconds * 1000).toInt()).coerceAtLeast(0)
            mediaPlayer.seekTo(rewindPosition)
        }
    }

    fun toggleMute() {
        isMuted = !isMuted
        map.keys.forEach { setVolume(it) }
    }

    private fun setVolume(num: Int) {
        map[num]?.let { mediaPlayer ->
            val volume = if (isMuted) 0.0f else volMap[num] ?: 1.0f
            mediaPlayer.setVolume(volume, volume)
        }
    }

    fun setVolume(num: Int, volume: Float) {
        val adjustedVolume = volume.coerceIn(0.0f, 1.0f)
        volMap[num] = adjustedVolume
        map[num]?.setVolume(adjustedVolume, adjustedVolume)
    }
}
