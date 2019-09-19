package uk.gov.hmrc.govukfrontend.support

import org.scalacheck.Prop.{forAll, secure}
import org.scalacheck.{Arbitrary, ShrinkLowPriority}
import org.scalatest.concurrent.{IntegrationPatience, ScalaFutures}
import play.api.libs.json.OWrites
import ScalaCheckUtils.classify
import uk.gov.hmrc.govukfrontend.views.components.backLinkIntegrationSpec.propertyWithSeed
import uk.gov.hmrc.govukfrontend.views.{JsonHelpers, JsoupHelpers}
import Implicits._

/**
  * Base trait for integration testing a TWirl template against the Nunjucks template rendering service
  *
  * @tparam T Type representing the input parameters of the Twirl template
  */
trait IntegrationSpec[T]
    extends TemplateServiceClient
    with ShrinkLowPriority
    with JsoupHelpers
    with JsonHelpers
    with ScalaFutures
    with IntegrationPatience {

  def govukComponentName: String

  /**
    * [[OWrites]] for a case class representing a Twirl template's parameters
    */
  implicit def writesTemplateParams: OWrites[T]

  /**
    * [[Arbitrary]] instances of template parameters to use in property testing
    *
    * @return [[Arbitrary[T]]]
    */
  implicit def arbTemplateParams: Arbitrary[T]

  /**
    * [[Stream]] of [[org.scalacheck.Prop.classify]] conditions to collect statistics on a property
    * Used to check the distribution of generated data
    *
    * @param templateParams
    * @return [[Stream[(Boolean, Any, Any)]]] of arguments to [[org.scalacheck.Prop.classify]]
    */
  def classifiers(templateParams: T): Stream[(Boolean, Any, Any)] =
    Stream.empty[(Boolean, Any, Any)]

  /**
    * Calls the Twirl template with the given parameters and converts the resulting markup into a [[String]]
    *
    * @param templateParams
    * @return [[String]] containing the markup
    */
  def twirlTemplateAsString(templateParams: T): String

  /**
    * Property that renders the Twirl template and uses the template rendering service to render the equivalent
    * Nunjucks template, and checks the outputs are equal. If provided, it uses the [[classifiers]] to collect statistics
    * about the template parameters to analyse the distribution of the generators.
    */
  propertyWithSeed(s"$govukComponentName should render the same markup as the nunjucks renderer", None) = forAll {
    templateParams: T =>
      classify(classifiers(templateParams))(secure {

        val response = renderComponent(templateParams, govukComponentName)

        val nunJucksOutputHtml =
          parseAndCompressHtml(response.futureValue.bodyAsString)

        val twirlOutputHtml =
          parseAndCompressHtml(twirlTemplateAsString(templateParams))

        twirlOutputHtml == nunJucksOutputHtml
      })
  }
}
