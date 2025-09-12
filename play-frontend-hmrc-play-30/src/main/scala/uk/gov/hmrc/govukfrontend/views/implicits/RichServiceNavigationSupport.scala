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

package uk.gov.hmrc.govukfrontend.views.implicits

import play.api.Configuration
import play.api.i18n.Messages
import uk.gov.hmrc.govukfrontend.views.Aliases.ServiceNavigation
import uk.gov.hmrc.govukfrontend.views.viewmodels.servicenavigation.ServiceNavigationSlot
import uk.gov.hmrc.hmrcfrontend.config.LanguageConfig
import uk.gov.hmrc.hmrcfrontend.views.html.components.HmrcServiceNavigationLanguageSelect
import uk.gov.hmrc.hmrcfrontend.views.html.helpers.HmrcServiceNavigationLanguageSelectHelper

trait RichServiceNavigationSupport {

  implicit class RichServiceNavigation(serviceNavigation: ServiceNavigation)(implicit val messages: Messages) {

    def withLanguageToggle()(implicit conf: Configuration): ServiceNavigation = {
      val languageSelectHtml                   = new HmrcServiceNavigationLanguageSelectHelper(
        new HmrcServiceNavigationLanguageSelect(),
        new LanguageConfig(conf)
      ).apply().toString()
      val slots: Option[ServiceNavigationSlot] = serviceNavigation.slots
        .map(_.copy(end = Some(languageSelectHtml)))
        .orElse(Some(ServiceNavigationSlot(end = Some(languageSelectHtml))))
      serviceNavigation.copy(
        slots = slots,
        classes = s"${serviceNavigation.classes} hmrc-service-navigation--with-language-select"
      )
    }
  }
}
