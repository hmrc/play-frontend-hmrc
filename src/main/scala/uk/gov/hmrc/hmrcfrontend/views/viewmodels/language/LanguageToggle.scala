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

import scala.collection.immutable.SortedMap

case class LanguageToggle(linkMap: SortedMap[Language, String] = SortedMap.empty)

object LanguageToggle {

  def apply(linkMap: (Language, String)*): LanguageToggle = LanguageToggle(SortedMap(linkMap: _*))

  implicit object LanguageToggleFormat extends OFormat[LanguageToggle] {
    def reads(json: JsValue): JsResult[LanguageToggle] = json match {
      case JsObject(attributes) =>

        val linkMapMaybes: Iterable[JsResult[(Language, String)]] = attributes.map {
          case (k, v) =>
            val langMaybe = Json.fromJson[Language](JsString(k))
            (langMaybe, v) match {
              case (JsSuccess(lang, _), JsObject(langMap)) =>
                val hrefMaybe = langMap.get("href")
                hrefMaybe match {
                  case Some(JsString(href)) => JsSuccess((lang, href))
                  case Some(_) => JsError(JsPath \ k \ "href", "error.expected.jsstring")
                  case None => JsError(JsPath \ k, "error.expected.languagelinkmap")
                }
              case (JsSuccess(_, _), _) => JsError(JsPath \ k, "error.expected.jsobject")
              case (e@JsError(_), _)      => JsError.merge(JsError(JsPath, "error.expected.language"), e)
            }
        }

        val successes = linkMapMaybes.collect {case v: JsSuccess[(Language, String)] => v}
        val errors = linkMapMaybes.collect {case e: JsError => e}

        if (errors.nonEmpty)
          errors.foldLeft(JsError(JsPath, "error.expected.languagelinkmap"))((l, r) => JsError.merge(l, r))
        else {
          val linkMap: Array[(Language, String)] = successes.map(_.value).toArray
          JsSuccess(LanguageToggle(linkMap: _*))
        }

      case _ => JsError("error.expected.jsobject")
    }

    def writes(o: LanguageToggle): JsObject = JsObject(
      o.linkMap.map {
        case (language, link) =>
          val langJsStringRep = Json.toJson(language).as[JsString].value
          val hrefJObj = Json.obj("href" -> JsString(link))
          (langJsStringRep, hrefJObj)
      }
    )

  }
}
