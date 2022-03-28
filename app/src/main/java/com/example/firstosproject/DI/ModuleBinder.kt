package com.example.firstosproject.DI

import com.example.data.RepositoryWithBind
import com.example.domain.repositories.Repository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ProviderBinder

@InstallIn(SingletonComponent::class)
@Module
abstract class ModuleBinder {
    @Binds
    @ProviderBinder
    abstract fun bindRepositoryWithBind(repositoryWithBind: RepositoryWithBind):Repository
}