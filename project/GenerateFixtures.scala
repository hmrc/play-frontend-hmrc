import java.io.File
import sbt._
import play.api.libs.json.{JsObject, Json}
import scalaj.http.{Http, HttpOptions}

import scala.util.{Failure, Success, Try}

object GenerateFixtures {
  def getExamples(govukFrontendVersion: String): Seq[JsObject] = {

    val rendererPort: String = "3000"
    val endpoint: String     = s"http://localhost:$rendererPort/snapshot/govuk/$govukFrontendVersion"

    val attempt: Try[Seq[JsObject]] = Try {
      val response = Http(endpoint)
        .option(HttpOptions.connTimeout(2000))
        .option(HttpOptions.readTimeout(5000))
        .asString
        .body

      Json.parse(response).as[Seq[JsObject]]
    }

    attempt match {
      case Success(snapshot) => snapshot
      case Failure(e)        =>
        println(s"Failed to fetch snapshot from Template Service at $endpoint. Details: [${e.getLocalizedMessage}].")
        Seq()
    }
  }

  def generateFixtures(javaResourcesDir: File, govukFrontendVersion: String): Unit = {
    val resourcesDir    = javaResourcesDir
    val fixturesDir     = resourcesDir / "fixtures"
    val testFixturesDir =
      fixturesDir / s"test-fixtures"

    IO.delete(testFixturesDir)
    IO.createDirectory(testFixturesDir)

    IO.write((testFixturesDir / "VERSION.txt"), govukFrontendVersion)

    for (example <- getExamples(govukFrontendVersion)) {
      val componentName = (example \ "componentName").as[String]
      val componentJson = Json.obj(
        "name" -> componentName
      )
      val inputJson     = (example \ "input").as[JsObject]
      val exampleDir    = testFixturesDir / (example \ "exampleId").as[String]

      IO.createDirectory(exampleDir)
      IO.write((exampleDir / "input.json"), Json.prettyPrint(inputJson))
      IO.write(
        (exampleDir / "component.json"),
        Json.prettyPrint(componentJson)
      )
      IO.write((exampleDir / "output.txt"), (example \ "output").as[String])
    }
  }
}
