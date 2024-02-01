package uk.gov.hmrc.govukfrontend.views.viewmodels.tasklist

import uk.gov.hmrc.govukfrontend.views.viewmodels.content.{Content, Empty}
import play.api.libs.functional.syntax._
import play.api.libs.json._
import uk.gov.hmrc.govukfrontend.views.viewmodels.CommonJsonFormats._

case class TaskListItem(
    title: TaskListItemTitle = TaskListItemTitle(),
    hint: Content = Empty,
    status: TaskListItemStatus = TaskListItemStatus(),
    href: String = "#",
    classes: String = ""
)

object TaskListItem {
    def defaultObject: TaskListItem = TaskListItem()

    implicit def jsonReads: Reads[TaskListItem] = 
        (
            (__ \ "title").readWithDefault[TaskListItemTitle](defaultObject.title) and
                (__ \ "hint").readWithDefault[Content](defaultObject.hint) and
                (__ \ "status").readWithDefault[TaskListItemStatus](defaultObject.status) and
                (__ \ "href").readWithDefault[String](defaultObject.href) and
                (__ \ "classes").readWithDefault[String](defaultObject.classes)
        )(TaskListItem.apply _)

    implicit def jsonWrites: OWrites[TaskListItem] =
        (
            (__ \ "title").write[TaskListItemTitle] and
                (__ \ "hint").write[Content] and
                (__ \ "status").write[TaskListItemStatus] and
                (__ \ "href").writeNullable[String] and
                (__ \ "classes").writeNullable[String]        
        )(unlift(TaskListItem.unapply))
}