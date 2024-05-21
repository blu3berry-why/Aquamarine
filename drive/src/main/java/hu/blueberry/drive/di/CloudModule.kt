package hu.blueberry.drive.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hu.blueberry.drive.base.CloudBase
import hu.blueberry.drive.repositories.DriveRepository
import hu.blueberry.drive.services.DriveService
import hu.blueberry.drive.services.GoogleSheetsService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CloudModule {

    @Provides
    fun cloudBase(context: Context) = CloudBase(context)


    @Provides
    @Singleton
    fun provideDriveManager(cloudBase: CloudBase) = DriveService(cloudBase)

    @Provides
    @Singleton
    fun provideSheetsManager(cloudBase: CloudBase) = GoogleSheetsService(cloudBase)

    @Provides
    @Singleton
    fun providesDriveRepository(driveManager: DriveService): DriveRepository = DriveRepository(driveManager)
}