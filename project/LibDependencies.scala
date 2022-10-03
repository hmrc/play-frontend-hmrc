import sbt._
import PlayCrossCompilation.dependencies
import play.core.PlayVersion
import play.sbt.PlayImport.ws
import sbt.ModuleID

object LibDependencies {
  val govukFrontendVersion: String = "4.3.1"
  val hmrcFrontendVersion: String  = "5.11.0"
  val playLanguageVersion: String  = "5.3.0"

  val compile: Seq[ModuleID] = dependencies(
    shared = Seq(
      "com.typesafe.play"  %% "play"            % PlayVersion.current,
      "com.typesafe.play"  %% "filters-helpers" % PlayVersion.current,
      "uk.gov.hmrc.webjars" % "hmrc-frontend"   % hmrcFrontendVersion
    ),
    play28 = Seq(
      "uk.gov.hmrc" %% "play-language" % s"$playLanguageVersion-play-28"
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
      "com.typesafe.play"            %% "play-test"                % PlayVersion.current % Test,
      ws                              % Test
    ),
    play28 = Seq(
      "org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0" % Test
    )
  )

  def apply(): Seq[ModuleID] = compile ++ test

}
