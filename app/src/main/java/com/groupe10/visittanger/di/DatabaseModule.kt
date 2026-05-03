package com.groupe10.visittanger.di

import android.content.Context
import androidx.room.Room
import com.groupe10.visittanger.data.local.AppDatabase
import com.groupe10.visittanger.data.local.dao.FavoriteDao
import com.groupe10.visittanger.data.repository.FavoriteRepositoryImpl
import com.groupe10.visittanger.domain.repository.FavoriteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        )
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun provideFavoriteDao(database: AppDatabase): FavoriteDao =
        database.favoriteDao()

    @Provides
    @Singleton
    fun provideFavoriteRepository(
        impl: FavoriteRepositoryImpl
    ): FavoriteRepository = impl
}
