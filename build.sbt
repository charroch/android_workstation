organization := "com.novoda"

name := "oliveira"

version := "0.1.0"

scalaVersion := "2.9.1"

libraryDependencies ++= Seq(
  "com.google.android.tools" % "ddmlib" % "r10",
  "com.github.scopt" %% "scopt" % "1.1.2"
)

unmanagedBase <<= baseDirectory { base => base / "custom_lib" }

resolvers += "Scala Tools Nexus Releases" at "http://nexus.scala-tools.org/content/repositories/releases/"