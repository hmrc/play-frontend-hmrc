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
import uk.gov.hmrc.govukfrontend.support.ScalaCheckUtils.ClassifyParams
import uk.gov.hmrc.govukfrontend.support.TemplateIntegrationSpec
import uk.gov.hmrc.govukfrontend.views.html.components._
import uk.gov.hmrc.govukfrontend.views.viewmodels.checkboxes.Checkboxes
import uk.gov.hmrc.govukfrontend.views.viewmodels.checkboxes.Generators._
import scala.util.Try

object govukCheckboxesTemplateIntegrationSpec
    extends TemplateIntegrationSpec[Checkboxes](govukComponentName = "govukCheckboxes", seed = None) {

  override def render(checkboxesParams: Checkboxes): Try[HtmlFormat.Appendable] =
    Try(
      GovukCheckboxes(
        Checkboxes(
          describedBy      = checkboxesParams.describedBy,
          fieldset         = checkboxesParams.fieldset,
          hint             = checkboxesParams.hint,
          errorMessage     = checkboxesParams.errorMessage,
          formGroupClasses = checkboxesParams.formGroupClasses,
          idPrefix         = checkboxesParams.idPrefix,
          name             = checkboxesParams.name,
          items            = checkboxesParams.items,
          classes          = checkboxesParams.classes,
          attributes       = checkboxesParams.attributes
        ))
    )

  override def classifiers(checkboxesParams: Checkboxes): Stream[ClassifyParams] = {
    val checkboxesClassifiers =
      (checkboxesParams.describedBy.forall(_.isEmpty), "empty describedBy", "non-empty describedBy") #::
        (checkboxesParams.fieldset.isEmpty, "empty fieldsetParams", "non-empty fieldsetParams") #::
        (checkboxesParams.hint.isEmpty, "empty hintParams", "non-empty hintParams") #::
        (checkboxesParams.errorMessage.isEmpty, "empty errorMessageParams", "non-empty errorMessageParams") #::
        (checkboxesParams.formGroupClasses.isEmpty, "empty formGroupClasses", "non-empty formGroupClasses") #::
        (checkboxesParams.idPrefix.forall(_.isEmpty), "empty idPrefix", "non-empty idPrefix") #::
        (checkboxesParams.name.isEmpty, "empty name", "non-empty name") #::
        (checkboxesParams.items.isEmpty, "empty items", "non-empty items") #::
        (checkboxesParams.classes.isEmpty, "empty classes", "non-empty classes") #::
        (checkboxesParams.attributes.isEmpty, "empty attributes", "non-empty attributes") #::
        Stream.empty[ClassifyParams]
    checkboxesClassifiers #::: checkboxesParams.fieldset
      .map(fieldsetClassifiers)
      .getOrElse(Stream.empty[ClassifyParams])
  }

  def fieldsetClassifiers(fieldsetParams: Fieldset): Stream[ClassifyParams] =
    (fieldsetParams.describedBy.forall(_.isEmpty), "empty describedBy", "non-empty describedBy") #::
      (fieldsetParams.legend.isEmpty, "empty legend", "non-empty legend") #::
      (fieldsetParams.classes.isEmpty, "empty classes", "non-empty classes") #::
      (fieldsetParams.role.forall(_.isEmpty), "empty role", "non-empty role") #::
      (fieldsetParams.attributes.isEmpty, "empty attributes", "non-empty attributes") #::
      Stream
      .empty[ClassifyParams]
      .map {
        case (condition, ifTrue, ifFalse) =>
          // prefix previous classifiers with 'fieldsetParams' to avoid name clashes when reporting
          (condition, s"fieldsetParams $ifTrue", s"fieldsetParams $ifFalse")
      }

}
