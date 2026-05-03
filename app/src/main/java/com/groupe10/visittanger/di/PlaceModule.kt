package com.groupe10.visittanger.di

import com.groupe10.visittanger.data.repository.PlaceRepositoryImpl
import com.groupe10.visittanger.domain.repository.PlaceRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class PlaceModule {

    @Binds
    @Singleton
    abstract fun bindPlaceRepository(
        placeRepositoryImpl: PlaceRepositoryImpl
    ): PlaceRepository
}
