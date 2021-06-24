import sbt._

object Version {
  val cats           = "2.6.1"
  val catsEffect     = "3.1.1"
  val chimney        = "0.5.3"
  val circe          = "0.14.1"
  val circeConfig    = "0.8.0"
  val circeOptics    = "0.14.1"
  val doobie         = "1.0.0-M5"
  val enumeratum     = "1.6.1"
  val flyway         = "7.9.1"
  val fs2            = "3.0.0"
  val hikariVersion  = "4.0.3"
  val logback        = "1.2.3"
  val monocle        = "2.0.3"
  val munit          = "0.7.26"
  val mySqlConnector = "8.0.25"
  val norbitExcel    = "1.8.0"
  val oslib          = "0.7.8"
  val pprint         = "0.6.6"
  val pureConfig     = "0.16.0"
  val scalaTest      = "3.2.7"
  val scalacheck     = "1.15.2"
  val scalamock      = "5.1.0"
  val slf4j          = "1.7.30"
  val typesafeConfig = "1.4.1"
}

object Dependencies {

  val slf4j = Seq("org.slf4j" % "slf4j-api" % Version.slf4j)

  val cats = Seq("org.typelevel" %% "cats-core" % Version.cats, "org.typelevel" %% "cats-effect" % Version.catsEffect)
  val fs2  = Seq("co.fs2" %% "fs2-core" % Version.fs2) // Skip the addons for now

  val doobie = Seq(
    "org.tpolecat" %% "doobie-core"   % Version.doobie, // Doobie now 2.12+
    "org.tpolecat" %% "doobie-hikari" % Version.doobie,
    "org.tpolecat" %% "doobie-quill"  % Version.doobie
  )

  val mySqlClient = Seq(
    "mysql" % "mysql-connector-java" % Version.mySqlConnector //.. >5.1.39</version>
  )

  val circe = Seq(
    "io.circe" %% "circe-core"    % Version.circe,
    "io.circe" %% "circe-generic" % Version.circe,
    "io.circe" %% "circe-optics"  % Version.circeOptics,
    "io.circe" %% "circe-config"  % Version.circeConfig,
    "io.circe" %% "circe-literal" % Version.circe
    // "io.circe" %% "circe-generic-extra" % Version.circe
  )

  val excelWriter = Seq("com.norbitltd" %% "spoiwo" % Version.norbitExcel)

  val chimney    = Seq("io.scalaland" %% "chimney" % Version.chimney)
  val enumeratum = Seq("com.beachape" %% "enumeratum" % Version.enumeratum)

  val utilDependencies = Seq(
    "ch.qos.logback" % "logback-classic" % Version.logback % Runtime, // EPL 1.0, LGPL 2.1
    "com.typesafe"   % "config"          % Version.typesafeConfig,
    "com.lihaoyi"   %% "pprint"          % Version.pprint,
    "com.lihaoyi"   %% "os-lib"          % Version.oslib,
    "com.outr"      %% "scribe"          % "3.5.5" // Apache
  )

  /** Cannot be used with Spark 2.1.0, see website. Hopefully we are beyond that. */
  val pureConfig = Seq(
    "com.github.pureconfig" %% "pureconfig"             % Version.pureConfig, //Mozilla Public License, v2.0
    "com.github.pureconfig" %% "pureconfig-cats"        % Version.pureConfig,
    "com.github.pureconfig" %% "pureconfig-cats-effect" % Version.pureConfig
  )

  // FIXME: Go Back to ScalaCheck even though munit nicer
  val testLibs = Seq(
    "org.scalacheck" %% "scalacheck"       % Version.scalacheck % Test,
    "org.scalameta"  %% "munit"            % Version.munit      % Test,
    "org.scalameta"  %% "munit-scalacheck" % Version.munit      % Test
  )

  val hikariCP = Seq(
    "com.zaxxer" % "HikariCP" % Version.hikariVersion //Apache 2.0
  )

  val monocle =
    Seq("com.github.julien-truffaut" %% "monocle-core" % Version.monocle, "com.github.julien-truffaut" %% "monocle-macro" % Version.monocle)
}
