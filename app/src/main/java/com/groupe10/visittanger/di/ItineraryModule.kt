package com.groupe10.visittanger.di

import com.groupe10.visittanger.data.repository.ItineraryRepositoryImpl
import com.groupe10.visittanger.domain.repository.ItineraryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ItineraryModule {
    
    @Provides
    @Singleton
    fun provideItineraryRepository(
        impl: ItineraryRepositoryImpl
    ): ItineraryRepository = impl
}
