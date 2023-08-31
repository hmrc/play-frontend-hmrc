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

package uk.gov.hmrc.govukfrontend.views.viewmodels.dateinput

import play.api.data.Forms.text
import play.api.data.validation._
import play.api.data.{Forms, Mapping}
import play.api.i18n.MessagesApi
import uk.gov.hmrc.govukfrontend.views.viewmodels.dateinput.DateValidationSupport.toLocalDate
import uk.gov.hmrc.hmrcfrontend.views.viewmodels.language.{Cy, En}

import java.time.Month._
import java.time.{LocalDate, Month}
import scala.util.{Failure, Success, Try}

object DateValidationSupport {

  def yearBefore(beforeDate: LocalDate): Constraint[GovUKDate] = Constraint("year.before") { govukDate =>
    val valid: Option[Boolean] = govukDate.localDate().map { localDate =>
      localDate.isBefore(beforeDate)
    }

    valid match {
      case Some(passed) =>
        if(passed)
          Valid
        else
          Invalid(Seq(ValidationError(s"Date is not before ${beforeDate.getYear}")))
      case _ => Invalid(Seq(ValidationError(s"Invalid date!")))
    }
  }

  private def validDate()(implicit messagesApi: MessagesApi): Constraint[(String, String, String)] =
    Constraint("constraints.date") { dateData: (String, String, String) =>
      toLocalDate(dateData) match {
        case Some(_) => Valid
        case _       => Invalid(Seq(ValidationError("Not a valid date!")))
      }
    }

  def toLocalDate(dateData: (String, String, String))(implicit messagesApi: MessagesApi): Option[LocalDate] = {
    val day   = dateData._1
    val month = dateData._2
    val year  = dateData._3

    Try(month.toInt) match {
      case Success(_) =>
        Try(LocalDate.of(year.toInt, month.toInt, day.toInt)) match {
          case Success(date) => Some(date)
          case Failure(_)    => None
        }
      case Failure(_) =>
        // TODO: we fetch english and welsh month values to assert against, this is a just an example of fetching and using the messages values
        val messagesWelsh                 = messagesApi.messages(Cy.code)
        val messagesEnglish               = messagesApi.messages(En.code)
        val enFebruaryLong: String        = messagesEnglish("february").toLowerCase
        val enFebruaryAbbreviated: String = messagesEnglish("february.abbrv").toLowerCase
        val cyFebruaryLong: String        = messagesWelsh("february").toLowerCase
        val cyFebruaryAbbreviated: String = messagesWelsh("february.abbrv").toLowerCase
        val monthEnum: Option[Month]      = month.toLowerCase match {
          case "jan" | "january"   => Some(JANUARY)
          case d
              if d == enFebruaryLong | d == enFebruaryAbbreviated | d == cyFebruaryLong | d == cyFebruaryAbbreviated =>
            Some(FEBRUARY)
          case "mar" | "march"     => Some(MARCH)
          case "apr" | "april"     => Some(APRIL)
          case "may"               => Some(MAY)
          case "jun" | "june"      => Some(JUNE)
          case "jul" | "july"      => Some(JULY)
          case "aug" | "august"    => Some(AUGUST)
          case "sep" | "september" => Some(SEPTEMBER)
          case "oct" | "october"   => Some(OCTOBER)
          case "nov" | "november"  => Some(NOVEMBER)
          case "dec" | "december"  => Some(DECEMBER)
          case _                   => None
        }

        monthEnum match {
          case Some(m) =>
            Try(LocalDate.of(year.toInt, m, day.toInt)) match {
              case Success(date) => Some(date)
              case Failure(_)    => None
            }
          case _       => None
        }
    }
  }

  def govukDate()(implicit messagesApi: MessagesApi): Mapping[GovUKDate] =
    Forms
      .tuple(
        "day"   -> text,
        "month" -> text,
        "year"  -> text
      )
      .verifying(validDate)
      .transform(
        validatedDateTuple => GovUKDate(validatedDateTuple._1, validatedDateTuple._2, validatedDateTuple._3),
        dateData => (dateData.day, dateData.month, dateData.year)
      )
}

abstract class DateData(day: String, month: String, year: String)(implicit messagesApi: MessagesApi) {
  def localDate(): Option[LocalDate] =
    toLocalDate(day, month, year)
}

case class GovUKDate(day: String, month: String, year: String)(implicit messagesApi: MessagesApi)
    extends DateData(day: String, month: String, year: String)
