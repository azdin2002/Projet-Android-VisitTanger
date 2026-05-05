package com.groupe10.visittanger.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.groupe10.visittanger.data.local.entity.VisitedPlaceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface VisitedPlaceDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun markAsVisited(entity: VisitedPlaceEntity)

    @Query("SELECT COUNT(*) FROM visited_places WHERE userId = :userId")
    fun getVisitedCount(userId: String): Flow<Int>
}
