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

package uk.gov.hmrc.play.language

import java.time.{LocalDate, LocalDateTime}
import java.util.Locale
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.i18n.{Lang, Messages, MessagesApi}

class LanguageUtilsSpec extends AnyFlatSpec with Matchers with GuiceOneAppPerSuite {

  implicit val messagesApi: MessagesApi = app.injector.instanceOf[MessagesApi]
  val messagesEnglish: Messages         = messagesApi.preferred(Seq(Lang(new Locale("en"))))
  val messagesWelsh: Messages           = messagesApi.preferred(Seq(Lang(new Locale("cy"))))
  val messagesSpanish: Messages         = messagesApi.preferred(Seq(Lang(new Locale("es"))))
  val languageUtils: LanguageUtils      = app.injector.instanceOf[LanguageUtils]

  val date        = LocalDate.of(2015, 1, 25)
  val oldDate     = LocalDate.of(1840, 12, 1)
  val dateAndTime = LocalDateTime.of(2015, 1, 25, 3, 45)

  "Method formatDate(date: LocalDate)" should "return correctly formatted date in both English and Welsh" in {
    languageUtils.Dates.formatDate(date)(messagesEnglish) shouldBe "25 January 2015"
    languageUtils.Dates.formatDate(date)(messagesWelsh)   shouldBe "25 Ionawr 2015"
  }

  "Method formatDate(date: LocalDate)" should "return correctly formatted date when no language defined" in {
    languageUtils.Dates.formatDate(date)(messagesEnglish) shouldBe "25 January 2015"
  }

  "Method formatDate(date: LocalDate)" should "return correctly formatted date for an old date" in {
    languageUtils.Dates.formatDate(oldDate)(messagesEnglish) shouldBe "1 December 1840"
  }

  "Method formatDate(date: Option[LocalDate], default: String)" should "return correctly formatted date in both English and Welsh" in {
    languageUtils.Dates.formatDate(Some(date), "n/a")(messagesEnglish) shouldBe "25 January 2015"
    languageUtils.Dates.formatDate(Some(date), "n/a")(messagesWelsh)   shouldBe "25 Ionawr 2015"
  }

  "Method formatDateAbbrMonth" should "return correctly formatted date in both English and Welsh" in {
    languageUtils.Dates.formatDateAbbrMonth(date)(messagesEnglish) shouldBe "25 Jan 2015"
    languageUtils.Dates.formatDateAbbrMonth(date)(messagesWelsh)   shouldBe "25 Ion 2015"
  }

  "Method shortDate" should "return correctly formatted date in both English and Welsh" in {
    languageUtils.Dates.shortDate(date)(messagesEnglish) shouldBe "2015-01-25"
    languageUtils.Dates.shortDate(date)(messagesWelsh)   shouldBe "2015-01-25"
  }

  "Method formatDate(date: Option[LocalDate], default: String)" should "return a default if None was passed as date" in {
    languageUtils.Dates.formatDate(None, "some_default")(messagesEnglish) shouldBe "some_default"
  }

  "Method formatEasyReadingTimeStamp" should "return correctly formatted date and time in both English and Welsh" in {
    languageUtils.Dates.formatEasyReadingTimestamp(Some(dateAndTime), "default value")(
      messagesEnglish
    ) shouldBe "3:45am, Sunday 25 January 2015"
    languageUtils.Dates.formatEasyReadingTimestamp(Some(dateAndTime), "default value")(
      messagesWelsh
    ) shouldBe "3:45yb, Dydd Sul 25 Ionawr 2015"
  }

  "Method formatEasyReadingTimeStamp" should "return a default value if None was passed as dateTime" in {
    languageUtils.Dates.formatEasyReadingTimestamp(None, "some_default")(messagesEnglish) shouldBe "some_default"
  }

  "Method formatDateRange" should "return correctly formatted date and time range in both English and Welsh" in {
    languageUtils.Dates.formatDateRange(date, date)(messagesEnglish) shouldBe "25 January 2015 to 25 January 2015"
    languageUtils.Dates.formatDateRange(date, date)(messagesWelsh)   shouldBe "25 Ionawr 2015 i 25 Ionawr 2015"
  }

  "Method formatDays" should "return correct singular/plural for day/days in both English and Welsh" in {
    languageUtils.Dates.formatDays(1)(messagesEnglish)   shouldBe "1 day"
    languageUtils.Dates.formatDays(5)(messagesEnglish)   shouldBe "5 days"
    languageUtils.Dates.formatDays(-15)(messagesEnglish) shouldBe "-15 days"

    languageUtils.Dates.formatDays(1)(messagesWelsh)   shouldBe "1 diwrnod"
    languageUtils.Dates.formatDays(5)(messagesWelsh)   shouldBe "5 diwrnod"
    languageUtils.Dates.formatDays(-15)(messagesWelsh) shouldBe "-15 diwrnod"
  }

}
