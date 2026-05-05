package com.study.development.infrastructure.di

import com.study.development.domain.ports.outbound.AuthPort
import com.study.development.domain.ports.outbound.CartPort
import com.study.development.domain.ports.inbound.ProductPort
import com.study.development.domain.ports.inbound.OrderPort
import com.study.development.infrastructure.repository.AuthRepositoryImpl
import com.study.development.infrastructure.repository.CartRepositoryImpl
import com.study.development.infrastructure.repository.ProductRepositoryImpl
import com.study.development.infrastructure.repository.OrderRepositoryImpl
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
    abstract fun bindAuthPort(impl: AuthRepositoryImpl): AuthPort

    @Binds
    @Singleton
    abstract fun bindCartPort(impl: CartRepositoryImpl): CartPort

    @Binds
    @Singleton
    abstract fun bindProductPort(impl: ProductRepositoryImpl): ProductPort

    @Binds
    @Singleton
    abstract fun bindOrderPort(impl: OrderRepositoryImpl): OrderPort
}
