package com.reactnativeaperoadsmodule.ads.control.funtion;

import androidx.annotation.Nullable;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.nativead.NativeAd;

public class AdCallback {


    public void onAdClosed() {
    }



    public void onAdFailedToLoad(@Nullable  LoadAdError i) {
    }
    public void onAdFailedToShow(@Nullable AdError adError) {
    }

    public void onAdLeftApplication() {
    }


    public void onAdLoaded() {
    }

    public void onInterstitialLoad(InterstitialAd interstitialAd) {

    }

    public void onAdClicked() {
    }

    public void onAdImpression() {
    }


    public void onUnifiedNativeAdLoaded( NativeAd unifiedNativeAd) {

    }
}
