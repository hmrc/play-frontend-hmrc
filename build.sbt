val scala2_12 = "2.12.18"
val scala2_13 = "2.13.11"

lazy val IntegrationTest = config("it") extend Test

lazy val commonSettings = Seq(
  majorVersion     := 7,
  isPublicArtefact := true,
  scalaVersion     := scala2_13
)

lazy val sharedSettings: Seq[sbt.Def.SettingsDefinition] = Seq(
  scalacOptions    += "-Wconf:src=views/.*:s",
  scalacOptions    += "-Wconf:src=routes/.*:s",
  sharedSources,
  TwirlKeys.templateImports := templateImports,
  Test / generateGovukFixtures := {
    val generateFixtures = GenerateFixtures(
      fixturesDir = baseDirectory.value / "src/test/resources/fixtures/govuk-frontend",
      frontend = "govuk",
      version = LibDependencies.govukFrontendVersion
    )
    generateFixtures.generate()
  },
  Test / generateHmrcFixtures := {
    val generateFixtures = GenerateFixtures(
      fixturesDir = baseDirectory.value / "src/test/resources/fixtures/hmrc-frontend",
      frontend = "hmrc",
      version = LibDependencies.hmrcFrontendVersion
    )
    generateFixtures.generate()
  },
  Test / parallelExecution := false,
  Test / unmanagedResourceDirectories ++= Seq(baseDirectory(_ / "target/web/public/test").value),
  buildInfoKeys ++= Seq[BuildInfoKey](
//      "playVersion"          -> PlayCrossCompilation.playVersion,
    "govukFrontendVersion" -> LibDependencies.govukFrontendVersion,
    "hmrcFrontendVersion"  -> LibDependencies.hmrcFrontendVersion,
    Compile / TwirlKeys.compileTemplates / sources // needed? hmrcfrontendbuildinfo.BuildInfo.twirlCompileTemplates_sources doesn't seem to be used
  ),
  buildInfoPackage := "hmrcfrontendbuildinfo"
)

lazy val library = (project in file("."))
  .settings(
    commonSettings,
    publish / skip := true
  )
  .aggregate(
    sys.env.get("PLAY_VERSION") match {
      case Some("2.8") => playFrontendHmrcPlay28
      case _           => playFrontendHmrcPlay29
    }
  )

  val sharedSources = Seq(
    Compile         / unmanagedSourceDirectories   += baseDirectory.value / s"../src-common/main/scala",
    Compile         / unmanagedResourceDirectories += baseDirectory.value / s"../src-common/main/resources",
    Test            / unmanagedSourceDirectories   += baseDirectory.value / s"../src-common/test/scala",
    Test            / unmanagedResourceDirectories += baseDirectory.value / s"../src-common/test/resources",
    Compile / TwirlKeys.compileTemplates / sourceDirectories += baseDirectory.value / s"../src-common/main/twirl",
    Compile / routes / sources ++= {
      //baseDirectory.value / s"../src-common/main/resources/hmrcfrontend.routes"
      // compile any routes files in the root named "routes" or "*.routes"
      val dirs = (unmanagedResourceDirectories in Compile).value
      (dirs * "routes").get ++ (dirs * "*.routes").get
    },
    Compile         / unmanagedSourceDirectories   += crossTarget.value / s"routes/main"
  )

lazy val playFrontendHmrcPlay28 = Project("play-frontend-hmrc-play-28", file("play-frontend-hmrc-play-28"))
  .enablePlugins(SbtTwirl, RoutesCompiler, BuildInfoPlugin)
  .configs(IntegrationTest)
  .settings(commonSettings)
  .settings(sharedSettings :_*)
  .settings(
    crossScalaVersions := Seq(scala2_12, scala2_13),
    libraryDependencies ++= LibDependencies.shared ++ LibDependencies.play28,
    libraryDependencySchemes += "org.scala-lang.modules" %% "scala-xml" % VersionScheme.Always, // required since we're cross building for Play 2.8 which isn't compatible with sbt 1.9
     // what is this used for? (note, it's the version the library was compiled with, not the service)
     // Only looks like hmrcfrontendbuildinfo.BuildInfo.hmrcFrontendVersion and govukFrontendVersion are used.
    buildInfoKeys ++= Seq[BuildInfoKey](
      "playVersion"          -> LibDependencies.play28Version
    ),
    // Is this needed?
    //PlayKeys.playMonitoredFiles ++= (Compile / TwirlKeys.compileTemplates / sourceDirectories).value,
  )
  .settings(inConfig(IntegrationTest)(Defaults.itSettings): _*)
  .settings(IntegrationTest / unmanagedSourceDirectories   += baseDirectory.value / s"../src-common/it/scala")
  .settings(inConfig(IntegrationTest)(org.scalafmt.sbt.ScalafmtPlugin.scalafmtConfigSettings))

lazy val playFrontendHmrcPlay29 = Project("play-frontend-hmrc-play-29", file("play-frontend-hmrc-play-29"))
  .enablePlugins(SbtTwirl, RoutesCompiler, BuildInfoPlugin)
  .configs(IntegrationTest)
  .settings(commonSettings)
  .settings(sharedSettings :_*)
  .settings(
    crossScalaVersions := Seq(scala2_13),
    libraryDependencies ++= LibDependencies.shared ++ LibDependencies.play29,
    buildInfoKeys ++= Seq[BuildInfoKey](
      "playVersion"          -> LibDependencies.play29Version
    ),
    //PlayKeys.playMonitoredFiles ++= (Compile / TwirlKeys.compileTemplates / sourceDirectories).value,
  )
  .settings(inConfig(IntegrationTest)(Defaults.itSettings): _*)
  .settings(IntegrationTest / unmanagedSourceDirectories   += baseDirectory.value / s"../src-common/it/scala")
  .settings(inConfig(IntegrationTest)(org.scalafmt.sbt.ScalafmtPlugin.scalafmtConfigSettings))

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

lazy val generateGovukFixtures = taskKey[Unit]("Generate unit test fixtures for GOV.UK")
lazy val generateHmrcFixtures  = taskKey[Unit]("Generate unit test fixtures for HMRC")
