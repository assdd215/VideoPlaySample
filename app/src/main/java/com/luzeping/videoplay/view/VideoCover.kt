package com.luzeping.videoplay.view

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout

/**
 *  author : luzeping
 *  date   : 2020/4/17
 *  desc   : 播放器播放的封面View
 */

class VideoCover : FrameLayout {

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attr: AttributeSet?) : super(context, attr) {
        init()
    }

    constructor(context: Context, attr: AttributeSet?, defStyleAttr : Int) : super(context, attr, defStyleAttr){
        init()
    }

    private fun init() {
        removeAllViews()

    }

}