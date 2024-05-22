package hu.blueberry.drive.model

import androidx.compose.runtime.snapshots.SnapshotStateList

data class MemoryDatabase(
    var folderId: String? = null,
    var spreadsheetId: String? = null
)
