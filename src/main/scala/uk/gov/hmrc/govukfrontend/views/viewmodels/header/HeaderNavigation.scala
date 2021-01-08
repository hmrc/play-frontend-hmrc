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

package uk.gov.hmrc.govukfrontend.views.viewmodels.header

import uk.gov.hmrc.govukfrontend.views.viewmodels.JsonDefaultValueFormatter
import uk.gov.hmrc.govukfrontend.views.viewmodels.content.{Content, Empty, Text}
import play.api.libs.functional.syntax._
import play.api.libs.json._
import uk.gov.hmrc.govukfrontend.views.viewmodels.content.Content.writesContent

final case class HeaderNavigation(
  @deprecated("Use content", "Since play-frontend-govuk v0.48.0") text: Option[String] = None,
  href: Option[String]                                                                 = None,
  active: Boolean                                                                      = false,
  attributes: Map[String, String]                                                      = Map.empty,
  content: Content                                                                     = Empty
)

object HeaderNavigation extends JsonDefaultValueFormatter[HeaderNavigation] {

  override def defaultObject: HeaderNavigation = HeaderNavigation()

  override def defaultReads: Reads[HeaderNavigation] =
    (
      Reads.pure(None) and
        (__ \ "href").readNullable[String] and
        (__ \ "active").read[Boolean] and
        (__ \ "attributes").read[Map[String, String]] and
        Content.reads
    )(HeaderNavigation.apply _)

  override implicit def jsonWrites: OWrites[HeaderNavigation] = OWrites { hn =>
    val content = hn.content match {
      case Empty => hn.text.map(Text).getOrElse(Empty)
      case _ => hn.content
    }
    Json.obj(
      "href"       -> hn.href,
      "active"     -> hn.active,
      "attributes" -> hn.attributes
    ) ++ writesContent().writes(content)
  }
}
