package com.playzelo.highstakesdice.components

import android.content.Context
import android.media.SoundPool
import com.playzelo.highstakesdice.R

class SoundManager(private val context: Context) {

    // ---------------- Short Sounds (SoundPool) ----------------
    private val soundPool = SoundPool.Builder()
        .setMaxStreams(5)
        .build()

    private val tokensound: Int = soundPool.load(context, R.raw.stacktokensound, 1)
    private val winsound: Int = soundPool.load(context, R.raw.winsound, 1)

    private val dangersound: Int = soundPool.load(context, R.raw.dangersound, 1)

    fun TokenSound(volume: Float = 1f) {
        soundPool.play(tokensound, 1f, 1f, 1, 0, 1f)
    }

    fun WinSound(volume: Float = 1f) {
        soundPool.play(winsound, 1f, 1f, 1, 0, 1f)
    }

    fun DangerSound(volume: Float = 1f) {
        soundPool.play(dangersound, 1f, 1f, 1, 0, 1f)
    }

}