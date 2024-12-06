package hu.blueberry.drinks.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hu.blueberry.drinks.model.MemoryDatabase2
import hu.blueberry.persistentstorage.model.updatedextradata.WorkingDirectory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DrinksModule {

    @Provides
    @Singleton
    fun provideMemoryDatabase(): MemoryDatabase2 {
        return MemoryDatabase2(WorkingDirectory(WorkingDirectory.SavedDatabaseId,null,null))
    }
}