import uk.gov.hmrc.DefaultBuildSettings

val scala2_13 = "2.13.15"
val scala3    = "3.3.3"

ThisBuild / majorVersion := 12
ThisBuild / isPublicArtefact := true
ThisBuild / scalaVersion := scala2_13
ThisBuild / scalacOptions += "-Wconf:src=src_managed/.*:s" // silence all warnings on autogenerated files

lazy val sharedSettings: Seq[sbt.Def.SettingsDefinition] = Seq(
  scalacOptions += "-Wconf:src=views/.*:s",
  scalacOptions += "-Wconf:src=routes/.*:s",
  resolvers += Resolver.mavenLocal,
  TwirlKeys.templateImports := templateImports,
  TwirlKeys.constructorAnnotations += "@javax.inject.Inject()",
  Test / generateGovukFixtures := {
    val generateFixtures = GenerateFixtures(
      fixturesDir = (Test / resourceDirectory).value / "fixtures/govuk-frontend",
      frontend = "govuk",
      version = LibDependencies.govukFrontendVersion
    )
    generateFixtures.generate()
  },
  Test / generateHmrcFixtures := {
    val generateFixtures = GenerateFixtures(
      fixturesDir = (Test / resourceDirectory).value / "fixtures/hmrc-frontend",
      frontend = "hmrc",
      version = LibDependencies.hmrcFrontendVersion
    )
    generateFixtures.generate()
  },
  Test / parallelExecution := false,
  buildInfoKeys ++= Seq[BuildInfoKey](
    "govukFrontendVersion" -> LibDependencies.govukFrontendVersion,
    "hmrcFrontendVersion"  -> LibDependencies.hmrcFrontendVersion
  ),
  buildInfoPackage := "hmrcfrontendbuildinfo"
)

lazy val library = (project in file("."))
  .settings(publish / skip := true)
  .aggregate(
    sys.env.get("PLAY_VERSION") match {
      case Some("2.9") => playFrontendHmrcPlay29
      case _           => playFrontendHmrcPlay30
    }
  )

def copySources(module: Project) = Seq(
  Compile / scalaSource := (module / Compile / scalaSource).value,
  Compile / resourceDirectory := (module / Compile / resourceDirectory).value,
  Test / scalaSource := (module / Test / scalaSource).value,
  Test / resourceDirectory := (module / Test / resourceDirectory).value
)

def copyPlayResources(module: Project) = Seq(
  Compile / TwirlKeys.compileTemplates / sourceDirectories += (module / baseDirectory).value / s"src/main/twirl",
  Compile / routes / sources ++= {
    // baseDirectory.value / s"../src-common/main/resources/hmrcfrontend.routes"
    // compile any routes files in the root named "routes" or "*.routes"
    val dirs = (module / Compile / unmanagedResourceDirectories).value
    (dirs * "routes").get ++ (dirs * "*.routes").get
  }
)

lazy val playFrontendHmrcPlay29 = Project("play-frontend-hmrc-play-29", file("play-frontend-hmrc-play-29"))
  .enablePlugins(SbtTwirl, RoutesCompiler, BuildInfoPlugin)
  .settings(copySources(playFrontendHmrcPlay30))
  .settings(copyPlayResources(playFrontendHmrcPlay30))
  .settings(sharedSettings: _*)
  .settings(
    libraryDependencies ++= LibDependencies.shared ++ LibDependencies.play29,
    buildInfoKeys ++= Seq[BuildInfoKey]("playVersion" -> LibDependencies.play29Version)
  )

lazy val playFrontendHmrcPlay30 = Project("play-frontend-hmrc-play-30", file("play-frontend-hmrc-play-30"))
  .enablePlugins(SbtTwirl, RoutesCompiler, BuildInfoPlugin)
  .settings(
    Compile / TwirlKeys.compileTemplates / sourceDirectories += baseDirectory.value / s"src/main/twirl",
    Compile / routes / sources ++= {
      // baseDirectory.value / s"../src-common/main/resources/hmrcfrontend.routes"
      // compile any routes files in the root named "routes" or "*.routes"
      val dirs = (Compile / unmanagedResourceDirectories).value
      (dirs * "routes").get ++ (dirs * "*.routes").get
    }
  )
  .settings(sharedSettings: _*)
  .settings(
    crossScalaVersions := Seq(scala2_13, scala3),
    libraryDependencies ++= LibDependencies.shared ++ LibDependencies.play30,
    buildInfoKeys ++= Seq[BuildInfoKey]("playVersion" -> LibDependencies.play30Version)
  )

lazy val templateImports: Seq[String] = Seq(
  "_root_.play.twirl.api.Html",
  "_root_.play.twirl.api.HtmlFormat",
  "_root_.play.twirl.api.JavaScript",
  "_root_.play.twirl.api.Txt",
  "_root_.play.twirl.api.Xml",
  "play.api.mvc._",
  "play.api.data._",
  "play.api.i18n._",
  "play.api.templates.PlayMagic._",
  "uk.gov.hmrc.hmrcfrontend.views.html.components.implicits._",
  "_root_.play.twirl.api.TwirlFeatureImports._",
  "_root_.play.twirl.api.TwirlHelperImports._"
)

lazy val it = project
  .settings(publish / skip := true)
  .aggregate(
    sys.env.get("PLAY_VERSION") match {
      case Some("2.9") => itPlay29
      case _           => itPlay30
    }
  )

lazy val itPlay29 = (project in file("it-play-29"))
  .enablePlugins(PlayScala) // AssetsSpec requires that this library is a fully fledged Play service
  .disablePlugins(PlayLayoutPlugin)
  .settings(copySources(itPlay30))
  .settings(DefaultBuildSettings.itSettings())
  .settings(
    Test / unmanagedResourceDirectories ++= Seq(baseDirectory(_ / "target/web/public/test").value)
  )
  .dependsOn(playFrontendHmrcPlay29 % "test->test")

lazy val itPlay30 = (project in file("it-play-30"))
  .enablePlugins(PlayScala) // AssetsSpec requires that this library is a fully fledged Play service
  .disablePlugins(PlayLayoutPlugin)
  .settings(DefaultBuildSettings.itSettings())
  .settings(
    Test / unmanagedResourceDirectories ++= Seq(baseDirectory(_ / "target/web/public/test").value)
  )
  .dependsOn(playFrontendHmrcPlay30 % "test->test")

lazy val generateGovukFixtures = taskKey[Unit]("Generate unit test fixtures for GOV.UK")
lazy val generateHmrcFixtures  = taskKey[Unit]("Generate unit test fixtures for HMRC")
