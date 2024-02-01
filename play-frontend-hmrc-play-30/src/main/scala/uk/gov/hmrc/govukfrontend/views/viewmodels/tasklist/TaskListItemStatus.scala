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

package uk.gov.hmrc.govukfrontend.views.viewmodels.tasklist

import uk.gov.hmrc.govukfrontend.views.viewmodels.tag.Tag
import uk.gov.hmrc.govukfrontend.views.viewmodels.content.{Content, Empty}
import play.api.libs.functional.syntax._
import play.api.libs.json._

case class TaskListItemStatus(
    tag: Option[Tag] = None,
    content: Content = Empty,
    classes: String = ""
)

object TaskListItemStatus {
    def defaultObject: TaskListItemStatus = TaskListItemStatus()

    implicit def jsonReads: Reads[TaskListItemStatus] =
        (
            (__ \ "tag").readNullable[Tag] and
                Content.reads and
                (__ \ "classes").readWithDefault[String](defaultObject.classes)
        )(TaskListItemStatus.apply _)

    implicit def jsonWrites: OWrites[TaskListItemStatus] =
        (
            (__ \ "tag").writeNullable[Tag] and
                Content.writes and
                (__ \ "classes").write[String]
        )(unlift(TaskListItemStatus.unapply))
}
