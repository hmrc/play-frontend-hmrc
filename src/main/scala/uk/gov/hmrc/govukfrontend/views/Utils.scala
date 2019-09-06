/*
 * Copyright 2019 HM Revenue & Customs
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

import play.api.templates.PlayMagic.toHtmlArgs
import play.twirl.api.{Html, HtmlFormat}

trait Utils {
  implicit class RichHtml(html: Html) {
    def padLeft(padCount: Int = 1, padding: String = " "): Html = {
      val padStr = " " * (if (html.body.isEmpty) 0 else padCount)
      HtmlFormat.fill(collection.immutable.Seq(Html(padStr), html))
    }
  }

  implicit class RichString(s: String) {
    def toOption: Option[String] =
      if (s.nonEmpty) Some(s) else None
  }

  // FIXME: check if we need to escape classes like [[toHtmlArgs]] does for attribute values
  // https://cheatsheetseries.owasp.org/cheatsheets/Cross_Site_Scripting_Prevention_Cheat_Sheet.html
  def toClasses(firstClass: String, rest: String*): String =
    rest.foldLeft(firstClass)((acc, curr) => if (curr.isEmpty) acc else s"$acc $curr")

  def toAttributes(attributes: Map[String, String], padCount: Int = 1): Html = {
    val htmlArgs = toHtmlArgs(attributes.map { case (k, v) => Symbol(k) -> v })
    htmlArgs.padLeft(if (attributes.nonEmpty) padCount else 0)
  }

  object NonEmptyString {
    def unapply(s: String): Option[String] =
      if (s != null && s.trim.nonEmpty) Some(s) else None
  }
}

object Utils extends Utils
