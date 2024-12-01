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
        return appendList(
            parentIdList,
            appendItem = {
                appendParent(it)
            },
            relationBetweenAppendedItems = ::and
        )
    }

    fun listOfMimeTypes(mimeTypeList: List<String>?): GoogleDriveQueryBuilder {
        return appendList(
            itemList = mimeTypeList,
            appendItem = {
                mimeType(it)
            },
            relationBetweenAppendedItems = ::or
        )
    }

    private fun <T> appendList(
        itemList: List<T>?,
        appendItem: (T) -> GoogleDriveQueryBuilder,
        relationBetweenAppendedItems: () -> GoogleDriveQueryBuilder
    ): GoogleDriveQueryBuilder {
        if (itemList.isNullOrEmpty()){
            previousArgumentWasEmtpy = true
            return this
        }

        startParentheses()

        val mutableItemList = itemList.toMutableList()
        val lastItem = mutableItemList.removeAt(mutableItemList.lastIndex)

        for(item in mutableItemList){
            appendItem(item)
            relationBetweenAppendedItems()
        }

        appendItem(lastItem)
        endParentheses()
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

    fun startParentheses():GoogleDriveQueryBuilder{
        queryBuilder.append(" (")
        return this
    }
    fun endParentheses():GoogleDriveQueryBuilder{
        queryBuilder.append(") ")
        return this
    }

    fun and(expression: (GoogleDriveQueryBuilder) -> GoogleDriveQueryBuilder): GoogleDriveQueryBuilder{
        return logicalOperation(expression, queryText = "and")
    }

    fun or(expression: (GoogleDriveQueryBuilder) -> GoogleDriveQueryBuilder): GoogleDriveQueryBuilder {
        return logicalOperation(expression, "or")
    }

    private fun logicalOperation(expression: (GoogleDriveQueryBuilder) -> GoogleDriveQueryBuilder, queryText:String): GoogleDriveQueryBuilder{
        val returnedExpression= expression(GoogleDriveQueryBuilder()).createQuery()
        if (returnedExpression.isNotEmpty()){
            previousArgumentWasEmtpy = false
            //Put operation before but only if the given query is not empty
            queryBuilder.append(" $queryText ")
            // Put the part between parentheses
            startParentheses()
            this.queryText(returnedExpression)
            endParentheses()
        }
        return this
    }



}