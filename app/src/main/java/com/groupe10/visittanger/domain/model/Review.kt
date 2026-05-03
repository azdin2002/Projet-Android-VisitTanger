package com.groupe10.visittanger.domain.model

data class Review(
    val id: String,
    val userName: String,
    val userPhoto: String? = null,
    val rating: Float,
    val comment: String,
    val date: String
)
