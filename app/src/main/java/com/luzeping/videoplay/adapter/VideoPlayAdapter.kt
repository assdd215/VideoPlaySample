package com.luzeping.videoplay.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.luzeping.videoplay.IVideoHolder
import com.luzeping.videoplay.R

class VideoPlayAdapter : RecyclerView.Adapter<VideoPlayHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoPlayHolder {
        return VideoPlayHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_video_play, parent, false))
    }

    override fun getItemCount(): Int = 30

    override fun onBindViewHolder(holder: VideoPlayHolder, position: Int) {
        if (holder.itemView is AppCompatTextView) {
            holder.itemView.text = position.toString()
        }
    }

}


class VideoPlayHolder(itemView: View) : RecyclerView.ViewHolder(itemView), IVideoHolder {

    init {

    }

    override fun getTargetView(): View {
        return itemView
    }

    override fun onHolderDetached() {
        itemView.visibility = View.VISIBLE
    }

    override fun onStartMovie() {
        itemView.visibility = View.INVISIBLE
    }

}