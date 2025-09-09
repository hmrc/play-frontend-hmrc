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

case class LogoOverrides(
  useTudorCrown: Option[Boolean] = None,
  rebrand: Option[Boolean] = None
)

object LogoOverrides {

  def defaultObject: LogoOverrides = LogoOverrides()

  implicit def jsonReads: Reads[LogoOverrides] =
    (
      (__ \ "useTudorCrown").readNullable[Boolean] and
        (__ \ "rebrand").readNullable[Boolean]
    )(LogoOverrides.apply _)
}
