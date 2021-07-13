resolvers += MavenRepository("HMRC-open-artefacts-maven2", "https://open.artefacts.tax.service.gov.uk/maven2")
resolvers += Resolver.url("HMRC-open-artefacts-ivy2", url("https://open.artefacts.tax.service.gov.uk/ivy2"))(
  Resolver.ivyStylePatterns
)

addSbtPlugin(
  sys.env.get("PLAY_VERSION") match {
    case Some("2.8") => "com.typesafe.play" % "sbt-plugin" % "2.8.7"
    case Some("2.7") => "com.typesafe.play" % "sbt-plugin" % "2.7.9"
    case _           => "com.typesafe.play" % "sbt-plugin" % "2.6.25"
  }
)

addSbtPlugin("com.typesafe.sbt" % "sbt-twirl" % "1.4.2")

addSbtPlugin("uk.gov.hmrc" % "sbt-auto-build" % "3.3.0")

addSbtPlugin("uk.gov.hmrc" % "sbt-play-cross-compilation" % "2.2.0")

addSbtPlugin("com.eed3si9n" % "sbt-buildinfo" % "0.7.0")

addSbtPlugin("org.scalameta" % "sbt-scalafmt" % "2.4.0")
