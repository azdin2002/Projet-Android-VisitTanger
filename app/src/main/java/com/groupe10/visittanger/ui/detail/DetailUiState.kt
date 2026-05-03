package com.groupe10.visittanger.ui.detail

import com.groupe10.visittanger.domain.model.Place
import com.groupe10.visittanger.domain.model.Review

data class DetailUiState(
    val place: Place? = null,
    val reviews: List<Review> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null,
    val isFavoriteToggling: Boolean = false,
    val selectedPhotoIndex: Int = 0,
    val showAllReviews: Boolean = false
)
