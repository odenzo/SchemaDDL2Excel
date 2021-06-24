package com.odenzo.dbdumper.app

import cats.effect.IO
import cats.syntax.all._
import com.norbitltd.spoiwo.model.enums.CellFill
import com.norbitltd.spoiwo.model._
import com.norbitltd.spoiwo.natures.xlsx.Model2XlsxConversions._

object SchemaReport {

  /** Generates a Sheet that can be added to a document */
  def generateExcel(schema: String, info: List[TableDataFull]): IO[Sheet] = {
    IO {
      println(s"Generating Excel for $schema with ${info.length} tables.")
      makeExcelSheet(schema: String, info)
    }
  }

  val headers = List("Table", "Field", "Data Type", "Nullable", "Default Value", "Description")
  def makeExcelSheet(schema: String, data: List[TableDataFull]): Sheet = {
    val headerStyle   = CellStyle(fillPattern = CellFill.Solid, fillForegroundColor = Color.AquaMarine, font = Font(bold = true))
    val defaultColumn = Column(autoSized = true)

    val tnameFirst = CellStyle(
      font = Font(bold = true, color = Color.Black),
      fillForegroundColor = Color.Yellow,
      fillPattern = CellFill.Solid,
      wrapText = true
    )
    val tname = CellStyle(font = Font(bold = false, color = Color.MediumSlateBlue), fillPattern = CellFill.None, wrapText = false) // Try and dim the text

    val rowsPerTable: List[List[Row]] = data.map { v: TableDataFull =>
      val subhead: Row = Row(Cell(v.table.tableName, style = tnameFirst), Cell(v.table.tableComment))
      val colInfo: List[Row] = v.columns.map { cd =>
        Row(
          Cell(cd.tableName, style = tname),
          Cell(cd.columnName),
          Cell(cd.columnType),
          Cell(cd.isNullable.contains("YES")),
          Cell(cd.columnDefault.getOrElse("")),
          Cell(cd.columnComment)
        )
      }
      subhead :: colInfo
    }

    val rrows = rowsPerTable.flatten
    Sheet(
      name = schema,
      columns = List.fill(6)(defaultColumn),
      rows = Row(style = headerStyle).withCellValues(headers) :: rrows,
      tables = List(
        Table(
          cellRange = CellRange(0 -> (rrows.length), 0 -> 6),
          style = TableStyle(TableStyleName.TableStyleLight1, showRowStripes = true),
        )
      )
    )

  }
}
