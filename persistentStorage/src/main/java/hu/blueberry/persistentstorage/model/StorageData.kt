package hu.blueberry.persistentstorage.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable


@Serializable
data class StorageData(
    var name : String,
    var open : Int = 0,
    var cart : Int = 0,
    var close : Int = 0,
)