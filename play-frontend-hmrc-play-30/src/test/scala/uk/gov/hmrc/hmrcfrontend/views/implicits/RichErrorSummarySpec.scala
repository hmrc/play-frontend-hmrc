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

package uk.gov.hmrc.hmrcfrontend.views.implicits

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.Application
import play.api.data.Forms.{mapping, text}
import play.api.data.{Form, FormError}
import play.api.i18n.{Lang, Messages}
import play.api.inject.guice.GuiceApplicationBuilder
import uk.gov.hmrc.govukfrontend.views.viewmodels.content.{HtmlContent, Text}
import uk.gov.hmrc.govukfrontend.views.viewmodels.errorsummary.ErrorSummary
import uk.gov.hmrc.helpers.MessagesSupport
import uk.gov.hmrc.hmrcfrontend.views.html.components.implicits._

class RichErrorSummarySpec extends AnyWordSpec with Matchers with MessagesSupport with GuiceOneAppPerSuite {
  override def fakeApplication(): Application =
    new GuiceApplicationBuilder().configure(Map("play.i18n.langs" -> List("en", "cy"))).build()

  case class DateData(day: String, month: String, year: String)

  case class PageData(date: DateData)

  val form: Form[PageData] = Form[PageData](
    mapping = mapping(
      "date" -> mapping(
        "day"   -> text,
        "month" -> text,
        "year"  -> text
      )(DateData.apply)(dd => Some((dd.day, dd.month, dd.year)))
    )(PageData.apply)(pd => Some(pd.date)),
    data = Map(
      "date.day"   -> "1",
      "date.month" -> "2",
      "date.year"  -> "2020"
    ),
    errors = Seq(
      FormError(key = "date.month", "The date must include a month"),
      FormError(key = "date", "The date must be in the past")
    ),
    value = None
  )

  "Given an ErrorSummary object, calling withFormErrorsAsText" should {
    "populate the title" in {
      val errorSummary = ErrorSummary().withFormErrorsAsText(form)
      errorSummary.title shouldBe Text("There is a problem")
    }

    "populate the title in Welsh" in {
      implicit val messages: Messages = messagesApi.preferred(Seq(Lang("cy")))

      val errorSummary = ErrorSummary().withFormErrorsAsText(form)
      errorSummary.title shouldBe Text("Mae problem wedi codi")
    }

    "preserve the existing title" in {
      val errorSummary = ErrorSummary(title = Text("Something went wrong")).withFormErrorsAsText(form)

      errorSummary.title shouldBe Text("Something went wrong")
    }

    "populate the errorList" in {
      val errorSummary = ErrorSummary().withFormErrorsAsText(form)

      errorSummary.errorList should have length 2
    }

    "populate the href attributes for the errors in the errorList" in {
      val errorSummary = ErrorSummary().withFormErrorsAsText(form)

      errorSummary.errorList.head.href shouldBe Some("#date.month")
      errorSummary.errorList(1).href   shouldBe Some("#date")
    }

    "populate the href attributes for the errors in the errorList with a mapping" in {
      val errorSummary = ErrorSummary().withFormErrorsAsText(form, mapping = Map("date" -> "date.day"))

      errorSummary.errorList.head.href shouldBe Some("#date.month")
      errorSummary.errorList(1).href   shouldBe Some("#date.day")
    }

    "populate the content attributes for the errors in the errorList" in {
      val errorSummary = ErrorSummary().withFormErrorsAsText(form)

      errorSummary.errorList.head.content shouldBe Text("The date must include a month")
      errorSummary.errorList(1).content   shouldBe Text("The date must be in the past")
    }
  }

  "Given an ErrorSummary object, calling withFormErrorsAsHtml" should {
    "populate the content attributes for the errors in the errorList using the Html constructor" in {
      val errorSummary = ErrorSummary().withFormErrorsAsHtml(form)

      errorSummary.errorList.head.content shouldBe HtmlContent("The date must include a month")
      errorSummary.errorList(1).content   shouldBe HtmlContent("The date must be in the past")
    }

    "populate the href attributes for the errors in the errorList with a mapping" in {
      val errorSummary = ErrorSummary().withFormErrorsAsHtml(form, mapping = Map("date" -> "date.day"))

      errorSummary.errorList.head.href shouldBe Some("#date.month")
      errorSummary.errorList(1).href   shouldBe Some("#date.day")
    }
  }
}
