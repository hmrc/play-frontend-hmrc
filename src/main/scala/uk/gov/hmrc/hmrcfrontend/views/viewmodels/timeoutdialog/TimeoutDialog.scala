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

package uk.gov.hmrc.hmrcfrontend.views.viewmodels.timeoutdialog

import play.api.libs.json._
import uk.gov.hmrc.hmrcfrontend.views.viewmodels.JsonDefaultValueFormatter

case class TimeoutDialog(
  name: String                        = "",
  language: Option[String]            = None,
  timeout: Option[Int]                = None,
  countdown: Option[Int]              = None,
  keepAliveUrl: Option[String]        = None,
  signOutUrl: Option[String]          = None,
  timeoutUrl: Option[String]          = None,
  title: Option[String]               = None,
  message: Option[String]             = None,
  messageSuffix: Option[String]       = None,
  keepAliveButtonText: Option[String] = None,
  signOutButtonText: Option[String]   = None
)

object TimeoutDialog extends JsonDefaultValueFormatter[TimeoutDialog] {

  override def defaultObject: TimeoutDialog = TimeoutDialog()

  override def defaultReads: Reads[TimeoutDialog] = Json.reads[TimeoutDialog]

  override implicit def jsonWrites: OWrites[TimeoutDialog] = Json.writes[TimeoutDialog]
}
