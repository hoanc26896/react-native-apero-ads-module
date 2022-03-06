import React, { useEffect, useRef } from 'react';
import {
  NativeModules, Platform
} from 'react-native';
import { AdBanner } from './container/AdBannerView';

export const LINKING_ERROR =
  `The package 'react-native-apero-ads-module' doesn't seem to be linked. Make sure: \n\n` +
  Platform.select({ ios: "- You have run 'pod install'\n", default: '' }) +
  '- You rebuilt the app after installing the package\n' +
  '- You are not using Expo managed workflow\n';

export enum AperoAdsModuleStatusEnum {
  AD_FAIL_TO_LOAD = 0,
  AD_FAIL_TO_SHOW = 1,
  AD_CLOSE = 2
}

const AperoAdsModule = NativeModules.AperoAdsModule
  ? NativeModules.AperoAdsModule
  : new Proxy(
    {},
    {
      get() {
        throw new Error(LINKING_ERROR);
      },
    }
  );

export function multiply(a: number, b: number): Promise<number> {
  return AperoAdsModule.multiply(a, b);
}

export function getInstance() {
  return AperoAdsModule.admodInstance
}

export function onCreate() {
  return AperoAdsModule.onCreate()
}

/**
 * Show SplashActivity
 * @param adInterstitialSplash 
 * @param timeout 
 * @param timeDelay 
 * @param callback 
 * @returns 
 */
export function onSplashActivity(adInterstitialSplash: string,
  timeout: number,
  timeDelay: number,
  callback: (status: AperoAdsModuleStatusEnum) => void) {
  return AperoAdsModule.onSplashActivity(adInterstitialSplash, timeout, timeDelay, callback)
}

/**
 * Prepare Inter Ads
 * @param idAdInterstital 
 * @param callback 
 * @returns 
 */
export function loadInterCreate(idAdInterstital: string, callback: (createInterstitial: object) => void) {
  return AperoAdsModule.loadInterCreate(idAdInterstital, callback)
}

/**
 * Force show Inter ads
 * @param createInterstitial 
 * @param callback 
 * @returns 
 */
export function forceShowInterstitial(callback: (status: AperoAdsModuleStatusEnum) => void) {
  return AperoAdsModule.forceShowInterstitial(callback)
}

/**
 * Allow activity after excute ads
 * @param isOpen 
 * @returns 
 */
export function setOpenActivityAfterShowInterAds(isOpen: boolean = false) {
  return AperoAdsModule.setOpenActivityAfterShowInterAds(isOpen)
}

export * from "./container/AdBannerView"