package hu.blueberry.projectaquamarine.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hu.blueberry.projectaquamarine.auth.helper.AuthenticatedUser
import javax.inject.Singleton

@Module
// The component will decide how long this component will live
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideAuthenticatedUser() = AuthenticatedUser()



}