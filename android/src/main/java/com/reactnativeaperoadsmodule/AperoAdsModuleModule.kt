package com.reactnativeaperoadsmodule

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

  companion object {
    val AD_FAIL_TO_LOAD = 0
    val AD_FAIL_TO_SHOW = 1
    val AD_CLOSE = 2
  }

  override fun initialize() {
    super.initialize()
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
    if (admodInstance == null){
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
   * load Interstitial
   */
  @ReactMethod
  fun loadInterCreate(idAdInterstital: String, promise: Promise) {
    runOnUiThread {
    admodInstance.getInterstitalAds(
      currentActivity,
      idAdInterstital,
      object : AdCallback() {
        override fun onInterstitialLoad(interstitialAd: InterstitialAd) {
          createInterstitial = interstitialAd
          promise.resolve(true)
        }

        override fun onAdFailedToLoad(i: LoadAdError?) {
          super.onAdFailedToLoad(i)
          promise.resolve(false)
        }
      })}
  }

  /**
   * forceShowInterstitial
   */
  @ReactMethod
  fun forceShowInterstitial(promise: Promise) {
    runOnUiThread {
      admodInstance.forceShowInterstitial(
        currentActivity,
        createInterstitial,
        object : AdCallback() {
          override fun onAdClosed() {
            promise.resolve(true)
          }

          override fun onAdFailedToShow(adError: AdError?) {
            super.onAdFailedToShow(adError)
            promise.resolve(false)
          }

          override fun onAdFailedToLoad(i: LoadAdError?) {
            super.onAdFailedToLoad(i)
            promise.resolve(false)
          }
        }
      )
    }
  }
}
