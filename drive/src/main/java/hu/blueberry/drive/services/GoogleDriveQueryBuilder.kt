package hu.blueberry.drive.services

class GoogleDriveQueryBuilder {
    var queryBuilder = StringBuilder()

    fun and(): GoogleDriveQueryBuilder {
        queryBuilder.append(" and ")
        return this
    }

    fun or(): GoogleDriveQueryBuilder {
        queryBuilder.append(" or ")
        return this
    }

    fun contains(): GoogleDriveQueryBuilder {
        queryBuilder.append(" contains ")
        return this
    }

    fun mimeType(mimeType:String): GoogleDriveQueryBuilder {
        queryBuilder.append("mimeType='${mimeType}'")
        return this
    }
    fun parents(parentIdList:List<String>): GoogleDriveQueryBuilder {
        for(parent in parentIdList){
            queryBuilder.append(" and '${parent}' in parents")
        }
        return this
    }

    fun stringValue(value: String): GoogleDriveQueryBuilder {
        queryBuilder.append("'$value'")
        return this
    }

    fun queryText(value: String): GoogleDriveQueryBuilder {
        queryBuilder.append(value)
        return this
    }

    fun createQuery(): String {
        return queryBuilder.toString()
    }


}