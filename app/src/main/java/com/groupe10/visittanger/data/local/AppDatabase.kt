package com.groupe10.visittanger.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.groupe10.visittanger.data.local.converter.MapConverter
import com.groupe10.visittanger.data.local.dao.FavoriteDao
import com.groupe10.visittanger.data.local.dao.PlaceDao
import com.groupe10.visittanger.data.local.dao.VisitedPlaceDao
import com.groupe10.visittanger.data.local.entity.FavoriteEntity
import com.groupe10.visittanger.data.local.entity.PlaceEntity
import com.groupe10.visittanger.data.local.entity.VisitedPlaceEntity

@Database(
    entities = [FavoriteEntity::class, VisitedPlaceEntity::class, PlaceEntity::class],
    version = 7,
    exportSchema = false,
)
@TypeConverters(MapConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
    abstract fun visitedPlaceDao(): VisitedPlaceDao
    abstract fun placeDao(): PlaceDao

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

        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `visited_places` (
                        `placeId` TEXT NOT NULL,
                        `userId` TEXT NOT NULL,
                        `visitedAt` INTEGER NOT NULL,
                        PRIMARY KEY(`placeId`)
                    )
                    """.trimIndent(),
                )
            }
        }

        val MIGRATION_3_4 = object : Migration(3, 4) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `visited_places_new` (
                        `placeId` TEXT NOT NULL,
                        `userId` TEXT NOT NULL,
                        `visitedAt` INTEGER NOT NULL,
                        PRIMARY KEY(`placeId`, `userId`)
                    )
                    """.trimIndent(),
                )
                db.execSQL(
                    """
                    INSERT OR IGNORE INTO `visited_places_new` (`placeId`, `userId`, `visitedAt`)
                    SELECT `placeId`, `userId`, `visitedAt`
                    FROM `visited_places`
                    """.trimIndent(),
                )
                db.execSQL("DROP TABLE `visited_places`")
                db.execSQL("ALTER TABLE `visited_places_new` RENAME TO `visited_places`")
            }
        }

        val MIGRATION_4_5 = object : Migration(4, 5) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `places` (
                        `id` TEXT NOT NULL,
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
                        PRIMARY KEY(`id`)
                    )
                    """.trimIndent(),
                )
            }
        }
    }
}
