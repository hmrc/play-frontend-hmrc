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
                (__ \ "content").readWithDefault[Content](defaultObject.content) and
                (__ \ "classes").readWithDefault[String](defaultObject.classes)
        )(TaskListItemStatus.apply _)

    implicit def jsonWrites: OWrites[TaskListItemStatus] =
        (
            (__ \ "tag").writeNullable[Tag] and
                (__ \ "content").write[Content] and
                (__ \ "classes").write[String]
        )(unlift(TaskListItemStatus.unapply))
}
