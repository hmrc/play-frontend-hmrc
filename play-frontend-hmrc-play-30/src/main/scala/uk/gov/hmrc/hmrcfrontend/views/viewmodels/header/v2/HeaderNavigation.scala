/*
 * Copyright 2025 HM Revenue & Customs
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

package uk.gov.hmrc.hmrcfrontend.views.viewmodels.header.v2

import play.api.libs.functional.syntax._
import play.api.libs.json.{Reads, __}
import uk.gov.hmrc.hmrcfrontend.views.viewmodels.header.NavigationItem

case class HeaderNavigation(
  navigation: Option[Seq[NavigationItem]] = None,
  navigationClasses: String = "",
  navigationLabel: Option[String] = None
)

object HeaderNavigation {

  def defaultObject: HeaderNavigation = HeaderNavigation()

  implicit def jsonReads: Reads[HeaderNavigation] =
    (
      (__ \ "navigation").readNullable[Seq[NavigationItem]] and
        (__ \ "navigationClasses").readWithDefault[String](defaultObject.navigationClasses) and
        (__ \ "navigationLabel").readNullable[String]
    )(HeaderNavigation.apply _)
}
