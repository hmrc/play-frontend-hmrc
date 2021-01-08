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

package uk.gov.hmrc.hmrcfrontend.views.viewmodels.header

import play.api.libs.functional.syntax._
import play.api.libs.json._
import uk.gov.hmrc.hmrcfrontend.views.viewmodels.JsonDefaultValueFormatter
import uk.gov.hmrc.hmrcfrontend.views.viewmodels.language.{Cy, En, Language, LanguageToggle}

import scala.collection.immutable.SortedMap

case class Header(
                   homepageUrl: String = "/",
                   assetsPath: String = "/assets/images",
                   productName: Option[String] = None,
                   serviceName: Option[String] = None,
                   serviceUrl: String = "",
                   navigation: Option[Seq[NavigationItem]] = None,
                   navigationClasses: String = "",
                   containerClasses: String = "govuk-width-container",
                   classes: String = "",
                   attributes: Map[String, String] = Map.empty,
                   language: Language = En,
                   displayHmrcBanner: Boolean = false,
                   signOutHref: Option[String] = None,
                   private val inputLanguageToggle: Option[LanguageToggle] = None
                 ) {

  // We use this method instead of using the input language toggle directly
  // as the version in `hmrc-frontend` is less flexible, and sets a default
  // href for `cy` and `en` as an empty string. This behaviour is mirrored
  // here both to pass the unit tests and to maintain parity with
  // `hmrc-frontend`.
  val languageToggle: Option[LanguageToggle] = inputLanguageToggle match {
    case Some(x) =>
      val defaults: SortedMap[Language, String] = SortedMap(En -> "", Cy -> "")
      Some(LanguageToggle(defaults ++ x.linkMap))
    case None => None
  }
}

object Header extends JsonDefaultValueFormatter[Header] {

  override def defaultObject: Header = Header()

  override def defaultReads: Reads[Header] =
    (
      (__ \ "homepageUrl").read[String] and
        (__ \ "assetsPath").read[String] and
        (__ \ "productName").readNullable[String] and
        (__ \ "serviceName").readNullable[String] and
        (__ \ "serviceUrl").read[String] and
        (__ \ "navigation").readNullable[Seq[NavigationItem]] and
        (__ \ "navigationClasses").read[String] and
        (__ \ "containerClasses").read[String] and
        (__ \ "classes").read[String] and
        (__ \ "attributes").read[Map[String, String]] and
        (__ \ "language").read[Language] and
        (__ \ "displayHmrcBanner").read[Boolean] and
        (__ \ "signOutHref").readNullable[String] and
        (__ \ "languageToggle").readNullable[LanguageToggle]
      ) (Header.apply _)

  override implicit def jsonWrites: OWrites[Header] =
    (
      (__ \ "homepageUrl").write[String] and
        (__ \ "assetsPath").write[String] and
        (__ \ "productName").writeNullable[String] and
        (__ \ "serviceName").writeNullable[String] and
        (__ \ "serviceUrl").write[String] and
        (__ \ "navigation").writeNullable[Seq[NavigationItem]] and
        (__ \ "navigationClasses").write[String] and
        (__ \ "containerClasses").write[String] and
        (__ \ "classes").write[String] and
        (__ \ "attributes").write[Map[String, String]] and
        (__ \ "language").write[Language] and
        (__ \ "displayHmrcBanner").write[Boolean] and
        (__ \ "signOutHref").writeNullable[String] and
        (__ \ "languageToggle").writeNullable[LanguageToggle]
      ) (header => unlift(Header.unapply)(header.copy(inputLanguageToggle = header.languageToggle)))
}
