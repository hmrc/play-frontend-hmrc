resolvers += MavenRepository("HMRC-open-artefacts-maven2", "https://open.artefacts.tax.service.gov.uk/maven2")
resolvers += Resolver.url("HMRC-open-artefacts-ivy2", url("https://open.artefacts.tax.service.gov.uk/ivy2"))(
  Resolver.ivyStylePatterns
)

addSbtPlugin("uk.gov.hmrc"   % "sbt-auto-build"             % "3.14.0")
addSbtPlugin("org.scalameta" % "sbt-scalafmt"               % "2.4.0")

libraryDependencySchemes += "org.scala-lang.modules" %% "scala-xml" % VersionScheme.Always // required since we're cross building for Play 2.8 which isn't compatible with sbt 1.9

// TODO only for Play 2.8?
addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.8.20") // needed to compile Assets as well as provide sbt-twirl (sbt-routes-compiler?)
//addSbtPlugin("com.typesafe.sbt"  % "sbt-twirl"  % "1.5.1")

//addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.9.0-M7")
//addSbtPlugin("com.typesafe.play"  % "sbt-twirl"  % "1.6.0-RC4")

/*sys.env.get("PLAY_VERSION") match {
  case Some("2.8") => addSbtPlugin("com.typesafe.sbt"  % "sbt-twirl"  % "1.5.1")
  case Some("2.9") => addSbtPlugin("com.typesafe.play" % "sbt-twirl"  % "1.6.0-RC4")
  case _           => sys.error("Unsupported PLAY_VERSION")
}
*/
