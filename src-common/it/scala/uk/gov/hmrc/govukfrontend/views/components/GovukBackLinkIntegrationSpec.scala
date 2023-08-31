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
package components

import play.twirl.api.HtmlFormat
import uk.gov.hmrc.support.ScalaCheckUtils.ClassifyParams
import uk.gov.hmrc.govukfrontend.views.GovukFrontendDependency.govukFrontendVersion
import uk.gov.hmrc.govukfrontend.views.html.components._
import uk.gov.hmrc.govukfrontend.views.viewmodels.backlink.BackLink
import uk.gov.hmrc.govukfrontend.views.viewmodels.backlink.Generators._
import uk.gov.hmrc.helpers.MessagesSupport
import uk.gov.hmrc.support.TemplateIntegrationBaseSpec

import scala.collection.compat.immutable.LazyList
import scala.util.Try

object GovukBackLinkIntegrationSpec
    extends TemplateIntegrationBaseSpec[BackLink](
      componentName = "govukBackLink",
      seed = None
    )
    with MessagesSupport {

  protected val libraryName: String = "govuk"

  protected val libraryVersion: String = govukFrontendVersion

  private val component = app.injector.instanceOf[GovukBackLink]

  override def render(notificationBanner: BackLink): Try[HtmlFormat.Appendable] =
    Try(component(notificationBanner))

  override def classifiers(backLink: BackLink): LazyList[ClassifyParams] =
    (backLink.href.isEmpty, "empty href", "non-empty href") #::
      (backLink.href.length > 10, "long href", "short href") #::
      (backLink.classes.isEmpty, "empty classes", "non-empty classes") #::
      (backLink.attributes.isEmpty, "empty attributes", "non-empty attributes") #::
      (backLink.attributes.values.exists(_.isEmpty), "empty attributes values", "non-empty attributes values") #::
      (!backLink.content.nonEmpty, "empty content", "non-empty content") #::
      LazyList.empty[ClassifyParams]

}
