package com.luzeping.videoplay.presenter

class VideoPlayPresenter {

    //模拟通过position找到数据列表中的rtmp
    fun getRtmp(position: Int) : String{
        return "123"
    }

    //模拟请求
    fun requestData(success: Success?) {
        success?.response()
    }

}

interface Success {
    fun response()
}