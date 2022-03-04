package com.reactnativeaperoadsmodule

import android.annotation.SuppressLint
import android.util.Log
import com.facebook.react.bridge.*
import com.facebook.react.bridge.UiThreadUtil.runOnUiThread
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.reactnativeaperoadsmodule.ads.control.ads.Admod
import com.reactnativeaperoadsmodule.ads.control.funtion.AdCallback

class AperoAdsModuleModule(reactContext: ReactApplicationContext) :
  ReactContextBaseJavaModule(reactContext) {
  private lateinit var createInterstitial: InterstitialAd
  var admodInstance: Admod = Admod.getInstance()
  var reactContext: ReactApplicationContext? = null
  companion object {
    val AD_FAIL_TO_LOAD = 0
    val AD_FAIL_TO_SHOW = 1
    val AD_CLOSE = 2
  }

  fun AperoAdsModuleModule(reactContext: ReactApplicationContext?) {
    this.reactContext = reactContext
  }

  override fun getName(): String {
    return "AperoAdsModule"
  }

  // Example method
  // See https://reactnative.dev/docs/native-modules-android
  @ReactMethod
  fun multiply(a: Int, b: Int, promise: Promise) {
    promise.resolve(a * b)
  }

  /**
   * Init Ads
   */
  @ReactMethod
  fun onCreate() {
    admodInstance.setFan(false)
    admodInstance.setAppLovin(false)
    admodInstance.setColony(false)
  }

  /**
   * Ad Splash
   */
  @ReactMethod
  fun onSplashActivity(
    ad_interstitial_splash: String,
    timeout: Int,
    timeDelay: Int,
    callback: Callback
  ) {
    var adCallback: AdCallback = object : AdCallback() {
      override fun onAdFailedToLoad(i: LoadAdError?) {
        callback(AD_FAIL_TO_LOAD)
      }

      override fun onAdFailedToShow(adError: AdError?) {
        callback(AD_FAIL_TO_SHOW)
      }

      override fun onAdClosed() {
        super.onAdClosed()
        callback(AD_CLOSE)
      }
    }
    if (admodInstance == null) {
      admodInstance = Admod.getInstance()
    }
    runOnUiThread {
      //code that runs in main
      admodInstance
        .loadSplashInterstitalAds(
          currentActivity,
          ad_interstitial_splash,
          timeout.toLong(),
          timeDelay.toLong(),
          adCallback
        )
    }
  }

  /**
   * setOpenActivityAfterShowInterAds
   */
  @ReactMethod
  fun setOpenActivityAfterShowInterAds(isOpen: Boolean = false) {
    if (admodInstance == null) {
      admodInstance = Admod.getInstance()
    }
    runOnUiThread {
      admodInstance.setOpenActivityAfterShowInterAds(isOpen)
    }
  }

  /**
   * load Interstitial
   */
  @ReactMethod
  fun loadInterCreate(idAdInterstital: String, callback: Callback) {
    if (admodInstance == null) {
      admodInstance = Admod.getInstance()
    }
    runOnUiThread {
      admodInstance.getInterstitalAds(
        currentActivity,
        idAdInterstital,
        object : AdCallback() {
          @SuppressLint("LongLogTag")
          override fun onInterstitialLoad(interstitialAd: InterstitialAd) {
            createInterstitial = interstitialAd
            Log.e("loadInterCreate - onInterstitialLoad - interstitialAd", interstitialAd.toString())
            callback(createInterstitial)
          }

          override fun onAdFailedToLoad(i: LoadAdError?) {
            super.onAdFailedToLoad(i)
            callback("")
          }
        })
    }
  }

  /**
   * forceShowInterstitial
   */
  @ReactMethod
  fun forceShowInterstitial(callback: Callback) {
    if (admodInstance == null) {
      admodInstance = Admod.getInstance()
    }
    var adCallback: AdCallback = object : AdCallback() {
      @SuppressLint("LongLogTag")
      override fun onAdFailedToLoad(i: LoadAdError?) {
        Log.e("forceShowInterstitial - AdCallback - onAdFailedToLoad", "")
        callback(AD_FAIL_TO_LOAD)
      }
      @SuppressLint("LongLogTag")
      override fun onAdFailedToShow(adError: AdError?) {
        Log.e("forceShowInterstitial - AdCallback - onAdFailedToShow", "")
        callback(AD_FAIL_TO_SHOW)
      }

      @SuppressLint("LongLogTag")
      override fun onAdClosed() {
        super.onAdClosed()
        Log.e("forceShowInterstitial - AdCallback - onAdClosed", "")
        callback(AD_CLOSE)
      }
    }
    runOnUiThread {
      admodInstance.forceShowInterstitial(
        currentActivity,
        createInterstitial,
        adCallback
      )
    }
  }
}
