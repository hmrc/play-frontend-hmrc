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

import org.scalacheck.Arbitrary._
import org.scalacheck._
import play.twirl.api.HtmlFormat
import uk.gov.hmrc.govukfrontend.support.ScalaCheckUtils.ClassifyParams
import uk.gov.hmrc.govukfrontend.support.TemplateIntegrationSpec
import uk.gov.hmrc.govukfrontend.views.components.CheckboxesParamsGenerators._
import uk.gov.hmrc.govukfrontend.views.html.components._
import uk.gov.hmrc.govukfrontend.views.viewmodels.Generators._
import uk.gov.hmrc.govukfrontend.views.viewmodels.checkboxes.CheckboxesParams
import scala.util.Try

object checkboxesTemplateIntegrationSpec
    extends TemplateIntegrationSpec[CheckboxesParams](govukComponentName = "govukCheckboxes", seed = None) {

  override def render(checkboxesParams: CheckboxesParams): Try[HtmlFormat.Appendable] =
    Try(
      Checkboxes(
        CheckboxesParams(
          describedBy        = checkboxesParams.describedBy,
          fieldsetParams     = checkboxesParams.fieldsetParams,
          hintParams         = checkboxesParams.hintParams,
          errorMessageParams = checkboxesParams.errorMessageParams,
          formGroupClasses   = checkboxesParams.formGroupClasses,
          idPrefix           = checkboxesParams.idPrefix,
          name               = checkboxesParams.name,
          items              = checkboxesParams.items,
          classes            = checkboxesParams.classes,
          attributes         = checkboxesParams.attributes
        ))
    )

  override def classifiers(checkboxesParams: CheckboxesParams): Stream[ClassifyParams] = {
    val checkboxesClassifiers =
      (checkboxesParams.describedBy.forall(_.isEmpty), "empty describedBy", "non-empty describedBy") #::
        (checkboxesParams.fieldsetParams.isEmpty, "empty fieldsetParams", "non-empty fieldsetParams") #::
        (checkboxesParams.hintParams.isEmpty, "empty hintParams", "non-empty hintParams") #::
        (checkboxesParams.errorMessageParams.isEmpty, "empty errorMessageParams", "non-empty errorMessageParams") #::
        (checkboxesParams.formGroupClasses.isEmpty, "empty formGroupClasses", "non-empty formGroupClasses") #::
        (checkboxesParams.idPrefix.forall(_.isEmpty), "empty idPrefix", "non-empty idPrefix") #::
        (checkboxesParams.name.isEmpty, "empty name", "non-empty name") #::
        (checkboxesParams.items.isEmpty, "empty items", "non-empty items") #::
        (checkboxesParams.classes.isEmpty, "empty classes", "non-empty classes") #::
        (checkboxesParams.attributes.isEmpty, "empty attributes", "non-empty attributes") #::
        Stream.empty[ClassifyParams]
    checkboxesClassifiers #::: checkboxesParams.fieldsetParams
      .map(fieldsetClassifiers)
      .getOrElse(Stream.empty[ClassifyParams])
  }

  def fieldsetClassifiers(fieldsetParams: FieldsetParams): Stream[ClassifyParams] =
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

object CheckboxesParamsGenerators {
  implicit val arbCheckboxParams: Arbitrary[CheckboxesParams] = Arbitrary(genCheckboxParams)

  lazy val genCheckboxParams = for {
    describedBy        <- Gen.option(genAlphaStrOftenEmpty())
    fieldsetParams     <- Gen.option(genFieldsetParams)
    hintParams         <- Gen.option(genHintParams)
    errorMessageParams <- Gen.option(genErrorMessageParams)
    formGroupClasses   <- genClasses()
    idPrefix           <- Gen.option(genAlphaStrOftenEmpty())
    name               <- genNonEmptyAlphaStr
    nItems             <- Gen.chooseNum(0, 5)
    items              <- Gen.listOfN(nItems, genCheckboxItem)
    classes            <- genClasses()
    attributes         <- genAttributes()
  } yield
    CheckboxesParams(
      describedBy        = describedBy,
      fieldsetParams     = fieldsetParams,
      hintParams         = hintParams,
      errorMessageParams = errorMessageParams,
      formGroupClasses   = formGroupClasses,
      idPrefix           = idPrefix,
      name               = name,
      items              = items,
      classes            = classes,
      attributes         = attributes
    )

  lazy val genCheckboxItem = for {
    content         <- genContent
    id              <- Gen.option(genAlphaStrOftenEmpty())
    name            <- Gen.option(genAlphaStrOftenEmpty())
    value           <- genAlphaStrOftenEmpty()
    label           <- Gen.option(genLabelParams)
    hint            <- Gen.option(genHintParams)
    checked         <- arbBool.arbitrary
    conditionalHtml <- Gen.option(arbHtml.arbitrary)
    disabled        <- arbBool.arbitrary
    attributes      <- genAttributes()
  } yield
    CheckboxItem(
      content         = content,
      id              = id,
      name            = name,
      value           = value,
      label           = label,
      hint            = hint,
      checked         = checked,
      conditionalHtml = conditionalHtml,
      disabled        = disabled,
      attributes      = attributes
    )

  lazy val genLabelParams = for {
    forAttr       <- Gen.option(genAlphaStrOftenEmpty())
    isPageHeading <- arbBool.arbitrary
    classes       <- genClasses()
    attributes    <- genAttributes()
    content       <- genContent
  } yield
    LabelParams(
      forAttr       = forAttr,
      isPageHeading = isPageHeading,
      classes       = classes,
      attributes    = attributes,
      content       = content)

  lazy val genFieldsetParams: Gen[FieldsetParams] = for {
    describedBy <- Gen.option(genAlphaStrOftenEmpty())
    legend      <- Gen.option(genLegend)
    classes     <- genClasses()
    role        <- Gen.option(genAlphaStrOftenEmpty())
    attributes  <- genAttributes()
  } yield
    FieldsetParams(describedBy = describedBy, legend = legend, classes = classes, role = role, attributes = attributes)

  lazy val genLegend: Gen[Legend] = for {
    content       <- genContent
    classes       <- genClasses()
    isPageHeading <- arbBool.arbitrary
  } yield Legend(content = content, classes = classes, isPageHeading)

  lazy val genHintParams = for {
    id         <- Gen.option(genAlphaStrOftenEmpty())
    classes    <- genClasses()
    attributes <- genAttributes()
    content    <- genContent
  } yield HintParams(id = id, classes = classes, attributes = attributes, content = content)

  lazy val genErrorMessageParams = for {
    classes            <- genClasses()
    attributes         <- genAttributes()
    visuallyHiddenText <- Gen.option(genAlphaStrOftenEmpty())
    content            <- genContent
  } yield
    ErrorMessageParams(
      classes            = classes,
      attributes         = attributes,
      visuallyHiddenText = visuallyHiddenText,
      content            = content)
}
