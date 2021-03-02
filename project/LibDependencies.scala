import sbt._
import PlayCrossCompilation.dependencies
import play.sbt.PlayImport.ws
import sbt.ModuleID

object LibDependencies {

  lazy val hmrcFrontendVersion = "1.26.2"
  private val playFrontendGovukVersion = "0.64.0"

  val compile: Seq[ModuleID] = dependencies(
    shared = Seq(
      "org.joda"          %  "joda-convert"        % "2.0.2",
      "org.webjars.npm"   %  "hmrc-frontend"       % hmrcFrontendVersion,
      "org.scalaj"        %% "scalaj-http"         % "2.4.2"
    ),
    play26 = Seq(
      "uk.gov.hmrc"       %% "play-frontend-govuk" % s"${playFrontendGovukVersion}-play-26-SNAPSHOT",
      "com.typesafe.play" %% "play"                % "2.6.25",
      "com.typesafe.play" %% "filters-helpers"     % "2.6.25"
    ),
    play27 = Seq(
      "uk.gov.hmrc"       %% "play-frontend-govuk" % s"${playFrontendGovukVersion}-play-27-SNAPSHOT",
      "com.typesafe.play" %% "play"                % "2.7.9",
      "com.typesafe.play" %% "filters-helpers"     % "2.7.9"
    ),
    play28 = Seq(
      "uk.gov.hmrc"       %% "play-frontend-govuk" % s"${playFrontendGovukVersion}-play-28-SNAPSHOT",
      "com.typesafe.play" %% "play"                % "2.8.7",
      "com.typesafe.play" %% "filters-helpers"     % "2.8.7"
    )
  )

  val test: Seq[ModuleID] = dependencies(
    shared = Seq(
      "org.jsoup"                     %  "jsoup"                    % "1.13.1"      % Test,
      "org.scalatest"                 %% "scalatest"                % "3.2.3"       % Test,
      "org.scalatestplus"             %% "scalatestplus-mockito"    % "1.0.0-M2"    % Test,
      "org.scalatestplus"             %% "scalatestplus-scalacheck" % "3.1.0.0-RC2" % Test,
      "org.scalacheck"                %% "scalacheck"               % "1.15.3"      % Test,
      "com.googlecode.htmlcompressor" %  "htmlcompressor"           % "1.5.2"       % Test,
      "com.github.pathikrit"          %% "better-files"             % "3.9.1"       % Test,
      "com.lihaoyi"                   %% "pprint"                   % "0.6.1"       % Test,
      "org.bitbucket.cowwoc"          %  "diff-match-patch"         % "1.2"         % Test,
      ws
    ),
    play26 = Seq(
      "org.scalatestplus.play"        %% "scalatestplus-play"       % "3.1.3"       % Test,
      "com.typesafe.play"             %% "play-test"                % "2.6.25"      % Test,
      "com.vladsch.flexmark"          %  "flexmark-all"             % "0.35.10"     % Test
    ),
    play27 = Seq(
      "org.scalatestplus.play"        %% "scalatestplus-play"       % "4.0.3"       % Test,
      "com.typesafe.play"             %% "play-test"                % "2.7.9"       % Test,
      "com.vladsch.flexmark"          %  "flexmark-all"             % "0.35.10"     % Test
    ),
    play28 = Seq(
      "org.scalatestplus.play"        %% "scalatestplus-play"       % "5.1.0"       % Test,
      "com.typesafe.play"             %% "play-test"                % "2.8.7"       % Test,
      "com.vladsch.flexmark"          %  "flexmark-all"             % "0.35.10"     % Test
    )
  )

  def apply(): Seq[ModuleID] = compile ++ test

}
