package uk.gov.hmrc.support

import org.scalatestplus.play.PortNumber
import play.api.libs.ws.{WSClient, WSRequest}
import Utils._

trait WSScalaTestClient {

  def wsUrl(url: String)(implicit portNumber: PortNumber, wsClient: WSClient): WSRequest =
    wsClient.url("http://localhost:" + portNumber.value +/ url)
}
