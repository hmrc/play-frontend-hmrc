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

import play.twirl.api.Html
import uk.gov.hmrc.govukfrontend.views.Aliases.PageLayout
import uk.gov.hmrc.hmrcfrontend.views.Aliases.Header

case class TemplateOverrides(
  additionalHeadBlock: Option[Html] = None,
  additionalScriptsBlock: Option[Html] = None,
  beforeContentBlock: Option[Html] = None,
  mainContentLayout: Option[Html => Html] = None,
  pageLayout: Option[PageLayout => Html] = None,
  headerContainerClasses: String = Header.defaultObject.containerClasses
)

object TemplateOverrides {
  val noMainContentLayout: Html => Html = identity
}
