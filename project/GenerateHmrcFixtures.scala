import play.api.libs.json.{JsObject, Json}
import sbt._
import scalaj.http.{Http, HttpOptions, HttpRequest}

import scala.util.{Failure, Success, Try}

case class GenerateHmrcFixtures(fixturesDir: File) {
  private val rendererPort: String        = "3000"
  private val baseUrl: String             = s"http://localhost:$rendererPort"
  private val hmrcFrontendVersion: String = LibDependencies.hmrcFrontendVersion

  def generate(): Unit =
    generateTestFixtures()

  private def getExamples(): Seq[JsObject] = {
    val endpoint: String = s"$baseUrl/snapshot/hmrc/$hmrcFrontendVersion"

    val attempt: Try[Seq[JsObject]] = Try {
      val response = http(endpoint).asString.body

      Json.parse(response).as[Seq[JsObject]]
    }

    attempt match {
      case Success(snapshot) => snapshot
      case Failure(e)        =>
        println(s"Failed to fetch snapshot from Template Service at $endpoint. Details: [${e.getLocalizedMessage}].")
        Seq()
    }
  }

  private def generateTestFixtures(): Unit = {
    val testFixturesDir =
      fixturesDir / s"test-fixtures"

    IO.delete(testFixturesDir)
    IO.createDirectory(testFixturesDir)

    IO.write((testFixturesDir / "VERSION.txt"), hmrcFrontendVersion)

    for (example <- getExamples()) {
      val componentName = (example \ "componentName").as[String]
      val componentJson = Json.obj(
        "name" -> componentName
      )
      val inputJson     = (example \ "input").as[JsObject]
      val exampleDir    = testFixturesDir / (example \ "exampleId").as[String]

      IO.createDirectory(exampleDir)
      IO.write(exampleDir / "input.json", Json.prettyPrint(inputJson))
      IO.write(
        exampleDir / "component.json",
        Json.prettyPrint(componentJson)
      )
      IO.write(exampleDir / "output.txt", (example \ "output").as[String])
    }
  }

  private def http(endpoint: String): HttpRequest =
    Http(endpoint)
      .option(HttpOptions.connTimeout(2000))
      .option(HttpOptions.readTimeout(5000))
}
