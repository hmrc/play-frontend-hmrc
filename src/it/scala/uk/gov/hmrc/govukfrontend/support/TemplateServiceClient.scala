package uk.gov.hmrc.govukfrontend.support

import org.scalatest.wordspec.AnyWordSpecLike
import org.scalatestplus.play.PortNumber
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.libs.json.{Json, OWrites}
import play.api.libs.ws.{WSClient, WSResponse}
import uk.gov.hmrc.govukfrontend.views.GovukFrontendDependency.govukFrontendVersion
import scala.concurrent.Future

trait TemplateServiceClient extends AnyWordSpecLike with WSScalaTestClient with GuiceOneAppPerSuite {

  implicit val portNumber: PortNumber = PortNumber(3000)

  implicit lazy val wsClient: WSClient = app.injector.instanceOf[WSClient]

  /**
    * Render a govuk-frontend component using the template service
    *
    * @param govukComponentName the govuk-frontend component name as documented in the template service
    * @param templateParams
    * @return [[WSResponse]] with the rendered component
    */
  def render[T: OWrites](
    govukComponentName: String,
    templateParams: T,
    govukVersion: String = govukFrontendVersion
  ): Future[WSResponse] =
    wsUrl(s"component/govuk/$govukVersion/$govukComponentName")
      .post(Json.toJson(templateParams))
}
