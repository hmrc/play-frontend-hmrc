package uk.gov.hmrc.govukfrontend.support

import org.scalatestplus.play.PortNumber
import play.api.libs.ws.{WSClient, WSRequest}
import Utils._

trait WSScalaTestClient {

  def wsUrl(url: String)(implicit portNumber: PortNumber, wsClient: WSClient): WSRequest =
    doCall(url, wsClient, portNumber)

  private def doCall(url: String, wsClient: WSClient, portNumber: PortNumber) =
    wsClient.url("http://localhost:" + portNumber.value +/ url)
}
