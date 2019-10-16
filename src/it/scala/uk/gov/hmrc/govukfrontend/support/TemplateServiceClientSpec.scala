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
import uk.gov.hmrc.govukfrontend.views.viewmodels.backlink.BackLink

class TemplateServiceClientSpec
    extends WordSpec
    with TemplateServiceClient
    with Matchers
    with ScalaFutures
    with IntegrationPatience
    with JsoupHelpers {

  "TemplateServiceClient" when {
    "making a request to the 'render' endpoint" should {
      "successfully obtain a response from the template service" in {

        Server.withRouter() {
          case GET(p"/govuk/v$govukVersion/components/govukBackLink ") =>
            Action {
              Results.Ok("""<a href="#" class="govuk-back-link">Back</a>""")
            }
        } { implicit port =>
          val response = render(
            templateParams     = BackLink(href = "#"),
            govukComponentName = "govukBackLink",
            govukVersion       = govukFrontendVersion
          ).futureValue

          response.status shouldBe 200

          parseAndCompressHtml(response.bodyAsString) shouldBe parseAndCompressHtml(
            """<a href="#" class="govuk-back-link">Back</a>""")
        }
      }
    }
  }
}
