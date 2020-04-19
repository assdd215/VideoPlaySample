package com.luzeping.videoplay

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.luzeping.android.video.BaseFragment
import com.luzeping.videoplay.adapter.VideoPlayAdapter
import com.luzeping.videoplay.presenter.Success
import com.luzeping.videoplay.presenter.VideoPlayPresenter
import com.luzeping.videoplay.proxy.BaseVideoProxy
import com.luzeping.videoplay.proxy.LiveProxyCallback
import com.luzeping.videoplay.view.VideoCover

/**
 *  author : luzeping
 *  date   : 2020/4/17
 *  desc   :
 */

open class VideoPlayFragment : BaseFragment() {

    companion object {
        const val INVALID_POS = -1
    }

    private var currentPos = INVALID_POS
    private var prePos = INVALID_POS

    private lateinit var recyclerView : RecyclerView
    private lateinit var videoCover: VideoCover
    private lateinit var playerProxy: BaseVideoProxy
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var presenter: VideoPlayPresenter
    private lateinit var adapter: VideoPlayAdapter


    override fun layoutId(): Int = R.layout.fragment_video_play

    override fun initView(view: View) {
        super.initView(view)

        recyclerView = view.findViewById(R.id.recycler_view)
        videoCover = view.findViewById(R.id.video_cover)

        initData()
        initRecyclerView()
        initPlayer()
        getData(true)
    }

    private fun initRecyclerView() {
        layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = layoutManager
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
                val pos = recyclerView.getChildAdapterPosition(view)
                if (pos == currentPos) {
                    playerProxy.handlePause()
                    hideVideoCover()
                    detachHolder(currentPos)
                }
            }

            override fun onChildViewAttachedToWindow(view: View) {

            }


        })
    }

    private fun initData() {
        playerProxy = BaseVideoProxy(this, videoCover)
        presenter = VideoPlayPresenter()
        adapter = VideoPlayAdapter()
    }

    private fun initPlayer() {
        playerProxy.setCallback(object : LiveProxyCallback {
            override fun onVideoFirstFrame() {
                if (prePos != currentPos) detachHolder(prePos)
                val holder = recyclerView.findViewHolderForAdapterPosition(currentPos)
                if (holder is IVideoHolder) {
                    holder.onStartMovie()
                }
            }

        })
    }

    private fun getData(refresh: Boolean) {
        presenter.requestData(object : Success {
            override fun response() {
                //TODO 期间更新数据，adapter.notifyItemRangeChanged()刷新adapter
                recyclerView.post {
                    findTargetPosAndPlay(refresh)
                }
            }
        })
    }

    private fun updateTranslation() {
        if (view == null) return
        if (currentPos < 0) currentPos = INVALID_POS
        if (currentPos == INVALID_POS) {
            hideVideoCover()
            return
        }
        playerProxy.videoCover.visibility = View.VISIBLE
        val holder = recyclerView.findViewHolderForAdapterPosition(currentPos)
        if (holder is IVideoHolder) {
            val videoHolder: IVideoHolder = holder as IVideoHolder
            val viewLoc = IntArray(2)
            if (playerProxy.getParentView() == null) return
            playerProxy.getParentView()!!.getLocationInWindow(viewLoc)
            val location = IntArray(2)
            videoHolder.getTargetView().getLocationInWindow(location)
            playerProxy.videoCover.translationY = (location[1] - viewLoc[1]).toFloat()
            playerProxy.videoCover.translationX = (location[0] - viewLoc[0]).toFloat()
        } else {
            currentPos = INVALID_POS
            hideVideoCover()
        }
    }

    private fun findTargetPosAndPlay(refresh: Boolean) {
        if (refresh) {
            currentPos = INVALID_POS
            prePos = currentPos
        }
        recyclerView.post {
            val pre = currentPos
            currentPos = findTargetPos()
            if (pre != currentPos) {
                resizeVideoCover()
                prePos = pre
                detachHolder(prePos)
            }
            updateTranslation()
            if (currentPos != INVALID_POS && pre != currentPos) {
                startPlay()
            }
        }
    }

    //TODO 此处实现如何找到目标item
    protected open fun findTargetPos():Int {
        if (view == null) return INVALID_POS
        val firstPos: Int = layoutManager.findFirstCompletelyVisibleItemPosition()
        val lastPos: Int = layoutManager.findLastCompletelyVisibleItemPosition()
        var targetPos: Int = INVALID_POS
        for (i in firstPos .. lastPos) {
            val holder = recyclerView.findViewHolderForAdapterPosition(i) as? IVideoHolder ?: continue
            return i
        }
        return targetPos
    }

    private fun resizeVideoCover() {
        if (currentPos < 0) currentPos = INVALID_POS
        if (currentPos == INVALID_POS) {
            hideVideoCover()
            return
        }
        val holder = recyclerView.findViewHolderForAdapterPosition(currentPos)
        if (holder is IVideoHolder) {
            val target: View = holder.getTargetView()
            val params: ViewGroup.LayoutParams = playerProxy.videoCover.layoutParams
            params.width = target.width
            params.height = target.height
            playerProxy.videoCover.layoutParams = params
        }
    }

    private fun hideVideoCover() {
        playerProxy.handlePause()
        playerProxy.videoCover.visibility = View.INVISIBLE
    }

    private fun detachHolder(position: Int) {
        val holder = recyclerView.findViewHolderForAdapterPosition(position)
        if (holder is IVideoHolder) {
            holder.onHolderDetached()
        }
    }

    private fun startPlay() {
        val rtmp = presenter.getRtmp(currentPos)
        if (rtmp.isNotEmpty()) {
            playerProxy.startPlay(rtmp)
        }
    }

}