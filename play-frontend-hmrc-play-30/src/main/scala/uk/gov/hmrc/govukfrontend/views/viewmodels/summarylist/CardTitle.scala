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

package uk.gov.hmrc.govukfrontend.views.viewmodels
package summarylist

import play.api.libs.functional.syntax._
import play.api.libs.json.{OWrites, Reads, __}
import uk.gov.hmrc.govukfrontend.views.viewmodels.content.{Content, Empty}

case class CardTitle(
  content: Content = Empty,
  headingLevel: Option[Int] = None,
  classes: String = ""
)
object CardTitle {

  def defaultObject: CardTitle = CardTitle()

  implicit def jsonReads: Reads[CardTitle] = (
    Content.reads and
      (__ \ "headingLevel").readNullable[Int] and
      (__ \ "classes").readWithDefault[String](defaultObject.classes)
  )(CardTitle.apply _)

  implicit def jsonWrites: OWrites[CardTitle] = (
    Content.writesContent() and
      (__ \ "headingLevel").writeNullable[Int] and
      (__ \ "classes").write[String]
  )(o => WritesUtils.unapplyCompat(unapply)(o))

}
