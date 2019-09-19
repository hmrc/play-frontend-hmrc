package uk.gov.hmrc.govukfrontend
package support

import org.scalatest.concurrent.{IntegrationPatience, ScalaFutures}
import org.scalatest.{Matchers, WordSpec}
import play.api.mvc._
import play.api.routing.sird._
import play.core.server.Server
import uk.gov.hmrc.govukfrontend.support.Implicits._
import uk.gov.hmrc.govukfrontend.views.GovukFrontendDependency.govukFrontendVersion
import uk.gov.hmrc.govukfrontend.views.JsoupHelpers
import uk.gov.hmrc.govukfrontend.views.components.BackLinkParams

class TemplateServiceClientSpec
    extends WordSpec
    with Matchers
    with ScalaFutures
    with IntegrationPatience
    with JsoupHelpers {

  "TemplateServiceClient" should {
    "successfully obtain a response from the template service" in {

      val templateServiceClient = new TemplateServiceClient {}

      Server.withRouter() {
        case GET(p"/govuk/v$govukVersion/components/govukBackLink") =>
          Action {
            Results.Ok("""<a href="#" class="govuk-back-link">Back</a>""")
          }
      } { implicit port =>
        val response = templateServiceClient
          .renderComponent(
            templateParams     = BackLinkParams(href = "#"),
            govukComponentName = "govukBackLink",
            govukVersion       = govukFrontendVersion)
          .futureValue

        response.status shouldBe 200

        parseAndCompressHtml(response.bodyAsString) shouldBe parseAndCompressHtml(
          """<a href="#" class="govuk-back-link">Back</a>""")
      }
    }
  }
}
