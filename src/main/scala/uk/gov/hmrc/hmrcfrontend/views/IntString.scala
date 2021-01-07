/*
 * Copyright 2021 HM Revenue & Customs
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

import play.api.libs.json.{Format, JsError, JsNumber, JsResult, JsString, JsSuccess, JsValue, Reads}

import scala.util.Try


case class IntString(int: Int) extends AnyVal {
  def str: String = int.toString
}

object IntString {

  def apply(intStr: String): Try[IntString] = Try(intStr.toInt).map(IntString(_))

  implicit object IntStringFormat extends Format[IntString] {
    def reads(json: JsValue): JsResult[IntString] = json match {
      case JsNumber(n) if n.isValidInt => JsSuccess(IntString(n.toInt))
      case JsNumber(_) => JsError("error.expected.validinteger")
      case JsString(s) => IntString(s).map(JsSuccess(_)).getOrElse(JsError("error.expected.integerstring"))
      case _ => JsError("error.expected.integerjsstringorjsnumber")
    }

    def writes(o: IntString): JsString = JsString(o.str)
  }

  implicit class IntStringReads(intStringReads: Reads[IntString]) {
    def int: Reads[Int] = intStringReads.map(_.int)
    def str: Reads[String] = intStringReads.map(_.str)
  }

  implicit class IntStringNullableReads(intStringNullableReads: Reads[Option[IntString]]) {
    def int: Reads[Option[Int]] = intStringNullableReads.map(_.map(_.int))
    def str: Reads[Option[String]] = intStringNullableReads.map(_.map(_.str))
  }

}
