/*
 * Copyright 2022 HM Revenue & Customs
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

import play.api.libs.json._
import play.twirl.api.{HtmlFormat, Template1}
import uk.gov.hmrc.helpers.views.TemplateTestHelper

import scala.reflect.ClassTag
import scala.util.Try

/**
  * Base class for unit testing against test fixtures generated from govuk-frontend's yaml documentation files for
  * components
  */
abstract class TemplateUnitSpec[T: Reads, C <: Template1[T, HtmlFormat.Appendable]: ClassTag](
  govukComponentName: String
) extends TemplateTestHelper[T](govukComponentName) {

  override protected val baseFixturesDirectory: String = "/fixtures/govuk-frontend"

  private val skipBecauseOfJsonValidation             = Seq(
    "date-input-with-values",
    "summary-list-value-with-html"
  )
  private val skipBecauseOfAttributeOrdering          = Seq(
    "details-attributes",
    "warning-text-attributes",
    "breadcrumbs-attributes"
  )
  private val skipBecauseRequiredItemsSeemToBeMissing = Seq(
    "select-with-falsey-values",
    "date-input-items-with-classes",
    "date-input-with-id-on-items",
    "date-input-fieldset-html",
    "date-input-classes",
    "date-input-with-empty-items",
    "date-input-items-without-classes",
    "date-input-no-data",
    "date-input-attributes",
    "date-input-custom-pattern",
    "date-input-with-nested-name",
    "select-with-describedBy",
    "select-error",
    "select-error-and-describedBy",
    "select-hint",
    "select-hint-and-describedBy",
    "select-attributes",
    "select-attributes-on-items",
    "input-hint-with-describedBy",
    "input-classes",
    "input-with-error,-hint-and-describedBy",
    "input-with-error-and-hint",
    "input-attributes",
    "input-inputmode",
    "input-with-describedBy",
    "input-value",
    "input-error-with-describedBy",
    "input-custom-type",
    "textarea-with-error-message-and-described-by",
    "textarea-attributes",
    "textarea-with-describedBy",
    "textarea-with-hint-and-error-message",
    "textarea-with-hint,-error-message-and-described-by",
    "textarea-with-hint-and-described-by",
    "textarea-classes",
    "skip-link-default-values",
    "skip-link-html-as-text",
    "skip-link-html",
    "skip-link-attributes",
    "skip-link-classes",
    "skip-link-custom-text",
    "checkboxes-with-hints-on-items",
    "character-count-spellcheck-enabled",
    "character-count-custom-classes-with-error-message",
    "character-count-attributes",
    "character-count-formGroup-with-classes",
    "character-count-spellcheck-disabled",
    "character-count-custom-classes-on-countMessage",
    "character-count-classes",
    "file-upload-with-error-and-describedBy",
    "file-upload-attributes",
    "file-upload-with-describedBy",
    "file-upload-with-error,-describedBy-and-hint",
    "file-upload-classes",
    "file-upload-error",
    "file-upload-with-hint-and-describedBy",
    "button-input-type",
    "button-input-classes",
    "button-input-attributes"
  )
  private val skipBecauseChangesNeededWithGDS         = Seq(
    "checkboxes-with-falsey-values",
    "radios-with-falsey-items",
    "accordion-with-falsey-values"
  )

  override protected val skip = skipBecauseOfJsonValidation ++
    skipBecauseOfAttributeOrdering ++ skipBecauseRequiredItemsSeemToBeMissing ++
    skipBecauseChangesNeededWithGDS

  protected val component: C = app.injector.instanceOf[C]

  /**
    * Calls the Twirl template with the given parameters and returns the resulting markup
    *
    * @param templateParams  : T
    * @return [[Try[HtmlFormat.Appendable]]] containing the markup
    */
  def render(templateParams: T): Try[HtmlFormat.Appendable] =
    Try(component.render(templateParams))

  matchTwirlAndNunjucksHtml(fixturesDirs)

}
