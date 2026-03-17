package com.study.development.di

import android.content.Context
import com.study.development.infrastructure.adapters.local.CartStorage
import com.study.development.infrastructure.adapters.local.SessionPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StorageModule {

    @Provides
    @Singleton
    fun provideCartStorage(@ApplicationContext context: Context): CartStorage =
        CartStorage(context)

    @Provides
    @Singleton
    fun provideSessionPreferences(@ApplicationContext context: Context): SessionPreferences =
        SessionPreferences(context)
}
