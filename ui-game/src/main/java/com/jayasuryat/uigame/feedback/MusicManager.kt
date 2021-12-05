package com.jayasuryat.uigame.feedback

import android.content.Context
import android.media.MediaPlayer
import com.jayasuryat.uigame.R
import com.jayasuryat.util.TrackedLazyCollector

internal class MusicManager(private val context: Context) {

    private val tlc: TrackedLazyCollector<MediaPlayer> by lazy { TrackedLazyCollector() }

    // region : MediaPlayers
    private val sfxPop: MediaPlayer by mediaPlayerOf(R.raw.pop) {
        setVolume(0.05f, 0.05f)
    }
    private val sfxAffirmative: MediaPlayer by mediaPlayerOf(R.raw.affirmative) {
        setVolume(0.05f, 0.05f)
    }
    private val sfxCancel: MediaPlayer by mediaPlayerOf(R.raw.cancel) {
        setVolume(0.02f, 0.02f)
    }
    // endregion

    // region : Public API
    fun pop() = sfxPop.start()
    fun affirmative() = sfxAffirmative.start()
    fun cancel() = sfxCancel.start()

    fun dispose() {
        tlc.onEachInitialized { player ->
            player.stop()
            player.release()
        }
    }
    // endregion

    // region : Helper methods

    private val MediaPlayer.isPaused: Boolean
        get() = !isPlaying && currentPosition > 1

    private fun mediaPlayerOf(
        resId: Int,
        configuration: MediaPlayer.() -> Unit = {},
    ): Lazy<MediaPlayer> = tlc.trackedLazy {
        MediaPlayer
            .create(context, resId)
            .apply { configuration() }
    }
    // endregion
}