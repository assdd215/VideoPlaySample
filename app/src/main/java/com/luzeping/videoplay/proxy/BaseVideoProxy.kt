package com.luzeping.videoplay.proxy

import android.view.View
import com.luzeping.android.video.BaseFragment
import com.luzeping.videoplay.view.VideoCover

/**
 *  author : luzeping
 *  date   : 2020/4/17
 *  desc   : 播放器代理
 */

open class BaseVideoProxy(private val fragment: BaseFragment, val videoCover: VideoCover)  {

    private lateinit var commonPlayer: CommonPlayer
    private var callback: LiveProxyCallback? = null

    init {
        init()
    }

    private fun init() {
        initPlayer()
        setUpVideoCover()
    }

    protected open fun initPlayer() {
        commonPlayer = CommonPlayer()
        commonPlayer.setPlayerStatus(playerStatus = object : PlayerStatus {
            override fun onVideoFirstFrame() {
                onStartMovie()
            }
        })
    }

    private fun setUpVideoCover() {

    }

    private fun onStartMovie() {
        callback?.onVideoFirstFrame()
    }

    open fun handlePause() {

    }

    open fun handleResume() {

    }
    
    fun startPlay(rtmp: String) {
        commonPlayer.startPlay(rtmp)
    }

    fun getParentView() : View? {
        return videoCover.parent as View?
    }

    fun setCallback(callback: LiveProxyCallback) {
        this.callback = callback
    }

}

class CommonPlayer {

    private var surfaceView: View? = null
    private var playerStatus: PlayerStatus? = null

    fun addSurfaceView(view: View) {
        surfaceView = view
    }

    fun startPlay(rtmp: String) {
        surfaceView?.removeCallbacks(runnable)
        surfaceView?.postDelayed(runnable, 500)
    }

    private val runnable: Runnable = Runnable {
        playerStatus?.onVideoFirstFrame()
    }

    fun pause() {

    }

    fun resume() {

    }

    fun stop() {

    }

    fun setPlayerStatus(playerStatus: PlayerStatus) {
        this.playerStatus = playerStatus
    }


}

interface PlayerStatus {
    fun onVideoFirstFrame()
}

interface LiveProxyCallback {
    fun onVideoFirstFrame()
}