package com.luzeping.android.video

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

/**
 *  author : luzeping
 *  date   : 2020/4/17
 *  desc   : 自封装的Base Fragment
 */

open abstract class BaseFragment : Fragment() {

    open var mRootView: View? = null
    open var isViewCreated = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (mRootView == null) {
            mRootView = layoutInflater.inflate(layoutId(), container, false)
            initView(mRootView!!)
        } else {
            val parent: ViewGroup? = mRootView!!.parent as ViewGroup
            parent?.removeView(mRootView)
        }
        isViewCreated = true
        return mRootView
    }

    protected abstract fun layoutId(): Int

    open fun initView(view: View) {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        isViewCreated = false
    }

    protected open fun isFragmentVisible() : Boolean {
        return isAdded
                && isResumed
                && userVisibleHint
                && !isHidden
    }
}