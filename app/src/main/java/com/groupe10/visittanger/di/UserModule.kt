package com.groupe10.visittanger.di

import com.groupe10.visittanger.data.repository.UserRepositoryImpl
import com.groupe10.visittanger.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserModule {
    
    @Provides
    @Singleton
    fun provideUserRepository(
        impl: UserRepositoryImpl
    ): UserRepository = impl
}
