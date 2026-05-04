package com.groupe10.visittanger.di

import com.groupe10.visittanger.data.remote.api.WeatherApiService
import com.groupe10.visittanger.data.repository.WeatherRepositoryImpl
import com.groupe10.visittanger.domain.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WeatherModule {

    @Provides
    @Singleton
    fun provideWeatherApiService(
        @Named("weather") retrofit: Retrofit
    ): WeatherApiService =
        retrofit.create(WeatherApiService::class.java)

    @Provides
    @Singleton
    fun provideWeatherRepository(
        impl: WeatherRepositoryImpl
    ): WeatherRepository = impl
}
