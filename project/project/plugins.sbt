resolvers += Resolver.url("hmrc-sbt-plugin-releases", url("https://dl.bintray.com/hmrc/sbt-plugin-releases"))(
  Resolver.ivyStylePatterns)

addSbtPlugin("uk.gov.hmrc" % "sbt-play-cross-compilation" % "0.20.0")
