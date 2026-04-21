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

package uk.gov.hmrc.hmrcfrontend.views
package components

import play.api.inject.guice.GuiceApplicationBuilder
import play.api.test.FakeRequest
import play.twirl.api.HtmlFormat
import uk.gov.hmrc.hmrcfrontend.views.html.components._

import scala.util.Try

class HmrcReportTechnicalIssueSpec extends TemplateUnitBaseSpec[ReportTechnicalIssue]("hmrcReportTechnicalIssue") {
  private val component = app.injector.instanceOf[HmrcReportTechnicalIssue]

  def render(templateParams: ReportTechnicalIssue): Try[HtmlFormat.Appendable] =
    Try(component(templateParams)(FakeRequest()))

  "HmrcReportTechnicalIssue" should {
    "not propagate use of service nav via query param when it's not enabled" in {
      val html = component(ReportTechnicalIssue())(FakeRequest())

      html.select("a.hmrc-report-technical-issue").attr("href") shouldNot endWith(
        "&useServiceNavigation"
      )
    }

    "propagate use of service nav via query param when it's enabled" in {
      val componentWhenEnabled =
        GuiceApplicationBuilder()
          .configure("play-frontend-hmrc.forceServiceNavigation" -> "true")
          .build()
          .injector
          .instanceOf[HmrcReportTechnicalIssue]

      val html = componentWhenEnabled(ReportTechnicalIssue())(FakeRequest())

      html.select("a.hmrc-report-technical-issue").attr("href") should endWith(
        "&useServiceNavigation"
      )
    }
  }
}
