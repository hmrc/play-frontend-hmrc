/*
 * Copyright 2024 HM Revenue & Customs
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

import uk.gov.hmrc.helpers.MessagesSupport
import play.twirl.api.HtmlFormat
import uk.gov.hmrc.govukfrontend.views.html.components._

import scala.util.Try

class GovukServiceNavigationSpec
    extends TemplateUnitBaseSpec[ServiceNavigation]("govukServiceNavigation")
    with MessagesSupport {
  private val component = app.injector.instanceOf[GovukServiceNavigation]

  def render(templateParams: ServiceNavigation): Try[HtmlFormat.Appendable] = Try(component(templateParams))

  "serviceNavigation" should {
    "render custom service name correctly" in {
      val params = ServiceNavigation(serviceName = Some("my-service"))
      val output = component(params).select(".govuk-service-navigation__text")

      output.first().text() shouldBe "my-service"
    }

    "render ServiceNavigationItems correctly in a nav element" in {
      val params = ServiceNavigation(
        navigation = Seq(
          ServiceNavigationItem(
            content = Text("Cupcakes")
          )
        )
      )
      val output = component(params)

      output.select(".govuk-service-navigation__text").first().text() shouldBe "Cupcakes"
      output.toString()                                                 should include("<nav")
    }

    "render ServiceNavigationItems with links correctly" in {
      val params = ServiceNavigation(
        serviceName = Some("cupcakes-service"),
        navigation = Seq(
          ServiceNavigationItem(
            content = Text("Cupcakes"),
            href = "#"
          )
        )
      )
      val output = component(params).select(".govuk-service-navigation__link")

      output.first().text()       shouldBe "Cupcakes"
      output.first().attr("href") shouldBe "#"
    }

    "render ServiceNavigation with slotted content with no nav element" in {
      val params = ServiceNavigation(
        serviceName = Some("cupcakes-service"),
        slots = Some(
          ServiceNavigationSlot(
            start = HtmlContent("<div class=\"my-custom-class\">Cupcakes are delicious!</div>")
          )
        )
      )
      val output = component(params)

      output.select(".my-custom-class").first().text() shouldBe "Cupcakes are delicious!"
      output.toString() shouldNot include("<nav")
    }

    "render ServiceNavigation with slotted content with a nav element" in {
      val params = ServiceNavigation(
        serviceName = Some("cupcakes-service"),
        slots = Some(
          ServiceNavigationSlot(
            navigationStart = HtmlContent("<div class=\"my-custom-class\">Cupcakes are still delicious!</div>")
          )
        )
      )
      val output = component(params)

      output.select(".my-custom-class").first().text() shouldBe "Cupcakes are still delicious!"
      output.toString()                                  should include("<nav")
    }
  }
}
