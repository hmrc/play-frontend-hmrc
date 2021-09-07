/*
 * Copyright 2021 HM Revenue & Customs
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

package uk.gov.hmrc.govukfrontend.views
package components

import uk.gov.hmrc.govukfrontend.views.html.components._
import uk.gov.hmrc.helpers.views.JsoupHelpers

class GovukSummaryListSpec
    extends TemplateUnitSpec[SummaryList, GovukSummaryList]("govukSummaryList")
    with JsoupHelpers {

  "govukSummaryList" should {
    "handle lists where some rows have no actions" in {
      val rowWithNoAction =
        SummaryListRow(key = Key(HtmlContent("foo")), value = Value(HtmlContent("FOO")), actions = None)
      val rowWithAction   = SummaryListRow(
        key = Key(HtmlContent("bar")),
        value = Value(HtmlContent("BAR")),
        actions = Some(Actions(items = List(ActionItem(content = HtmlContent("link")))))
      )
      val html            = component(SummaryList(rowWithNoAction :: rowWithAction :: Nil))

      html.select(".govuk-summary-list__row") should have size 2
      html.select(".govuk-link")              should have size 1
    }
  }
}
