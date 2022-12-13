resolvers += MavenRepository("HMRC-open-artefacts-maven2", "https://open.artefacts.tax.service.gov.uk/maven2")
resolvers += Resolver.url("HMRC-open-artefacts-ivy2", url("https://open.artefacts.tax.service.gov.uk/ivy2"))(
  Resolver.ivyStylePatterns
)

sys.env.getOrElse("PLAY_VERSION", "2.8") match {
  case "2.8" =>
    Seq(
      "com.typesafe.play" % "sbt-plugin" % "2.8.18",
      "com.typesafe.sbt"  % "sbt-twirl"  % "1.5.1"
    ).map(addSbtPlugin)
}

addSbtPlugin("uk.gov.hmrc"   % "sbt-auto-build"             % "3.8.0")
addSbtPlugin("uk.gov.hmrc"   % "sbt-play-cross-compilation" % "2.3.0")
addSbtPlugin("com.eed3si9n"  % "sbt-buildinfo"              % "0.7.0")
addSbtPlugin("org.scalameta" % "sbt-scalafmt"               % "2.4.0")
