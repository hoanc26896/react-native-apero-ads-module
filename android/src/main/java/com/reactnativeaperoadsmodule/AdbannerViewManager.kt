package com.reactnativeadbanner

import android.annotation.SuppressLint
import android.util.DisplayMetrics
import android.util.Log
import android.view.Choreographer
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.NonNull
import androidx.fragment.app.FragmentActivity
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReadableArray
import com.facebook.react.common.MapBuilder
import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.ViewGroupManager
import com.facebook.react.uimanager.annotations.ReactProp
import com.facebook.react.uimanager.annotations.ReactPropGroup
import com.reactnativeaperoadsmodule.ads.control.ReactFragment.ReactBannerFragment
import com.reactnativeaperoadsmodule.ads.control.ads.Admod


class AdBannerViewManager(val reactContext: ReactApplicationContext) :
  ViewGroupManager<FrameLayout>() {
  private val COMMAND_CREATE = 1
  private var propWidth = 0
  private var propHeight = 0
  private var propPosition = "bottom"

  private var admodInstance = Admod.getInstance()

  private val REACT_NAME = "AdBannerViewManager"
  private var parentView: ViewGroup? = null

  @NonNull
  override fun getName() = REACT_NAME

  @NonNull
  override fun createViewInstance(reactContext: ThemedReactContext): FrameLayout {
    return FrameLayout(reactContext)
  }

  override fun getCommandsMap(): MutableMap<String, Int>? {
    return MapBuilder.of("create", COMMAND_CREATE)
  }

  override fun receiveCommand(root: FrameLayout, commandId: String?, args: ReadableArray?) {
    super.receiveCommand(root, commandId, args)
    val reactNativeViewId = args!!.getInt(0)
    val commandIdInt = commandId!!.toInt()

    when (commandIdInt) {
      COMMAND_CREATE -> createFragment(root, reactNativeViewId)
      else -> {
      }
    }
  }

  @ReactProp(name = "isBottomPosition", defaultBoolean = true)
  open fun setIsBottomPosition(view: FrameLayout?, isBottomPosition: Boolean) {
    propPosition = if (!isBottomPosition) "top" else "bottom"
  }

  @ReactPropGroup(names = ["width", "height"], customType = "Style")
  fun setStyle(view: FrameLayout?, index: Int, value: Int) {
    if (index == 0) {
      propWidth = value
    }
    if (index == 1) {
      propHeight = value
    }
  }

  /**
   * Replace your React Native view with a custom fragment
   */
  fun createFragment(root: FrameLayout, reactNativeViewId: Int) {
     parentView = root.findViewById<View>(reactNativeViewId) as ViewGroup
    setupLayout(parentView!!)
    val myFragment = ReactBannerFragment()
    val activity = reactContext.currentActivity as FragmentActivity?
    activity!!.supportFragmentManager
      .beginTransaction()
      .replace(reactNativeViewId, myFragment, reactNativeViewId.toString())
      .commit()
  }

  fun setupLayout(view: View) {
    Choreographer.getInstance().postFrameCallback(object : Choreographer.FrameCallback {
      override fun doFrame(frameTimeNanos: Long) {
        manuallyLayoutChildren(view)
        view.viewTreeObserver.dispatchOnGlobalLayout()
        Choreographer.getInstance().postFrameCallback(this)
      }
    })
  }

  /**
   * Layout all children properly
   */
  @SuppressLint("LongLogTag")
  fun manuallyLayoutChildren(view: View) {
    // propWidth and propHeight coming from react-native props
    if (propWidth == 0){
      propWidth = view.measuredWidth
    }
    if (propHeight == 0){
      propHeight = view.measuredHeight
    }

    val width = propWidth
    val height = propHeight
    Log.e("manuallyLayoutChildren - width", width.toString())
    Log.e("manuallyLayoutChildren - height", height.toString())
    view.measure(
      View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY),
      View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY)
    )
    view.layout(0, 0, width, height)
  }
}
