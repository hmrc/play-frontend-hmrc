import sbt._
import PlayCrossCompilation.dependencies
import play.core.PlayVersion
import play.sbt.PlayImport.ws
import sbt.ModuleID

object LibDependencies {
  val hmrcFrontendVersion: String      = "2.0.0"
  val playFrontendGovukVersion: String = "0.83.0"
  val playLanguageVersion: String      = "5.0.0"

  val compile: Seq[ModuleID] = dependencies(
    shared = Seq(
      "com.typesafe.play"  %% "play"            % PlayVersion.current,
      "com.typesafe.play"  %% "filters-helpers" % PlayVersion.current,
      "org.joda"            % "joda-convert"    % "2.0.2",
      "uk.gov.hmrc.webjars" % "hmrc-frontend"   % hmrcFrontendVersion,
      "org.scalaj"         %% "scalaj-http"     % "2.4.2"
    ),
    play26 = Seq(
      "uk.gov.hmrc" %% "play-language"       % s"$playLanguageVersion-play-26",
      "uk.gov.hmrc" %% "play-frontend-govuk" % s"$playFrontendGovukVersion-play-26"
    ),
    play27 = Seq(
      "uk.gov.hmrc" %% "play-language"       % s"$playLanguageVersion-play-27",
      "uk.gov.hmrc" %% "play-frontend-govuk" % s"$playFrontendGovukVersion-play-27"
    ),
    play28 = Seq(
      "uk.gov.hmrc" %% "play-language"       % s"$playLanguageVersion-play-28",
      "uk.gov.hmrc" %% "play-frontend-govuk" % s"$playFrontendGovukVersion-play-28"
    )
  )

  val test: Seq[ModuleID] = dependencies(
    shared = Seq(
      "com.typesafe.play"            %% "play-test"                % PlayVersion.current % Test,
      "com.vladsch.flexmark"          % "flexmark-all"             % "0.35.10"           % Test,
      "org.jsoup"                     % "jsoup"                    % "1.13.1"            % Test,
      "org.scalatest"                %% "scalatest"                % "3.2.3"             % Test,
      "org.scalatestplus"            %% "scalatestplus-mockito"    % "1.0.0-M2"          % Test,
      "org.scalatestplus"            %% "scalatestplus-scalacheck" % "3.1.0.0-RC2"       % Test,
      "org.scalacheck"               %% "scalacheck"               % "1.15.3"            % Test,
      "com.googlecode.htmlcompressor" % "htmlcompressor"           % "1.5.2"             % Test,
      "com.github.pathikrit"         %% "better-files"             % "3.9.1"             % Test,
      "com.lihaoyi"                  %% "pprint"                   % "0.6.1"             % Test,
      "org.bitbucket.cowwoc"          % "diff-match-patch"         % "1.2"               % Test,
      ws
    ),
    play26 = Seq(
      "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.3" % Test
    ),
    play27 = Seq(
      "org.scalatestplus.play" %% "scalatestplus-play" % "4.0.3" % Test
    ),
    play28 = Seq(
      "org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0" % Test
    )
  )

  def apply(): Seq[ModuleID] = compile ++ test

}
