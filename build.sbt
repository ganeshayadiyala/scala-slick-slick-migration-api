name := "akkahttpintroduction"

version := "1.0"

scalaVersion := "2.11.7"

val akkaHttpV = "2.4.2"


resolvers ++= Seq(
  "apache-snapshots" at "http://repository.apache.org/snapshots/",
  "maven-repo" at "https://repo1.maven.org/maven2/",
  "abc" at "https://repo.spring.io/libs-release/"
)

resolvers += Resolver.jcenterRepo

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http-core" %  akkaHttpV,
  "com.typesafe.akka" %% "akka-http-experimental" %  akkaHttpV,
  "com.typesafe.akka" %% "akka-http-spray-json-experimental" % akkaHttpV,
  "com.typesafe.akka" %% "akka-http-xml-experimental" % akkaHttpV,
  "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpV,
  "org.scalatest" %% "scalatest" % "2.2.5",
  "org.apache.spark" %% "spark-sql" % "2.0.0",
  "io.jsonwebtoken"%"jjwt"%"0.6.0",
  "com.typesafe.slick" %% "slick" % "3.3.0",
  "io.github.nafg" %% "slick-migration-api" % "0.6.1",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.3.2",
  "org.postgresql" % "postgresql" % "9.4-1206-jdbc42"
)
    