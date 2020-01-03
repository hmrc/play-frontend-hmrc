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

package uk.gov.hmrc.hmrcfrontend.views.viewmodels.language

import play.api.libs.json._

sealed trait Language extends Ordered[Language] {
  def code: String
  def name: String

  override def compare(that: Language): Int = {
    import uk.gov.hmrc.hmrcfrontend.views.viewmodels.language.Language.En
    (this, that) match {
      case (En, En)  => 0
      case (En, _)   => -1
      case (_, En)   => 1
      case (_, _)    => this.name compare that.name
    }
  }
}

object Language {

  object Cy extends Language {
    val code: String = "cy"
    val name: String = "Cymraeg"
  }

  object En extends Language {
    val code: String = "en"
    val name: String = "English"
  }

  implicit object LanguageFormat extends Format[Language] {
    override def reads(json: JsValue): JsResult[Language] = json match {
      case JsString("en") => JsSuccess(En)
      case JsString("cy") => JsSuccess(Cy)
      case JsString(_) => JsError("error.expected.validlanguage")
      case _ => JsError("error.expected.jsstring")
    }

    override def writes(o: Language): JsString = JsString(o match {
      case Cy => "cy"
      case En => "en"
    })
  }

}
