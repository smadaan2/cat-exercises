import sbt.Keys.{scalacOptions, version}

val root = (project in file("."))
            .settings(
              name := "CatExercises",
              version := "0.1",
              scalaVersion := "2.12.4",
              libraryDependencies ++= Seq("org.scalaz" %% "scalaz-core" % "7.2.16",
              "com.typesafe.scala-logging" %% "scala-logging" % "3.7.2",
              "org.typelevel" %% "cats-core" % "1.0.1" withSources(),
              "org.typelevel" %% "cats-testkit" % "1.0.1",
              //"org.typelevel" %% "cats-effect" % "1.0.1",
              //"org.typelevel" %% "cats-mtl" % "1.0.1",
              //"org.typelevel" %% "cats-free" % "1.0.1",
              "org.typelevel" %% "cats-kernel" % "1.0.1" withSources(),
              "org.typelevel" %% "cats-macros" % "1.0.1",
                compilerPlugin("org.spire-math" %% "kind-projector" % "0.9.6")
            ),
              scalacOptions += "-Ypartial-unification",

            )



