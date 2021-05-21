package uk.gov.hmrc.hmrcfrontend.views.components

import play.api.mvc.RequestHeader
import play.api.test.FakeRequest
import play.twirl.api.HtmlFormat
import uk.gov.hmrc.hmrcfrontend.MessagesSupport
import uk.gov.hmrc.hmrcfrontend.support.TemplateIntegrationSpec
import uk.gov.hmrc.hmrcfrontend.views.html.components._
import uk.gov.hmrc.hmrcfrontend.views.viewmodels.addtoalist.Generators._

import scala.util.Try

// Test comment out due to differences caused by use of FormWithCsrf: PLATUI-1194

//object hmrcAddToAListSpec
//    extends TemplateIntegrationSpec[AddToAList](hmrcComponentName = "hmrcAddToAList", seed = None)
//    with MessagesSupport {
//
//  override def render(addToAList: AddToAList): Try[HtmlFormat.Appendable] = {
//    implicit val request: RequestHeader = FakeRequest("GET", "/foo")
//    Try(HmrcAddToAList(addToAList))
//  }
//}
