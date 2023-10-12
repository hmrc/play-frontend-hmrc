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

package uk.gov.hmrc.govukfrontend.views.viewmodels.date

import java.time.LocalDate
import scala.language.implicitConversions

case class MonthEntered(entered: String, value: Int)
case class DateEntered(day: Int, month: MonthEntered, year: Int)

case class GovUkDate(
  entered: DateEntered,
  value: LocalDate
)

object GovUkDate {

  def apply(day: Int, month: MonthEntered, year: Int): GovUkDate = GovUkDate(
    entered = DateEntered(day, month, year),
    value = LocalDate.of(year, month.value, day)
  )

  implicit def toLocalDate(govUkDate: GovUkDate): LocalDate = govUkDate.value

  implicit def fromLocalDate(localDate: LocalDate): GovUkDate = GovUkDate(
    entered = DateEntered(
      day = localDate.getDayOfMonth,
      month = MonthEntered(localDate.getMonthValue.toString, localDate.getMonthValue),
      year = localDate.getYear
    ),
    value = localDate
  )

}
