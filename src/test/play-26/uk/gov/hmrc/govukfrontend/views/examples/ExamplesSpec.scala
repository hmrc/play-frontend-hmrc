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

package uk.gov.hmrc.govukfrontend.views.examples

import org.scalatest.prop.TableDrivenPropertyChecks
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.govukfrontend.views.JsoupHelpers
import uk.gov.hmrc.govukfrontend.views.html.components.Input
import uk.gov.hmrc.govukfrontend.views.html.examples._

class ExamplesSpec extends WordSpec with Matchers with JsoupHelpers with TableDrivenPropertyChecks {

  "Examples" should {
    "render as expected" in {
      forAll(testData) { (example, expected) =>
        parseAndCompressHtml(example.body) shouldBe parseAndCompressHtml(expected)
      }
    }
  }

  lazy val testData =
    Table(
      ("Example", "Expected"),
      (new backLink().apply(), backLinkHtml),
      (new button().apply(), buttonHtml),
      (new details().apply(), detailsHtml),
      (new errorMessage().apply(), errorMessageHtml),
      (new errorSummary().apply(), errorSummaryHtml),
      (new fieldset(Input).apply(), fieldsetHtml)
    )

  val backLinkHtml = """<a href="#" class="govuk-back-link">Back</a>"""

  val buttonHtml = """<button type="submit" class="govuk-button"> Save and continue </button>"""

  val detailsHtml =
    """<details class="govuk-details">
                      |  <summary class="govuk-details__summary">
                      |    <span class="govuk-details__summary-text">
                      |      Help with nationality
                      |    </span>
                      |  </summary>
                      |  <div class="govuk-details__text">
                      |    We need to know your nationality so we can work out which elections you’re entitled to vote in. If you cannot provide your nationality, you’ll have to send copies of identity documents through the post.
                      |  </div>
                      |</details>""".stripMargin

  val errorMessageHtml = """<span class="govuk-error-message">
                           |  <span class="govuk-visually-hidden">Gwall:</span> Rhowch eich enw llawn
                           |</span>""".stripMargin

  val errorSummaryHtml =
    """<div class="govuk-error-summary" aria-labelledby="error-summary-title" role="alert" tabindex="-1" data-module="error-summary">
                           |  <h2 class="govuk-error-summary__title" id="error-summary-title">
                           |    There is a problem
                           |  </h2>
                           |  <div class="govuk-error-summary__body">
                           |    <ul class="govuk-list govuk-error-summary__list">
                           |      <li>
                           |        <a href="#passport-issued-error">The date your passport was issued must be in the past</a>
                           |      </li>
                           |      <li>
                           |        <a href="#postcode-error">Enter a postcode, like AA1 1AA</a>
                           |      </li>
                           |    </ul>
                           |  </div>
                           |</div>""".stripMargin

  val fieldsetHtml =
    """<fieldset class="govuk-fieldset">
                       |  <legend class="govuk-fieldset__legend govuk-fieldset__legend--xl">
                       |    <h1 class="govuk-fieldset__heading">
                       |      What is your address?
                       |    </h1>
                       |  </legend>
                       |
                       |  <div class="govuk-form-group">
                       |    <label class="govuk-label" for="address-line-1">
                       |      Building and street <span class="govuk-visually-hidden">line 1 of 2</span>
                       |    </label>
                       |    <input class="govuk-input" id="address-line-1" name="address-line-1" type="text">
                       |  </div>
                       |
                       |  <div class="govuk-form-group">
                       |    <label class="govuk-label" for="address-line-2">
                       |      <span class="govuk-visually-hidden">Building and street line 2 of 2</span>
                       |    </label>
                       |    <input class="govuk-input" id="address-line-2" name="address-line-2" type="text">
                       |  </div>
                       |
                       |  <div class="govuk-form-group">
                       |    <label class="govuk-label" for="address-town">
                       |      Town or city
                       |    </label>
                       |    <input class="govuk-input govuk-!-width-two-thirds" id="address-town" name="address-town" type="text">
                       |  </div>
                       |
                       |  <div class="govuk-form-group">
                       |    <label class="govuk-label" for="address-county">
                       |      County
                       |    </label>
                       |    <input class="govuk-input govuk-!-width-two-thirds" id="address-county" name="address-county" type="text">
                       |  </div>
                       |
                       |  <div class="govuk-form-group">
                       |    <label class="govuk-label" for="address-postcode">
                       |      Postcode
                       |    </label>
                       |    <input class="govuk-input govuk-input--width-10" id="address-postcode" name="address-postcode" type="text">
                       |  </div>
                       |
                       |</fieldset>""".stripMargin

}
