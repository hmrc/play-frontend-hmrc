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

package uk.gov.hmrc.govukfrontend.views.viewmodels

import play.api.libs.json._
import play.twirl.api.Html

object CommonJsonFormats {
  val readsFormGroupClasses: Reads[String] =
    (__ \ "formGroup" \ "classes").read[String].orElse(Reads.pure(""))

  val writesFormGroupClasses: OWrites[String] = new OWrites[String] {
    override def writes(classes: String): JsObject =
      Json.obj("formGroup" -> Json.obj("classes" -> classes))
  }

  val readsConditionalHtml: Reads[Option[Html]] =
    (__ \ "conditional" \ "html").readNullable[String].map(_.map(Html(_))).orElse(Reads.pure(None))

  val writesConditionalHtml: OWrites[Option[Html]] = new OWrites[Option[Html]] {
    override def writes(o: Option[Html]): JsObject =
      o.map(o => Json.obj("conditional" -> Json.obj("html" -> o.body))).getOrElse(Json.obj())
  }
}
