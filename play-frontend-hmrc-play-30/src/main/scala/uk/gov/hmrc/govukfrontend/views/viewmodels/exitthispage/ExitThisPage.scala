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

package uk.gov.hmrc.govukfrontend.views.viewmodels.exitthispage

import play.api.libs.functional.syntax._
import play.api.libs.json.{OWrites, Reads, __}
import uk.gov.hmrc.govukfrontend.views.viewmodels.CommonJsonFormats.attributesReads
import uk.gov.hmrc.govukfrontend.views.viewmodels.content.{Content, Empty}

case class ExitThisPage(
  content: Content = Empty,
  redirectUrl: Option[String] = None,
  id: Option[String] = None,
  classes: String = "",
  attributes: Map[String, String] = Map.empty,
  activatedText: Option[String] = None,
  timedOutText: Option[String] = None,
  pressTwoMoreTimesText: Option[String] = None,
  pressOneMoreTimeText: Option[String] = None
)

object ExitThisPage {

  def defaultObject: ExitThisPage = ExitThisPage()

  implicit def jsonReads: Reads[ExitThisPage] =
    (
      Content.readsWithDefault(defaultObject.content) and
        (__ \ "redirectUrl").readNullable[String] and
        (__ \ "id").readNullable[String] and
        (__ \ "classes").readWithDefault[String](defaultObject.classes) and
        (__ \ "attributes").readWithDefault[Map[String, String]](defaultObject.attributes)(attributesReads) and
        (__ \ "activatedText").readNullable[String] and
        (__ \ "timedOutText").readNullable[String] and
        (__ \ "pressTwoMoreTimesText").readNullable[String] and
        (__ \ "pressOneMoreTimeText").readNullable[String]
    )(ExitThisPage.apply _)

  implicit def jsonWrites: OWrites[ExitThisPage] =
    (
      Content.writes and
        (__ \ "redirectUrl").writeNullable[String] and
        (__ \ "id").writeNullable[String] and
        (__ \ "classes").write[String] and
        (__ \ "attributes").write[Map[String, String]] and
        (__ \ "activatedText").writeNullable[String] and
        (__ \ "timedOutText").writeNullable[String] and
        (__ \ "pressTwoMoreTimesText").writeNullable[String] and
        (__ \ "pressOneMoreTimeText").writeNullable[String]
    )(etp => (etp.content, etp.redirectUrl, etp.id, etp.classes, etp.attributes, etp.activatedText, etp.timedOutText, etp.pressTwoMoreTimesText, etp.pressOneMoreTimeText))
}
