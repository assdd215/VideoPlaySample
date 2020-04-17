package com.luzeping.android.video.proxy

import android.view.View
import com.luzeping.android.video.BaseFragment
import com.luzeping.videoplay.view.VideoCover

/**
 *  author : luzeping
 *  date   : 2020/4/17
 *  desc   : 播放器代理
 */

open class BaseVideoProxy(private val fragment: BaseFragment, private val videoCover: VideoCover)  {

    protected lateinit var commonPlayer: CommonPlayer

    init {
        init()
    }

    private fun init() {

        setUpVideoCover()
    }

    protected open fun initPlayer() {

    }

    private fun setUpVideoCover() {

    }

    open fun handlePause() {

    }

    open fun handleResume() {

    }


}

class CommonPlayer {
    fun addSurfaceView(view: View) {

    }

    fun startPlay() {

    }

    fun pause() {

    }

    fun resume() {

    }

    fun stop() {

    }


}