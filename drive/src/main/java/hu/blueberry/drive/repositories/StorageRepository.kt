package hu.blueberry.drive.repositories

import hu.blueberry.drive.services.FileService
import javax.inject.Inject


class StorageRepository @Inject constructor(
    var fileService: FileService
){

}