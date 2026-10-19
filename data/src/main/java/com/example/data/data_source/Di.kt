package com.example.data.data_source

import com.example.data.data_source_contract.AuthDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class Di {

    @Binds
    abstract fun bindLoginDataSource(
        loginDataSourceImpl: AuthDataSourceImpl
    ): AuthDataSource
}