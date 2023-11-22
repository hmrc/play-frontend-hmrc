package uk.gov.hmrc.hmrcfrontend.views.components

import play.twirl.api.HtmlFormat
import uk.gov.hmrc.helpers.MessagesSupport
import uk.gov.hmrc.hmrcfrontend.views.HmrcFrontendDependency.hmrcFrontendVersion
import uk.gov.hmrc.hmrcfrontend.views.html.components._
import uk.gov.hmrc.hmrcfrontend.views.viewmodels.charactercount.Generators._
import uk.gov.hmrc.support.TemplateIntegrationBaseSpec

import scala.util.Try

object HmrcCharacterCountIntegrationSpec
    extends TemplateIntegrationBaseSpec[CharacterCount](
      componentName = "hmrcCharacterCount",
      seed = None
    )
    with MessagesSupport {

  protected val libraryName: String = "hmrc"

  protected val libraryVersion: String = hmrcFrontendVersion

  private val component = app.injector.instanceOf[HmrcCharacterCount]

  override def render(characterCount: CharacterCount): Try[HtmlFormat.Appendable] =
    Try(component(characterCount))

}
