package com.luzeping.videoplay

import android.view.View

interface IVideoHolder {

    fun getTargetView() : View

    fun onHolderDetached()

    fun onStartMovie()
}