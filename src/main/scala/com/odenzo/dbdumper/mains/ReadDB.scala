package com.odenzo.dbdumper.mains

import cats._
import cats.data._
import cats.syntax.all._
import cats.effect._
import cats.effect.syntax.all._
import com.esotericsoftware.kryo.io.ByteBufferOutputStream
import com.norbitltd.spoiwo.model._
import com.norbitltd.spoiwo.natures.csv.Model2CsvConversions.CsvWorkbook
import com.norbitltd.spoiwo.natures.xlsx._
import com.norbitltd.spoiwo.natures.csv._
import com.norbitltd.spoiwo.natures.xlsx.Model2XlsxConversions.XlsxWorkbook
import fs2._
import com.odenzo.dbdumper.app._
import com.odenzo.utils.OPrint.oprint
import os.Path

import java.io.FileOutputStream
import java.time.LocalDate
import scala.reflect.io.File

object ReadDB extends IOApp {
  val schemas     = List("cm", "cm_gladiator") // Missing tdss:  "tdss_new")
  val outDir      = "output"                   // Relative
  val osOut: Path = os.pwd / outDir

  // ssh -N -L 3307:development-mysql-feb-18-2021.cqyx9zsuwjgd.us-east-1.rds.amazonaws.com:3309:3306 -p 22 ec2-user@30.143.243.20
  override def run(args: List[String]): IO[ExitCode] = {

    os.makeDir.all(os.pwd / outDir)

    for {
      dbConfig  <- ConfigLoader.loadConfig()
      _          = scribe.info(s"Local DB: ${oprint(dbConfig)}")
      doobie     = new DoobieService(dbConfig)
      perSchema <- schemas.traverse { s => processSchema(s, doobie) }
      _ <- perSchema.traverseTap { case (s: String, sheet: Sheet) =>
             val filePath = osOut / s"schema_${s}_${LocalDate.now().toString}.xlsx"
             writeSheetToDisk(sheet, filePath)
           }
      workbook     = Workbook.Empty.withSheets(perSchema.map(_._2)).withActiveSheet(0)
      workbookFile = osOut / s"workbook_${LocalDate.now().toString}.xlsx"
      _           <- writeWorkbookToDisk(workbook, workbookFile)
      _           <- IO(os.proc("open", workbookFile.toString()))
    } yield ExitCode.Success
  }

  def writeSheetToDisk(excel: Sheet, dest: os.Path): IO[FileOutputStream] = {
    val workbook = Workbook.Empty.withSheets(excel).withActiveSheet(0)
    writeWorkbookToDisk(workbook, dest)
  }
  def writeWorkbookToDisk(excel: Workbook, dest: os.Path): IO[FileOutputStream] = IO {
    excel.writeToOutputStream(new FileOutputStream(dest.toIO))
  }

  /** Get all schema, table, and table column information for a given schema and convert to one excel sheet Seperate by schema because later
    * might have different DB server for each schema
    */
  def processSchema(schema: String, server: DoobieService): IO[(String, Sheet)] = {
    fs2
      .Stream(schema)
      .evalMap((s: String) => server.findTables(s))
      .flatMap(fs2.Stream.emits(_))
      .filterNot(_.tableName == "flyway_schema_history")
      .evalMap(t => server.getColumnDataForTable(t))
      .compile
      .toList
      .flatMap(v => SchemaReport.generateExcel(schema, v))
      .tupleLeft(schema)

  }

}
