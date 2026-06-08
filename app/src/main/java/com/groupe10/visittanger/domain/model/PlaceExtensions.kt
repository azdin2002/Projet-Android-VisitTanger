package com.groupe10.visittanger.domain.model

import com.groupe10.visittanger.domain.util.localized

fun Place.localizedName(lang: String): String = names.localized(lang).ifBlank { name }

fun Place.localizedTeaser(lang: String): String = teaser.localized(lang)

fun Place.localizedDescription(lang: String): String = description.localized(lang)

fun Place.localizedLocalTip(lang: String): String = localTips.localized(lang)
