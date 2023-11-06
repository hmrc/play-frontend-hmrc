package uk.gov.hmrc.hmrcfrontend.support

import org.scalacheck.Arbitrary
import play.api.libs.json.OWrites
import play.twirl.api.{HtmlFormat, Template1}
import uk.gov.hmrc.hmrcfrontend.views.HmrcFrontendDependency.hmrcFrontendVersion
import uk.gov.hmrc.support.TemplateIntegrationBaseSpec

import scala.reflect.ClassTag
import scala.util.Try

/**
  * Base class for integration testing a Twirl template against the Nunjucks template rendering service
  */
abstract class TemplateIntegrationSpec[T: OWrites: Arbitrary, C <: Template1[T, HtmlFormat.Appendable]: ClassTag](
  hmrcComponentName: String,
  seed: Option[String] = None
) extends TemplateIntegrationBaseSpec[T](hmrcComponentName, seed) {

  protected val libraryName: String = "hmrc"

  protected val libraryVersion: String = hmrcFrontendVersion

  private val component: C = app.injector.instanceOf[C]

  /**
    * Calls the Twirl template with the given parameters and returns the resulting markup
    *
    * @param templateParams: T
    * @return [[Try[HtmlFormat.Appendable]]] containing the markup
    */
  override def render(templateParams: T): Try[HtmlFormat.Appendable] =
    Try(component.render(templateParams))
}
