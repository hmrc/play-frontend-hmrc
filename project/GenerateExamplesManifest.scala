import org.apache.commons.io.FilenameUtils
import play.api.libs.json.Json
import sbt._
import scalaj.http._

object GenerateExamplesManifest {

  //FIXME: To be moved to a separate project with automatic Twirl example generation, test and manifest creation.
  /**
    *
    * sbt task implementation that generates a <code>manifest.json</code> with references to the example files which can be used by the
    * Govuk Design System browser extension [[https://github.com/hmrc/play-frontend-govuk-extension]]
    *
    * It collects all the examples in the folder [[src/test/play-26/twirl/uk/gov/hmrc/govukfrontend/views/examples]]
    * (the play-25 examples are auto-generated) and generates a <code>manifest.json</code> file in the folder
    * [[src/test/resources]]which contains the location of each play-25 and play-26 example, along with the md5 hash
    * provided by the template service.
    *
    * To exclude an example from the manifest include a Twirl comment with the following contents
    * <code>@*exclude-from-manifest*@</code> in the template code.
    *
    * The template-service [[https://github.com/hmrc/template-service-spike]] must be running for the task to succeed,
    * as it reads from the live Govuk Design System [[https://design-system.service.gov.uk/components/]] to provide the
    * examples and md5 hashes.
    *
    * @param play26Examples
    * @return
    */
  def generateExamplesManifest(play26Examples: Set[File], manifestFile: File): Set[File] =
    removeExcludes(play26Examples).toList match {
      case Nil => Set.empty
      case examples =>
        val content = manifestContent(examples)
        IO.write(content = content, file = manifestFile, append = false)
        Set(manifestFile)
    }

  private def removeExcludes(examples: Set[File]): Set[File] = {
    val excludesRegex = """@\*exclude-from-manifest\*@"""
    examples.filterNot(example => excludesRegex.r.findFirstMatchIn(IO.read(example)).nonEmpty)
  }

  private def toCamelCase(s: String): String = {
    val xs = s.split("-")
    (xs.headOption.toSeq ++ xs.drop(1).map(_.capitalize)).mkString
  }

  private def sanitiseFolderName(folder: String): String = folder match {
    case "input" => "textinput"
    case _       => folder.toLowerCase.replaceAll("-", "")
  }

  private def sanitiseExample(example: TemplateServiceResponse): TemplateServiceResponse = {
    val regex = """(.*)/(.*)""".r
    example.name match {
      case regex(folder, name) =>
        example.copy(name = sanitiseFolderName(folder) + "/" + toCamelCase(name))
    }
  }

  private def makeExampleRef(example: File, playDirName: String, md5Hash: String) =
    ExampleRef(
      uri          = s"$playDirName/twirl/uk/gov/hmrc/govukfrontend/views/examples/${exampleFolder(example)}/${example.name}",
      htmlChecksum = md5Hash
    )

  /**
    * Generates the contents of the <code>manifest.json</code> with references to the example files which can be used by the
    * * Govuk Design System plugin [[https://github.com/hmrc/play-frontend-govuk-extension]]
    *
    * @param play26Examples
    * @return
    */
  private def manifestContent(play26Examples: List[File]): String = {
    val sanitisedExamples = retrieveExamples(play26Examples).map(sanitiseExample)
    val manifestElems = play26Examples
      .map { example =>
        val id = idFromPath(example)
        val md5Hash = sanitisedExamples
          .find(_.name == id)
          .getOrElse(throw new RuntimeException(s"Could not find example named $id"))
          .md5

        ManifestJson(
          id = id,
          versions = Versions(
            play25 = makeExampleRef(example, "play-25", md5Hash),
            play26 = makeExampleRef(example, "play-26", md5Hash)
          )
        )
      }
      .sortBy(_.id)

    val json = Json.toJson(manifestElems)
    Json.prettyPrint(json)
  }

  private def removeTwirlExtension(fileName: String): String = {
    import FilenameUtils.removeExtension
    removeExtension(removeExtension(fileName)) // remove extension twice foo.scala.html => foo
  }

  private def idFromPath(file: File): String =
    exampleFolder(file) + "/" + removeTwirlExtension(file.name)

  private def exampleFolder(file: File): String =
    file.getParentFile.name

  private val govukComponents = Seq(
    "accordion",
    "backLink",
    "breadcrumbs",
    "button",
    "characterCount",
    "checkboxes",
    "dateInput",
    "details",
    "errorMessage",
    "errorSummary",
    "fieldset",
    "fileUpload",
    "footer",
    "header",
    "insetText",
    "panel",
    "phaseBanner",
    "radios",
    "select",
    "skipLink",
    "summaryList",
    "table",
    "tabs",
    "tag",
    "input",
    "textarea",
    "warningText"
  ).map("govuk" + _.capitalize)

  private def exampleToComponentMap(file: File): String = exampleFolder(file) match {
    case "textinput" => "govukInput"
    case name =>
      govukComponents
        .find(_.substring("govuk".length).toLowerCase == name)
        .getOrElse(throw new IllegalArgumentException(s"Could not find the govuk component name for $name"))
  }

  final case class TemplateServiceResponse(name: String, html: String, md5: String, nunjucks: String)

  object TemplateServiceResponse {
    implicit val reads = Json.reads[TemplateServiceResponse]
  }

  private def retrieveExamples(files: List[File]): Seq[TemplateServiceResponse] = {
    def retrieveExample(govukComponent: String): Seq[TemplateServiceResponse] = {
      val response = Http(s"http://localhost:3000/examples-output/$govukComponent").asString.body
      Json.parse(response).as[Seq[TemplateServiceResponse]]
    }

    val componentNames = files.map(exampleToComponentMap)
    componentNames.flatMap(retrieveExample)
  }
}
