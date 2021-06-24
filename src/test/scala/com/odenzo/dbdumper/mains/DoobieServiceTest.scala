package com.odenzo.dbdumper.mains

import com.odenzo.dbdumper.app.{ConfigLoader, DoobieService}
import com.odenzo.utils.OPrint.oprint
import munit.FunSuite

class DoobieServiceTest extends FunSuite {

  import cats.effect.IO

  val doobie: IO[DoobieService] = ConfigLoader.loadConfig().map { dbConfig =>
    scribe.info(s"Local DB: ${oprint(dbConfig)}")
    new DoobieService(dbConfig)
  }

  test("Grant Super User") {
    doobie.map(_.grantSuperUser("root"))
  }

  test("Add Table Comment") {
    doobie.map(_.addTableComment("trm", "audit_logs", "Do not persist this across database migrations"))
  }

}
