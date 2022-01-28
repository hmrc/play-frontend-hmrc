/*
 * Copyright 2022 HM Revenue & Customs
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
import uk.gov.hmrc.hmrcfrontend.views.html.components.HmrcPageHeading
import uk.gov.hmrc.hmrcfrontend.views.html.helpers.HmrcPageHeadingHelper
import uk.gov.hmrc.hmrcfrontend.views.viewmodels.pageheading.PageHeading

class hmrcPageHeadingHelperSpec extends AnyWordSpecLike with Matchers with MessagesSupport {

  override lazy val defaultLangs = new DefaultLangs(Seq(Lang("en"), Lang("cy")))

  "HmrcPageHeadingHelper" should {

    val englishMessages = messagesApi.preferred(Seq(Lang("en")))
    val welshMessages   = messagesApi.preferred(Seq(Lang("cy")))

    "render passed in text as header" in {
      val pageHeadingHelper     = PageHeading(text = "This is a heading!")
      val hmrcPageHeadingHelper = new HmrcPageHeadingHelper(new HmrcPageHeading())
      val component             = hmrcPageHeadingHelper(pageHeadingHelper)(englishMessages)

      val h1 = Jsoup.parse(component.toString).select("h1")
      h1.text() shouldBe "This is a heading!"
    }

    "render passed in text as header with caption section in english" in {
      val pageHeadingHelper     = PageHeading(text = "This is a heading!", section = Some("Details"))
      val hmrcPageHeadingHelper = new HmrcPageHeadingHelper(new HmrcPageHeading())
      val component             = hmrcPageHeadingHelper(pageHeadingHelper)(englishMessages)

      val document = Jsoup.parse(component.toString)
      val h1       = document.select("h1")
      h1.text() shouldBe "This is a heading!"

      val p = document.select("p")
      p.text() shouldBe "This section is Details"
    }

    "render passed in text as header with caption section in welsh" in {
      val pageHeadingHelper     = PageHeading(text = "Dyma bennawd!", section = Some("Manylion"))
      val hmrcPageHeadingHelper = new HmrcPageHeadingHelper(new HmrcPageHeading())
      val component             = hmrcPageHeadingHelper(pageHeadingHelper)(welshMessages)

      val document = Jsoup.parse(component.toString)
      val h1       = document.select("h1")
      h1.text() shouldBe "Dyma bennawd!"

      val p = document.select("p")
      p.text() shouldBe "Teitl yr adran hon yw Manylion"
    }
  }
}
