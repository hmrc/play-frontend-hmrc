import uk.gov.hmrc.playcrosscompilation.PlayVersion.Play26

resolvers ++= Seq(
  Resolver.url("hmrc-sbt-plugin-releases", url("https://dl.bintray.com/hmrc/sbt-plugin-releases"))(
    Resolver.ivyStylePatterns),
  "HMRC Releases" at "https://dl.bintray.com/hmrc/releases",
  "Typesafe Releases" at "https://repo.typesafe.com/typesafe/releases/"
)

(managedSources in Compile) += (baseDirectory.value / "project" / "PlayCrossCompilation.scala")

addSbtPlugin("com.typesafe.play" % "sbt-plugin" % PlayCrossCompilation.playRevision)

addSbtPlugin("uk.gov.hmrc" % "sbt-auto-build" % "2.13.0")

addSbtPlugin("uk.gov.hmrc" % "sbt-git-versioning" % "2.2.0")

addSbtPlugin("com.typesafe.sbt" % "sbt-twirl" % "1.4.2")

addSbtPlugin("uk.gov.hmrc" % "sbt-play-cross-compilation" % "1.2.0")

addSbtPlugin("uk.gov.hmrc" % "sbt-artifactory" % "1.13.0")

addSbtPlugin("com.eed3si9n" % "sbt-buildinfo" % "0.7.0")