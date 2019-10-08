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

package uk.gov.hmrc.govukfrontend.views.viewmodels
package errormessage

import play.api.libs.functional.syntax._
import play.api.libs.json._
import uk.gov.hmrc.govukfrontend.views.viewmodels.content.Content

final case class ErrorMessageParams(
  id: Option[String]                 = None,
  classes: String                    = "",
  attributes: Map[String, String]    = Map.empty,
  visuallyHiddenText: Option[String] = Some("Error"),
  content: Content
)

object ErrorMessageParams {

  // Converts the ambiguously documented visuallyHiddenText parameter from govuk-frontend to the correct type.
  // The original govuk-frontend implementation will show any truthy value as the hidden text when visuallyHiddenText is set to it.
  // [[https://github.com/alphagov/govuk-frontend/blob/v3.2.0/src/govuk/components/error-message/template.test.js#L82]]
  // When visuallyHiddenText is a falsy value we want to hide the text
  // If it is not provided we default to "Error"
  val readsVisuallyHiddenText: Reads[Option[String]] = (
    (__ \ "visuallyHiddenText")
      .readWithDefault[JsValue](JsString("Error"))
      .map {
        case JsNull                => None
        case JsString("")          => None
        case JsString(text)        => Some(text)
        case JsFalse               => None
        case JsNumber(n) if n == 0 => None
        case x                     => Some(x.toString) // not intended behaviour but that is how govuk-frontend behaves
      }
  )

  val writesVisuallyHiddenText: OWrites[Option[String]] = new OWrites[Option[String]] {
    override def writes(o: Option[String]): JsObject = o match {
      case Some(text) => Json.obj("visuallyHiddenText" -> text)
      case None       => Json.obj("visuallyHiddenText" -> false)
    }
  }

  implicit val reads: Reads[ErrorMessageParams] = (
    (__ \ "id").readNullable[String] and
      (__ \ "classes").readWithDefault[String]("") and
      (__ \ "attributes").readWithDefault[Map[String, String]](Map.empty) and
      readsVisuallyHiddenText and
      Content.reads
  )(ErrorMessageParams.apply _)

  implicit val writes: OWrites[ErrorMessageParams] = (
    (__ \ "id").writeNullable[String] and
      (__ \ "classes").write[String] and
      (__ \ "attributes").write[Map[String, String]] and
      writesVisuallyHiddenText and
      Content.writes
  )(unlift(ErrorMessageParams.unapply))
}
