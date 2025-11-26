resolvers += MavenRepository("HMRC-open-artefacts-maven2", "https://open.artefacts.tax.service.gov.uk/maven2")
resolvers += Resolver.url("HMRC-open-artefacts-ivy2", url("https://open.artefacts.tax.service.gov.uk/ivy2"))(
  Resolver.ivyStylePatterns
)

addSbtPlugin("uk.gov.hmrc"       % "sbt-auto-build" % "3.24.0")
addSbtPlugin("org.scalameta"     % "sbt-scalafmt"   % "2.5.2")
addSbtPlugin("org.playframework" % "sbt-plugin"     % "3.0.9")
