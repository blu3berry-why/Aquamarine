package hu.blueberry.camera.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import hu.blueberry.camera.services.FileManager
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object CameraModule {

@Provides
@Singleton
fun provideFileManager(@ApplicationContext context: Context) = FileManager(context)

}