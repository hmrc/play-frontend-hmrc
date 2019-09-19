import play.sbt.PlayImport.PlayKeys._
import uk.gov.hmrc.playcrosscompilation.PlayVersion.{Play25, Play26}
import PlayCrossCompilation.{dependencies, playVersion}
import de.heikoseeberger.sbtheader.HeaderKey
import GeneratePlay25Twirl.generatePlay25Templates

val libName = "play-frontend-govuk"

lazy val playDir =
  (if (PlayCrossCompilation.playVersion == Play25) "play-25"
   else "play-26")

lazy val IntegrationTest = config("it") extend Test

lazy val root = Project(libName, file("."))
  .enablePlugins(PlayScala, SbtAutoBuildPlugin, SbtGitVersioning, SbtTwirl, SbtArtifactory)
  .disablePlugins(PlayLayoutPlugin)
  .configs(IntegrationTest)
  .settings(
    name := libName,
    majorVersion := 0,
    scalaVersion := "2.11.12",
    crossScalaVersions := List("2.11.12", "2.12.8"),
    libraryDependencies ++= libDependencies,
    dependencyOverrides ++= overrides,
    resolvers :=
      Seq(
        "HMRC Releases" at "https://dl.bintray.com/hmrc/releases",
        "typesafe-releases" at "http://repo.typesafe.com/typesafe/releases/"
      ),
    TwirlKeys.templateImports := templateImports,
    PlayCrossCompilation.playCrossCompilationSettings,
    makePublicallyAvailableOnBintray := true,
    unmanagedSourceDirectories in Compile += baseDirectory.value / "src/main/twirl",
    unmanagedSourceDirectories in Test += baseDirectory.value / "src/test/twirl",
    (sourceDirectories in (Compile, TwirlKeys.compileTemplates)) +=
      baseDirectory.value / "src" / "main" / playDir / "twirl",
    (sourceDirectories in (Test, TwirlKeys.compileTemplates)) +=
      baseDirectory.value / "src" / "test" / playDir / "twirl",
    (generatePlay25TemplatesTask in Compile) := {
      val cachedFun: Set[File] => Set[File] =
        FileFunction.cached(
          cacheBaseDirectory = streams.value.cacheDirectory / "compile-generate-play-25-templates-task",
          inStyle            = FilesInfo.lastModified,
          outStyle           = FilesInfo.exists) { (in: Set[File]) =>
          println("Generating Play 2.5 templates")
          generatePlay25Templates(in)
        }

      val play26TemplatesDir         = baseDirectory.value / "src/main/play-26/twirl"
      val play26Templates: Set[File] = (play26TemplatesDir ** ("*.scala.html")).get.toSet
      cachedFun(play26Templates).toSeq
    },
    // FIXME: refactor to remove task implementation duplication
    (generatePlay25TemplatesTask in Test) := {
      val cachedFun: Set[File] => Set[File] =
        FileFunction.cached(
          cacheBaseDirectory = streams.value.cacheDirectory / "test-generate-play-25-templates-task",
          inStyle            = FilesInfo.lastModified,
          outStyle           = FilesInfo.exists) { (in: Set[File]) =>
          println("Generating Play 2.5 test templates")
          generatePlay25Templates(in)
        }

      val play26TemplatesDir         = baseDirectory.value / "src/test/play-26/twirl"
      val play26Templates: Set[File] = (play26TemplatesDir ** ("*.scala.html")).get.toSet
      cachedFun(play26Templates).toSeq
    },
    (TwirlKeys.compileTemplates in Compile) :=
      ((TwirlKeys.compileTemplates in Compile) dependsOn (generatePlay25TemplatesTask in Compile)).value,
    (TwirlKeys.compileTemplates in Test) :=
      ((TwirlKeys.compileTemplates in Test) dependsOn (generatePlay25TemplatesTask in Test)).value,
    parallelExecution in sbt.Test := false,
    playMonitoredFiles ++= (sourceDirectories in (Compile, TwirlKeys.compileTemplates)).value,
    routesGenerator := {
      if (playVersion == Play25) StaticRoutesGenerator
      else InjectedRoutesGenerator
    },
    HeaderKey.excludes += "fixtures/*/*/*.html",
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

lazy val libDependencies: Seq[ModuleID] = dependencies(
  shared = {
    import PlayCrossCompilation.playRevision

    val compile = Seq(
      "com.typesafe.play" %% "play"            % playRevision,
      "com.typesafe.play" %% "filters-helpers" % playRevision,
      "org.joda"          % "joda-convert"     % "2.0.2",
      "org.webjars.npm"   % "govuk-frontend"   % "3.2.0"
    )

    val test = Seq(
      "org.scalatest"                 %% "scalatest"                 % "3.0.8",
      "org.pegdown"                   % "pegdown"                    % "1.6.0",
      "org.jsoup"                     % "jsoup"                      % "1.11.3",
      "com.typesafe.play"             %% "play-test"                 % playRevision,
      "org.scalacheck"                %% "scalacheck"                % "1.14.1",
      "com.googlecode.htmlcompressor" % "htmlcompressor"             % "1.5.2",
      "com.github.pathikrit"          %% "better-files"              % "3.8.0",
      "com.lihaoyi"                   %% "pprint"                    % "0.5.3",
      ws
    ).map(_ % Test)

    compile ++ test
  },
  play25 = {
    val test = Seq(
      "org.scalatestplus.play" %% "scalatestplus-play" % "2.0.1"
    ).map(_ % Test)
    test
  },
  play26 = {
    val test = Seq(
      "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2"
    )
    test
  }
)

lazy val overrides: Set[ModuleID] = dependencies(
  play25 = Seq(
    "com.typesafe.play" %% "twirl-api" % "1.1.1",
    // use same version as for Play 2.6 so we get enhanced Read stuff such as
    // JsPath.readWithDefault and Json.using[Json.WithDefaultValues]
    "com.typesafe.play" %% "play-json" % "2.6.12" % Test
  )
).toSet

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
    case Play26 =>
      Seq(
        "_root_.play.twirl.api.TwirlFeatureImports._",
        "_root_.play.twirl.api.TwirlHelperImports._"
      )
  }

  allImports ++ specificImports
}

lazy val generatePlay25TemplatesTask = taskKey[Seq[File]]("Generate Play 2.5 templates")
