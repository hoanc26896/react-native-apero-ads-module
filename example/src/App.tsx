import * as React from 'react';

import { StyleSheet, View, Text, TouchableOpacity, Platform, ToastAndroid } from 'react-native';
import { AdBanner, AperoAdsModuleStatusEnum, forceShowInterstitial, loadInterCreate, onSplashActivity, setOpenActivityAfterShowInterAds } from 'react-native-apero-ads-module';


export default function App() {


  React.useEffect(() => {
    loadInterCreate("ca-app-pub-3940256099942544/1033173712", createInterstitial => {
      if (createInterstitial) {
        // Prepare Success
      } else {
        // Prepare Fall
        notifyMessage("Prepare false");
      }
    })
  }, [])

  const onPressSplash = () => {
    onSplashActivity("ca-app-pub-3940256099942544/1033173712", 0, 0, status => {
      if (status == AperoAdsModuleStatusEnum.AD_CLOSE) {
        notifyMessage("Load ads closed");
      } else {
        notifyMessage("Load ads false");
      }
    });
  }

  const onPressInter = () => {
    // setOpenActivityAfterShowInterAds(true)
    forceShowInterstitial(status => {
      if (status == AperoAdsModuleStatusEnum.AD_CLOSE) {
        notifyMessage("Load ads closed");
      } else {
        notifyMessage("Load ads false");
      }
    })
  }



  function notifyMessage(msg: string) {
    if (Platform.OS === 'android') {
      ToastAndroid.show(msg, ToastAndroid.SHORT)
    }
  }

  return (
    <View style={styles.container}>
      <TouchableOpacity style={styles.btn} onPress={onPressSplash}>
        <Text>Button Splash</Text>
      </TouchableOpacity>
      <TouchableOpacity style={styles.btn} onPress={onPressInter}>
        <Text>Button Inter</Text>
      </TouchableOpacity>
      <AdBanner />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'space-around',
  },
  box: {
    width: 60,
    height: 60,
    marginVertical: 20,
  },
  btn: {
    width: 100,
    height: 40,
    backgroundColor: "green",
    justifyContent: "center",
    alignItems: "center"
  }
});
