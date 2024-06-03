/*
 * Copyright 2023 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.hmrcfrontend.views.helpers

import org.jsoup.Jsoup
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpecLike
import play.api.i18n.Lang
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.test.FakeRequest
import play.api.test.Helpers.contentAsString
import play.api.test.Helpers._

import scala.jdk.CollectionConverters._
import java.util.{List => JavaList}
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.Application
import play.api.mvc.AnyContentAsEmpty
import uk.gov.hmrc.helpers.MessagesSupport
import uk.gov.hmrc.hmrcfrontend.views.Aliases.FooterItem
import uk.gov.hmrc.hmrcfrontend.views.html.helpers.HmrcStandardFooter

class hmrcStandardFooterSpec extends AnyWordSpecLike with Matchers with MessagesSupport with GuiceOneAppPerSuite {

  implicit val fakeRequest: FakeRequest[AnyContentAsEmpty.type] = FakeRequest("GET", "/foo")

  def buildApp(properties: Map[String, String] = Map.empty): Application =
    new GuiceApplicationBuilder()
      .configure(Map("play.i18n.langs" -> List("en", "cy")) ++ properties)
      .build()

  val englishLinkTextEntries: JavaList[String] = List(
    "Cookies",
    "Privacy policy",
    "Terms and conditions",
    "Help using GOV.UK",
    "Contact",
    "Rhestr o Wasanaethau Cymraeg",
    "Open Government Licence v3.0",
    "© Crown copyright"
  ).asJava

  val welshLinkTextEntries: JavaList[String] = List(
    "Cwcis",
    "Polisi preifatrwydd",
    "Telerau ac Amodau",
    "Help wrth ddefnyddio GOV.UK",
    "Cysylltu",
    "Rhestr o Wasanaethau Cymraeg",
    "y Drwydded Llywodraeth Agored v3.0",
    "© Hawlfraint y Goron"
  ).asJava

  "HmrcStandardFooter" should {
    "generate the correct list of links" in {
      implicit val app = buildApp()

      val component = app.injector.instanceOf[HmrcStandardFooter]
      val content   = contentAsString(component()(messages, fakeRequest))
      val document  = Jsoup.parse(content)
      val links     = document.getElementsByTag("a")

      links.eachText() should be(englishLinkTextEntries)
    }

    "add a lang and hreflang attribute to the Welsh language information link" in {
      implicit val app = buildApp()

      val component = app.injector.instanceOf[HmrcStandardFooter]
      val content   = contentAsString(component()(messages, fakeRequest))
      val document  = Jsoup.parse(content)
      val links     = document.getElementsByTag("a")

      links.get(5).attr("lang")     should be("cy")
      links.get(5).attr("hreflang") should be("cy")
    }

    "allow additional links to be added" in {
      implicit val app = buildApp()

      val component             = app.injector.instanceOf[HmrcStandardFooter]
      val additionalFooterItems = Seq(
        FooterItem(
          Some("Service specific link 1"),
          Some("/any-service/link-1")
        ),
        FooterItem(
          Some("Service specific link 2"),
          Some("/any-service/link-2")
        )
      )

      val content  =
        contentAsString(component(additionalFooterItems)(messages, fakeRequest))
      val document = Jsoup.parse(content)
      val links    = document.getElementsByTag("a")

      links.eachText() should be(
        List(
          "Cookies",
          "Privacy policy",
          "Terms and conditions",
          "Help using GOV.UK",
          "Contact",
          "Rhestr o Wasanaethau Cymraeg",
          "Service specific link 1",
          "Service specific link 2",
          "Open Government Licence v3.0",
          "© Crown copyright"
        ).asJava
      )
    }

    "generate a link to an accessibility statement" in {
      implicit val app = buildApp()

      val component = app.injector.instanceOf[HmrcStandardFooter]
      val content   =
        contentAsString(
          component(accessibilityStatementUrl = Some("https://www.example.com/accessibility-statement"))(
            messages,
            fakeRequest
          )
        )
      val document  = Jsoup.parse(content)
      val links     = document.getElementsByTag("a")

      links.eachText() should be(
        List(
          "Cookies",
          "Accessibility statement",
          "Privacy policy",
          "Terms and conditions",
          "Help using GOV.UK",
          "Contact",
          "Rhestr o Wasanaethau Cymraeg",
          "Open Government Licence v3.0",
          "© Crown copyright"
        ).asJava
      )

      links.get(1).attr("href") should be("https://www.example.com/accessibility-statement")
    }

    "generate the correct list of links in Welsh" in {
      implicit val app  = buildApp()
      val welshMessages = messagesApi.preferred(Seq(Lang("cy")))

      val component = app.injector.instanceOf[HmrcStandardFooter]
      val content   = contentAsString(component()(welshMessages, fakeRequest))
      val document  = Jsoup.parse(content)
      val links     = document.getElementsByTag("a")

      links.eachText() should be(welshLinkTextEntries)
    }

    "generate the correct copyright message in Welsh" in {
      implicit val app  = buildApp()
      val welshMessages = messagesApi.preferred(Seq(Lang("cy")))

      val component = app.injector.instanceOf[HmrcStandardFooter]
      val content   = contentAsString(component()(welshMessages, fakeRequest))
      val document  = Jsoup.parse(content)
      val elements  = document.getElementsByClass("govuk-footer__licence-description")

      elements.first.text should be(
        "Mae‘r holl gynnwys ar gael o dan y Drwydded Llywodraeth Agored v3.0, oni nodir yn wahanol"
      )
    }

    "generate the correct visually hidden support links message in Welsh" in {
      implicit val app  = buildApp()
      val welshMessages = messagesApi.preferred(Seq(Lang("cy")))

      val component = app.injector.instanceOf[HmrcStandardFooter]
      val content   = contentAsString(component()(welshMessages, fakeRequest))
      val document  = Jsoup.parse(content)
      val elements  = document.getElementsByClass("govuk-visually-hidden")

      elements.first.text should be("Cysylltiadau cymorth")
    }
  }
}
