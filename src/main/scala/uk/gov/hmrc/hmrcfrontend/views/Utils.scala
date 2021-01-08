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

import play.api.templates.PlayMagic.toHtmlArgs
import play.twirl.api.Html
import html.components.implicits._
import java.net.URLEncoder

trait Utils {

  /**
    * Creates a space-separated list of CSS classes to be included in a template.
    *
    * @param firstClass
    * @param rest
    * @return
    */
  def toClasses(firstClass: String, rest: String*): String =
    rest.foldLeft(firstClass)((acc, curr) => if (curr.isEmpty) acc else s"$acc $curr")

  /**
    * Creates an HTML fragment with pairs of attribute=value.
    * The attributes HTML fragment is padded on the left with 1 space by default so when it is used in a template
    * it is nicely separated from the previous element.
    *
    * @param attributes
    * @param padCount
    * @return [[Html]]
    */
  def toAttributes(attributes: Map[String, String], padCount: Int = 1): Html = {
    val htmlArgs = toHtmlArgs(attributes.map { case (k, v) => Symbol(k) -> v })
    htmlArgs.padLeft(if (attributes.nonEmpty) padCount else 0)
  }

  object NonEmptyString {
    def unapply(s: String): Option[String] =
      if (s != null && s.nonEmpty) Some(s) else None
  }

  def urlEncode(s: String): String =
    URLEncoder.encode(s, "UTF-8").replace("+", "%20")
}

object Utils extends Utils
