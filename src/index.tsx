import { NativeModules, Platform } from 'react-native';

const LINKING_ERROR =
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

export function onSplashActivity(adInterstitialSplash: string,
  timeout: number,
  timeDelay: number,
  callback: (status: AperoAdsModuleStatusEnum) => void) {
  return AperoAdsModule.onSplashActivity(adInterstitialSplash, timeout, timeDelay, callback)
}

export function loadInterCreate(idAdInterstital: string): Promise<boolean> {
  return AperoAdsModule.loadInterCreate(idAdInterstital)
}

export function forceShowInterstitial(): Promise<boolean> {
  return AperoAdsModule.forceShowInterstitial()
}
