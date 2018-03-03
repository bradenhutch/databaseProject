name := """databaseProject"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

resolvers += Resolver.sonatypeRepo("snapshots")

scalaVersion := "2.12.4"

crossScalaVersions := Seq("2.11.12", "2.12.4")

libraryDependencies ++= Seq(
	guice,
	jdbc,
	evolutions,
	"org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test,
	"mysql" % "mysql-connector-java" % "5.1.41",
	"org.playframework.anorm" %% "anorm" % "2.6.0"
)

