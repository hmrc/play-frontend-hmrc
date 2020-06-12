import GeneratePlay25Twirl.generatePlay25Templates
import GenerateFixtures.generateFixtures
import play.sbt.PlayImport.PlayKeys._
import sbt.CrossVersion
import uk.gov.hmrc.playcrosscompilation.PlayVersion.Play25

val libName = "play-frontend-govuk"
val silencerVersion = "1.4.4"

lazy val playDir =
  (if (PlayCrossCompilation.playVersion == Play25) "play-25"
   else "play-26")

lazy val IntegrationTest = config("it") extend Test


lazy val root = Project(libName, file("."))
  .enablePlugins(PlayScala, SbtAutoBuildPlugin, SbtGitVersioning, SbtTwirl, SbtArtifactory, BuildInfoPlugin)
  .disablePlugins(PlayLayoutPlugin)
  .configs(IntegrationTest)
  .settings(
    name := libName,
    majorVersion := 0,
    scalaVersion := "2.12.10",
    crossScalaVersions := List("2.11.12", "2.12.10"),
    libraryDependencies ++= LibDependencies.libDependencies,
    dependencyOverrides ++= LibDependencies.overrides,
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
    // ***************
    (sourceDirectories in (Compile, TwirlKeys.compileTemplates)) +=
      baseDirectory.value / "src" / "main" / playDir / "twirl",
    generatePlay25TemplatesTask := {
      val cachedFun: Set[File] => Set[File] =
        FileFunction.cached(
          cacheBaseDirectory = streams.value.cacheDirectory / "generate-play-25-templates-task",
          inStyle            = FilesInfo.lastModified,
          outStyle           = FilesInfo.exists) { (in: Set[File]) =>
          println("Generating Play 2.5 templates")
          generatePlay25Templates(in)
        }

      val play26TemplatesDir         = baseDirectory.value / "src/main/play-26/twirl"
      val play26Templates: Set[File] = (play26TemplatesDir ** ("*.scala.html")).get.toSet
      cachedFun(play26Templates).toSeq
    },
    (generateUnitTestFixtures in Test) := {
      generateFixtures(baseDirectory.value / "src/test/resources", LibDependencies.govukFrontendVersion)
    },
    (TwirlKeys.compileTemplates in Compile) :=
      ((TwirlKeys.compileTemplates in Compile) dependsOn (generatePlay25TemplatesTask)).value,
    (TwirlKeys.compileTemplates in Test) :=
      ((TwirlKeys.compileTemplates in Test) dependsOn (generatePlay25TemplatesTask)).value,
    parallelExecution in sbt.Test := false,
    playMonitoredFiles ++= (sourceDirectories in (Compile, TwirlKeys.compileTemplates)).value,
    unmanagedResourceDirectories in Test ++= Seq(baseDirectory(_ / "target/web/public/test").value),
    buildInfoKeys ++= Seq[BuildInfoKey](
      "playVersion" -> PlayCrossCompilation.playVersion,
      sources in (Compile, TwirlKeys.compileTemplates)
    )
  )
  .settings(inConfig(IntegrationTest)(itSettings): _*)

lazy val itSettings = Defaults.itSettings ++ Seq(
  unmanagedSourceDirectories += sourceDirectory.value / playDir,
  unmanagedResourceDirectories += sourceDirectory.value / playDir / "resources"
)

lazy val templateImports: Seq[String] = {

  val allImports = Seq(
    "_root_.play.twirl.api.Html",
    "_root_.play.twirl.api.HtmlFormat",
    "_root_.play.twirl.api.JavaScript",
    "_root_.play.twirl.api.Txt",
    "_root_.play.twirl.api.Xml",
    "play.api.mvc._",
    "play.api.data._",
    "play.api.i18n._",
    "play.api.templates.PlayMagic._",
    "uk.gov.hmrc.govukfrontend.views.html.components.implicits._"
  )

  val specificImports = PlayCrossCompilation.playVersion match {
    case Play25 =>
      Seq(
        "_root_.play.twirl.api.TemplateMagic._"
      )
    case _ =>
      Seq(
        "_root_.play.twirl.api.TwirlFeatureImports._",
        "_root_.play.twirl.api.TwirlHelperImports._"
      )
  }

  allImports ++ specificImports
}

lazy val generatePlay25TemplatesTask = taskKey[Seq[File]]("Generate Play 2.5 templates")
lazy val generateUnitTestFixtures = taskKey[Unit]("Generate unit test fixtures")
