package com.odenzo.dbdumper.app

import com.norbitltd.spoiwo.model.{Row, Sheet}
import com.norbitltd.spoiwo.natures.xlsx.Model2XlsxConversions._

class ExcelWriter {

  val helloWorldSheet = Sheet(name = "Hello Sheet", row = Row().withCellValues("Hello World!"))
  helloWorldSheet.saveAsXlsx("C:\\Reports\\hello_world.xlsx")

}
