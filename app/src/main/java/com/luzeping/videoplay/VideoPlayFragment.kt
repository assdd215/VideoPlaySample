package com.luzeping.android.video

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.luzeping.android.video.proxy.BaseVideoProxy
import com.luzeping.videoplay.R
import com.luzeping.videoplay.view.VideoCover
import kotlinx.android.synthetic.main.fragment_video_play.*

/**
 *  author : luzeping
 *  date   : 2020/4/17
 *  desc   :
 */

class VideoPlayFragment : BaseFragment() {

    companion object {
        val INVALID_POS = -1
    }

    private var currentPos = INVALID_POS
    private var prePos = INVALID_POS

    private lateinit var recyclerView : RecyclerView
    private lateinit var videoCover: VideoCover
    private lateinit var playerProxy: BaseVideoProxy


    override fun layoutId(): Int = R.layout.fragment_video_play

    override fun initView(view: View) {
        super.initView(view)

        recyclerView = recycler_view
        videoCover = video_cover

        initData()
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                when(newState) {
                    RecyclerView.SCROLL_STATE_IDLE -> {
                        findTargetPosAndPlay(false)
                    }
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                updateTranslation()
            }
        })

        recyclerView.addOnChildAttachStateChangeListener(object : RecyclerView.OnChildAttachStateChangeListener{
            override fun onChildViewDetachedFromWindow(view: View) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildViewAttachedToWindow(view: View) {
                val pos = recyclerView.getChildAdapterPosition(view)
                if (pos == currentPos) {
                    playerProxy.handlePause()
                    hideVideoCover()
                    detachHolder(currentPos)
                }
            }


        })
    }

    private fun initData() {
        playerProxy = BaseVideoProxy(this, videoCover)
    }

    private fun updateTranslation() {
        if (view == null) return
    }

    private fun findTargetPosAndPlay(refresh: Boolean) {

    }

    private fun findTargetPos() {

    }

    private fun resizeVideoCover() {

    }

    private fun hideVideoCover() {}

    private fun detachHolder(position: Int) {}

    private fun startPlay() {}

}