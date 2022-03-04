import React, { useEffect, useRef } from 'react';
import { UIManager, findNodeHandle, PixelRatio, Dimensions } from 'react-native';

import { AdbannerViewManager } from '../index';

const createFragment = (viewId: number) =>
    UIManager.dispatchViewManagerCommand(
        viewId,
        // we are calling the 'create' command
        UIManager.AdbannerViewManager.Commands.banner.toString(),
        [viewId]
    );

const loadAds = (viewId: number) =>
    UIManager.dispatchViewManagerCommand(
        viewId,
        // we are calling the 'create' command
        UIManager.AdbannerViewManager.Commands.load.toString(),
        [viewId]
    );

interface Props {
    isShow: boolean
}

export const AdBanner = (props: Props) => {
    const ref = useRef(null);
    const { isShow = true } = props

    useEffect(() => {
        const viewId = findNodeHandle(ref.current);
        createFragment(viewId);
        loadAds(viewId)
    }, []);

    // useEffect(() => {
    //     if (isShow) {
    //         const viewId = findNodeHandle(ref.current);

    //     }

    // }, [isShow])


    return (
        <AdbannerViewManager
            bannerId={"ca-app-pub-3940256099942544/6300978111"}
            style={{
                // converts dpi to px, provide desired height
                height: 100,
                // converts dpi to px, provide desired width
                width: Dimensions.get('window').width
            }}
            ref={ref}
        />
    );
}