name := "zio-test"
version := "0.1"
scalaVersion := "2.13.1"

libraryDependencies ++= Seq(
  "dev.zio" %% "zio" % "1.0.0-RC18",
  "dev.zio" %% "zio-streams" % "1.0.0-RC18",
  "com.zaxxer" % "nuprocess" % "1.2.3"
)