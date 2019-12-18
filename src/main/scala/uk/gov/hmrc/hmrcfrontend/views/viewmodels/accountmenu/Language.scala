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

package uk.gov.hmrc.hmrcfrontend.views.viewmodels.accountmenu

import play.api.libs.json._

sealed trait Language

object Language {

  case class Cy() extends Language

  implicit object CyFormat extends Format[Cy] {
    def reads(json: JsValue): JsResult[Cy] = LanguageFormat.reads(json).map(_.asInstanceOf[Cy])
    def writes(o: Cy): JsString = LanguageFormat.writes(o)
  }

  case class En() extends Language

  implicit object EnFormat extends Format[En] {
    def reads(json: JsValue): JsResult[En] = LanguageFormat.reads(json).map(_.asInstanceOf[En])
    def writes(o: En): JsString = LanguageFormat.writes(o)
  }

  implicit object LanguageFormat extends Format[Language] {
    def reads(json: JsValue): JsResult[Language] = json match {
      case JsString("en") => JsSuccess(En())
      case JsString("cy") => JsSuccess(Cy())
      case JsString(_) => JsError("error.expected.validlanguage")
      case _ => JsError("error.expected.jsstring")
    }

    def writes(o: Language): JsString = JsString(o match {
      case En() => "en"
      case Cy() => "cy"
    })
  }

}
