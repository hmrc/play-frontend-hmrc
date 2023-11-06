package uk.gov.hmrc.hmrcfrontend.views.components

import play.api.mvc.RequestHeader
import play.api.test.FakeRequest
import play.twirl.api.HtmlFormat
import uk.gov.hmrc.helpers.MessagesSupport
import uk.gov.hmrc.hmrcfrontend.views.HmrcFrontendDependency.hmrcFrontendVersion
import uk.gov.hmrc.hmrcfrontend.views.html.components._
import uk.gov.hmrc.hmrcfrontend.views.viewmodels.addtoalist.Generators._
import uk.gov.hmrc.support.TemplateIntegrationBaseSpec

import scala.util.Try

object HmrcAddToAListIntegrationSpec
    extends TemplateIntegrationBaseSpec[AddToAList](
      componentName = "hmrcAddToAList",
      seed = None
    )
    with MessagesSupport {

  protected val libraryName: String = "hmrc"

  protected val libraryVersion: String = hmrcFrontendVersion

  private val component = app.injector.instanceOf[HmrcAddToAList]

  override def render(addToAList: AddToAList): Try[HtmlFormat.Appendable] = {
    implicit val request: RequestHeader = FakeRequest("GET", "/foo")
    Try(component(addToAList))
  }
}
