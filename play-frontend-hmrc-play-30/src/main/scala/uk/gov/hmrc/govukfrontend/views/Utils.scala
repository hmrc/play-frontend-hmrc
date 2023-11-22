/*
 * Copyright 2023 HM Revenue & Customs
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

import play.twirl.api.{Html, HtmlFormat}
import uk.gov.hmrc.supportfrontend.views.UtilsSupport

import scala.collection.immutable

trait Utils extends UtilsSupport {

  private[views] def isNonEmptyOptionString(value: Option[String]): Boolean = value match {
    case Some(NonEmptyString(_)) => true
    case _                       => false
  }

  private[views] def calculateAssetPath(path: Option[String], file: String): String =
    path
      .map(p => s"$p/$file")
      .getOrElse(uk.gov.hmrc.hmrcfrontend.controllers.routes.Assets.at(s"govuk/$file").url)

  private[views] def govukPluralisedI18nAttributes(
    translationKey: String,
    pluralForms: Option[Map[String, String]]
  ): immutable.Iterable[Html] =
    pluralForms.getOrElse(Map.empty).map { case (k, v) =>
      Html(s"""data-i18n.$translationKey.${HtmlFormat.escape(k)}="${HtmlFormat.escape(v)}" """)
    }
}

object Utils extends Utils
