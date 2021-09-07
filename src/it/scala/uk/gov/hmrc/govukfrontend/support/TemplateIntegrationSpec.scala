package uk.gov.hmrc.govukfrontend.support

import org.scalacheck.Prop.{forAll, secure}
import org.scalacheck.{Arbitrary, Properties, ShrinkLowPriority, Test}
import org.scalatest.concurrent.{IntegrationPatience, ScalaFutures}
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.libs.json.{Json, OWrites}
import play.twirl.api.{HtmlFormat, Template1}
import uk.gov.hmrc.govukfrontend.views.GovukFrontendDependency.govukFrontendVersion
import uk.gov.hmrc.helpers.Implicits._
import uk.gov.hmrc.helpers.ScalaCheckUtils.{ClassifyParams, classify}
import uk.gov.hmrc.helpers.{DiffReporter, TemplateServiceClient}
import uk.gov.hmrc.helpers.views.{JsoupHelpers, PreProcessor, TemplateValidationException, TwirlRenderer}

import scala.util.{Failure, Success, Try}
import scala.reflect.ClassTag

/**
  * Base class for integration testing a Twirl template against the Nunjucks template rendering service
  *
  * @tparam T Type representing the input parameters of the Twirl template
  */
abstract class TemplateIntegrationSpec[T: OWrites: Arbitrary, C <: Template1[T, HtmlFormat.Appendable]: ClassTag](
  govukComponentName: String,
  seed: Option[String] = None
) extends Properties(govukComponentName)
    with DiffReporter[T]
    with TemplateServiceClient
    with PreProcessor
    with TwirlRenderer[T]
    with ShrinkLowPriority
    with JsoupHelpers
    with ScalaFutures
    with IntegrationPatience
    with GuiceOneAppPerSuite {

  override protected val frontendVersion: String = govukFrontendVersion

  protected val component: C = app.injector.instanceOf[C]

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
    * Calls the Twirl template with the given parameters and returns the resulting markup
    *
    * @param templateParams: T
    * @return [[Try[HtmlFormat.Appendable]]] containing the markup
    */
  def render(templateParams: T): Try[HtmlFormat.Appendable] =
    Try(component.render(templateParams))

  override def overrideParameters(p: Test.Parameters): Test.Parameters =
    p.withMinSuccessfulTests(20)

  /**
    * Property that renders the Twirl template and uses the template rendering service to render the equivalent
    * Nunjucks template, and checks the outputs are equal. If provided, it uses the [[classifiers]] to collect statistics
    * about the template parameters to analyse the distribution of the generators.
    */
  propertyWithSeed(s"$govukComponentName should render the same markup as the nunjucks renderer", seed) = forAll {
    templateParams: T =>
      classify(classifiers(templateParams))(secure {

        val response = render("govuk", govukComponentName, templateParams)

        val nunJucksOutputHtml = response.futureValue.bodyAsString

        val tryRenderTwirl =
          render(templateParams)
            .transform(html => Success(html.body), f => Failure(new TemplateValidationException(f.getMessage)))

        tryRenderTwirl match {

          case Success(twirlOutputHtml)                      =>
            val preProcessedTwirlHtml    = preProcess(twirlOutputHtml)
            val preProcessedNunjucksHtml = preProcess(nunJucksOutputHtml)
            val prop                     = preProcessedTwirlHtml == preProcessedNunjucksHtml

            if (!prop) {
              reportDiff(
                preProcessedTwirlHtml = preProcessedTwirlHtml,
                preProcessedNunjucksHtml = preProcessedNunjucksHtml,
                templateParams = templateParams,
                templateParamsJson = Json.prettyPrint(Json.toJson(templateParams)),
                componentName = govukComponentName
              )
            }

            prop
          case Failure(TemplateValidationException(message)) =>
            println(s"Failed to validate the parameters for the $govukComponentName template")
            println(s"Exception: $message")
            println("Skipping property evaluation")

            true
        }
      })
  }

}
