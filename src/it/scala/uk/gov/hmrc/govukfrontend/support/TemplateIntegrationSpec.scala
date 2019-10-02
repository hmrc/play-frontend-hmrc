package uk.gov.hmrc.govukfrontend.support

import org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch
import org.jsoup.Jsoup
import org.scalacheck.Prop.{forAll, secure}
import org.scalacheck.{Arbitrary, Properties, ShrinkLowPriority, Test}
import org.scalatest.concurrent.{IntegrationPatience, ScalaFutures}
import play.api.libs.json.{Json, OWrites}
import play.twirl.api.HtmlFormat
import uk.gov.hmrc.govukfrontend.support.Implicits._
import uk.gov.hmrc.govukfrontend.support.ScalaCheckUtils.{ClassifyParams, classify}
import uk.gov.hmrc.govukfrontend.views.{JsonHelpers, JsoupHelpers, TemplateValidationException}
import scala.collection.JavaConverters._
import scala.util.{Failure, Success, Try}

/**
  * Base trait for integration testing a Twirl template against the Nunjucks template rendering service
  *
  * @tparam T Type representing the input parameters of the Twirl template
  */
abstract class TemplateIntegrationSpec[T: OWrites: Arbitrary](govukComponentName: String, seed: Option[String] = None)
    extends Properties(govukComponentName)
    with TemplateServiceClient
    with ShrinkLowPriority
    with JsoupHelpers
    with JsonHelpers
    with ScalaFutures
    with IntegrationPatience {

  /**
    * [[Stream]] of [[org.scalacheck.Prop.classify]] conditions to collect statistics on a property
    * Used to check the distribution of generated data
    *
    * @param templateParams
    * @return [[Stream[ClassifyParams]] of arguments to [[org.scalacheck.Prop.classify]]
    */
  def classifiers(templateParams: T): Stream[ClassifyParams] =
    Stream.empty[ClassifyParams]

  /**
    * Calls the Twirl template with the given parameters and converts the resulting markup into a [[String]]
    *
    * @param templateParams
    * @return [[Try[HtmlFormat.Appendable]]] containing the markup
    */
  def twirlRender(templateParams: T): Try[HtmlFormat.Appendable]

  /***
    * Diff utility to report diffs in test failures
    */
  private lazy val diffWrapper = DiffWrapper(new DiffMatchPatch())

  /**
    * Property that renders the Twirl template and uses the template rendering service to render the equivalent
    * Nunjucks template, and checks the outputs are equal. If provided, it uses the [[classifiers]] to collect statistics
    * about the template parameters to analyse the distribution of the generators.
    */
  propertyWithSeed(s"$govukComponentName should render the same markup as the nunjucks renderer", seed) = forAll {
    templateParams: T =>
      classify(classifiers(templateParams))(secure {

        val response = renderComponent(govukComponentName, templateParams)

        val nunJucksOutputHtml =
          parseAndCompressHtml(response.futureValue.bodyAsString)

        val tryTwirlRender =
          twirlRender(templateParams)
            .transform(
              html => Success(parseAndCompressHtml(html.body)),
              f => Failure(new TemplateValidationException(f.getMessage)))

        tryTwirlRender match {
          case Success(twirlOutputHtml) =>
            val prop = nunJucksOutputHtml == twirlOutputHtml

            if (!prop)
              showDiff(
                nunJucksOutputHtml,
                twirlOutputHtml,
                Json.prettyPrint(Json.toJson(templateParams)),
                templateParams)
            else ()

            prop
          case Failure(TemplateValidationException(_)) =>
            println(
              s"Failed to validate the parameters for the template for $govukComponentName ... Skipping property evaluation")
            true
        }
      })
  }

  /**
    * Compute the diff and print the path to the diff file
    *
    * @param nunJucksOutputHtml
    * @param twirlOutputHtml
    * @param templateParamsJson
    * @param templateParams
    */
  def showDiff(
    nunJucksOutputHtml: String,
    twirlOutputHtml: String,
    templateParamsJson: String,
    templateParams: T
  ): Unit = {
    val diffResult = diffWrapper.diff(nunJucksOutputHtml, twirlOutputHtml)
    val path       = diffWrapper.diffToHtml(diffResult.asScala)

    println("\nThe rendered templates are different\n")
    println("-" * 80)
    println("Nunjucks")
    println("-" * 80)
    println(s"\nparameters: ")
    println(templateParamsJson)
    val formattedNunjucksHtml = Jsoup.parseBodyFragment(nunJucksOutputHtml).body.html
    println(s"\nNunjucks output:\n$formattedNunjucksHtml\n")

    println("-" * 80)
    println("Twirl")
    println("-" * 80)
    println(s"\nparameters: ")
    pprint.pprintln(templateParams, width = 80, height = 500)
    val formattedTwirlHtml = Jsoup.parseBodyFragment(twirlOutputHtml).body.html
    println(s"\nTwirl output:\n$formattedTwirlHtml\n")
    println(s"\ndiff between Nunjucks and Twirl outputs (please open diff HTML file in a browser): file://$path\n")
  }
}
