package com.mario.startwarskt.Util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by mario on 9/24/17.
 */
fun ViewGroup.inflate(layoutId: Int): View {
    return LayoutInflater.from(context).inflate(layoutId, this, false)
}