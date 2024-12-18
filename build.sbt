ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.5.2"

lazy val root = (project in file("."))
  .settings(
    name := "Aoc2015-Scala",
    idePackagePrefix := Some("org.mpdev.scala.aoc2015")
  )

libraryDependencies += "org.scala-lang" %% "toolkit" % "0.4.0"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.19" % "test"
libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.5.6"

