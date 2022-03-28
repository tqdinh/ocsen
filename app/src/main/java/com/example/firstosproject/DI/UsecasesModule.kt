package com.example.firstosproject.DI

import com.example.data.MapUseCasesImpl
import com.example.data.RepositoryWithBind
import com.example.domain.repositories.Repository
import com.example.domain.usecases.MapUsecases
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BinderMapUsecase

//@Module
//@InstallIn(SingletonComponent::class)
//abstract class UsecasesModule {
//    @Binds
//    abstract fun bindRepositoryWithBind(repositoryWithBind: RepositoryWithBind): Repository
//
//    @BinderMapUsecase
//    @Binds
//    abstract fun bindMapusecase(mapUseCasesImpl: MapUseCasesImpl):MapUsecases
//}

@Module
@InstallIn(SingletonComponent::class)
object UsecasesModule
{
    @Provides
    fun provideUsecase(repository: Repository):MapUsecases=MapUseCasesImpl(repository)

}