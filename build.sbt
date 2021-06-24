import MyCompileOptions._
ThisBuild / organization := "com.odenzo"
ThisBuild / scalaVersion := "2.13.6"
ThisBuild / run / fork   := true

Test / logBuffered       := false
Test / parallelExecution := false

lazy val compileSettings =
  Seq(scalacOptions ++= optsV13 ++ warningsV13 ++ lintersV13, javacOptions ++= Seq("-source", "1.11", "-target", "1.8"))

lazy val commonSettings = Seq(
  libraryDependencies ++=
    Dependencies.pureConfig ++
      Dependencies.cats ++
      Dependencies.fs2 ++
      Dependencies.circe ++
      Dependencies.utilDependencies ++
      Dependencies.doobie ++
      Dependencies.mySqlClient ++
      Dependencies.excelWriter ++
      Dependencies.testLibs,
)

lazy val app = project
  .in(file("."))
  .settings(compileSettings, commonSettings)

lazy val root = project
  .in(file("."))
  .settings(name := "DBDocumentor")
  .aggregate(app)
