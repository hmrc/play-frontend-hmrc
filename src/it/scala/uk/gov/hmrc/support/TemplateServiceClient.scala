package uk.gov.hmrc.support

import org.scalatest.wordspec.AnyWordSpecLike
import org.scalatestplus.play.PortNumber
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.libs.json.{Json, OWrites}
import play.api.libs.ws.{WSClient, WSResponse}

import scala.concurrent.Future

trait TemplateServiceClient extends AnyWordSpecLike with WSScalaTestClient with GuiceOneAppPerSuite {

  implicit val portNumber: PortNumber = PortNumber(3000)

  implicit lazy val wsClient: WSClient = app.injector.instanceOf[WSClient]

  protected val libraryName: String

  protected val libraryVersion: String

  /**
    * Render a hmrc-frontend component using the template service
    *
    * @param componentName the hmrc-frontend component name as documented in the template service
    * @param templateParams
    * @return [[WSResponse]] with the rendered component
    */
  def render[T: OWrites](
    componentName: String,
    templateParams: T,
    libraryVersion: String = libraryVersion
  ): Future[WSResponse] =
    wsUrl(s"component/$libraryName/$libraryVersion/$componentName")
      .post(Json.toJson(templateParams))
}
