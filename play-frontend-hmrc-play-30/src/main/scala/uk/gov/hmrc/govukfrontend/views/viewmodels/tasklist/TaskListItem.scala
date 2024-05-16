/*
 * Copyright 2024 HM Revenue & Customs
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
package tasklist

import uk.gov.hmrc.govukfrontend.views.viewmodels.content.{Content, Empty}
import uk.gov.hmrc.govukfrontend.views.viewmodels.hint.Hint
import play.api.libs.functional.syntax._
import play.api.libs.json._

case class TaskListItem(
  title: TaskListItemTitle = TaskListItemTitle(),
  hint: Option[Hint] = None,
  status: TaskListItemStatus = TaskListItemStatus(),
  href: Option[String] = None,
  classes: String = ""
)

object TaskListItem {
  def defaultObject: TaskListItem = TaskListItem()

  implicit def jsonReads: Reads[TaskListItem] =
    (
      (__ \ "title").readWithDefault[TaskListItemTitle](defaultObject.title) and
        (__ \ "hint").readNullable[Hint] and
        (__ \ "status").readWithDefault[TaskListItemStatus](defaultObject.status) and
        (__ \ "href").readNullable[String] and
        (__ \ "classes").readWithDefault[String](defaultObject.classes)
    )(TaskListItem.apply _)

  implicit def jsonWrites: OWrites[TaskListItem] =
    (
      (__ \ "title").write[TaskListItemTitle] and
        (__ \ "hint").writeNullable[Hint] and
        (__ \ "status").write[TaskListItemStatus] and
        (__ \ "href").writeNullable[String] and
        (__ \ "classes").write[String]
    )(o => WritesUtils.unapplyCompat(unapply)(o))
}
