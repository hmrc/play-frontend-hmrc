/*
 * Copyright 2020 HM Revenue & Customs
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

package uk.gov.hmrc.govukfrontend.views.viewmodels.errorsummary

import play.api.libs.functional.syntax._
import play.api.libs.json._
import uk.gov.hmrc.govukfrontend.views.viewmodels.JsonDefaultValueFormatter
import uk.gov.hmrc.govukfrontend.views.viewmodels.content.{Content, Empty}

case class ErrorSummary(
  errorList: Seq[ErrorLink]       = Nil,
  classes: String                 = "",
  attributes: Map[String, String] = Map.empty,
  title: Content                  = Empty,
  description: Content            = Empty
)

object ErrorSummary extends JsonDefaultValueFormatter[ErrorSummary] {

  override def defaultObject: ErrorSummary = ErrorSummary()

  override def defaultReads: Reads[ErrorSummary] =
    (
      (__ \ "errorList").read[Seq[ErrorLink]] and
        (__ \ "classes").read[String] and
        (__ \ "attributes").read[Map[String, String]] and
        Content.readsHtmlOrText((__ \ "titleHtml"), (__ \ "titleText")) and
        Content.readsHtmlOrText((__ \ "descriptionHtml"), (__ \ "descriptionText"))
    )(ErrorSummary.apply _)

  override implicit def jsonWrites: OWrites[ErrorSummary] =
    (
      (__ \ "errorList").write[Seq[ErrorLink]] and
        (__ \ "classes").write[String] and
        (__ \ "attributes").write[Map[String, String]] and
        Content.writesContent("titleHtml", "titleText") and
        Content.writesContent("descriptionHtml", "descriptionText")
    )(unlift(ErrorSummary.unapply))

}
