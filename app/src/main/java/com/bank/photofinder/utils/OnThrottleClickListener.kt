package com.bank.photofinder.utils

import CLICK_DELAY
import android.view.View

class OnThrottleClickListener(
    private val clickListener: View.OnClickListener,
    private val interval: Long = CLICK_DELAY
) :
    View.OnClickListener {
    private var clickable = true
    override fun onClick(v: View?) {
        if (clickable) {
            clickable = false
            v?.run {
                postDelayed({
                    clickable = true
                }, interval)
                clickListener.onClick(v)
            }
        } else {
        }
    }
}