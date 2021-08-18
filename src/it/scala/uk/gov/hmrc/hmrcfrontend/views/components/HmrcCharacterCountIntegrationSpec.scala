package uk.gov.hmrc.hmrcfrontend.views.components

import play.api.mvc.RequestHeader
import play.api.test.FakeRequest
import play.twirl.api.HtmlFormat
import uk.gov.hmrc.hmrcfrontend.MessagesSupport
import uk.gov.hmrc.hmrcfrontend.support.TemplateIntegrationBaseSpec
import uk.gov.hmrc.hmrcfrontend.views.html.components._
import uk.gov.hmrc.hmrcfrontend.views.viewmodels.charactercount.Generators._
import scala.util.Try

object HmrcCharacterCountIntegrationSpec
    extends TemplateIntegrationBaseSpec[CharacterCount](
      hmrcComponentName = "hmrcCharacterCount",
      seed = None
    )
    with MessagesSupport {

  private val component = app.injector.instanceOf[HmrcCharacterCount]

  override def render(characterCount: CharacterCount): Try[HtmlFormat.Appendable] = {
    implicit val request: RequestHeader = FakeRequest("GET", "/foo")
    Try(component(characterCount))
  }
}
