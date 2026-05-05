package com.groupe10.visittanger.data.local.dao

import androidx.room.*
import com.groupe10.visittanger.data.local.entity.FavoriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorites WHERE userId = :userId ORDER BY savedAt DESC")
    fun getFavoritesByUser(userId: String): Flow<List<FavoriteEntity>>

    @Query("SELECT * FROM favorites WHERE placeId = :placeId AND userId = :userId LIMIT 1")
    suspend fun getFavoriteById(placeId: String, userId: String): FavoriteEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: FavoriteEntity)

    @Delete
    suspend fun deleteFavorite(favorite: FavoriteEntity)

    @Query("DELETE FROM favorites WHERE placeId = :placeId AND userId = :userId")
    suspend fun deleteFavoriteById(placeId: String, userId: String)

    @Query("SELECT EXISTS(SELECT 1 FROM favorites WHERE placeId = :placeId AND userId = :userId)")
    fun isFavorite(placeId: String, userId: String): Flow<Boolean>

    @Query("SELECT COUNT(*) FROM favorites WHERE userId = :userId")
    fun getFavoritesCount(userId: String): Flow<Int>
}
