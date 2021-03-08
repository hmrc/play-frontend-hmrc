package uk.gov.hmrc.hmrcfrontend.views.helpers

import org.scalatest.{Matchers, WordSpecLike}
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.Application
import play.api.inject.guice.GuiceApplicationBuilder
import uk.gov.hmrc.hmrcfrontend.MessagesSupport

class hmrcCharacterCountHelperSpec extends WordSpecLike
  with Matchers
  with MessagesSupport
  with GuiceOneAppPerSuite {

  def buildApp(properties: Map[String, String] = Map.empty): Application =
    new GuiceApplicationBuilder()
      .configure(Map("play.allowGlobalApplication" -> "true") ++ properties)
      .build()

  "HmrcCharacterCountHelper" should {
    "render an expected hmrcCharacterCount from a CharacterCount" in {
      
    }
  }

}
