package com.odenzo.dbdumper.app

import cats.effect.IO
import pureconfig.generic.ProductHint
import pureconfig.generic.auto._
object ConfigLoader {

  import pureconfig._

  implicit def hint[A] = ProductHint[A](ConfigFieldMapping(CamelCase, CamelCase))

  /** This provides a list of validation list errors, but we throw as Exception for now */
  def loadConfig(): IO[DbConfig] = IO {
    val src: ConfigObjectSource = loadDefaultConfig()
    val localDBConfig           = parse(src)
    localDBConfig
  }

  /* Quick hack before investigating better Tomcat Based Deployment config */
  def loadConfigResource(resourceName: String): ConfigObjectSource = {
    scribe.info(s"Loading Config from Resource: $resourceName")
    ConfigSource.resources(resourceName)
  }

  /** Loads default application.conf and reference.conf etc */
  private def loadDefaultConfig(): ConfigObjectSource = ConfigSource.default

  /** Throws Exceptions */
  private def parse(tconfig: ConfigObjectSource): DbConfig = tconfig.at("local.db").loadOrThrow[DbConfig]

}
