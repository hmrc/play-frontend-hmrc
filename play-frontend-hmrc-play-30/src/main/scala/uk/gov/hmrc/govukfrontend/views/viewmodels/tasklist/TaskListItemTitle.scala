package uk.gov.hmrc.govukfrontend.views.viewmodels.tasklist

import uk.gov.hmrc.govukfrontend.views.viewmodels.content.{Content, Empty}
import play.api.libs.functional.syntax._
import play.api.libs.json._

case class TaskListItemTitle(
    content: Content = Empty,
    classes: String = ""
)

object TaskListItemTitle {
    def defaultObject: TaskListItemTitle = TaskListItemTitle()

    implicit def jsonReads: Reads[TaskListItemTitle] = 
        (
            (__ \ "content").readWithDefault[Content](defaultObject.content) and
                (__ \ "classes").readWithDefault[String](defaultObject.classes)
        )(TaskListItemTitle.apply _)

    implicit def jsonWrites: OWrites[TaskListItemTitle] =
        (
            (__ \ "content").write[Content] and
                (__ \ "classes").write[String]
        )(unlift(TaskListItemTitle.unapply))
}