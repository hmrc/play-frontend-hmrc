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

package uk.gov.hmrc.govukfrontend.views.components

import play.twirl.api.HtmlFormat
import uk.gov.hmrc.govukfrontend.support.ScalaCheckUtils.ClassifyParams
import uk.gov.hmrc.govukfrontend.support.TemplateIntegrationSpec
import uk.gov.hmrc.govukfrontend.views.html.components._
import uk.gov.hmrc.govukfrontend.views.viewmodels.fieldset.Fieldset
import uk.gov.hmrc.govukfrontend.views.viewmodels.fieldset.Generators._
import scala.util.Try

object govukFieldsetIntegrationSpec
    extends TemplateIntegrationSpec[Fieldset, GovukFieldset](govukComponentName = "govukFieldset", seed = None) {

  override def render(fieldset: Fieldset): Try[HtmlFormat.Appendable] =
    Try(component(fieldset))

  /* Again just an idea for classifiers...

  override def classifiers(fieldset: Fieldset): Stream[ClassifyParams] =
    (fieldset.describedBy.forall(_.isEmpty), "empty describedBy", "non-empty describedBy") #::
      (fieldset.legend.isEmpty, "empty legend", "non-empty legend") #::
      (fieldset.classes.isEmpty, "empty classes", "non-empty classes") #::
      (fieldset.role.forall(_.isEmpty), "empty role", "non-empty role") #::
      (fieldset.attributes.isEmpty, "empty attributes", "non-empty attributes") #::
      Stream
      .empty[ClassifyParams]
      .map {
        case (condition, ifTrue, ifFalse) =>
          // prefix previous classifiers with 'fieldset' to avoid name clashes when reporting
          (condition, s"fieldset $ifTrue", s"fieldset $ifFalse")
      }
   */
}
