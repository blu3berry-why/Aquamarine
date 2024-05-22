package hu.blueberry.persistentstorage.model.converter

import androidx.room.TypeConverter
import hu.blueberry.persistentstorage.model.MeasureUnit
import hu.blueberry.persistentstorage.model.ProductType
import hu.blueberry.persistentstorage.model.StorageData
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class Converters{

    @TypeConverter
    fun toMeasureUnit(value: String) = enumValueOf<MeasureUnit>(value)

    @TypeConverter
    fun fromMeasuerUnit(value: MeasureUnit) = value.name

    @TypeConverter
    fun toProductType(value: String) = enumValueOf<ProductType>(value)

    @TypeConverter
    fun fromProductType(value: ProductType) = value.name

    @TypeConverter
    fun toStorageData(value: String) = Json.decodeFromString<StorageData>(value)

    @TypeConverter
    fun fromStorageData(storageData: StorageData) = Json.encodeToString(StorageData.serializer(), storageData)

    @TypeConverter
    fun fromStorageDataList(storageDataList: MutableList<StorageData>) = Json.encodeToString(
        ListSerializer(StorageData.serializer()) ,storageDataList)

    @TypeConverter
    fun toStorageDataList(value: String) = Json.decodeFromString<MutableList<StorageData>>(value)

}