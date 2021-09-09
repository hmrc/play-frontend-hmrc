package uk.gov.hmrc.govukfrontend.support

import org.scalacheck.Arbitrary
import play.api.libs.json.OWrites
import play.twirl.api.{HtmlFormat, Template1}
import uk.gov.hmrc.govukfrontend.views.GovukFrontendDependency.govukFrontendVersion
import uk.gov.hmrc.support.TemplateIntegrationBaseSpec

import scala.util.Try
import scala.reflect.ClassTag

/**
  * Base class for integration testing a Twirl template against the Nunjucks template rendering service
  *
  * @tparam T Type representing the input parameters of the Twirl template
  */
abstract class TemplateIntegrationSpec[T: OWrites: Arbitrary, C <: Template1[T, HtmlFormat.Appendable]: ClassTag](
  govukComponentName: String,
  seed: Option[String] = None
) extends TemplateIntegrationBaseSpec[T](govukComponentName, seed) {

  protected val libraryName: String = "govuk"

  protected val libraryVersion: String = govukFrontendVersion

  private val component: C = app.injector.instanceOf[C]

  def render(templateParams: T): Try[HtmlFormat.Appendable] =
    Try(component.render(templateParams))

}
