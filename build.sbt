import GenerateFixtures.generateFixtures
import play.sbt.PlayImport.PlayKeys._
import uk.gov.hmrc.playcrosscompilation.PlayVersion

val libName         = "play-frontend-hmrc"
val silencerVersion = "1.7.1"

lazy val playDir = "play-26"

lazy val IntegrationTest = config("it") extend Test

lazy val root = Project(libName, file("."))
  .enablePlugins(PlayScala, SbtAutoBuildPlugin, SbtGitVersioning, SbtTwirl, BuildInfoPlugin)
  .disablePlugins(PlayLayoutPlugin)
  .configs(IntegrationTest)
  .settings(
    name := libName,
    majorVersion := 0,
    scalaVersion := "2.12.13",
    libraryDependencies ++= LibDependencies(),
    resolvers :=
      Seq(
        "HMRC Releases" at "https://dl.bintray.com/hmrc/releases",
        "typesafe-releases" at "https://repo.typesafe.com/typesafe/releases/",
        "bintray" at "https://dl.bintray.com/webjars/maven"
      ),
    TwirlKeys.templateImports := templateImports,
    PlayCrossCompilation.playCrossCompilationSettings,
    makePublicallyAvailableOnBintray := true,
    // ***************
    // Use the silencer plugin to suppress warnings from unused imports in compiled twirl templates
    scalacOptions += "-P:silencer:pathFilters=views;routes",
    libraryDependencies ++= Seq(
      compilerPlugin("com.github.ghik" % "silencer-plugin" % silencerVersion cross CrossVersion.full),
      "com.github.ghik" % "silencer-lib" % silencerVersion % Provided cross CrossVersion.full
    ),
    excludeFilter in unmanagedSources := {
      if (PlayCrossCompilation.playVersion == PlayVersion.Play28) "deprecatedPlay26Helpers.scala" else ""
    },
    // ***************
    (sourceDirectories in (Compile, TwirlKeys.compileTemplates)) +=
      baseDirectory.value / "src" / "main" / playDir / "twirl",
    (generateUnitTestFixtures in Test) := {
      generateFixtures(baseDirectory.value / "src/test/resources", LibDependencies.hmrcFrontendVersion)
    },
    parallelExecution in sbt.Test := false,
    playMonitoredFiles ++= (sourceDirectories in (Compile, TwirlKeys.compileTemplates)).value,
    unmanagedResourceDirectories in Test ++= Seq(baseDirectory(_ / "target/web/public/test").value),
    buildInfoKeys ++= Seq[BuildInfoKey](
      "playVersion" -> PlayCrossCompilation.playVersion,
      sources in (Compile, TwirlKeys.compileTemplates)
    ),
    buildInfoPackage := "hmrcfrontendbuildinfo"
  )
  .settings(inConfig(IntegrationTest)(itSettings): _*)
  .settings(inConfig(IntegrationTest)(org.scalafmt.sbt.ScalafmtPlugin.scalafmtConfigSettings))

lazy val itSettings = Defaults.itSettings ++ Seq(
  unmanagedSourceDirectories += sourceDirectory.value / playDir,
  unmanagedResourceDirectories += sourceDirectory.value / playDir / "resources"
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

lazy val generateUnitTestFixtures = taskKey[Unit]("Generate unit test fixtures")
