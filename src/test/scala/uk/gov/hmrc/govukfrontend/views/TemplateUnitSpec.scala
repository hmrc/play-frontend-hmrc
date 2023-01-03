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
    "date-input-with-values"
  )
  private val skipBecauseOfAttributeOrdering          = Seq(
    "details-attributes",
    "warning-text-attributes",
    "breadcrumbs-attributes"
  )
  /* skipBecauseRequiredItemsSeemToBeMissing are for tests that require properties that are missing from the nunjucks
     template example data, the reason its missing is because of the way the govuk frontend team setup there tests.
     https://github.com/alphagov/govuk-frontend/issues/2834
   */
  private val skipBecauseRequiredItemsSeemToBeMissing = Seq(
    "select-with-falsey-values",
    "select-attributes-on-items",
    "skip-link-default-values",
    "skip-link-html-as-text",
    "skip-link-html",
    "skip-link-attributes",
    "skip-link-classes",
    "skip-link-custom-text",
    "character-count-spellcheck-enabled",
    "character-count-spellcheck-disabled",
    "character-count-custom-classes-on-countMessage",
    "checkboxes-item-checked-overrides-values",
    "checkboxes-with-pre-checked-values",
    "radios-prechecked-using-value",
    "radios-with-conditional-items-and-pre-checked-value",
    "select-with-selected-value"
  )
  private val skipBecauseChangesNeededWithGDS         = Seq(
    "checkboxes-with-falsey-values",
    "radios-with-falsey-items",
    "accordion-with-falsey-values"
  )

  override protected val skippedExamples: Seq[String] = skipBecauseOfJsonValidation ++
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
