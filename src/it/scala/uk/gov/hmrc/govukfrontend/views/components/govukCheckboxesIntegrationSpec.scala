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

package uk.gov.hmrc.govukfrontend.views
package components

import play.twirl.api.HtmlFormat
import uk.gov.hmrc.govukfrontend.support.TemplateIntegrationSpec
import uk.gov.hmrc.govukfrontend.views.html.components._
import uk.gov.hmrc.govukfrontend.views.viewmodels.checkboxes.Checkboxes
import uk.gov.hmrc.govukfrontend.views.viewmodels.checkboxes.Generators._
import scala.util.Try

object govukCheckboxesIntegrationSpec
    extends TemplateIntegrationSpec[Checkboxes, GovukCheckboxes](govukComponentName = "govukCheckboxes", seed = None) {

  override def render(checkboxes: Checkboxes): Try[HtmlFormat.Appendable] =
    Try(component(checkboxes))

  /* The classifiers here are very clunky so just an experiment that is not very useful
     Think of a better way to look at distributions...

  override def classifiers(checkboxes: Checkboxes): Stream[ClassifyParams] = {
    val checkboxesClassifiers =
      (checkboxes.describedBy.forall(_.isEmpty), "empty describedBy", "non-empty describedBy") #::
        (checkboxes.fieldset.isEmpty, "empty fieldsetParams", "non-empty fieldsetParams") #::
        (checkboxes.hint.isEmpty, "empty hintParams", "non-empty hintParams") #::
        (checkboxes.errorMessage.isEmpty, "empty errorMessageParams", "non-empty errorMessageParams") #::
        (checkboxes.formGroupClasses.isEmpty, "empty formGroupClasses", "non-empty formGroupClasses") #::
        (checkboxes.idPrefix.forall(_.isEmpty), "empty idPrefix", "non-empty idPrefix") #::
        (checkboxes.name.isEmpty, "empty name", "non-empty name") #::
        (checkboxes.items.isEmpty, "empty items", "non-empty items") #::
        (checkboxes.classes.isEmpty, "empty classes", "non-empty classes") #::
        (checkboxes.attributes.isEmpty, "empty attributes", "non-empty attributes") #::
        Stream.empty[ClassifyParams]
    checkboxesClassifiers #::: checkboxes.fieldset
      .map(govukFieldsetIntegrationSpec.classifiers)
      .getOrElse(Stream.empty[ClassifyParams])
  }
   */
}
