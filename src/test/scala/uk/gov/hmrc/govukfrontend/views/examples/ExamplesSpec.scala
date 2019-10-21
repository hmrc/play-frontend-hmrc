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
import uk.gov.hmrc.govukfrontend.views.PreProcessor
import uk.gov.hmrc.govukfrontend.views.TemplateDiff._
import uk.gov.hmrc.govukfrontend.views.html.examples._

class ExamplesSpec extends WordSpec with Matchers with PreProcessor with TableDrivenPropertyChecks {

  "Examples" should {
    "render as the nunjucks examples" in {

      forAll(testData) { (exampleName, example, expected) =>
        {
          val twirlHtml    = preProcess(example.body)
          val expectedHtml = preProcess(expected)

          assert(
            twirlHtml == expectedHtml, {
              val diff = templateDiffPath(
                twirlOutputHtml    = twirlHtml,
                nunJucksOutputHtml = expectedHtml,
                diffFilePrefix     = Some(exampleName.replace("/", "_")))
              s"Examples don't match, check the diff file: file://$diff"
            }
          )
        }
      }
    }
  }

  lazy val testData =
    Table(
      ("Example name", "Example", "Expected"),
      ("backlink/default", BackLinkDefault(), backLinkDefaultHtml),
      ("button/default", ButtonDefault(), buttonDefaultHtml),
      ("button/disabled", ButtonDisabled(), buttonDisabledHtml),
      ("button/preventDoubleClick", ButtonPreventDoubleClick(), buttonPreventDoubleClickHtml),
      ("button/secondary", ButtonSecondary(), buttonSecondaryHtml),
      ("button/secondaryCombo", ButtonSecondaryCombo(), buttonSecondaryComboHtml),
      ("button/start", ButtonStart(), buttonStartHtml),
      ("button/warning", ButtonWarning(), buttonWarningHtml),
      ("details/default", DetailsDefault(), detailsDefaultHtml),
      ("errormessage/default", ErrorMessageDefault(), errorMessageDefaultHtml),
      ("errormessage/label", ErrorMessageLabel(), errorMessageLabelHtml),
      ("errormessage/legend", ErrorMessageLegend(), errorMessageLegendHtml),
      ("errorsummary/default", ErrorSummaryDefault(), errorSummaryDefaultHtml),
      ("errorsummary/fullPageExample", ErrorSummaryFullPageExample(), errorSummaryFullPageExampleHtml),
      ("errorsummary/linking", ErrorSummaryLinking(), errorSummaryLinkingHtml),
      (
        "errorsummary/linkingCheckboxesRadios",
        ErrorSummaryLinkingCheckboxesRadios(),
        errorSummarylinkingCheckboxesRadiosHtml),
      (
        "errorsummary/linkingMultipleFields",
        ErrorSummaryLinkingMultipleFields(),
        errorSummaryLinkingMultipleFieldsHtml),
      ("fieldset/addressGroup", FieldsetAddressGroup(), fieldsetAddressGroupHtml),
      ("fieldset/default", FieldsetDefault(), fieldsetDefaultHtml),
      ("footer/default", FooterDefault(), footerDefaultHtml),
      ("footer/withNavigation", FooterWithNavigation(), footerWithNavigationHtml),
      ("footer/withMeta", FooterWithMeta(), footerWithMetaHtml),
      ("footer/full", FooterFull(), footerFullHtml),
      ("header/default", HeaderDefault(), headerDefaultHtml),
      ("header/withServiceName", HeaderWithServiceName(), headerWithServiceNameHtml),
      (
        "header/withServiceNameAndNavigation",
        HeaderWithServiceNameAndNavigation(),
        headerWithServiceNameAndNavigationHtml),
      ("panel/default", PanelDefault(), panelDefaultHtml),
      ("textinput/default", InputDefault(), inputDefaultHtml),
      ("textinput/error", InputError(), inputErrorHtml),
      ("textinput/inputFixedWidth", InputFixedWidth(), inputFixedWidthHtml),
      ("textinput/inputFluidWidth", InputFluidWidth(), inputFluidWidthHtml),
      ("textinput/inputHintText", InputHintText(), inputHintTextHtml),
      ("textarea/default", TextareaDefault(), textareaDefaultHtml),
      ("textarea/error", TextareaError(), textareaErrorHtml),
      ("textarea/specifyingRows", TextareaSpecifyingRows(), textareaSpecifyingRowsHtml),
      ("radios/conditionalReveal", RadiosConditionalReveal(), radiosConditionalRevealHtml),
      ("radios/default", RadiosDefault(), radiosDefaultHtml),
      ("radios/stacked", RadiosStacked(), radiosStackedHtml),
      ("radios/hint", RadiosHint(), radiosHintHtml),
      ("radios/divider", RadiosDivider(), radiosDividerHtml),
      ("radios/small", RadiosSmall(), radiosSmallHtml),
      ("radios/error", RadiosError(), radiosErrorHtml),
      ("summarylist/default", SummaryListDefault(), summaryListDefaultHtml),
      ("summarylist/withoutActions", SummaryListWithoutActions(), summaryListWithoutActionsHtml),
      ("summarylist/withoutBorders", SummaryListWithoutBorders(), summaryListWithoutBordersHtml)
    )

  val backLinkDefaultHtml = """<a href="#" class="govuk-back-link">Back</a>"""

  val buttonDefaultHtml =
    """<button class="govuk-button" data-module="govuk-button">
      |  Save and continue
      |</button>""".stripMargin

  val buttonStartHtml =
    """<a href="#" role="button" draggable="false" class="govuk-button govuk-button--start" data-module="govuk-button">
      |  Start now
      |  <svg class="govuk-button__start-icon" xmlns="http://www.w3.org/2000/svg" width="17.5" height="19" viewBox="0 0 33 40" role="presentation" focusable="false">
      |    <path fill="currentColor" d="M0 0h13l20 20-20 20H0l20-20z" />
      |  </svg>
      |</a>""".stripMargin

  val buttonSecondaryHtml =
    """<button class="govuk-button govuk-button--secondary" data-module="govuk-button">
      |  Find address
      |</button>""".stripMargin

  val buttonSecondaryComboHtml =
    """<button class="govuk-button govuk-!-margin-right-1" data-module="govuk-button">
      |  Save and continue
      |</button>
      |
      |<button class="govuk-button govuk-button--secondary" data-module="govuk-button">
      |  Save as draft
      |</button>""".stripMargin

  val buttonWarningHtml =
    """<button class="govuk-button govuk-button--warning" data-module="govuk-button">
      |  Delete account
      |</button>""".stripMargin

  val buttonDisabledHtml =
    """<button disabled="disabled" aria-disabled="true" class="govuk-button govuk-button--disabled" data-module="govuk-button">
      |  Disabled button
      |</button>""".stripMargin

  val buttonPreventDoubleClickHtml =
    """<button data-prevent-double-click="true" class="govuk-button" data-module="govuk-button">
      |  Confirm and send
      |</button>""".stripMargin

  val detailsDefaultHtml =
    """<details class="govuk-details" data-module="govuk-details">
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

  val errorMessageLegendHtml =
    """<div class="govuk-form-group govuk-form-group--error">
      |  <fieldset class="govuk-fieldset" aria-describedby="nationality-hint nationality-error">
      |    <legend class="govuk-fieldset__legend govuk-fieldset__legend--xl">
      |      <h1 class="govuk-fieldset__heading">
      |        What is your nationality?
      |      </h1>
      |    </legend>
      |    <span id="nationality-hint" class="govuk-hint">
      |      If you have dual nationality, select all options that are relevant to you.
      |    </span>
      |    <span id="nationality-error" class="govuk-error-message">
      |      <span class="govuk-visually-hidden">Error:</span> Select if you are British, Irish or a citizen of a different country
      |    </span>
      |    <div class="govuk-checkboxes">
      |      <div class="govuk-checkboxes__item">
      |        <input class="govuk-checkboxes__input" id="nationality" name="nationality" type="checkbox" value="british">
      |        <label class="govuk-label govuk-checkboxes__label" for="nationality">
      |          British
      |        </label>
      |      </div>
      |      <div class="govuk-checkboxes__item">
      |        <input class="govuk-checkboxes__input" id="nationality-2" name="nationality" type="checkbox" value="irish">
      |        <label class="govuk-label govuk-checkboxes__label" for="nationality-2">
      |          Irish
      |        </label>
      |      </div>
      |      <div class="govuk-checkboxes__item">
      |        <input class="govuk-checkboxes__input" id="nationality-3" name="nationality" type="checkbox" value="other">
      |        <label class="govuk-label govuk-checkboxes__label" for="nationality-3">
      |          Citizen of another country
      |        </label>
      |      </div>
      |    </div>
      |  </fieldset>
      |</div>""".stripMargin

  val errorMessageLabelHtml =
    """<div class="govuk-form-group govuk-form-group--error">
      |  <label class="govuk-label" for="national-insurance-number">
      |    National Insurance number
      |  </label>
      |  <span id="national-insurance-number-hint" class="govuk-hint">
      |    It’s on your National Insurance card, benefit letter, payslip or P60. For example, ‘QQ 12 34 56 C’.
      |  </span>
      |  <span id="national-insurance-number-error" class="govuk-error-message">
      |    <span class="govuk-visually-hidden">Error:</span> Enter a National Insurance number in the correct format
      |  </span>
      |  <input class="govuk-input govuk-input--error" id="national-insurance-number" name="national-insurance-number" type="text" aria-describedby="national-insurance-number-hint national-insurance-number-error">
      |</div>""".stripMargin

  val errorSummaryDefaultHtml =
    """<div class="govuk-error-summary" aria-labelledby="error-summary-title" role="alert" tabindex="-1" data-module="govuk-error-summary">
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

  val errorSummaryLinkingHtml =
    """<div class="govuk-error-summary" aria-labelledby="error-summary-title" role="alert" tabindex="-1" data-module="govuk-error-summary">
      |  <h2 class="govuk-error-summary__title" id="error-summary-title">
      |    There is a problem
      |  </h2>
      |  <div class="govuk-error-summary__body">
      |    <ul class="govuk-list govuk-error-summary__list">
      |      <li>
      |        <a href="#name">Enter your full name</a>
      |      </li>
      |    </ul>
      |  </div>
      |</div>
      |
      |<h1 class="govuk-heading-xl">Your details</h1>
      |
      |<div class="govuk-form-group govuk-form-group--error">
      |  <label class="govuk-label" for="name">
      |    Full name
      |  </label>
      |  <span id="name-error" class="govuk-error-message">
      |    <span class="govuk-visually-hidden">Error:</span> Enter your full name
      |  </span>
      |  <input class="govuk-input govuk-input--error" id="name" name="name" type="text" aria-describedby="name-error">
      |</div>""".stripMargin

  val errorSummaryLinkingMultipleFieldsHtml =
    """<div class="govuk-error-summary" aria-labelledby="error-summary-title" role="alert" tabindex="-1" data-module="govuk-error-summary">
      |  <h2 class="govuk-error-summary__title" id="error-summary-title">
      |    There is a problem
      |  </h2>
      |  <div class="govuk-error-summary__body">
      |    <ul class="govuk-list govuk-error-summary__list">
      |      <li>
      |        <a href="#passport-issued-year">Passport issue date must include a year</a>
      |      </li>
      |    </ul>
      |  </div>
      |</div>
      |
      |<div class="govuk-form-group govuk-form-group--error">
      |  <fieldset class="govuk-fieldset" role="group" aria-describedby="passport-issued-error">
      |    <legend class="govuk-fieldset__legend govuk-fieldset__legend--xl">
      |      <h1 class="govuk-fieldset__heading">
      |        When was your passport issued?
      |      </h1>
      |    </legend>
      |    <span id="passport-issued-error" class="govuk-error-message">
      |      <span class="govuk-visually-hidden">Error:</span> Passport issue date must include a year
      |    </span>
      |    <div class="govuk-date-input" id="passport-issued">
      |      <div class="govuk-date-input__item">
      |        <div class="govuk-form-group">
      |          <label class="govuk-label govuk-date-input__label" for="passport-issued-day">
      |            Day
      |          </label>
      |          <input class="govuk-input govuk-date-input__input govuk-input--width-2" id="passport-issued-day" name="day" type="number" value="5" pattern="[0-9]*">
      |        </div>
      |      </div>
      |      <div class="govuk-date-input__item">
      |        <div class="govuk-form-group">
      |          <label class="govuk-label govuk-date-input__label" for="passport-issued-month">
      |            Month
      |          </label>
      |          <input class="govuk-input govuk-date-input__input govuk-input--width-2" id="passport-issued-month" name="month" type="number" value="12" pattern="[0-9]*">
      |        </div>
      |      </div>
      |      <div class="govuk-date-input__item">
      |        <div class="govuk-form-group">
      |          <label class="govuk-label govuk-date-input__label" for="passport-issued-year">
      |            Year
      |          </label>
      |          <input class="govuk-input govuk-date-input__input govuk-input--width-4 govuk-input--error" id="passport-issued-year" name="year" type="number" pattern="[0-9]*">
      |        </div>
      |      </div>
      |    </div>
      |  </fieldset>
      |</div>""".stripMargin

  val errorSummarylinkingCheckboxesRadiosHtml =
    """<div class="govuk-error-summary" aria-labelledby="error-summary-title" role="alert" tabindex="-1" data-module="govuk-error-summary">
      |  <h2 class="govuk-error-summary__title" id="error-summary-title">
      |    There is a problem
      |  </h2>
      |  <div class="govuk-error-summary__body">
      |    <ul class="govuk-list govuk-error-summary__list">
      |      <li>
      |        <a href="#nationality">Select if you are British, Irish or a citizen of a different country</a>
      |      </li>
      |    </ul>
      |  </div>
      |</div>
      |
      |<div class="govuk-form-group govuk-form-group--error">
      |  <fieldset class="govuk-fieldset" aria-describedby="nationality-hint nationality-error">
      |    <legend class="govuk-fieldset__legend govuk-fieldset__legend--xl">
      |      <h1 class="govuk-fieldset__heading">
      |        What is your nationality?
      |      </h1>
      |    </legend>
      |    <span id="nationality-hint" class="govuk-hint">
      |      If you have dual nationality, select all options that are relevant to you.
      |    </span>
      |    <span id="nationality-error" class="govuk-error-message">
      |      <span class="govuk-visually-hidden">Error:</span> Select if you are British, Irish or a citizen of a different country
      |    </span>
      |    <div class="govuk-checkboxes">
      |      <div class="govuk-checkboxes__item">
      |        <input class="govuk-checkboxes__input" id="nationality" name="nationality" type="checkbox" value="british" aria-describedby="nationality-item-hint">
      |        <label class="govuk-label govuk-checkboxes__label" for="nationality">
      |          British
      |        </label>
      |        <span id="nationality-item-hint" class="govuk-hint govuk-checkboxes__hint">
      |          including English, Scottish, Welsh and Northern Irish
      |        </span>
      |      </div>
      |      <div class="govuk-checkboxes__item">
      |        <input class="govuk-checkboxes__input" id="nationality-2" name="nationality" type="checkbox" value="irish">
      |        <label class="govuk-label govuk-checkboxes__label" for="nationality-2">
      |          Irish
      |        </label>
      |      </div>
      |      <div class="govuk-checkboxes__item">
      |        <input class="govuk-checkboxes__input" id="nationality-3" name="nationality" type="checkbox" value="other">
      |        <label class="govuk-label govuk-checkboxes__label" for="nationality-3">
      |          Citizen of another country
      |        </label>
      |      </div>
      |    </div>
      |  </fieldset>
      |</div>""".stripMargin

  val errorSummaryFullPageExampleHtml =
    """<div class="govuk-width-container">
      |  <a href="#" class="govuk-back-link">Back</a>
      |
      |  <main class="govuk-main-wrapper " id="main-content" role="main">
      |    <div class="govuk-grid-row">
      |      <div class="govuk-grid-column-two-thirds">
      |        <form action="/form-handler" method="post" novalidate>
      |          <div class="govuk-error-summary" aria-labelledby="error-summary-title" role="alert" tabindex="-1" data-module="govuk-error-summary">
      |            <h2 class="govuk-error-summary__title" id="error-summary-title">
      |              There is a problem
      |            </h2>
      |            <div class="govuk-error-summary__body">
      |              <ul class="govuk-list govuk-error-summary__list">
      |                <li>
      |                  <a href="#passport-issued-year">Passport issue date must include a year</a>
      |                </li>
      |              </ul>
      |            </div>
      |          </div>
      |
      |          <div class="govuk-form-group govuk-form-group--error">
      |            <fieldset class="govuk-fieldset" role="group" aria-describedby="passport-issued-error">
      |              <legend class="govuk-fieldset__legend govuk-fieldset__legend--xl">
      |                <h1 class="govuk-fieldset__heading">
      |                  When was your passport issued?
      |                </h1>
      |              </legend>
      |              <span id="passport-issued-error" class="govuk-error-message">
      |                <span class="govuk-visually-hidden">Error:</span> Passport issue date must include a year
      |              </span>
      |              <div class="govuk-date-input" id="passport-issued">
      |                <div class="govuk-date-input__item">
      |                  <div class="govuk-form-group">
      |                    <label class="govuk-label govuk-date-input__label" for="passport-issued-day">
      |                      Day
      |                    </label>
      |                    <input class="govuk-input govuk-date-input__input govuk-input--width-2" id="passport-issued-day" name="day" type="number" value="5" pattern="[0-9]*">
      |                  </div>
      |                </div>
      |                <div class="govuk-date-input__item">
      |                  <div class="govuk-form-group">
      |                    <label class="govuk-label govuk-date-input__label" for="passport-issued-month">
      |                      Month
      |                    </label>
      |                    <input class="govuk-input govuk-date-input__input govuk-input--width-2" id="passport-issued-month" name="month" type="number" value="12" pattern="[0-9]*">
      |                  </div>
      |                </div>
      |                <div class="govuk-date-input__item">
      |                  <div class="govuk-form-group">
      |                    <label class="govuk-label govuk-date-input__label" for="passport-issued-year">
      |                      Year
      |                    </label>
      |                    <input class="govuk-input govuk-date-input__input govuk-input--width-4 govuk-input--error" id="passport-issued-year" name="year" type="number" pattern="[0-9]*">
      |                  </div>
      |                </div>
      |              </div>
      |            </fieldset>
      |          </div>
      |
      |          <button class="govuk-button" data-module="govuk-button">
      |            Continue
      |          </button>
      |
      |        </form>
      |      </div>
      |    </div>
      |  </main>
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

  val fieldsetDefaultHtml =
    """<fieldset class="govuk-fieldset">
      |  <legend class="govuk-fieldset__legend govuk-fieldset__legend--xl">
      |    <h1 class="govuk-fieldset__heading">
      |      Legend as page heading
      |    </h1>
      |  </legend>
      |</fieldset>""".stripMargin

  val footerDefaultHtml =
    """<footer class="govuk-footer " role="contentinfo">
      |  <div class="govuk-width-container ">
      |    <div class="govuk-footer__meta">
      |      <div class="govuk-footer__meta-item govuk-footer__meta-item--grow">
      |
      |        <svg role="presentation" focusable="false" class="govuk-footer__licence-logo" xmlns="http://www.w3.org/2000/svg" viewbox="0 0 483.2 195.7" height="17" width="41">
      |          <path fill="currentColor" d="M421.5 142.8V.1l-50.7 32.3v161.1h112.4v-50.7zm-122.3-9.6A47.12 47.12 0 0 1 221 97.8c0-26 21.1-47.1 47.1-47.1 16.7 0 31.4 8.7 39.7 21.8l42.7-27.2A97.63 97.63 0 0 0 268.1 0c-36.5 0-68.3 20.1-85.1 49.7A98 98 0 0 0 97.8 0C43.9 0 0 43.9 0 97.8s43.9 97.8 97.8 97.8c36.5 0 68.3-20.1 85.1-49.7a97.76 97.76 0 0 0 149.6 25.4l19.4 22.2h3v-87.8h-80l24.3 27.5zM97.8 145c-26 0-47.1-21.1-47.1-47.1s21.1-47.1 47.1-47.1 47.2 21 47.2 47S123.8 145 97.8 145" />
      |        </svg>
      |        <span class="govuk-footer__licence-description">
      |          All content is available under the
      |          <a class="govuk-footer__link" href="https://www.nationalarchives.gov.uk/doc/open-government-licence/version/3/" rel="license">Open Government Licence v3.0</a>, except where otherwise stated
      |        </span>
      |      </div>
      |      <div class="govuk-footer__meta-item">
      |        <a class="govuk-footer__link govuk-footer__copyright-logo" href="https://www.nationalarchives.gov.uk/information-management/re-using-public-sector-information/uk-government-licensing-framework/crown-copyright/">© Crown copyright</a>
      |      </div>
      |    </div>
      |  </div>
      |</footer>""".stripMargin

  val footerWithNavigationHtml =
    """<footer class="govuk-footer " role="contentinfo">
      |  <div class="govuk-width-container ">
      |    <div class="govuk-footer__navigation">
      |      <div class="govuk-footer__section">
      |        <h2 class="govuk-footer__heading govuk-heading-m">Two column list</h2>
      |        <ul class="govuk-footer__list govuk-footer__list--columns-2">
      |          <li class="govuk-footer__list-item">
      |            <a class="govuk-footer__link" href="#1">
      |              Navigation item 1
      |            </a>
      |          </li>
      |          <li class="govuk-footer__list-item">
      |            <a class="govuk-footer__link" href="#2">
      |              Navigation item 2
      |            </a>
      |          </li>
      |          <li class="govuk-footer__list-item">
      |            <a class="govuk-footer__link" href="#3">
      |              Navigation item 3
      |            </a>
      |          </li>
      |          <li class="govuk-footer__list-item">
      |            <a class="govuk-footer__link" href="#4">
      |              Navigation item 4
      |            </a>
      |          </li>
      |          <li class="govuk-footer__list-item">
      |            <a class="govuk-footer__link" href="#5">
      |              Navigation item 5
      |            </a>
      |          </li>
      |          <li class="govuk-footer__list-item">
      |            <a class="govuk-footer__link" href="#6">
      |              Navigation item 6
      |            </a>
      |          </li>
      |        </ul>
      |      </div>
      |      <div class="govuk-footer__section">
      |        <h2 class="govuk-footer__heading govuk-heading-m">Single column list</h2>
      |        <ul class="govuk-footer__list ">
      |          <li class="govuk-footer__list-item">
      |            <a class="govuk-footer__link" href="#1">
      |              Navigation item 1
      |            </a>
      |          </li>
      |          <li class="govuk-footer__list-item">
      |            <a class="govuk-footer__link" href="#2">
      |              Navigation item 2
      |            </a>
      |          </li>
      |          <li class="govuk-footer__list-item">
      |            <a class="govuk-footer__link" href="#3">
      |              Navigation item 3
      |            </a>
      |          </li>
      |        </ul>
      |      </div>
      |    </div>
      |    <hr class="govuk-footer__section-break">
      |    <div class="govuk-footer__meta">
      |      <div class="govuk-footer__meta-item govuk-footer__meta-item--grow">
      |
      |        <svg role="presentation" focusable="false" class="govuk-footer__licence-logo" xmlns="http://www.w3.org/2000/svg" viewbox="0 0 483.2 195.7" height="17" width="41">
      |          <path fill="currentColor" d="M421.5 142.8V.1l-50.7 32.3v161.1h112.4v-50.7zm-122.3-9.6A47.12 47.12 0 0 1 221 97.8c0-26 21.1-47.1 47.1-47.1 16.7 0 31.4 8.7 39.7 21.8l42.7-27.2A97.63 97.63 0 0 0 268.1 0c-36.5 0-68.3 20.1-85.1 49.7A98 98 0 0 0 97.8 0C43.9 0 0 43.9 0 97.8s43.9 97.8 97.8 97.8c36.5 0 68.3-20.1 85.1-49.7a97.76 97.76 0 0 0 149.6 25.4l19.4 22.2h3v-87.8h-80l24.3 27.5zM97.8 145c-26 0-47.1-21.1-47.1-47.1s21.1-47.1 47.1-47.1 47.2 21 47.2 47S123.8 145 97.8 145" />
      |        </svg>
      |        <span class="govuk-footer__licence-description">
      |          All content is available under the
      |          <a class="govuk-footer__link" href="https://www.nationalarchives.gov.uk/doc/open-government-licence/version/3/" rel="license">Open Government Licence v3.0</a>, except where otherwise stated
      |        </span>
      |      </div>
      |      <div class="govuk-footer__meta-item">
      |        <a class="govuk-footer__link govuk-footer__copyright-logo" href="https://www.nationalarchives.gov.uk/information-management/re-using-public-sector-information/uk-government-licensing-framework/crown-copyright/">© Crown copyright</a>
      |      </div>
      |    </div>
      |  </div>
      |</footer>""".stripMargin

  val footerWithMetaHtml =
    """<footer class="govuk-footer " role="contentinfo">
      |  <div class="govuk-width-container ">
      |    <div class="govuk-footer__meta">
      |      <div class="govuk-footer__meta-item govuk-footer__meta-item--grow">
      |        <h2 class="govuk-visually-hidden">Support links</h2>
      |        <ul class="govuk-footer__inline-list">
      |          <li class="govuk-footer__inline-list-item">
      |            <a class="govuk-footer__link" href="#1">
      |              Item 1
      |            </a>
      |          </li>
      |          <li class="govuk-footer__inline-list-item">
      |            <a class="govuk-footer__link" href="#2">
      |              Item 2
      |            </a>
      |          </li>
      |          <li class="govuk-footer__inline-list-item">
      |            <a class="govuk-footer__link" href="#3">
      |              Item 3
      |            </a>
      |          </li>
      |        </ul>
      |
      |        <svg role="presentation" focusable="false" class="govuk-footer__licence-logo" xmlns="http://www.w3.org/2000/svg" viewbox="0 0 483.2 195.7" height="17" width="41">
      |          <path fill="currentColor" d="M421.5 142.8V.1l-50.7 32.3v161.1h112.4v-50.7zm-122.3-9.6A47.12 47.12 0 0 1 221 97.8c0-26 21.1-47.1 47.1-47.1 16.7 0 31.4 8.7 39.7 21.8l42.7-27.2A97.63 97.63 0 0 0 268.1 0c-36.5 0-68.3 20.1-85.1 49.7A98 98 0 0 0 97.8 0C43.9 0 0 43.9 0 97.8s43.9 97.8 97.8 97.8c36.5 0 68.3-20.1 85.1-49.7a97.76 97.76 0 0 0 149.6 25.4l19.4 22.2h3v-87.8h-80l24.3 27.5zM97.8 145c-26 0-47.1-21.1-47.1-47.1s21.1-47.1 47.1-47.1 47.2 21 47.2 47S123.8 145 97.8 145" />
      |        </svg>
      |        <span class="govuk-footer__licence-description">
      |          All content is available under the
      |          <a class="govuk-footer__link" href="https://www.nationalarchives.gov.uk/doc/open-government-licence/version/3/" rel="license">Open Government Licence v3.0</a>, except where otherwise stated
      |        </span>
      |      </div>
      |      <div class="govuk-footer__meta-item">
      |        <a class="govuk-footer__link govuk-footer__copyright-logo" href="https://www.nationalarchives.gov.uk/information-management/re-using-public-sector-information/uk-government-licensing-framework/crown-copyright/">© Crown copyright</a>
      |      </div>
      |    </div>
      |  </div>
      |</footer>""".stripMargin

  val footerFullHtml =
    """<footer class="govuk-footer " role="contentinfo">
      |  <div class="govuk-width-container ">
      |    <div class="govuk-footer__navigation">
      |      <div class="govuk-footer__section">
      |        <h2 class="govuk-footer__heading govuk-heading-m">Services and information</h2>
      |        <ul class="govuk-footer__list govuk-footer__list--columns-2">
      |          <li class="govuk-footer__list-item">
      |            <a class="govuk-footer__link" href="#">
      |              Benefits
      |            </a>
      |          </li>
      |          <li class="govuk-footer__list-item">
      |            <a class="govuk-footer__link" href="#">
      |              Births, deaths, marriages and care
      |            </a>
      |          </li>
      |          <li class="govuk-footer__list-item">
      |            <a class="govuk-footer__link" href="#">
      |              Business and self-employed
      |            </a>
      |          </li>
      |          <li class="govuk-footer__list-item">
      |            <a class="govuk-footer__link" href="#">
      |              Childcare and parenting
      |            </a>
      |          </li>
      |          <li class="govuk-footer__list-item">
      |            <a class="govuk-footer__link" href="#">
      |              Citizenship and living in the UK
      |            </a>
      |          </li>
      |          <li class="govuk-footer__list-item">
      |            <a class="govuk-footer__link" href="#">
      |              Crime, justice and the law
      |            </a>
      |          </li>
      |          <li class="govuk-footer__list-item">
      |            <a class="govuk-footer__link" href="#">
      |              Disabled people
      |            </a>
      |          </li>
      |          <li class="govuk-footer__list-item">
      |            <a class="govuk-footer__link" href="#">
      |              Driving and transport
      |            </a>
      |          </li>
      |          <li class="govuk-footer__list-item">
      |            <a class="govuk-footer__link" href="#">
      |              Education and learning
      |            </a>
      |          </li>
      |          <li class="govuk-footer__list-item">
      |            <a class="govuk-footer__link" href="#">
      |              Employing people
      |            </a>
      |          </li>
      |          <li class="govuk-footer__list-item">
      |            <a class="govuk-footer__link" href="#">
      |              Environment and countryside
      |            </a>
      |          </li>
      |          <li class="govuk-footer__list-item">
      |            <a class="govuk-footer__link" href="#">
      |              Housing and local services
      |            </a>
      |          </li>
      |          <li class="govuk-footer__list-item">
      |            <a class="govuk-footer__link" href="#">
      |              Money and tax
      |            </a>
      |          </li>
      |          <li class="govuk-footer__list-item">
      |            <a class="govuk-footer__link" href="#">
      |              Passports, travel and living abroad
      |            </a>
      |          </li>
      |          <li class="govuk-footer__list-item">
      |            <a class="govuk-footer__link" href="#">
      |              Visas and immigration
      |            </a>
      |          </li>
      |          <li class="govuk-footer__list-item">
      |            <a class="govuk-footer__link" href="#">
      |              Working, jobs and pensions
      |            </a>
      |          </li>
      |        </ul>
      |      </div>
      |      <div class="govuk-footer__section">
      |        <h2 class="govuk-footer__heading govuk-heading-m">Departments and policy</h2>
      |        <ul class="govuk-footer__list ">
      |          <li class="govuk-footer__list-item">
      |            <a class="govuk-footer__link" href="#">
      |              How government works
      |            </a>
      |          </li>
      |          <li class="govuk-footer__list-item">
      |            <a class="govuk-footer__link" href="#">
      |              Departments
      |            </a>
      |          </li>
      |          <li class="govuk-footer__list-item">
      |            <a class="govuk-footer__link" href="#">
      |              Worldwide
      |            </a>
      |          </li>
      |          <li class="govuk-footer__list-item">
      |            <a class="govuk-footer__link" href="#">
      |              Policies
      |            </a>
      |          </li>
      |          <li class="govuk-footer__list-item">
      |            <a class="govuk-footer__link" href="#">
      |              Publications
      |            </a>
      |          </li>
      |          <li class="govuk-footer__list-item">
      |            <a class="govuk-footer__link" href="#">
      |              Announcements
      |            </a>
      |          </li>
      |        </ul>
      |      </div>
      |    </div>
      |    <hr class="govuk-footer__section-break">
      |    <div class="govuk-footer__meta">
      |      <div class="govuk-footer__meta-item govuk-footer__meta-item--grow">
      |        <h2 class="govuk-visually-hidden">Support links</h2>
      |        <ul class="govuk-footer__inline-list">
      |          <li class="govuk-footer__inline-list-item">
      |            <a class="govuk-footer__link" href="#">
      |              Help
      |            </a>
      |          </li>
      |          <li class="govuk-footer__inline-list-item">
      |            <a class="govuk-footer__link" href="#">
      |              Cookies
      |            </a>
      |          </li>
      |          <li class="govuk-footer__inline-list-item">
      |            <a class="govuk-footer__link" href="#">
      |              Contact
      |            </a>
      |          </li>
      |          <li class="govuk-footer__inline-list-item">
      |            <a class="govuk-footer__link" href="#">
      |              Terms and conditions
      |            </a>
      |          </li>
      |          <li class="govuk-footer__inline-list-item">
      |            <a class="govuk-footer__link" href="#">
      |              Rhestr o Wasanaethau Cymraeg
      |            </a>
      |          </li>
      |        </ul>
      |        <div class="govuk-footer__meta-custom">
      |          Built by the <a href="#" class="govuk-footer__link">Government Digital Service</a>
      |        </div>
      |
      |        <svg role="presentation" focusable="false" class="govuk-footer__licence-logo" xmlns="http://www.w3.org/2000/svg" viewbox="0 0 483.2 195.7" height="17" width="41">
      |          <path fill="currentColor" d="M421.5 142.8V.1l-50.7 32.3v161.1h112.4v-50.7zm-122.3-9.6A47.12 47.12 0 0 1 221 97.8c0-26 21.1-47.1 47.1-47.1 16.7 0 31.4 8.7 39.7 21.8l42.7-27.2A97.63 97.63 0 0 0 268.1 0c-36.5 0-68.3 20.1-85.1 49.7A98 98 0 0 0 97.8 0C43.9 0 0 43.9 0 97.8s43.9 97.8 97.8 97.8c36.5 0 68.3-20.1 85.1-49.7a97.76 97.76 0 0 0 149.6 25.4l19.4 22.2h3v-87.8h-80l24.3 27.5zM97.8 145c-26 0-47.1-21.1-47.1-47.1s21.1-47.1 47.1-47.1 47.2 21 47.2 47S123.8 145 97.8 145" />
      |        </svg>
      |        <span class="govuk-footer__licence-description">
      |          All content is available under the
      |          <a class="govuk-footer__link" href="https://www.nationalarchives.gov.uk/doc/open-government-licence/version/3/" rel="license">Open Government Licence v3.0</a>, except where otherwise stated
      |        </span>
      |      </div>
      |      <div class="govuk-footer__meta-item">
      |        <a class="govuk-footer__link govuk-footer__copyright-logo" href="https://www.nationalarchives.gov.uk/information-management/re-using-public-sector-information/uk-government-licensing-framework/crown-copyright/">© Crown copyright</a>
      |      </div>
      |    </div>
      |  </div>
      |</footer>""".stripMargin

  val headerDefaultHtml =
    """<header class="govuk-header " role="banner" data-module="govuk-header">
      |  <div class="govuk-header__container govuk-width-container">
      |    <div class="govuk-header__logo">
      |      <a href="#" class="govuk-header__link govuk-header__link--homepage">
      |        <span class="govuk-header__logotype">
      |          <svg role="presentation" focusable="false" class="govuk-header__logotype-crown" xmlns="http://www.w3.org/2000/svg" viewbox="0 0 132 97" height="30" width="36">
      |            <path fill="currentColor" fill-rule="evenodd" d="M25 30.2c3.5 1.5 7.7-.2 9.1-3.7 1.5-3.6-.2-7.8-3.9-9.2-3.6-1.4-7.6.3-9.1 3.9-1.4 3.5.3 7.5 3.9 9zM9 39.5c3.6 1.5 7.8-.2 9.2-3.7 1.5-3.6-.2-7.8-3.9-9.1-3.6-1.5-7.6.2-9.1 3.8-1.4 3.5.3 7.5 3.8 9zM4.4 57.2c3.5 1.5 7.7-.2 9.1-3.8 1.5-3.6-.2-7.7-3.9-9.1-3.5-1.5-7.6.3-9.1 3.8-1.4 3.5.3 7.6 3.9 9.1zm38.3-21.4c3.5 1.5 7.7-.2 9.1-3.8 1.5-3.6-.2-7.7-3.9-9.1-3.6-1.5-7.6.3-9.1 3.8-1.3 3.6.4 7.7 3.9 9.1zm64.4-5.6c-3.6 1.5-7.8-.2-9.1-3.7-1.5-3.6.2-7.8 3.8-9.2 3.6-1.4 7.7.3 9.2 3.9 1.3 3.5-.4 7.5-3.9 9zm15.9 9.3c-3.6 1.5-7.7-.2-9.1-3.7-1.5-3.6.2-7.8 3.7-9.1 3.6-1.5 7.7.2 9.2 3.8 1.5 3.5-.3 7.5-3.8 9zm4.7 17.7c-3.6 1.5-7.8-.2-9.2-3.8-1.5-3.6.2-7.7 3.9-9.1 3.6-1.5 7.7.3 9.2 3.8 1.3 3.5-.4 7.6-3.9 9.1zM89.3 35.8c-3.6 1.5-7.8-.2-9.2-3.8-1.4-3.6.2-7.7 3.9-9.1 3.6-1.5 7.7.3 9.2 3.8 1.4 3.6-.3 7.7-3.9 9.1zM69.7 17.7l8.9 4.7V9.3l-8.9 2.8c-.2-.3-.5-.6-.9-.9L72.4 0H59.6l3.5 11.2c-.3.3-.6.5-.9.9l-8.8-2.8v13.1l8.8-4.7c.3.3.6.7.9.9l-5 15.4v.1c-.2.8-.4 1.6-.4 2.4 0 4.1 3.1 7.5 7 8.1h.2c.3 0 .7.1 1 .1.4 0 .7 0 1-.1h.2c4-.6 7.1-4.1 7.1-8.1 0-.8-.1-1.7-.4-2.4V34l-5.1-15.4c.4-.2.7-.6 1-.9zM66 92.8c16.9 0 32.8 1.1 47.1 3.2 4-16.9 8.9-26.7 14-33.5l-9.6-3.4c1 4.9 1.1 7.2 0 10.2-1.5-1.4-3-4.3-4.2-8.7L108.6 76c2.8-2 5-3.2 7.5-3.3-4.4 9.4-10 11.9-13.6 11.2-4.3-.8-6.3-4.6-5.6-7.9 1-4.7 5.7-5.9 8-.5 4.3-8.7-3-11.4-7.6-8.8 7.1-7.2 7.9-13.5 2.1-21.1-8 6.1-8.1 12.3-4.5 20.8-4.7-5.4-12.1-2.5-9.5 6.2 3.4-5.2 7.9-2 7.2 3.1-.6 4.3-6.4 7.8-13.5 7.2-10.3-.9-10.9-8-11.2-13.8 2.5-.5 7.1 1.8 11 7.3L80.2 60c-4.1 4.4-8 5.3-12.3 5.4 1.4-4.4 8-11.6 8-11.6H55.5s6.4 7.2 7.9 11.6c-4.2-.1-8-1-12.3-5.4l1.4 16.4c3.9-5.5 8.5-7.7 10.9-7.3-.3 5.8-.9 12.8-11.1 13.8-7.2.6-12.9-2.9-13.5-7.2-.7-5 3.8-8.3 7.1-3.1 2.7-8.7-4.6-11.6-9.4-6.2 3.7-8.5 3.6-14.7-4.6-20.8-5.8 7.6-5 13.9 2.2 21.1-4.7-2.6-11.9.1-7.7 8.8 2.3-5.5 7.1-4.2 8.1.5.7 3.3-1.3 7.1-5.7 7.9-3.5.7-9-1.8-13.5-11.2 2.5.1 4.7 1.3 7.5 3.3l-4.7-15.4c-1.2 4.4-2.7 7.2-4.3 8.7-1.1-3-.9-5.3 0-10.2l-9.5 3.4c5 6.9 9.9 16.7 14 33.5 14.8-2.1 30.8-3.2 47.7-3.2z"></path>
      |            <image src="/assets/images/govuk-logotype-crown.png" xlink:href="" class="govuk-header__logotype-crown-fallback-image" width="36" height="32"></image>
      |          </svg>
      |          <span class="govuk-header__logotype-text">
      |            GOV.UK
      |          </span>
      |        </span>
      |      </a>
      |    </div>
      |  </div>
      |</header>""".stripMargin

  val headerWithServiceNameHtml =
    """<header class="govuk-header " role="banner" data-module="govuk-header">
      |  <div class="govuk-header__container govuk-width-container">
      |    <div class="govuk-header__logo">
      |      <a href="#" class="govuk-header__link govuk-header__link--homepage">
      |        <span class="govuk-header__logotype">
      |          <svg role="presentation" focusable="false" class="govuk-header__logotype-crown" xmlns="http://www.w3.org/2000/svg" viewbox="0 0 132 97" height="30" width="36">
      |            <path fill="currentColor" fill-rule="evenodd" d="M25 30.2c3.5 1.5 7.7-.2 9.1-3.7 1.5-3.6-.2-7.8-3.9-9.2-3.6-1.4-7.6.3-9.1 3.9-1.4 3.5.3 7.5 3.9 9zM9 39.5c3.6 1.5 7.8-.2 9.2-3.7 1.5-3.6-.2-7.8-3.9-9.1-3.6-1.5-7.6.2-9.1 3.8-1.4 3.5.3 7.5 3.8 9zM4.4 57.2c3.5 1.5 7.7-.2 9.1-3.8 1.5-3.6-.2-7.7-3.9-9.1-3.5-1.5-7.6.3-9.1 3.8-1.4 3.5.3 7.6 3.9 9.1zm38.3-21.4c3.5 1.5 7.7-.2 9.1-3.8 1.5-3.6-.2-7.7-3.9-9.1-3.6-1.5-7.6.3-9.1 3.8-1.3 3.6.4 7.7 3.9 9.1zm64.4-5.6c-3.6 1.5-7.8-.2-9.1-3.7-1.5-3.6.2-7.8 3.8-9.2 3.6-1.4 7.7.3 9.2 3.9 1.3 3.5-.4 7.5-3.9 9zm15.9 9.3c-3.6 1.5-7.7-.2-9.1-3.7-1.5-3.6.2-7.8 3.7-9.1 3.6-1.5 7.7.2 9.2 3.8 1.5 3.5-.3 7.5-3.8 9zm4.7 17.7c-3.6 1.5-7.8-.2-9.2-3.8-1.5-3.6.2-7.7 3.9-9.1 3.6-1.5 7.7.3 9.2 3.8 1.3 3.5-.4 7.6-3.9 9.1zM89.3 35.8c-3.6 1.5-7.8-.2-9.2-3.8-1.4-3.6.2-7.7 3.9-9.1 3.6-1.5 7.7.3 9.2 3.8 1.4 3.6-.3 7.7-3.9 9.1zM69.7 17.7l8.9 4.7V9.3l-8.9 2.8c-.2-.3-.5-.6-.9-.9L72.4 0H59.6l3.5 11.2c-.3.3-.6.5-.9.9l-8.8-2.8v13.1l8.8-4.7c.3.3.6.7.9.9l-5 15.4v.1c-.2.8-.4 1.6-.4 2.4 0 4.1 3.1 7.5 7 8.1h.2c.3 0 .7.1 1 .1.4 0 .7 0 1-.1h.2c4-.6 7.1-4.1 7.1-8.1 0-.8-.1-1.7-.4-2.4V34l-5.1-15.4c.4-.2.7-.6 1-.9zM66 92.8c16.9 0 32.8 1.1 47.1 3.2 4-16.9 8.9-26.7 14-33.5l-9.6-3.4c1 4.9 1.1 7.2 0 10.2-1.5-1.4-3-4.3-4.2-8.7L108.6 76c2.8-2 5-3.2 7.5-3.3-4.4 9.4-10 11.9-13.6 11.2-4.3-.8-6.3-4.6-5.6-7.9 1-4.7 5.7-5.9 8-.5 4.3-8.7-3-11.4-7.6-8.8 7.1-7.2 7.9-13.5 2.1-21.1-8 6.1-8.1 12.3-4.5 20.8-4.7-5.4-12.1-2.5-9.5 6.2 3.4-5.2 7.9-2 7.2 3.1-.6 4.3-6.4 7.8-13.5 7.2-10.3-.9-10.9-8-11.2-13.8 2.5-.5 7.1 1.8 11 7.3L80.2 60c-4.1 4.4-8 5.3-12.3 5.4 1.4-4.4 8-11.6 8-11.6H55.5s6.4 7.2 7.9 11.6c-4.2-.1-8-1-12.3-5.4l1.4 16.4c3.9-5.5 8.5-7.7 10.9-7.3-.3 5.8-.9 12.8-11.1 13.8-7.2.6-12.9-2.9-13.5-7.2-.7-5 3.8-8.3 7.1-3.1 2.7-8.7-4.6-11.6-9.4-6.2 3.7-8.5 3.6-14.7-4.6-20.8-5.8 7.6-5 13.9 2.2 21.1-4.7-2.6-11.9.1-7.7 8.8 2.3-5.5 7.1-4.2 8.1.5.7 3.3-1.3 7.1-5.7 7.9-3.5.7-9-1.8-13.5-11.2 2.5.1 4.7 1.3 7.5 3.3l-4.7-15.4c-1.2 4.4-2.7 7.2-4.3 8.7-1.1-3-.9-5.3 0-10.2l-9.5 3.4c5 6.9 9.9 16.7 14 33.5 14.8-2.1 30.8-3.2 47.7-3.2z"></path>
      |            <image src="/assets/images/govuk-logotype-crown.png" xlink:href="" class="govuk-header__logotype-crown-fallback-image" width="36" height="32"></image>
      |          </svg>
      |          <span class="govuk-header__logotype-text">
      |            GOV.UK
      |          </span>
      |        </span>
      |      </a>
      |    </div>
      |    <div class="govuk-header__content">
      |      <a href="#" class="govuk-header__link govuk-header__link--service-name">
      |        Service name
      |      </a>
      |    </div>
      |  </div>
      |</header>""".stripMargin

  val headerWithServiceNameAndNavigationHtml =
    """<header class="govuk-header " role="banner" data-module="govuk-header">
      |  <div class="govuk-header__container govuk-width-container">
      |    <div class="govuk-header__logo">
      |      <a href="#" class="govuk-header__link govuk-header__link--homepage">
      |        <span class="govuk-header__logotype">
      |          <svg role="presentation" focusable="false" class="govuk-header__logotype-crown" xmlns="http://www.w3.org/2000/svg" viewbox="0 0 132 97" height="30" width="36">
      |            <path fill="currentColor" fill-rule="evenodd" d="M25 30.2c3.5 1.5 7.7-.2 9.1-3.7 1.5-3.6-.2-7.8-3.9-9.2-3.6-1.4-7.6.3-9.1 3.9-1.4 3.5.3 7.5 3.9 9zM9 39.5c3.6 1.5 7.8-.2 9.2-3.7 1.5-3.6-.2-7.8-3.9-9.1-3.6-1.5-7.6.2-9.1 3.8-1.4 3.5.3 7.5 3.8 9zM4.4 57.2c3.5 1.5 7.7-.2 9.1-3.8 1.5-3.6-.2-7.7-3.9-9.1-3.5-1.5-7.6.3-9.1 3.8-1.4 3.5.3 7.6 3.9 9.1zm38.3-21.4c3.5 1.5 7.7-.2 9.1-3.8 1.5-3.6-.2-7.7-3.9-9.1-3.6-1.5-7.6.3-9.1 3.8-1.3 3.6.4 7.7 3.9 9.1zm64.4-5.6c-3.6 1.5-7.8-.2-9.1-3.7-1.5-3.6.2-7.8 3.8-9.2 3.6-1.4 7.7.3 9.2 3.9 1.3 3.5-.4 7.5-3.9 9zm15.9 9.3c-3.6 1.5-7.7-.2-9.1-3.7-1.5-3.6.2-7.8 3.7-9.1 3.6-1.5 7.7.2 9.2 3.8 1.5 3.5-.3 7.5-3.8 9zm4.7 17.7c-3.6 1.5-7.8-.2-9.2-3.8-1.5-3.6.2-7.7 3.9-9.1 3.6-1.5 7.7.3 9.2 3.8 1.3 3.5-.4 7.6-3.9 9.1zM89.3 35.8c-3.6 1.5-7.8-.2-9.2-3.8-1.4-3.6.2-7.7 3.9-9.1 3.6-1.5 7.7.3 9.2 3.8 1.4 3.6-.3 7.7-3.9 9.1zM69.7 17.7l8.9 4.7V9.3l-8.9 2.8c-.2-.3-.5-.6-.9-.9L72.4 0H59.6l3.5 11.2c-.3.3-.6.5-.9.9l-8.8-2.8v13.1l8.8-4.7c.3.3.6.7.9.9l-5 15.4v.1c-.2.8-.4 1.6-.4 2.4 0 4.1 3.1 7.5 7 8.1h.2c.3 0 .7.1 1 .1.4 0 .7 0 1-.1h.2c4-.6 7.1-4.1 7.1-8.1 0-.8-.1-1.7-.4-2.4V34l-5.1-15.4c.4-.2.7-.6 1-.9zM66 92.8c16.9 0 32.8 1.1 47.1 3.2 4-16.9 8.9-26.7 14-33.5l-9.6-3.4c1 4.9 1.1 7.2 0 10.2-1.5-1.4-3-4.3-4.2-8.7L108.6 76c2.8-2 5-3.2 7.5-3.3-4.4 9.4-10 11.9-13.6 11.2-4.3-.8-6.3-4.6-5.6-7.9 1-4.7 5.7-5.9 8-.5 4.3-8.7-3-11.4-7.6-8.8 7.1-7.2 7.9-13.5 2.1-21.1-8 6.1-8.1 12.3-4.5 20.8-4.7-5.4-12.1-2.5-9.5 6.2 3.4-5.2 7.9-2 7.2 3.1-.6 4.3-6.4 7.8-13.5 7.2-10.3-.9-10.9-8-11.2-13.8 2.5-.5 7.1 1.8 11 7.3L80.2 60c-4.1 4.4-8 5.3-12.3 5.4 1.4-4.4 8-11.6 8-11.6H55.5s6.4 7.2 7.9 11.6c-4.2-.1-8-1-12.3-5.4l1.4 16.4c3.9-5.5 8.5-7.7 10.9-7.3-.3 5.8-.9 12.8-11.1 13.8-7.2.6-12.9-2.9-13.5-7.2-.7-5 3.8-8.3 7.1-3.1 2.7-8.7-4.6-11.6-9.4-6.2 3.7-8.5 3.6-14.7-4.6-20.8-5.8 7.6-5 13.9 2.2 21.1-4.7-2.6-11.9.1-7.7 8.8 2.3-5.5 7.1-4.2 8.1.5.7 3.3-1.3 7.1-5.7 7.9-3.5.7-9-1.8-13.5-11.2 2.5.1 4.7 1.3 7.5 3.3l-4.7-15.4c-1.2 4.4-2.7 7.2-4.3 8.7-1.1-3-.9-5.3 0-10.2l-9.5 3.4c5 6.9 9.9 16.7 14 33.5 14.8-2.1 30.8-3.2 47.7-3.2z"></path>
      |            <image src="/assets/images/govuk-logotype-crown.png" xlink:href="" class="govuk-header__logotype-crown-fallback-image" width="36" height="32"></image>
      |          </svg>
      |          <span class="govuk-header__logotype-text">
      |            GOV.UK
      |          </span>
      |        </span>
      |      </a>
      |    </div>
      |    <div class="govuk-header__content">
      |      <a href="#" class="govuk-header__link govuk-header__link--service-name">
      |        Service name
      |      </a>
      |      <button type="button" class="govuk-header__menu-button govuk-js-header-toggle" aria-controls="navigation" aria-label="Show or hide Top Level Navigation">Menu</button>
      |      <nav>
      |        <ul id="navigation" class="govuk-header__navigation " aria-label="Top Level Navigation">
      |          <li class="govuk-header__navigation-item govuk-header__navigation-item--active">
      |            <a class="govuk-header__link" href="#1">
      |              Navigation item 1
      |            </a>
      |          </li>
      |          <li class="govuk-header__navigation-item">
      |            <a class="govuk-header__link" href="#2">
      |              Navigation item 2
      |            </a>
      |          </li>
      |          <li class="govuk-header__navigation-item">
      |            <a class="govuk-header__link" href="#3">
      |              Navigation item 3
      |            </a>
      |          </li>
      |          <li class="govuk-header__navigation-item">
      |            <a class="govuk-header__link" href="#4">
      |              Navigation item 4
      |            </a>
      |          </li>
      |        </ul>
      |      </nav>
      |    </div>
      |  </div>
      |</header>""".stripMargin

  val panelDefaultHtml =
    """<div class="govuk-panel govuk-panel--confirmation">
      |  <h1 class="govuk-panel__title">
      |    Application complete
      |  </h1>
      |  <div class="govuk-panel__body">
      |    Your reference number<br><strong>HDJ2123F</strong>
      |  </div>
      |</div>""".stripMargin

  val inputDefaultHtml =
    """<div class="govuk-form-group">
      |  <label class="govuk-label" for="event-name">
      |    Event name
      |  </label>
      |  <input class="govuk-input" id="event-name" name="event-name" type="text">
      |</div>""".stripMargin

  val inputFixedWidthHtml =
    """<div class="govuk-form-group">
      |  <label class="govuk-label" for="width-20">
      |    20 character width
      |  </label>
      |  <input class="govuk-input govuk-input--width-20" id="width-20" name="width-20" type="text">
      |</div>
      |
      |<div class="govuk-form-group">
      |  <label class="govuk-label" for="width-10">
      |    10 character width
      |  </label>
      |  <input class="govuk-input govuk-input--width-10" id="width-10" name="width-10" type="text">
      |</div>
      |
      |<div class="govuk-form-group">
      |  <label class="govuk-label" for="width-5">
      |    5 character width
      |  </label>
      |  <input class="govuk-input govuk-input--width-5" id="width-5" name="width-5" type="text">
      |</div>
      |
      |<div class="govuk-form-group">
      |  <label class="govuk-label" for="width-4">
      |    4 character width
      |  </label>
      |  <input class="govuk-input govuk-input--width-4" id="width-4" name="width-4" type="text">
      |</div>
      |
      |<div class="govuk-form-group">
      |  <label class="govuk-label" for="width-3">
      |    3 character width
      |  </label>
      |  <input class="govuk-input govuk-input--width-3" id="width-3" name="width-3" type="text">
      |</div>
      |
      |<div class="govuk-form-group">
      |  <label class="govuk-label" for="width-2">
      |    2 character width
      |  </label>
      |  <input class="govuk-input govuk-input--width-2" id="width-2" name="width-2" type="text">
      |</div>""".stripMargin

  val inputFluidWidthHtml =
    """<div class="govuk-form-group">
      |  <label class="govuk-label" for="full">
      |    Full width
      |  </label>
      |  <input class="govuk-input govuk-!-width-full" id="full" name="full" type="text">
      |</div>
      |
      |<div class="govuk-form-group">
      |  <label class="govuk-label" for="three-quarters">
      |    Three-quarters width
      |  </label>
      |  <input class="govuk-input govuk-!-width-three-quarters" id="three-quarters" name="three-quarters" type="text">
      |</div>
      |
      |<div class="govuk-form-group">
      |  <label class="govuk-label" for="two-thirds">
      |    Two-thirds width
      |  </label>
      |  <input class="govuk-input govuk-!-width-two-thirds" id="two-thirds" name="two-thirds" type="text">
      |</div>
      |
      |<div class="govuk-form-group">
      |  <label class="govuk-label" for="one-half">
      |    One-half width
      |  </label>
      |  <input class="govuk-input govuk-!-width-one-half" id="one-half" name="one-half" type="text">
      |</div>
      |
      |<div class="govuk-form-group">
      |  <label class="govuk-label" for="one-third">
      |    One-third width
      |  </label>
      |  <input class="govuk-input govuk-!-width-one-third" id="one-third" name="one-third" type="text">
      |</div>
      |
      |<div class="govuk-form-group">
      |  <label class="govuk-label" for="one-quarter">
      |    One-quarter width
      |  </label>
      |  <input class="govuk-input govuk-!-width-one-quarter" id="one-quarter" name="one-quarter" type="text">
      |</div>""".stripMargin

  val inputHintTextHtml =
    """<div class="govuk-form-group">
      |  <label class="govuk-label" for="event-name">
      |    Event name
      |  </label>
      |  <span id="event-name-hint" class="govuk-hint">
      |    The name you’ll use on promotional material.
      |  </span>
      |  <input class="govuk-input" id="event-name" name="event-name" type="text" aria-describedby="event-name-hint">
      |</div>""".stripMargin

  val inputErrorHtml =
    """<div class="govuk-form-group govuk-form-group--error">
      |  <label class="govuk-label" for="event-name">
      |    Event name
      |  </label>
      |  <span id="event-name-hint" class="govuk-hint">
      |    The name you’ll use on promotional material.
      |  </span>
      |  <span id="event-name-error" class="govuk-error-message">
      |    <span class="govuk-visually-hidden">Error:</span> Enter an event name
      |  </span>
      |  <input class="govuk-input govuk-input--error" id="event-name" name="event-name" type="text" aria-describedby="event-name-hint event-name-error">
      |</div>""".stripMargin

  val textareaDefaultHtml =
    """<div class="govuk-form-group">
      |  <label class="govuk-label" for="more-detail">
      |    Can you provide more detail?
      |  </label>
      |  <span id="more-detail-hint" class="govuk-hint">
      |    Do not include personal or financial information, like your National Insurance number or credit card details.
      |  </span>
      |  <textarea class="govuk-textarea" id="more-detail" name="more-detail" rows="5" aria-describedby="more-detail-hint"></textarea>
      |</div>""".stripMargin

  val textareaErrorHtml =
    """<div class="govuk-form-group govuk-form-group--error">
      |  <label class="govuk-label" for="more-detail">
      |    Can you provide more detail?
      |  </label>
      |  <span id="more-detail-hint" class="govuk-hint">
      |    Do not include personal or financial information, like your National Insurance number or credit card details.
      |  </span>
      |  <span id="more-detail-error" class="govuk-error-message">
      |    <span class="govuk-visually-hidden">Error:</span> Enter more detail
      |  </span>
      |  <textarea class="govuk-textarea govuk-textarea--error" id="more-detail" name="more-detail" rows="5" aria-describedby="more-detail-hint more-detail-error"></textarea>
      |</div>""".stripMargin

  val textareaSpecifyingRowsHtml =
    """<div class="govuk-form-group">
      |  <label class="govuk-label" for="more-detail">
      |    Can you provide more detail?
      |  </label>
      |  <span id="more-detail-hint" class="govuk-hint">
      |    Do not include personal or financial information, like your National Insurance number or credit card details.
      |  </span>
      |  <textarea class="govuk-textarea" id="more-detail" name="more-detail" rows="8" aria-describedby="more-detail-hint"></textarea>
      |</div>""".stripMargin

  val radiosDefaultHtml =
    """<div class="govuk-form-group">
      |  <fieldset class="govuk-fieldset" aria-describedby="changed-name-hint">
      |    <legend class="govuk-fieldset__legend govuk-fieldset__legend--xl">
      |      <h1 class="govuk-fieldset__heading">
      |        Have you changed your name?
      |      </h1>
      |    </legend>
      |    <span id="changed-name-hint" class="govuk-hint">
      |      This includes changing your last name or spelling your name differently.
      |    </span>
      |    <div class="govuk-radios govuk-radios--inline">
      |      <div class="govuk-radios__item">
      |        <input class="govuk-radios__input" id="changed-name" name="changed-name" type="radio" value="yes">
      |        <label class="govuk-label govuk-radios__label" for="changed-name">
      |          Yes
      |        </label>
      |      </div>
      |      <div class="govuk-radios__item">
      |        <input class="govuk-radios__input" id="changed-name-2" name="changed-name" type="radio" value="no">
      |        <label class="govuk-label govuk-radios__label" for="changed-name-2">
      |          No
      |        </label>
      |      </div>
      |    </div>
      |  </fieldset>
      |</div>""".stripMargin

  val radiosConditionalRevealHtml =
    """<div class="govuk-form-group">
      |  <fieldset class="govuk-fieldset" aria-describedby="how-contacted-conditional-hint">
      |    <legend class="govuk-fieldset__legend govuk-fieldset__legend--xl">
      |      <h1 class="govuk-fieldset__heading">
      |        How would you prefer to be contacted?
      |      </h1>
      |    </legend>
      |    <span id="how-contacted-conditional-hint" class="govuk-hint">
      |      Select one option.
      |    </span>
      |    <div class="govuk-radios govuk-radios--conditional" data-module="govuk-radios">
      |      <div class="govuk-radios__item">
      |        <input class="govuk-radios__input" id="how-contacted-conditional" name="how-contacted" type="radio" value="email" data-aria-controls="conditional-how-contacted-conditional">
      |        <label class="govuk-label govuk-radios__label" for="how-contacted-conditional">
      |          Email
      |        </label>
      |      </div>
      |      <div class="govuk-radios__conditional govuk-radios__conditional--hidden" id="conditional-how-contacted-conditional">
      |        <div class="govuk-form-group">
      |          <label class="govuk-label" for="contact-by-email">
      |            Email address
      |          </label>
      |          <input class="govuk-input govuk-!-width-one-third" id="contact-by-email" name="contact-by-email" type="email" spellcheck="false">
      |        </div>
      |
      |      </div>
      |      <div class="govuk-radios__item">
      |        <input class="govuk-radios__input" id="how-contacted-conditional-2" name="how-contacted" type="radio" value="phone" data-aria-controls="conditional-how-contacted-conditional-2">
      |        <label class="govuk-label govuk-radios__label" for="how-contacted-conditional-2">
      |          Phone
      |        </label>
      |      </div>
      |      <div class="govuk-radios__conditional govuk-radios__conditional--hidden" id="conditional-how-contacted-conditional-2">
      |        <div class="govuk-form-group">
      |          <label class="govuk-label" for="contact-by-phone">
      |            Phone number
      |          </label>
      |          <input class="govuk-input govuk-!-width-one-third" id="contact-by-phone" name="contact-by-phone" type="tel">
      |        </div>
      |
      |      </div>
      |      <div class="govuk-radios__item">
      |        <input class="govuk-radios__input" id="how-contacted-conditional-3" name="how-contacted" type="radio" value="text" data-aria-controls="conditional-how-contacted-conditional-3">
      |        <label class="govuk-label govuk-radios__label" for="how-contacted-conditional-3">
      |          Text message
      |        </label>
      |      </div>
      |      <div class="govuk-radios__conditional govuk-radios__conditional--hidden" id="conditional-how-contacted-conditional-3">
      |        <div class="govuk-form-group">
      |          <label class="govuk-label" for="contact-by-text">
      |            Mobile phone number
      |          </label>
      |          <input class="govuk-input govuk-!-width-one-third" id="contact-by-text" name="contact-by-text" type="tel">
      |        </div>
      |
      |      </div>
      |    </div>
      |  </fieldset>
      |</div>""".stripMargin

  val radiosStackedHtml =
    """<div class="govuk-form-group">
      |  <fieldset class="govuk-fieldset">
      |    <legend class="govuk-fieldset__legend govuk-fieldset__legend--xl">
      |      <h1 class="govuk-fieldset__heading">
      |        Where do you live?
      |      </h1>
      |    </legend>
      |    <div class="govuk-radios">
      |      <div class="govuk-radios__item">
      |        <input class="govuk-radios__input" id="where-do-you-live" name="where-do-you-live" type="radio" value="england">
      |        <label class="govuk-label govuk-radios__label" for="where-do-you-live">
      |          England
      |        </label>
      |      </div>
      |      <div class="govuk-radios__item">
      |        <input class="govuk-radios__input" id="where-do-you-live-2" name="where-do-you-live" type="radio" value="scotland">
      |        <label class="govuk-label govuk-radios__label" for="where-do-you-live-2">
      |          Scotland
      |        </label>
      |      </div>
      |      <div class="govuk-radios__item">
      |        <input class="govuk-radios__input" id="where-do-you-live-3" name="where-do-you-live" type="radio" value="wales">
      |        <label class="govuk-label govuk-radios__label" for="where-do-you-live-3">
      |          Wales
      |        </label>
      |      </div>
      |      <div class="govuk-radios__item">
      |        <input class="govuk-radios__input" id="where-do-you-live-4" name="where-do-you-live" type="radio" value="northern-ireland">
      |        <label class="govuk-label govuk-radios__label" for="where-do-you-live-4">
      |          Northern Ireland
      |        </label>
      |      </div>
      |    </div>
      |  </fieldset>
      |</div>""".stripMargin

  val radiosHintHtml =
    """<div class="govuk-form-group">
      |  <fieldset class="govuk-fieldset" aria-describedby="sign-in-hint">
      |    <legend class="govuk-fieldset__legend govuk-fieldset__legend--xl">
      |      <h1 class="govuk-fieldset__heading">
      |        How do you want to sign in?
      |      </h1>
      |    </legend>
      |    <span id="sign-in-hint" class="govuk-hint">
      |      You’ll need an account to prove your identity and complete your Self Assessment.
      |    </span>
      |    <div class="govuk-radios">
      |      <div class="govuk-radios__item">
      |        <input class="govuk-radios__input" id="sign-in" name="sign-in" type="radio" value="government-gateway" aria-describedby="sign-in-item-hint">
      |        <label class="govuk-label govuk-radios__label govuk-label--s" for="sign-in">
      |          Sign in with Government Gateway
      |        </label>
      |        <span id="sign-in-item-hint" class="govuk-hint govuk-radios__hint">
      |          You’ll have a user ID if you’ve registered for Self Assessment or filed a tax return online before.
      |        </span>
      |      </div>
      |      <div class="govuk-radios__item">
      |        <input class="govuk-radios__input" id="sign-in-2" name="sign-in" type="radio" value="govuk-verify" aria-describedby="sign-in-2-item-hint">
      |        <label class="govuk-label govuk-radios__label govuk-label--s" for="sign-in-2">
      |          Sign in with GOV.UK Verify
      |        </label>
      |        <span id="sign-in-2-item-hint" class="govuk-hint govuk-radios__hint">
      |          You’ll have an account if you’ve already proved your identity with either Barclays, CitizenSafe, Digidentity, Experian, Post Office, Royal Mail or SecureIdentity.
      |        </span>
      |      </div>
      |    </div>
      |  </fieldset>
      |</div>""".stripMargin

  val radiosDividerHtml =
    """<div class="govuk-form-group">
      |  <fieldset class="govuk-fieldset">
      |    <legend class="govuk-fieldset__legend govuk-fieldset__legend--xl">
      |      <h1 class="govuk-fieldset__heading">
      |        Where do you live?
      |      </h1>
      |    </legend>
      |    <div class="govuk-radios">
      |      <div class="govuk-radios__item">
      |        <input class="govuk-radios__input" id="where-do-you-live" name="where-do-you-live" type="radio" value="england">
      |        <label class="govuk-label govuk-radios__label" for="where-do-you-live">
      |          England
      |        </label>
      |      </div>
      |      <div class="govuk-radios__item">
      |        <input class="govuk-radios__input" id="where-do-you-live-2" name="where-do-you-live" type="radio" value="scotland">
      |        <label class="govuk-label govuk-radios__label" for="where-do-you-live-2">
      |          Scotland
      |        </label>
      |      </div>
      |      <div class="govuk-radios__item">
      |        <input class="govuk-radios__input" id="where-do-you-live-3" name="where-do-you-live" type="radio" value="wales">
      |        <label class="govuk-label govuk-radios__label" for="where-do-you-live-3">
      |          Wales
      |        </label>
      |      </div>
      |      <div class="govuk-radios__item">
      |        <input class="govuk-radios__input" id="where-do-you-live-4" name="where-do-you-live" type="radio" value="northern-ireland">
      |        <label class="govuk-label govuk-radios__label" for="where-do-you-live-4">
      |          Northern Ireland
      |        </label>
      |      </div>
      |      <div class="govuk-radios__divider">or</div>
      |      <div class="govuk-radios__item">
      |        <input class="govuk-radios__input" id="where-do-you-live-6" name="where-do-you-live" type="radio" value="abroad">
      |        <label class="govuk-label govuk-radios__label" for="where-do-you-live-6">
      |          I am a British citizen living abroad
      |        </label>
      |      </div>
      |    </div>
      |  </fieldset>
      |</div>""".stripMargin

  val radiosSmallHtml =
    """<div class="govuk-form-group">
      |  <fieldset class="govuk-fieldset">
      |    <legend class="govuk-fieldset__legend govuk-fieldset__legend--m">
      |      <h1 class="govuk-fieldset__heading">
      |        Filter
      |      </h1>
      |    </legend>
      |    <div class="govuk-radios govuk-radios--small">
      |      <div class="govuk-radios__item">
      |        <input class="govuk-radios__input" id="changed-name" name="changed-name" type="radio" value="month">
      |        <label class="govuk-label govuk-radios__label" for="changed-name">
      |          Monthly
      |        </label>
      |      </div>
      |      <div class="govuk-radios__item">
      |        <input class="govuk-radios__input" id="changed-name-2" name="changed-name" type="radio" value="year">
      |        <label class="govuk-label govuk-radios__label" for="changed-name-2">
      |          Yearly
      |        </label>
      |      </div>
      |    </div>
      |  </fieldset>
      |</div>""".stripMargin

  val radiosErrorHtml =
    """<div class="govuk-form-group govuk-form-group--error">
      |  <fieldset class="govuk-fieldset" aria-describedby="changed-name-hint changed-name-error">
      |    <legend class="govuk-fieldset__legend govuk-fieldset__legend--xl">
      |      <h1 class="govuk-fieldset__heading">
      |        Have you changed your name?
      |      </h1>
      |    </legend>
      |    <span id="changed-name-hint" class="govuk-hint">
      |      This includes changing your last name or spelling your name differently.
      |    </span>
      |    <span id="changed-name-error" class="govuk-error-message">
      |      <span class="govuk-visually-hidden">Error:</span> Select yes if you have changed your name
      |    </span>
      |    <div class="govuk-radios govuk-radios--inline">
      |      <div class="govuk-radios__item">
      |        <input class="govuk-radios__input" id="changed-name" name="changed-name" type="radio" value="yes">
      |        <label class="govuk-label govuk-radios__label" for="changed-name">
      |          Yes
      |        </label>
      |      </div>
      |      <div class="govuk-radios__item">
      |        <input class="govuk-radios__input" id="changed-name-2" name="changed-name" type="radio" value="no">
      |        <label class="govuk-label govuk-radios__label" for="changed-name-2">
      |          No
      |        </label>
      |      </div>
      |    </div>
      |  </fieldset>
      |</div>""".stripMargin

  val summaryListDefaultHtml =
    """<dl class="govuk-summary-list">
      |  <div class="govuk-summary-list__row">
      |    <dt class="govuk-summary-list__key">
      |      Name
      |    </dt>
      |    <dd class="govuk-summary-list__value">
      |      Sarah Philips
      |    </dd>
      |    <dd class="govuk-summary-list__actions">
      |      <a class="govuk-link" href="#">
      |        Change<span class="govuk-visually-hidden"> name</span>
      |      </a>
      |    </dd>
      |  </div>
      |  <div class="govuk-summary-list__row">
      |    <dt class="govuk-summary-list__key">
      |      Date of birth
      |    </dt>
      |    <dd class="govuk-summary-list__value">
      |      5 January 1978
      |    </dd>
      |    <dd class="govuk-summary-list__actions">
      |      <a class="govuk-link" href="#">
      |        Change<span class="govuk-visually-hidden"> date of birth</span>
      |      </a>
      |    </dd>
      |  </div>
      |  <div class="govuk-summary-list__row">
      |    <dt class="govuk-summary-list__key">
      |      Contact information
      |    </dt>
      |    <dd class="govuk-summary-list__value">
      |      72 Guild Street<br>London<br>SE23 6FH
      |    </dd>
      |    <dd class="govuk-summary-list__actions">
      |      <a class="govuk-link" href="#">
      |        Change<span class="govuk-visually-hidden"> contact information</span>
      |      </a>
      |    </dd>
      |  </div>
      |  <div class="govuk-summary-list__row">
      |    <dt class="govuk-summary-list__key">
      |      Contact details
      |    </dt>
      |    <dd class="govuk-summary-list__value">
      |      <p class="govuk-body">07700 900457</p>
      |      <p class="govuk-body">sarah.phillips@example.com</p>
      |    </dd>
      |    <dd class="govuk-summary-list__actions">
      |      <a class="govuk-link" href="#">
      |        Change<span class="govuk-visually-hidden"> contact details</span>
      |      </a>
      |    </dd>
      |  </div>
      |</dl>""".stripMargin

  val summaryListWithoutActionsHtml =
    """<dl class="govuk-summary-list">
      |  <div class="govuk-summary-list__row">
      |    <dt class="govuk-summary-list__key">
      |      Name
      |    </dt>
      |    <dd class="govuk-summary-list__value">
      |      Sarah Philips
      |    </dd>
      |  </div>
      |  <div class="govuk-summary-list__row">
      |    <dt class="govuk-summary-list__key">
      |      Date of birth
      |    </dt>
      |    <dd class="govuk-summary-list__value">
      |      5 January 1978
      |    </dd>
      |  </div>
      |  <div class="govuk-summary-list__row">
      |    <dt class="govuk-summary-list__key">
      |      Contact information
      |    </dt>
      |    <dd class="govuk-summary-list__value">
      |      72 Guild Street<br>London<br>SE23 6FH
      |    </dd>
      |  </div>
      |  <div class="govuk-summary-list__row">
      |    <dt class="govuk-summary-list__key">
      |      Contact details
      |    </dt>
      |    <dd class="govuk-summary-list__value">
      |      <p class="govuk-body">07700 900457</p>
      |      <p class="govuk-body">sarah.phillips@example.com</p>
      |    </dd>
      |  </div>
      |</dl>""".stripMargin

  val summaryListWithoutBordersHtml =
    """<dl class="govuk-summary-list govuk-summary-list--no-border">
      |  <div class="govuk-summary-list__row">
      |    <dt class="govuk-summary-list__key">
      |      Name
      |    </dt>
      |    <dd class="govuk-summary-list__value">
      |      Sarah Philips
      |    </dd>
      |  </div>
      |  <div class="govuk-summary-list__row">
      |    <dt class="govuk-summary-list__key">
      |      Date of birth
      |    </dt>
      |    <dd class="govuk-summary-list__value">
      |      5 January 1978
      |    </dd>
      |  </div>
      |  <div class="govuk-summary-list__row">
      |    <dt class="govuk-summary-list__key">
      |      Contact information
      |    </dt>
      |    <dd class="govuk-summary-list__value">
      |      72 Guild Street<br>London<br>SE23 6FH
      |    </dd>
      |  </div>
      |  <div class="govuk-summary-list__row">
      |    <dt class="govuk-summary-list__key">
      |      Contact details
      |    </dt>
      |    <dd class="govuk-summary-list__value">
      |      <p class="govuk-body">07700 900457</p>
      |      <p class="govuk-body">sarah.phillips@example.com</p>
      |    </dd>
      |  </div>
      |</dl>""".stripMargin
}
