/*
 * Copyright 2021 HM Revenue & Customs
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
import play.api.i18n.{DefaultLangs, Lang}
import uk.gov.hmrc.helpers.MessagesSupport
import uk.gov.hmrc.hmrcfrontend.views.html.components.HmrcNewTabLink
import uk.gov.hmrc.hmrcfrontend.views.html.helpers.HmrcNewTabLinkHelper
import uk.gov.hmrc.hmrcfrontend.views.viewmodels.newtablinkhelper.NewTabLinkHelper

class hmrcNewTabLinkHelperSpec extends AnyWordSpecLike with Matchers with MessagesSupport {

  override lazy val defaultLangs = new DefaultLangs(Seq(Lang("en"), Lang(code = "en-US"), Lang("cy"), Lang("it")))

  "HmrcNewTabLinkHelper" should {

    val englishMessages = messagesApi.preferred(Seq(Lang("en")))
    val welshMessages   = messagesApi.preferred(Seq(Lang("cy")))

    "render empty content if passed a viewmodel with empty string text" in {
      val newTabLinkHelper     = NewTabLinkHelper()
      val hmrcNewTabLinkHelper = new HmrcNewTabLinkHelper(new HmrcNewTabLink())
      val component            = hmrcNewTabLinkHelper(newTabLinkHelper)(englishMessages)
      component.toString().trim should be("")
    }

    "render link with the default English link text" in {
      val newTabLinkHelper     = NewTabLinkHelper(text = "Some link text")
      val hmrcNewTabLinkHelper = new HmrcNewTabLinkHelper(new HmrcNewTabLink())
      val component            = hmrcNewTabLinkHelper(newTabLinkHelper)(englishMessages)

      val links = Jsoup.parse(component.toString).select("a")

      links                        should have size 1
      links.first.attr("href")   shouldBe ""
      links.first.attr("rel")    shouldBe "noopener noreferrer"
      links.first.attr("target") shouldBe "_blank"
      links.text()               shouldBe "Some link text (opens in a new tab)"
    }

    "render link with the English link text for all support en codes" in {
      val enUsMessages         = messagesApi.preferred(Seq(Lang("en-US")))
      val newTabLinkHelper     = NewTabLinkHelper(text = "Some link text")
      val hmrcNewTabLinkHelper = new HmrcNewTabLinkHelper(new HmrcNewTabLink())
      val component            = hmrcNewTabLinkHelper(newTabLinkHelper)(enUsMessages)

      val links = Jsoup.parse(component.toString).select("a")

      links                        should have size 1
      links.first.attr("href")   shouldBe ""
      links.first.attr("rel")    shouldBe "noopener noreferrer"
      links.first.attr("target") shouldBe "_blank"
      links.text()               shouldBe "Some link text (opens in a new tab)"
    }

    "render link with the href" in {
      val newTabLinkHelper     = NewTabLinkHelper(text = "Some link text", href = Some("http://www.example.com"))
      val hmrcNewTabLinkHelper = new HmrcNewTabLinkHelper(new HmrcNewTabLink())
      val component            = hmrcNewTabLinkHelper(newTabLinkHelper)(englishMessages)

      val links = Jsoup.parse(component.toString).select("a")

      links                        should have size 1
      links.first.attr("href")   shouldBe "http://www.example.com"
      links.first.attr("rel")    shouldBe "noopener noreferrer"
      links.first.attr("target") shouldBe "_blank"
      links.text()               shouldBe "Some link text (opens in a new tab)"
    }

    "render link with classes" in {
      val newTabLinkHelper     = NewTabLinkHelper(
        text = "Some link text",
        href = Some("http://www.example.com"),
        classList = Some("custom-class")
      )
      val hmrcNewTabLinkHelper = new HmrcNewTabLinkHelper(new HmrcNewTabLink())
      val component            = hmrcNewTabLinkHelper(newTabLinkHelper)(englishMessages)

      val links = Jsoup.parse(component.toString).select("a")

      links                        should have size 1
      links.first.attr("href")   shouldBe "http://www.example.com"
      links.first.attr("rel")    shouldBe "noopener noreferrer"
      links.first.attr("target") shouldBe "_blank"
      links.first.attr("class")  shouldBe "govuk-link  custom-class"
      links.text()               shouldBe "Some link text (opens in a new tab)"
    }

    "render link with the Welsh link text" in {
      val newTabLinkHelper     = NewTabLinkHelper(text = "Rhywfaint o destun cyswllt")
      val hmrcNewTabLinkHelper = new HmrcNewTabLinkHelper(new HmrcNewTabLink())
      val component            = hmrcNewTabLinkHelper(newTabLinkHelper)(welshMessages)

      val links = Jsoup.parse(component.toString).select("a")

      links                        should have size 1
      links.first.attr("href")   shouldBe ""
      links.first.attr("rel")    shouldBe "noopener noreferrer"
      links.first.attr("target") shouldBe "_blank"
      links.text()               shouldBe "Rhywfaint o destun cyswllt (yn agor ffenestr neu dab newydd)"
    }

    "render link with the no link text for unsupported language" in {
      val italianMessages      = messagesApi.preferred(Seq(Lang("it")))
      val newTabLinkHelper     = NewTabLinkHelper(text = "Ciao raggazzi")
      val hmrcNewTabLinkHelper = new HmrcNewTabLinkHelper(new HmrcNewTabLink())
      val component            = hmrcNewTabLinkHelper(newTabLinkHelper)(italianMessages)

      val links = Jsoup.parse(component.toString).select("a")

      links                        should have size 1
      links.first.attr("href")   shouldBe ""
      links.first.attr("rel")    shouldBe "noopener noreferrer"
      links.first.attr("target") shouldBe "_blank"
      links.text()               shouldBe "Ciao raggazzi"
    }
  }
}
