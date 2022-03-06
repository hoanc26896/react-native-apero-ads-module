import React, { useEffect, useRef } from 'react';
import { UIManager, findNodeHandle, PixelRatio, Dimensions } from 'react-native';
import { AdBannerViewManager } from './AdBannerManager';

const { width, height } = Dimensions.get('window');

const createFragment = (viewId) =>
    UIManager.dispatchViewManagerCommand(
        viewId,
        // we are calling the 'create' command
        UIManager.AdBannerViewManager.Commands.create.toString(),
        [viewId]
    );



export const AdBanner = () => {
    const ref = useRef(null);

    useEffect(() => {
        const viewId = findNodeHandle(ref.current);
        createFragment(viewId);
    }, []);

    return (
        <AdBannerViewManager
            ref={ref}
            style={{
                // converts dpi to px, provide desired height
                height: PixelRatio.getPixelSizeForLayoutSize(50),
                // converts dpi to px, provide desired width
                width: PixelRatio.getPixelSizeForLayoutSize(width),
            }}
            isBottomPosition={true}
        />
    );
};