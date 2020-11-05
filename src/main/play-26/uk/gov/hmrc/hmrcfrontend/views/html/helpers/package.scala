/*
 * Copyright 2020 HM Revenue & Customs
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

package uk.gov.hmrc.hmrcfrontend.views.html

import play.api.Play
import uk.gov.hmrc.hmrcfrontend.config.AccessibilityStatementConfig
import uk.gov.hmrc.hmrcfrontend.views.config.{HmrcFooterItems => HmrcFooterItemsType}
import uk.gov.hmrc.hmrcfrontend.views.html.components.HmrcFooter

package object helpers {
  private lazy val accessibilityStatementConfig = Play.current.injector.instanceOf[AccessibilityStatementConfig]

  type HmrcFooterItems = HmrcFooterItemsType
  @deprecated(message = "Use DI", since = "Play 2.6")
  lazy val HmrcFooterItems = new HmrcFooterItems(accessibilityStatementConfig)

  type HmrcStandardFooter = hmrcStandardFooter
  @deprecated(message = "Use DI", since = "Play 2.6")
  lazy val HmrcStandardFooter = new hmrcStandardFooter(HmrcFooter, HmrcFooterItems)
}
