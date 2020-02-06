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

package uk.gov.hmrc.govukfrontend.views.components

import play.twirl.api.HtmlFormat
import uk.gov.hmrc.govukfrontend.views.TemplateUnitSpec
import uk.gov.hmrc.govukfrontend.views.html.components._
import scala.util.Try

class govukWarningTextSpec extends TemplateUnitSpec[WarningText]("govukWarningText") {

  /**
    * Calls the Twirl template with the given parameters and returns the resulting markup
    *
    * @param templateParams
    * @return [[Try[HtmlFormat.Appendable]]] containing the markup
    */
  override def render(templateParams: _root_.uk.gov.hmrc.govukfrontend.views.html.components.WarningText)
    : Try[HtmlFormat.Appendable] =
    Try(GovukWarningText(templateParams))
}
