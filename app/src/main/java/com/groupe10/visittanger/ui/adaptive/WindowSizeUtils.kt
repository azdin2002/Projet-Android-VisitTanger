package com.groupe10.visittanger.ui.adaptive

import android.app.Activity
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

enum class DeviceType {
    PHONE_PORTRAIT,    // Compact width
    PHONE_LANDSCAPE,   // Medium width
    TABLET_PORTRAIT,   // Medium width + height
    TABLET_LANDSCAPE   // Expanded width
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun rememberDeviceType(): DeviceType {
    val windowSizeClass = calculateWindowSizeClass(
        LocalContext.current as Activity
    )
    return when (windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Compact -> DeviceType.PHONE_PORTRAIT
        WindowWidthSizeClass.Medium -> DeviceType.PHONE_LANDSCAPE
        WindowWidthSizeClass.Expanded -> DeviceType.TABLET_LANDSCAPE
        else -> DeviceType.PHONE_PORTRAIT
    }
}

data class AdaptiveLayoutConfig(
    val deviceType: DeviceType,
    val showNavigationRail: Boolean,
    val showNavigationDrawer: Boolean,
    val showBottomBar: Boolean,
    val contentColumns: Int,
    val listDetailEnabled: Boolean
) {
    companion object {
        fun from(deviceType: DeviceType) = AdaptiveLayoutConfig(
            deviceType = deviceType,
            showNavigationRail = deviceType == DeviceType.PHONE_LANDSCAPE,
            showNavigationDrawer = deviceType == DeviceType.TABLET_LANDSCAPE,
            showBottomBar = deviceType == DeviceType.PHONE_PORTRAIT
                || deviceType == DeviceType.TABLET_PORTRAIT,
            contentColumns = when (deviceType) {
                DeviceType.PHONE_PORTRAIT -> 1
                DeviceType.PHONE_LANDSCAPE -> 2
                DeviceType.TABLET_PORTRAIT -> 2
                DeviceType.TABLET_LANDSCAPE -> 3
            },
            listDetailEnabled = deviceType == DeviceType.TABLET_LANDSCAPE
                || deviceType == DeviceType.TABLET_PORTRAIT
        )
    }
}
