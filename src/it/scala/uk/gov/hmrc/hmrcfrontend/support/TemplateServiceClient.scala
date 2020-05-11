package uk.gov.hmrc.hmrcfrontend.support

import org.scalatest.WordSpecLike
import org.scalatestplus.play.PortNumber
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.libs.json.{Json, OWrites}
import play.api.libs.ws.{WSClient, WSResponse}
import uk.gov.hmrc.hmrcfrontend.views.HmrcFrontendDependency.hmrcFrontendVersion
import scala.concurrent.Future

trait TemplateServiceClient extends WordSpecLike with WSScalaTestClient with GuiceOneAppPerSuite {

  implicit val portNumber: PortNumber = PortNumber(3000)

  implicit lazy val wsClient: WSClient = app.injector.instanceOf[WSClient]

  /**
    * Render a hmrc-frontend component using the template service
    *
    * @param hmrcComponentName the hmrc-frontend component name as documented in the template service
    * @param templateParams
    * @return [[WSResponse]] with the rendered component
    */
  def render[T: OWrites](hmrcComponentName: String, templateParams: T, hmrcVersion: String = hmrcFrontendVersion): Future[WSResponse] =
    wsUrl(s"component/hmrc/$hmrcVersion/$hmrcComponentName")
      .post(Json.toJson(templateParams))
}
