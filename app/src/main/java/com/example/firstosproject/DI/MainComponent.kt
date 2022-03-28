package com.example.firstosproject.DI


import com.example.domain.repositories.local.LocalDataSource
import com.example.firstosproject.SplashUpdater
import dagger.Component
import javax.inject.Singleton


//@Singleton
//@Component(modules = [UsecasesModule::class])
interface MainComponent {
   // fun provideLocalDatabase():LocalDataSource
    fun inject(splahActivity: SplashUpdater)
}