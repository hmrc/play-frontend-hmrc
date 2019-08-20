import play.sbt.PlayImport.PlayKeys._
import uk.gov.hmrc.playcrosscompilation.PlayVersion.{Play25, Play26}
import PlayCrossCompilation.{dependencies, playVersion}
import de.heikoseeberger.sbtheader.HeaderKey

val libName = "play-frontend-govuk"

lazy val root = Project(libName, file("."))
  .enablePlugins(PlayScala, SbtAutoBuildPlugin, SbtGitVersioning, SbtTwirl, SbtArtifactory)
  .disablePlugins(PlayLayoutPlugin)
  .settings(
    name := libName,
    majorVersion := 0,
    scalaVersion := "2.11.12",
    crossScalaVersions := List("2.11.12", "2.12.8"),
    libraryDependencies ++= appDependencies,
    dependencyOverrides ++= overrides,
    resolvers :=
      Seq(
        "HMRC Releases" at "https://dl.bintray.com/hmrc/releases",
        "typesafe-releases" at "http://repo.typesafe.com/typesafe/releases/"
      ),
    TwirlKeys.templateImports := templateImports,
    PlayCrossCompilation.playCrossCompilationSettings,
    makePublicallyAvailableOnBintray := true,
    unmanagedSourceDirectories in sbt.Compile += baseDirectory.value / "src/main/twirl",
    (sourceDirectories in (Compile, TwirlKeys.compileTemplates)) += {
      val twirlDir =
        if (PlayCrossCompilation.playVersion == Play25) {
          "src/main/play-25/twirl"
        } else {
          "src/main/play-26/twirl"
        }
      baseDirectory.value / twirlDir
    },
    parallelExecution in sbt.Test := false,
    playMonitoredFiles ++= (sourceDirectories in (Compile, TwirlKeys.compileTemplates)).value,
    routesGenerator := {
      if (playVersion == Play25) StaticRoutesGenerator
      else InjectedRoutesGenerator
    },
    HeaderKey.excludes += "fixtures/*/*/*.html"
  )

lazy val appDependencies: Seq[ModuleID] = dependencies(
  shared = {

    import PlayCrossCompilation.playRevision

    val compile = Seq(
      "com.typesafe.play" %% "play"            % playRevision,
      "com.typesafe.play" %% "filters-helpers" % playRevision,
      "org.joda"          % "joda-convert"     % "2.0.2",
      "org.webjars.npm"   % "govuk-frontend"   % "2.11.0",
      "uk.gov.hmrc"   % "govuk-frontend"   % "2.11.0"
    )

    val test = Seq(
      "org.scalatest"                 %% "scalatest"     % "3.0.5",
      "org.pegdown"                   % "pegdown"        % "1.6.0",
      "org.jsoup"                     % "jsoup"          % "1.11.3",
      "com.typesafe.play"             %% "play-test"     % playRevision,
      "org.scalacheck"                %% "scalacheck"    % "1.14.0",
      "com.googlecode.htmlcompressor" % "htmlcompressor" % "1.5.2"
    ).map(_ % Test)

    compile ++ test
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
    "play.api.templates.PlayMagic._"
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
