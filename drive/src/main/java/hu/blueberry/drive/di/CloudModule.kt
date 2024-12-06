package hu.blueberry.drive.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import hu.blueberry.drive.base.CloudBase
import hu.blueberry.drive.model.MemoryDatabase
import hu.blueberry.drive.permissions.PermissionRequestManager
import hu.blueberry.drive.repositories.DriveRepository
import hu.blueberry.drive.repositories.GoogleSheetRepository
import hu.blueberry.drive.services.DriveService
import hu.blueberry.drive.services.FileService
import hu.blueberry.drive.services.GoogleSheetsService
import hu.bme.aut.android.news.connection.NetworkConnectivityObserver
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CloudModule {

    @Provides
    fun cloudBase(@ApplicationContext context: Context) = CloudBase(context)

    @Provides
    @Singleton
    fun provideMemoryDatabase() = MemoryDatabase()


    @Provides
    @Singleton
    fun provideNetworkConnectivityObserver(@ApplicationContext context: Context) = NetworkConnectivityObserver(context)

    @Provides
    fun providePermissionRequestManager() = PermissionRequestManager()

    @Provides
    @Singleton
    fun provideDriveService(cloudBase: CloudBase) = DriveService(cloudBase)

    @Provides
    @Singleton
    fun provideSheetsService(cloudBase: CloudBase) = GoogleSheetsService(cloudBase)

    @Provides
    @Singleton
    fun provideFileService(@ApplicationContext context: Context) = FileService(context)

    @Provides
    fun providesDriveRepository(driveManager: DriveService, fileService: FileService): DriveRepository = DriveRepository(driveManager, fileService)

    @Provides
    fun providesSpreadSheetRepository( googlesheetService: GoogleSheetsService) = GoogleSheetRepository(googlesheetService)
}
