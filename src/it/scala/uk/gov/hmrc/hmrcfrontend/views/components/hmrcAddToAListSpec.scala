package uk.gov.hmrc.hmrcfrontend.views.components

import play.api.mvc.RequestHeader
import play.api.test.FakeRequest
import play.twirl.api.HtmlFormat
import uk.gov.hmrc.hmrcfrontend.MessagesSupport
import uk.gov.hmrc.hmrcfrontend.support.TemplateIntegrationSpec
import uk.gov.hmrc.hmrcfrontend.views.html.components._
import uk.gov.hmrc.hmrcfrontend.views.viewmodels.addtoalist.Generators._

import scala.util.Try

object hmrcAddToAListSpec
    extends TemplateIntegrationSpec[AddToAList, HmrcAddToAList](hmrcComponentName = "hmrcAddToAList", seed = None)
    with MessagesSupport {

  override def render(addToAList: AddToAList): Try[HtmlFormat.Appendable] = {
    implicit val request: RequestHeader = FakeRequest("GET", "/foo")
    Try(component(addToAList))
  }
}
