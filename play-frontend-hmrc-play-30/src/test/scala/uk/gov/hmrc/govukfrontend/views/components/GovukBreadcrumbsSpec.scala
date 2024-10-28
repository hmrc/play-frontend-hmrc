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

class GovukBreadcrumbsSpec extends TemplateUnitBaseSpec[Breadcrumbs]("govukBreadcrumbs") with MessagesSupport {

  private val component = app.injector.instanceOf[GovukBreadcrumbs]

  def render(templateParams: Breadcrumbs): Try[HtmlFormat.Appendable] = {
    implicit val request: RequestHeader = FakeRequest("GET", "/foo")

    Try(component(templateParams))
  }

  "GovukBreadcrumb" should {
    "render the attributes in order" in {
      val params = Breadcrumbs(attributes =
        Map(
          "id"   -> "my-navigation",
          "role" -> "navigation"
        )
      )
      val output = component(params)

      output.body should include(
        "<nav aria-label=\"Breadcrumb\" class=\"govuk-breadcrumbs\" id=\"my-navigation\" role=\"navigation\">"
      )
    }

    "render the attributes in order when input is reversed" in {
      val params = Breadcrumbs(attributes =
        Map(
          "role" -> "navigation",
          "id"   -> "my-navigation"
        )
      )
      val output = component(params)

      output.body should include(
        "<nav aria-label=\"Breadcrumb\" class=\"govuk-breadcrumbs\" role=\"navigation\" id=\"my-navigation\">"
      )
    }

    "render expected aria label correctly when language is not specified" in {
      val output = component(Breadcrumbs())
      val nav    = output.select(".govuk-breadcrumbs").first()
      nav.attr("aria-label") shouldBe "Breadcrumb"
    }

    "render expected aria label correctly when language is Welsh" in {
      val welshMessages: Messages = messagesApi.preferred(Seq(Lang("cy")))
      val output                  = component(Breadcrumbs())(welshMessages)
      val nav                     = output.select(".govuk-breadcrumbs").first()
      nav.attr("aria-label") shouldBe "Briwsion bara"
    }
  }
}
