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

package uk.gov.hmrc.govukfrontend.views.components

import uk.gov.hmrc.govukfrontend.views.html.components._
import uk.gov.hmrc.govukfrontend.views.viewmodels.warningtext.Generators._
import uk.gov.hmrc.govukfrontend.views.viewmodels.warningtext.WarningText
import uk.gov.hmrc.helpers.MessagesSupport
import uk.gov.hmrc.support.TemplateIntegrationBaseSpec
import uk.gov.hmrc.govukfrontend.views.GovukFrontendDependency.govukFrontendVersion
import play.twirl.api.HtmlFormat

import scala.util.Try

object GovukWarningTextIntegrationSpec
    extends TemplateIntegrationBaseSpec[WarningText](
      componentName = "govukWarningText",
      seed = None
    )
    with MessagesSupport {
  protected val libraryName: String = "govuk"

  protected val libraryVersion: String = govukFrontendVersion

  private val component = app.injector.instanceOf[GovukWarningText]

  def render(warningText: WarningText): Try[HtmlFormat.Appendable] =
    Try(component(warningText))
}
