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

package uk.gov.hmrc.hmrcfrontend.views.components

import play.twirl.api.HtmlFormat
import uk.gov.hmrc.hmrcfrontend.support.TemplateIntegrationSpec
import uk.gov.hmrc.hmrcfrontend.views.html.components._
// We use the below instead of a true arbitrary as the `hmrc-frontend` Nunjucks
// model of Header is less flexible and specifically requests the href for `cy`
// and `en` to be defined.
import uk.gov.hmrc.hmrcfrontend.views.viewmodels.header.Generators._
import uk.gov.hmrc.hmrcfrontend.views.viewmodels.header.Header

import scala.util.Try

object hmrcHeaderIntegrationSpec
  extends TemplateIntegrationSpec[Header](hmrcComponentName = "hmrcHeader", seed = None) {

  override def render(header: Header): Try[HtmlFormat.Appendable] =
    Try(HmrcHeader(header))
}
