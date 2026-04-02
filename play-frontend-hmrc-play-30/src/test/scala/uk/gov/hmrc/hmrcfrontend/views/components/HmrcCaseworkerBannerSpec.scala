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

package uk.gov.hmrc.hmrcfrontend.views
package components

import play.api.i18n.{Lang, Messages}
import play.twirl.api.HtmlFormat
import uk.gov.hmrc.govukfrontend.views.viewmodels.content.Text
import uk.gov.hmrc.hmrcfrontend.views.TemplateUnitBaseSpec
import uk.gov.hmrc.helpers.MessagesSupport
import uk.gov.hmrc.hmrcfrontend.views.html.components.HmrcCaseworkerBanner
import uk.gov.hmrc.hmrcfrontend.views.viewmodels.caseworkerbanner.CaseworkerBanner

import scala.util.Try

class HmrcCaseworkerBannerSpec
    extends TemplateUnitBaseSpec[CaseworkerBanner]("hmrcCaseworkerBanner")
    with MessagesSupport {

  private val component = app.injector.instanceOf[HmrcCaseworkerBanner]

  override def render(templateParams: CaseworkerBanner): Try[HtmlFormat.Appendable] = Try(component(templateParams))

  "hmrcCaseworkerBanner" should {

    "display Welsh translations if implicit messages language is Welsh" in {
      val welshMessages: Messages = messagesApi.preferred(Seq(Lang("cy")))
      val caseworkerBanner        = CaseworkerBanner(content = Text("Some Welsh Text"))

      val content = component(caseworkerBanner)(welshMessages)
      val title   = content.select(".govuk-notification-banner__title")

      title.text() shouldBe "Arweiniad Gweithiwr Achos"
    }

    "display English translations if messages language are English" in {
      val englishMessages: Messages = messagesApi.preferred(Seq(Lang("en")))
      val caseworkerBanner          = CaseworkerBanner(content = Text("Some English Text"))

      val content = component(caseworkerBanner)(englishMessages)
      val title   = content.select(".govuk-notification-banner__title")

      title.text() shouldBe "Caseworker guidance"
    }

    "display English translations when no explicit language passed in" in {
      val englishMessages: Messages = messagesApi.preferred(Seq())
      val caseworkerBanner          = CaseworkerBanner(content = Text("Some English Text"))

      val content = component(caseworkerBanner)(englishMessages)
      val title   = content.select(".govuk-notification-banner__title")

      title.text() shouldBe "Caseworker guidance"
    }
  }
}
