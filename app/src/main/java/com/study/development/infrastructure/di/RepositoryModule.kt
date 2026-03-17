package com.study.development.di

import com.study.development.infrastructure.repository.AuthRepositoryImpl
import com.study.development.infrastructure.repository.CartRepositoryImpl
import com.study.development.infrastructure.repository.ProductRepositoryImpl
import com.study.development.domain.ports.outbound.AuthPort
import com.study.development.domain.ports.outbound.CartPort
import com.study.development.domain.ports.outbound.ProductPort
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(impl: AuthRepositoryImpl): AuthPort

    @Binds
    @Singleton
    abstract fun bindCartRepository(impl: CartRepositoryImpl): CartPort

    @Binds
    @Singleton
    abstract fun bindProductRepository(impl: ProductRepositoryImpl): ProductPort
}
