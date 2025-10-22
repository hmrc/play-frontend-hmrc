import sbt._

object LibDependencies {
  val govukFrontendVersion: String = "5.13.0"
  val hmrcFrontendVersion: String  = "6.97.0"
  val playLanguageVersion: String  = "9.3.0"

  val play29Version = "2.9.7"
  val play30Version = "3.0.9"

  val shared = Seq(
    "uk.gov.hmrc.webjars"           % "hmrc-frontend"    % hmrcFrontendVersion,
    "org.jsoup"                     % "jsoup"            % "1.13.1"   % Test,
    "org.scalatestplus"            %% "mockito-3-4"      % "3.2.10.0" % Test,
    "org.scalatestplus"            %% "scalacheck-1-16"  % "3.2.14.0" % Test,
    "com.googlecode.htmlcompressor" % "htmlcompressor"   % "1.5.2"    % Test,
    "com.github.pathikrit"         %% "better-files"     % "3.9.2"    % Test,
    "com.lihaoyi"                  %% "pprint"           % "0.6.6"    % Test,
    "org.bitbucket.cowwoc"          % "diff-match-patch" % "1.2"      % Test,
    "com.vladsch.flexmark"          % "flexmark-all"     % "0.64.8"   % Test
  )

  val play29 = Seq(
    "uk.gov.hmrc"            %% "play-language-play-29" % playLanguageVersion,
    "com.typesafe.play"      %% "play"                  % play29Version,
    "com.typesafe.play"      %% "play-filters-helpers"  % play29Version,
    "com.typesafe.play"      %% "play-test"             % play29Version % Test,
    "com.typesafe.play"      %% "play-ahc-ws"           % play29Version % Test,
    "org.scalatestplus.play" %% "scalatestplus-play"    % "6.0.2"       % Test
  )

  val play30 = Seq(
    "uk.gov.hmrc"            %% "play-language-play-30" % playLanguageVersion,
    "org.playframework"      %% "play"                  % play30Version,
    "org.playframework"      %% "play-filters-helpers"  % play30Version,
    "org.playframework"      %% "play-test"             % play30Version % Test,
    "org.playframework"      %% "play-ahc-ws"           % play30Version % Test,
    "org.scalatestplus.play" %% "scalatestplus-play"    % "7.0.2"       % Test
  )
}
