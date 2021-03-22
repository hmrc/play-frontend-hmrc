resolvers += Resolver.bintrayIvyRepo("hmrc", "sbt-plugin-releases")
resolvers += Resolver.bintrayRepo("hmrc", "releases")

addSbtPlugin(
  sys.env.get("PLAY_VERSION") match {
    case Some("2.8") => "com.typesafe.play" % "sbt-plugin" % "2.8.7"
    case Some("2.7") => "com.typesafe.play" % "sbt-plugin" % "2.7.9"
    case _           => "com.typesafe.play" % "sbt-plugin" % "2.6.25"
  }
)

addSbtPlugin("com.typesafe.sbt" % "sbt-twirl"                  % "1.4.2"  )

addSbtPlugin("uk.gov.hmrc"      % "sbt-auto-build"             % "2.13.0" )

addSbtPlugin("uk.gov.hmrc"      % "sbt-git-versioning"         % "2.2.0"  )

addSbtPlugin("uk.gov.hmrc"      % "sbt-artifactory"            % "1.13.0" )

addSbtPlugin("uk.gov.hmrc"      % "sbt-play-cross-compilation" % "2.0.0"  )

addSbtPlugin("com.eed3si9n"     % "sbt-buildinfo"              % "0.7.0"  )

addSbtPlugin("org.scalameta" % "sbt-scalafmt" % "2.4.0")
