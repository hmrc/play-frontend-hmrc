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

package uk.gov.hmrc.hmrcfrontend.config

import play.api.Configuration

import javax.inject.Inject

case class RebrandConfig @Inject() (config: Configuration) {

  /*
  Rebrand can be enabled by feature flag below or by passing argument to the specific components.
  If feature flag is enabled, the passed arguments are ignored, so basically all pages will have rebrand enabled
  without possibility to disable rebrand on specific pages.
  If you need to enable or disable rebrand on specific pages, you will need to disable the feature flag
  and pass arguments to enable it on pages you want to have rebranded.
   */
  val useRebrand: Boolean = config.getOptional[Boolean]("play-frontend-hmrc.useRebrand").getOrElse(false)
}
