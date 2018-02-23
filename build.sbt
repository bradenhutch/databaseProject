name         := "databaseProject"

version      := "1.0-SNAPSHOT"

scalaVersion := "2.11.7"

TwirlKeys.templateImports += "dependencies._"

lazy val root = (project in file(".")).enablePlugins(PlayScala).settings(
  libraryDependencies ++= Seq(
    // Add your project dependencies here,
    evolutions,
    jdbc,
	ws,
    "mysql" % "mysql-connector-java" % "5.1.10",
    "com.typesafe.play" %% "anorm" % "2.4.0"
  )
)

scalacOptions ++= Seq("-Xmax-classfile-name","78")

