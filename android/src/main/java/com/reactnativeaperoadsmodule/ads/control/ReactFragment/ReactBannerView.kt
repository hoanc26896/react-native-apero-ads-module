package com.reactnativeaperoadsmodule.ads.control.ReactFragment

import android.content.Context
import android.graphics.Color
import android.widget.FrameLayout
import android.widget.TextView
import androidx.annotation.NonNull


class ReactBannerView(@NonNull context: Context?) : FrameLayout(context!!) {
  init {
    // set padding and background color
    setPadding(16, 16, 16, 16)
    setBackgroundColor(Color.parseColor("#5FD3F3"))

  }
}
