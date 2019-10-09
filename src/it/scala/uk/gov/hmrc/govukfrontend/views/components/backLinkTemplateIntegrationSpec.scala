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
import uk.gov.hmrc.govukfrontend.views.viewmodels.backlink.BackLinkParams
import uk.gov.hmrc.govukfrontend.views.viewmodels.backlink.Generators._
import scala.util.Try

object backLinkTemplateIntegrationSpec
    extends TemplateIntegrationSpec[BackLinkParams](govukComponentName = "govukBackLink", seed = None) {

  override def render(backLinkParams: BackLinkParams): Try[HtmlFormat.Appendable] =
    Try(BackLink(backLinkParams))

  override def classifiers(backLinkParams: BackLinkParams): Stream[ClassifyParams] =
    (backLinkParams.href.isEmpty, "empty href", "non-empty href") #::
      (backLinkParams.classes.isEmpty, "empty classes", "non-empty classes") #::
      (backLinkParams.attributes.isEmpty, "empty attributes", "non-empty attributes") #::
      (backLinkParams.content.nonEmpty && backLinkParams.content.isInstanceOf[HtmlContent], "non-empty Html", ()) #::
      (backLinkParams.content.nonEmpty && backLinkParams.content.isInstanceOf[Text], "non-empty Text", ()) #::
      (!backLinkParams.content.nonEmpty, "empty content", ()) #::
      Stream.empty[ClassifyParams]
}
