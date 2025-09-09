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

package uk.gov.hmrc.hmrcfrontend.views.viewmodels.hmrcstandardpage

import play.api.libs.functional.syntax._
import play.api.libs.json.{Reads, __}
import play.twirl.api.Html
import uk.gov.hmrc.govukfrontend.views.Aliases.PhaseBanner
import uk.gov.hmrc.govukfrontend.views.viewmodels.CommonJsonFormats.htmlReads
import uk.gov.hmrc.hmrcfrontend.views.Aliases.UserResearchBanner

case class Banners(
  displayHmrcBanner: Boolean = false,
  phaseBanner: Option[PhaseBanner] = None,
  userResearchBanner: Option[UserResearchBanner] = None,
  additionalBannersBlock: Option[Html] = None,
  useDeprecatedPositionForHmrcBanner: Boolean = false
)

object Banners {
  def defaultObject: Banners = Banners()

  implicit def jsonReads: Reads[Banners] =
    (
      (__ \ "displayHmrcBanner").readWithDefault[Boolean](defaultObject.displayHmrcBanner) and
        (__ \ "phaseBanner").readNullable[PhaseBanner] and
        (__ \ "userResearchBanner").readNullable[UserResearchBanner] and
        (__ \ "additionalBannersBlock").readNullable[Html] and
        (__ \ "useDeprecatedPositionForHmrcBanner").readWithDefault[Boolean](
          defaultObject.useDeprecatedPositionForHmrcBanner
        )
    )(Banners.apply _)
}
