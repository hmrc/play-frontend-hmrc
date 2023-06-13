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

class GovukPaginationSpec
    extends TemplateUnitBaseSpec[Pagination](
      govukComponentName = "govukPagination",
      fullyCompressedExamples = Seq(
        "pagination-default",
        "pagination-first-page",
        "pagination-last-page",
        "pagination-with-custom-accessible-labels-on-item-links",
        "pagination-with-custom-attributes",
        "pagination-with-custom-classes",
        "pagination-with-custom-link-and-item-text",
        "pagination-with-custom-navigation-landmark",
        "pagination-with-many-pages",
        "pagination-with-next-only",
        "pagination-with-prev-and-next-only",
        "pagination-with-prev-and-next-only-and-labels",
        "pagination-with-prev-and-next-only-and-very-long-labels",
        "pagination-with-prev-and-next-only-in-a-different-language",
        "pagination-with-previous-only"
      )
    )
    with MessagesSupport {

  private val component = app.injector.instanceOf[GovukPagination]

  override def render(templateParams: Pagination): Try[HtmlFormat.Appendable] = {
    implicit val request: RequestHeader = FakeRequest("GET", "/foo")

    Try(component(templateParams))
  }

  "GovukPagination" when {
    "implicit messages language is welsh" should {
      val welshMessages: Messages = messagesApi.preferred(Seq(Lang("cy")))

      "display welsh translation of Previous" in {
        val pagination = Pagination(
          previous = Some(
            PaginationLink(
              href = "#"
            )
          )
        )

        val content = component(pagination)(welshMessages)

        val title = content.select(".govuk-pagination__prev span")
        title.text() shouldBe "Blaenorol"
      }

      "display welsh translation of Next" in {
        val pagination = Pagination(
          next = Some(
            PaginationLink(
              href = "#"
            )
          )
        )

        val content = component(pagination)(welshMessages)

        val title = content.select(".govuk-pagination__next span")
        title.text() shouldBe "Nesaf"
      }
    }
  }
}
