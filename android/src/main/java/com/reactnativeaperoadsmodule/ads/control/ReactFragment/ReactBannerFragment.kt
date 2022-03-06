package com.reactnativeaperoadsmodule.ads.control.ReactFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.reactnativeaperoadsmodule.R

class ReactBannerFragment : Fragment() {
  var customView: ReactBannerView? = null
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment

//    customView = ReactBannerView(this.getContext());
//    return customView;
    return inflater.inflate(R.layout.fragment_react_banner, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
  }

  override fun onPause() {
    super.onPause()
    // do any logic that should happen in an `onPause` method
    // e.g.: customView.onPause();
  }

  override fun onResume() {
    super.onResume()
    // do any logic that should happen in an `onResume` method
    // e.g.: customView.onResume();
  }

  override fun onDestroy() {
    super.onDestroy()
    // do any logic that should happen in an `onDestroy` method
    // e.g.: customView.onDestroy();
  }
}
