package uk.gov.hmrc.helpers

import org.scalatest.wordspec.AnyWordSpecLike
import org.scalatestplus.play.PortNumber
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.libs.json.{Json, OWrites}
import play.api.libs.ws.{WSClient, WSResponse}

import scala.concurrent.Future

trait TemplateServiceClient extends AnyWordSpecLike with WSScalaTestClient with GuiceOneAppPerSuite {

  implicit val portNumber: PortNumber = PortNumber(3000)

  implicit lazy val wsClient: WSClient = app.injector.instanceOf[WSClient]

  protected val frontendVersion: String

  /**
    * Render a hmrc-frontend component using the template service
    *
    * @param componentName the hmrc-frontend component name as documented in the template service
    * @param templateParams
    * @return [[WSResponse]] with the rendered component
    */
  def render[T: OWrites](
    libraryPrefix: String,
    componentName: String,
    templateParams: T,
    version: String = frontendVersion
  ): Future[WSResponse] =
    wsUrl(s"component/$libraryPrefix/$version/$componentName")
      .post(Json.toJson(templateParams))
}
