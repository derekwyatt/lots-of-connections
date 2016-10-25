name := "lots-of-http-connections-reproducer"

version := "0.1"

scalaVersion := "2.11.8"

libraryDependencies := Seq(
  "com.typesafe.akka" % "akka-http" % "3.0.0-RC1",
  "org.scalatest" %% "scalatest" % "2.2.4" % "test"
)
