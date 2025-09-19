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

package uk.gov.hmrc.hmrcfrontend.extensions
import uk.gov.hmrc.hmrcfrontend.views.html.helpers.HmrcServiceNavigationLanguageSelectHelper
import uk.gov.hmrc.govukfrontend.views.viewmodels.servicenavigation.{ServiceNavigation, ServiceNavigationSlot}

import  play.api.i18n.Messages
import javax.inject.{Inject, Singleton}

// and in the scala-2.13 source have equivalent rich implicit but which teams have to import
// not sure how to get it more automatically pulled into scope though... :shrug: could have
// an Extensions object that collected up all the things, then it's just (using Extensions)
// rather than importing implicits.

@Singleton
class GovukServiceNavigationExtensions @Inject() (
  hmrcServiceNavigationLanguageSelect: HmrcServiceNavigationLanguageSelectHelper
) {
  extension (serviceNavigation: ServiceNavigation)
    def withLanguageSelect()(using Messages): ServiceNavigation = {
      println(hmrcServiceNavigationLanguageSelect().toString())
      serviceNavigation.copy(
        slots = Some(
          serviceNavigation.slots
            .getOrElse(ServiceNavigationSlot())
            .copy(end = Some(hmrcServiceNavigationLanguageSelect().toString())) // toString is a bit hmm
        ),
        classes = s"${serviceNavigation.classes} hmrc-service-navigation--with-language-select"
      )
    }
}
