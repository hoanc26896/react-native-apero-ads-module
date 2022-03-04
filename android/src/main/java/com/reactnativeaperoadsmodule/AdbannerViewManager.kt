package com.reactnativeadbanner

import android.annotation.SuppressLint
import android.graphics.Color
import android.util.Log
import android.view.Choreographer
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.fragment.app.FragmentActivity
import com.facebook.appevents.internal.ActivityLifecycleTracker.getCurrentActivity
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.ReadableArray
import com.facebook.react.common.MapBuilder
import com.facebook.react.uimanager.SimpleViewManager
import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.annotations.ReactProp
import com.facebook.react.uimanager.annotations.ReactPropGroup
import com.reactnativeaperoadsmodule.ads.control.ReactFragment.ReactBannerFragment
import com.reactnativeaperoadsmodule.ads.control.ads.Admod


class AdbannerViewManager(reactContext: ReactApplicationContext) :
  SimpleViewManager<FrameLayout>() {
  val COMMAND_BANNER = 1
  val COMMAND_LOAD = 1

  private var propWidth = 0
  private var propHeight = 0

  var reactContext: ReactApplicationContext? = null
  private var admodInstance = Admod.getInstance()

  private var bannerId = ""

  override fun getName() = "AdbannerViewManager"

  fun AdbannerViewManager(reactContext: ReactApplicationContext?) {
    this.reactContext = reactContext
  }

  /**
   * Load Banner
   */
  fun loadBanner() {
    if (admodInstance == null) {
      admodInstance = Admod.getInstance()
    }
    Log.e("loadBanner - bannerId", bannerId)
    admodInstance.loadBanner(reactContext?.currentActivity, bannerId)
  }

  @ReactProp(name = "bannerId")
  fun setBannerId(view: View, id: String) {
    bannerId = id
  }

  @ReactProp(name = "color")
  fun setColor(view: View, color: String) {
    view.setBackgroundColor(Color.parseColor(color))
  }

  override fun createViewInstance(reactContext: ThemedReactContext): FrameLayout {
    return FrameLayout(reactContext)
  }

  /**
   * Map the "create" command to an integer
   */
  @Nullable
  override fun getCommandsMap(): Map<String, Int>? {
    return MapBuilder.of(
      "banner", COMMAND_BANNER,
      "load", COMMAND_LOAD
    )
  }

  /**
   * Handle "create" command (called from JS) and call createFragment method
   */
  @SuppressLint("LongLogTag")
  fun receiveCommand(
    @NonNull root: FrameLayout?,
    commandId: String,
    args: ReadableArray?
  ) {
    super.receiveCommand(root!!, commandId, args)
    val reactNativeViewId = args!!.getInt(0)
    val commandIdInt = commandId.toInt()
    Log.e("receiveCommand - commandIdInt: ", commandIdInt.toString())
    when (commandIdInt) {
      COMMAND_BANNER -> createFragment(root, reactNativeViewId)
      COMMAND_LOAD -> loadBanner()
      else -> {
      }
    }
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
    val parentView = root.findViewById<View>(reactNativeViewId) as ViewGroup
    setupLayout(parentView)
    val myFragment = ReactBannerFragment()
    val activity: FragmentActivity = reactContext?.currentActivity as FragmentActivity
    activity.supportFragmentManager
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
  fun manuallyLayoutChildren(view: View) {
    // propWidth and propHeight coming from react-native props
    val width = propWidth
    val height = propHeight
    view.measure(
      View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY),
      View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY)
    )
    view.layout(0, 0, width, height)
  }
}
