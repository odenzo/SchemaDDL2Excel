package com.odenzo.dbdumper.app

class Models {}

case class TableDataFull(table: TableData, columns: List[ColumnData])

case class TableData(tableName: String, tableSchema: String, tableComment: String)

/** MySQL 5.x COLUMNS in information_schema */
case class ColumnData(
    tableCatalog: String,
    tableSchema: String,
    tableName: String,
    columnName: String,
    ordinalPosition: Int,
    columnDefault: Option[String],
    isNullable: Option[String],
    dataType: String,
    characterMaxLength: Option[Long],
    characterOctetLength: Option[Long],
    numericPrecision: Option[Long],
    numericScale: Option[Long],
    datetimePrecision: Option[Long],
    characterSetName: Option[String],
    collationName: Option[String],
    columnType: String,
    columnKey: String,
    extra: String,
    priveleges: String,
    columnComment: String,
    generationExpression: String
)
