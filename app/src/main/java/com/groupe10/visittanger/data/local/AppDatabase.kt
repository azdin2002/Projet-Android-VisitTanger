package com.groupe10.visittanger.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.groupe10.visittanger.data.local.converter.MapConverter
import com.groupe10.visittanger.data.local.dao.FavoriteDao
import com.groupe10.visittanger.data.local.entity.FavoriteEntity

@Database(
    entities = [FavoriteEntity::class],
    version = 2,
    exportSchema = false,
)
@TypeConverters(MapConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao

    companion object {
        const val DATABASE_NAME = "visit_tanger_db"

        /**
         * Ancienne table sans [FavoriteEntity.userId] : on recrée la table avec clé composite
         * (placeId, userId). Les lignes existantes ne sont pas rattachées à un utilisateur Firebase,
         * elles sont donc abandonnées (comportement attendu pour données locales non scopées).
         */
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `favorites_new` (
                        `placeId` TEXT NOT NULL,
                        `userId` TEXT NOT NULL,
                        `name` TEXT NOT NULL,
                        `description` TEXT NOT NULL,
                        `category` TEXT NOT NULL,
                        `latitude` REAL NOT NULL,
                        `longitude` REAL NOT NULL,
                        `address` TEXT NOT NULL,
                        `photos` TEXT NOT NULL,
                        `rating` REAL NOT NULL,
                        `reviewCount` INTEGER NOT NULL,
                        `openingHours` TEXT,
                        `price` TEXT,
                        `distanceKm` REAL,
                        `savedAt` INTEGER NOT NULL,
                        PRIMARY KEY(`placeId`, `userId`)
                    )
                    """.trimIndent(),
                )
                db.execSQL("DROP TABLE `favorites`")
                db.execSQL("ALTER TABLE `favorites_new` RENAME TO `favorites`")
            }
        }
    }
}
