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
      (BackLinkDefault.apply(), backLinkDefaultHtml),
      (ButtonDefault.apply(), buttonDefaultHtml),
      (DetailsDefault.apply(), detailsDefaultHtml),
//      (new errormessage.default(DateInput).apply(), errorMessageDefaultHtml) //FIXME update after upgrading to 3.2.0
      (ErrorSummaryDefault.apply(), errorSummaryDefaultHtml),
      (FieldsetAddressGroup.apply(), fieldsetAddressGroupHtml)
    )

  val backLinkDefaultHtml = """<a href="#" class="govuk-back-link">Back</a>"""

  val buttonDefaultHtml = """<button type="submit" class="govuk-button"> Save and continue </button>"""

  val detailsDefaultHtml =
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

  val errorMessageDefaultHtml =
    """<div class="govuk-form-group govuk-form-group--error">
    |  <fieldset class="govuk-fieldset" role="group" aria-describedby="passport-issued-hint passport-issued-error">
    |    <legend class="govuk-fieldset__legend govuk-fieldset__legend--xl">
    |      <h1 class="govuk-fieldset__heading">
    |        When was your passport issued?
    |      </h1>
    |    </legend>
    |    <span id="passport-issued-hint" class="govuk-hint">
    |      For example, 12 11 2007
    |    </span>
    |    <span id="passport-issued-error" class="govuk-error-message">
    |      <span class="govuk-visually-hidden">Error:</span> The date your passport was issued must be in the past
    |    </span>
    |    <div class="govuk-date-input" id="passport-issued">
    |      <div class="govuk-date-input__item">
    |        <div class="govuk-form-group">
    |          <label class="govuk-label govuk-date-input__label" for="passport-issued-day">
    |            Day
    |          </label>
    |          <input class="govuk-input govuk-date-input__input govuk-input--width-2 govuk-input--error" id="passport-issued-day" name="passport-issued-day" type="number" value="6" pattern="[0-9]*">
    |        </div>
    |      </div>
    |      <div class="govuk-date-input__item">
    |        <div class="govuk-form-group">
    |          <label class="govuk-label govuk-date-input__label" for="passport-issued-month">
    |            Month
    |          </label>
    |          <input class="govuk-input govuk-date-input__input govuk-input--width-2 govuk-input--error" id="passport-issued-month" name="passport-issued-month" type="number" value="3" pattern="[0-9]*">
    |        </div>
    |      </div>
    |      <div class="govuk-date-input__item">
    |        <div class="govuk-form-group">
    |          <label class="govuk-label govuk-date-input__label" for="passport-issued-year">
    |            Year
    |          </label>
    |          <input class="govuk-input govuk-date-input__input govuk-input--width-4 govuk-input--error" id="passport-issued-year" name="passport-issued-year" type="number" value="2076" pattern="[0-9]*">
    |        </div>
    |      </div>
    |    </div>
    |  </fieldset>
    |</div>""".stripMargin

  val errorSummaryDefaultHtml =
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

  val fieldsetAddressGroupHtml =
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
