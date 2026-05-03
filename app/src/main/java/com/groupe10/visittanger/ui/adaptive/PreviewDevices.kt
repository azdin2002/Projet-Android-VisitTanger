package com.groupe10.visittanger.ui.adaptive

import androidx.compose.ui.tooling.preview.Preview

// Preview smartphone portrait
@Preview(
    name = "Phone Portrait",
    device = "spec:width=360dp,height=800dp,dpi=420"
)
annotation class PhonePortraitPreview

// Preview smartphone paysage
@Preview(
    name = "Phone Landscape",
    device = "spec:width=800dp,height=360dp,dpi=420"
)
annotation class PhoneLandscapePreview

// Preview tablette portrait
@Preview(
    name = "Tablet Portrait",
    device = "spec:width=700dp,height=1024dp,dpi=320"
)
annotation class TabletPortraitPreview

// Preview tablette paysage
@Preview(
    name = "Tablet Landscape",
    device = "spec:width=1280dp,height=800dp,dpi=320"
)
annotation class TabletLandscapePreview
