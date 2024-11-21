package hu.blueberry.persistentstorage.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import hu.blueberry.persistentstorage.Database
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PersistentStorageModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context)
    =  Room.databaseBuilder(context, Database::class.java, "aquamarine")
        .fallbackToDestructiveMigration()
        .build()
}