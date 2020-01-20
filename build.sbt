lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "deliveroo",
      scalaVersion := "2.12.10",
      useSuperShell := false
    )),
    name := "deliveroo",
    libraryDependencies ++= Seq(
      Dependencies.catsCore,
      Dependencies.catsEffect,
      Dependencies.doodle,
      Dependencies.miniTest,
      Dependencies.miniTestLaws,
      Dependencies.scalaCheck
    )
  )
