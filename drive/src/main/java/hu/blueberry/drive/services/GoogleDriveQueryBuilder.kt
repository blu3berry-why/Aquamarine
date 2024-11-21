package hu.blueberry.drive.services

class GoogleDriveQueryBuilder {
    var queryBuilder = StringBuilder()
    var previousArgumentWasEmtpy = false

    fun and(): GoogleDriveQueryBuilder {
        if (previousArgumentWasEmtpy){
            previousArgumentWasEmtpy = false
            return this
        }
        queryBuilder.append(" and ")
        return this
    }

    fun or(): GoogleDriveQueryBuilder {
        if (previousArgumentWasEmtpy){
            previousArgumentWasEmtpy = false
            return this
        }
        queryBuilder.append(" or ")
        return this
    }

    fun contains(): GoogleDriveQueryBuilder {
        queryBuilder.append(" contains ")
        return this
    }

    fun mimeType(mimeType:String?): GoogleDriveQueryBuilder {
        if (mimeType == null){
            previousArgumentWasEmtpy = true
            return this
        }
        queryBuilder.append("mimeType='${mimeType}'")
        return this
    }

    fun parents(parentIdList:List<String>?): GoogleDriveQueryBuilder {
        if (parentIdList.isNullOrEmpty()){
            previousArgumentWasEmtpy = true
            return this
        }
        val mutableParentIdList = parentIdList.toMutableList()
        val lastParent = mutableParentIdList.removeAt(mutableParentIdList.lastIndex)

        for(parent in parentIdList){
            appendParent(parent).and()
        }

        appendParent(lastParent)
        return this
    }

    private fun appendParent(parent: String): GoogleDriveQueryBuilder {
        queryBuilder.append("'${parent}' in parents")
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