package com.groupe10.visittanger.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.groupe10.visittanger.data.local.dao.FavoriteDao
import com.groupe10.visittanger.data.local.entity.FavoriteEntity

@Database(
    entities = [FavoriteEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao

    companion object {
        const val DATABASE_NAME = "visit_tanger_db"
    }
}
