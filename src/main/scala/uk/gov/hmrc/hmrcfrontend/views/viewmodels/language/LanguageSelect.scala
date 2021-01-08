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

package uk.gov.hmrc.hmrcfrontend.views.viewmodels.language

import play.api.libs.json._

import uk.gov.hmrc.hmrcfrontend.views.viewmodels.language.Language.LanguageFormat

case class LanguageSelect(language: Language, private val languageLinks: (Language, String)*) {

  // We use this method instead of taking in a language toggle directly both
  // due to the API currently offered by `hmrc-frontend` for the language
  // select component, but also because the version in `hmrc-frontend` is less
  // flexible as it sets a default href for `cy` and `en` as an empty string.
  // This behaviour is mirrored here both to pass the unit tests and to
  // maintain parity with `hmrc-frontend`.
  val languageToggle: LanguageToggle = {
    val linkMapWithDefaults: Map[Language, String] = Map(En -> "", Cy -> "") ++ languageLinks
    LanguageToggle(linkMapWithDefaults.toArray: _*)
  }

}

object LanguageSelect {

  implicit object LanguageSelectOFormat extends OFormat[LanguageSelect] {
    override def reads(json: JsValue): JsResult[LanguageSelect] = json match {
      case JsObject(attributes) =>
        val langResult: JsResult[Language] = {
          attributes
            .get("language")
            .map(Json.fromJson[Language])
            .getOrElse(JsError(JsPath, "error.expected.language"))
        }

        val langToggleFields = JsObject(attributes.filterNot(_._1 == "language"))
        val langToggleResult: JsResult[LanguageToggle] = Json.fromJson[LanguageToggle](langToggleFields)

        val e = JsError(JsPath, "error.expected.languageselect")
        (langResult, langToggleResult) match {
          case (JsSuccess(lang, _), JsSuccess(langToggle, _)) => JsSuccess(LanguageSelect(lang, langToggle.linkMap.toArray: _*))
          case (e2@JsError(_), e3@JsError(_)) => Iterable(e2, e3).foldLeft(e)((l, r) => JsError.merge(l, r))
          case (e2@JsError(_), _) => JsError.merge(e, e2)
          case (_, e3@JsError(_)) => JsError.merge(e, e3)
        }

      case _ => JsError("error.expected.jsobject")
    }

    override def writes(o: LanguageSelect): JsObject = JsObject {
      val langEntry: (String, JsString) = "language" -> Json.toJson(o.language)(LanguageFormat).as[JsString]
      val linkMap = o.languageToggle.linkMap.map {
        case (k, v) =>
          val lang: String = Json.toJson(k)(LanguageFormat).as[JsString].value
          lang -> JsObject(Map("href" -> JsString(v)))
      }
      linkMap + langEntry
    }
  }

}
