package uk.gov.hmrc.hmrcfrontend.support

import play.api.libs.ws.WSResponse

object Implicits {

  implicit class RichWSResponse(response: WSResponse) {
    def bodyAsString: String = response.body[String]
  }
}
