/*
 * Copyright 2021 HM Revenue & Customs
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

package uk.gov.hmrc.govukfrontend.views.viewmodels.layout
import uk.gov.hmrc.govukfrontend.views.viewmodels.CommonJsonFormats._

import play.api.libs.json._
import play.twirl.api.Html
import uk.gov.hmrc.govukfrontend.views.viewmodels.footer.FooterItem

import scala.collection.Seq

final case class Layout(
  pageTitle: Option[String] = None,
  head: Option[Html] = None,
  header: Option[Html] = None,
  beforeContent: Option[Html] = None,
  footer: Option[Html] = None,
  footerItems: Option[Seq[FooterItem]] = None,
  bodyEnd: Option[Html] = None,
  scripts: Option[Html] = None,
  content: Option[Html] = None
)

object Layout {
  implicit val templateWrites: OWrites[Layout] = Json.writes[Layout]

  implicit val templateReads: Reads[Layout] = Json.reads[Layout]
}
