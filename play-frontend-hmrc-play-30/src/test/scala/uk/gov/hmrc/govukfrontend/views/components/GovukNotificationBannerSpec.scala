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

package uk.gov.hmrc.govukfrontend.views
package components

import play.api.i18n.{Lang, Messages}
import play.api.mvc.RequestHeader
import play.api.test.FakeRequest
import play.twirl.api.HtmlFormat
import uk.gov.hmrc.govukfrontend.views.html.components._
import uk.gov.hmrc.helpers.MessagesSupport

import scala.util.Try

class GovukNotificationBannerSpec
    extends TemplateUnitBaseSpec[NotificationBanner]("govukNotificationBanner")
    with MessagesSupport {

  private val component = app.injector.instanceOf[GovukNotificationBanner]

  def render(templateParams: NotificationBanner): Try[HtmlFormat.Appendable] = {
    implicit val request: RequestHeader = FakeRequest("GET", "/foo")

    Try(component(templateParams))
  }

  "GovukNotificationBanner" when {
    "implicit messages language is welsh" should {
      val welshMessages: Messages = messagesApi.preferred(Seq(Lang("cy")))

      "display welsh translation of Important" in {
        val notificationBanner = NotificationBanner()

        val content = component(notificationBanner)(welshMessages)

        val title = content.select(".govuk-notification-banner__title")
        title.text() shouldBe "Pwysig"
      }

      "display welsh translation of Success" in {
        val notificationBanner = NotificationBanner(bannerType = Some("success"))

        val content = component(notificationBanner)(welshMessages)

        val title = content.select(".govuk-notification-banner__title")
        title.text() shouldBe "Llwyddiant"
      }
    }
  }

}
