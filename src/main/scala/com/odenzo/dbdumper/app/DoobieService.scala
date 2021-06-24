package com.odenzo.dbdumper.app

class DoobieService(dbConfig: DbConfig) {

  //  // ssh -N -L 3307:development-mysql-feb-18-2021.cqyx9zsuwjgd.us-east-1.rds.amazonaws.com:3309:3306 -p 22 ec2-user@30.143.243.20
  println(s"Lighting up doobie...")
  import cats.effect.{IO, _}
  import doobie._
  import doobie.implicits._

  import scala.concurrent.ExecutionContext

  private val xa = Transactor.fromDriverManager[IO](dbConfig.driver, dbConfig.url, dbConfig.user, dbConfig.password)

  def getColumnDataForTable(table: TableData): IO[TableDataFull] = {
    sql"""SELECT *
        FROM INFORMATION_SCHEMA.COLUMNS
        WHERE table_name =  ${table.tableName} AND table_schema = ${table.tableSchema}"""
      .query[ColumnData]
      .to[List]
      .transact(xa)
      .map(TableDataFull(table, _))
  }

  def findTables(schema: String): IO[List[TableData]] = {
    sql"""SELECT table_name,TABLE_SCHEMA, table_comment
           FROM INFORMATION_SCHEMA.TABLES
           WHERE table_schema = $schema"""
      .query[TableData]
      .to[List]
      .transact(xa)

  }

  def addTableComment(schema: String, table: String, comment: String): IO[Int] = {
    val raw = s"ALTER TABLE $schema.$table COMMENT = $comment"
    Fragment.const(raw).update.run.transact(xa)
  }

  /** Grants all right on all schema on all table to user from all location */
  def grantSuperUser(user: String) = {
    sql"""GRANT ALL PRIVILEGES ON *.* TO $user@'%'""".update.run.transact(xa)
  }

}
