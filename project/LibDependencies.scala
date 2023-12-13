import sbt._

object LibDependencies {
  val govukFrontendVersion: String = "4.7.0"
  val hmrcFrontendVersion: String  = "5.61.0"
  val playLanguageVersion: String  = "7.0.0"

  val play28Version = "2.8.20"
  val play29Version = "2.9.0"
  val play30Version = "3.0.0"

  val shared = Seq(
    "org.scala-lang.modules"       %% "scala-collection-compat"  % "2.10.0",
    "uk.gov.hmrc.webjars"           % "hmrc-frontend"            % hmrcFrontendVersion,
    "org.jsoup"                     % "jsoup"                    % "1.13.1"      % Test,
    "org.scalatestplus"            %% "scalatestplus-mockito"    % "1.0.0-M2"    % Test,
    "org.scalatestplus"            %% "scalatestplus-scalacheck" % "3.1.0.0-RC2" % Test,
    "org.scalacheck"               %% "scalacheck"               % "1.15.3"      % Test,
    "com.googlecode.htmlcompressor" % "htmlcompressor"           % "1.5.2"       % Test,
    "com.github.pathikrit"         %% "better-files"             % "3.9.1"       % Test,
    "com.lihaoyi"                  %% "pprint"                   % "0.6.1"       % Test,
    "org.bitbucket.cowwoc"          % "diff-match-patch"         % "1.2"         % Test
  )

  val play28 = Seq(
    "uk.gov.hmrc"            %% "play-language-play-28" % playLanguageVersion,
    "com.typesafe.play"      %% "play"                  % play28Version,
    "com.typesafe.play"      %% "filters-helpers"       % play28Version,
    "com.typesafe.play"      %% "play-test"             % play28Version % Test,
    "com.typesafe.play"      %% "play-ahc-ws"           % play28Version % Test,
    "org.scalatestplus.play" %% "scalatestplus-play"    % "5.1.0"       % Test,
    "com.vladsch.flexmark"    % "flexmark-all"          % "0.35.10"     % Test
  )

  val play29 = Seq(
    "uk.gov.hmrc"            %% "play-language-play-29" % playLanguageVersion,
    "com.typesafe.play"      %% "play"                  % play29Version,
    "com.typesafe.play"      %% "play-filters-helpers"  % play29Version,
    "com.typesafe.play"      %% "play-test"             % play29Version % Test,
    "com.typesafe.play"      %% "play-ahc-ws"           % play29Version % Test,
    "org.scalatestplus.play" %% "scalatestplus-play"    % "6.0.0"       % Test,
    "com.vladsch.flexmark"    % "flexmark-all"          % "0.64.8"      % Test
  )

  val play30 = Seq(
    "uk.gov.hmrc"            %% "play-language-play-30" % playLanguageVersion,
    "org.playframework"      %% "play"                  % play30Version,
    "org.playframework"      %% "play-filters-helpers"  % play30Version,
    "org.playframework"      %% "play-test"             % play30Version % Test,
    "org.playframework"      %% "play-ahc-ws"           % play30Version % Test,
    "org.scalatestplus.play" %% "scalatestplus-play"    % "7.0.0"       % Test,
    "com.vladsch.flexmark"    % "flexmark-all"          % "0.64.8"      % Test
  )
}
