package com.example.firstosproject.DI

import android.app.Application
import androidx.room.Room
import com.example.data.LocalDataSourceImpl
import com.example.data.MapUseCasesImpl
import com.example.data.RemoteDataSourceImpl
import com.example.data.RepositoryWithProvider
import com.example.data.local.database.LocalDatabase
import com.example.domain.repositories.Repository
import com.example.domain.repositories.local.LocalDataSource
import com.example.domain.repositories.remote.RemoteDataSource
import com.example.domain.usecases.MapUsecases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ProviderProvider

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ProviderMapuseCase

@Module
@InstallIn(SingletonComponent::class)
object ModuleProvider {

    @Provides
    fun provideDb( application: Application): LocalDatabase =
        Room.databaseBuilder(
            application,
            LocalDatabase::class.java,
            "place_info.db"
        ).build()


    @Provides
    fun provideLocalDataSource(localDatabase: LocalDatabase):LocalDataSource=
        LocalDataSourceImpl(localDatabase)

    @Provides
    fun provideRemoteDataSource():RemoteDataSource=
        RemoteDataSourceImpl()

    @Provides
    @ProviderProvider
    fun provideRepositoryWithProvider(localDataSource: LocalDataSource,
                                       remoteDataSource: RemoteDataSource
    ):Repository=RepositoryWithProvider(localDataSource,remoteDataSource)


    @Provides
    @ProviderMapuseCase
    fun provideMapUseCase(@ProviderProvider repository: Repository):MapUsecases=MapUseCasesImpl(repository)
}