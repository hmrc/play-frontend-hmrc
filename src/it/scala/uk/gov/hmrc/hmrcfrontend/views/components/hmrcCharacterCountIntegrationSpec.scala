package uk.gov.hmrc.hmrcfrontend.views.components

import play.twirl.api.HtmlFormat
import uk.gov.hmrc.hmrcfrontend.support.TemplateIntegrationSpec
import uk.gov.hmrc.hmrcfrontend.views.html.components._
import uk.gov.hmrc.hmrcfrontend.views.viewmodels.charactercount.Generators._

import scala.util.Try

object hmrcCharacterCountIntegrationSpec
  extends TemplateIntegrationSpec[CharacterCount](hmrcComponentName = "hmrcCharacterCount", seed = None) {

  override def render(characterCount: CharacterCount): Try[HtmlFormat.Appendable] =
    Try(HmrcCharacterCount(characterCount))
}
