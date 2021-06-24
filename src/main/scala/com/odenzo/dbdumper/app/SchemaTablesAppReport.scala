package com.odenzo.dbdumper.app

/** Generates Summary Reports of Table per Schema in TookiTaki Format. Never bothered to convert to Excel */
object SchemaTablesAppReport {

//  /** Generates and writes CSV file for a schema */
//  def generateCSV(schema: String, info: List[TableDataFull]): IO[Unit] = {
//    IO {
//      val report: String = formatOutput(info, schema)
//      val filename       = s"tableReport.csv"
//      println(s"Writing to File: $filename")
//      os.makeDir.all(wd)
//      // Read/write files
//      os.write(wd / filename, report)
//    }
//  }
//
//  def formatOutput(tables: List[TableDataFull], schemas: List[String]): String = {
//
//    val headers: String = ("Table" :: schemas).appended("Description").mkString(",")
//    val data: Seq[String] = tables
//      .map { table =>
//        val scemaChecks = schemas.map(sc => if (table.tableSchema == sc) "Y" else "-")
//        (table.tableName :: scemaChecks)
//          .appended(table.tableComment)
//          .mkString(",")
//      }
//    data.prepended(headers).mkString("\n")
//  }

  /* val headerStyle =
   * CellStyle(fillPattern = CellFill.Solid, fillForegroundColor = Color.AquaMarine, font = Font(bold = true))
   *
   * val gettingStartedSheet = Sheet(name = "Some serious stuff") .withRows( Row(style = headerStyle).withCellValues("NAME", "BIRTH DATE",
   * "DIED AGED", "FEMALE"), Row().withCellValues("Marie Curie", new LocalDate(1867, 11, 7), 66, true), Row().withCellValues("Albert
   * Einstein", new LocalDate(1879, 3, 14), 76, false), Row().withCellValues("Erwin Shrodinger", new LocalDate(1887, 8, 12), 73, false) )
   * .withColumns( Column(index = 0, style = CellStyle(font = Font(bold = true)), autoSized = true) ) */
}
