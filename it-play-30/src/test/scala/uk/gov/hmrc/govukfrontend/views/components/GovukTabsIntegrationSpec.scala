/*
 * Copyright 2019 HM Revenue & Customs
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

package uk.gov.hmrc.govukfrontend.views.components

import play.twirl.api.HtmlFormat
import uk.gov.hmrc.govukfrontend.views.GovukFrontendDependency.govukFrontendVersion
import uk.gov.hmrc.govukfrontend.views.html.components._
import uk.gov.hmrc.govukfrontend.views.viewmodels.tabs.Generators._
import uk.gov.hmrc.helpers.MessagesSupport
import uk.gov.hmrc.support.TemplateIntegrationBaseSpec

import scala.util.Try

object GovukTabsIntegrationSpec
    extends TemplateIntegrationBaseSpec[Tabs](
      componentName = "govukTabs",
      seed = None
    )
    with MessagesSupport {

  protected val libraryName: String = "govuk"

  protected val libraryVersion: String = govukFrontendVersion

  private val component = app.injector.instanceOf[GovukTabs]

  override def render(tabs: Tabs): Try[HtmlFormat.Appendable] =
    Try(component(tabs))
}
