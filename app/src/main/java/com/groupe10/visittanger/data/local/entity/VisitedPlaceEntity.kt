package com.groupe10.visittanger.data.local.entity

import androidx.room.Entity

@Entity(
    tableName = "visited_places",
    primaryKeys = ["placeId", "userId"],
)
data class VisitedPlaceEntity(
    val placeId: String,
    val userId: String,
    val visitedAt: Long = System.currentTimeMillis(),
)
