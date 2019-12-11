import uk.gov.hmrc.playcrosscompilation.PlayVersion.Play26

resolvers ++= Seq(
  Resolver.url("hmrc-sbt-plugin-releases", url("https://dl.bintray.com/hmrc/sbt-plugin-releases"))(
    Resolver.ivyStylePatterns),
  "HMRC Releases" at "https://dl.bintray.com/hmrc/releases",
  "Typesafe Releases" at "http://repo.typesafe.com/typesafe/releases/"
)

(managedSources in Compile) += (baseDirectory.value / "project" / "PlayCrossCompilation.scala")

addSbtPlugin("com.typesafe.play" % "sbt-plugin" % PlayCrossCompilation.playRevision)

addSbtPlugin("uk.gov.hmrc" % "sbt-auto-build" % "1.15.0")

addSbtPlugin("uk.gov.hmrc" % "sbt-git-versioning" % "1.17.0")

val twirlPluginVersion =
  if (PlayCrossCompilation.playVersion == Play26) "1.3.15" else "1.1.1"

addSbtPlugin("com.typesafe.sbt" % "sbt-twirl" % twirlPluginVersion)

addSbtPlugin("uk.gov.hmrc" % "sbt-play-cross-compilation" % "0.17.0")

addSbtPlugin("uk.gov.hmrc" % "sbt-artifactory" % "0.19.0")



