import sbt._
import PlayCrossCompilation.dependencies
import sbt.ModuleID
import play.sbt.PlayImport.ws

object LibDependencies {
  lazy val govukFrontendVersion = "3.9.1"

  lazy val libDependencies: Seq[ModuleID] = dependencies(
    shared = {
      import PlayCrossCompilation.playRevision

      val compile = Seq(
        "com.typesafe.play" %% "play"            % playRevision,
        "com.typesafe.play" %% "filters-helpers" % playRevision,
        "org.joda"          % "joda-convert"     % "2.0.2",
        "org.webjars.npm"   % "govuk-frontend"   % govukFrontendVersion
      )

      val test = Seq(
        "org.scalatest"                 %% "scalatest"       % "3.0.8",
        "org.pegdown"                   % "pegdown"          % "1.6.0",
        "org.jsoup"                     % "jsoup"            % "1.11.3",
        "com.typesafe.play"             %% "play-test"       % playRevision,
        "org.scalacheck"                %% "scalacheck"      % "1.14.1",
        "com.googlecode.htmlcompressor" % "htmlcompressor"   % "1.5.2",
        "com.github.pathikrit"          %% "better-files"    % "3.8.0",
        "com.lihaoyi"                   %% "pprint"          % "0.5.3",
        "org.bitbucket.cowwoc"          % "diff-match-patch" % "1.2",
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
      ).map(_ % Test)
      test
    },
    play27 = {
      val test = Seq(
        "org.scalatestplus.play" %% "scalatestplus-play" % "4.0.3"
      ).map(_ % Test)
      test
    }
  )

  lazy val overrides: Set[ModuleID] = dependencies(
    play25 = Seq(
      "com.typesafe.play" %% "twirl-api" % "1.1.1"
    )
  ).toSet
}
