/*
 * Copyright 2020 HM Revenue & Customs
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

package uk.gov.hmrc.govukfrontend.views.viewmodels.template
import uk.gov.hmrc.govukfrontend.views.viewmodels.CommonJsonFormats._

import play.api.libs.json.{Json, OWrites, Reads}
import play.twirl.api.Html

final case class Template(
  htmlLang: Option[String]      = None,
  pageTitleLang: Option[String] = None,
  mainLang: Option[String]      = None,
  htmlClasses: Option[String]   = None,
  themeColor: Option[String]    = None,
  bodyClasses: Option[String]   = None,
  pageTitle: Option[String]     = None,
  headIcons: Option[Html]       = None,
  head: Option[Html]            = None,
  bodyStart: Option[Html]       = None,
  skipLink: Option[Html]        = None,
  header: Option[Html]          = None,
  footer: Option[Html]          = None,
  bodyEnd: Option[Html]         = None,
  mainClasses: Option[String]   = None,
  beforeContent: Option[Html]   = None,
  content: Option[Html]         = None
)

object Template {
  implicit val templateWrites: OWrites[Template] = Json.writes[Template]

  implicit val templateReads: Reads[Template] = Json.reads[Template]
}
