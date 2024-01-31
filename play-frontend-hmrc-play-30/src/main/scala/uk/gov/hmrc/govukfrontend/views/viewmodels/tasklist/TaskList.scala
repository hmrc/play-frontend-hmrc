package uk.gov.hmrc.govukfrontend.views.viewmodels.tasklist

import play.api.libs.functional.syntax._
import play.api.libs.json._
import uk.gov.hmrc.govukfrontend.views.viewmodels.CommonJsonFormats._

case class TaskList(
    items: List[TaskListItem] = List.empty,
    classes: String = "",
    attributes: Map[String, String] = Map.empty,
    idPrefix: String = ""
)

object TaskList {

    def defaultObject: TaskList = TaskList()

    implicit def jsonReads: Reads[TaskList] = 
        (
            (__ \ "items").readWithDefault[List[TaskListItem]](defaultObject.items) and
                (__ \ "classes").readWithDefault[String](defaultObject.classes) and
                (__ \ "attributes").readWithDefault[Map[String, String]](defaultObject.attributes)(attributesReads) and
                (__ \ "idPrefix").readWithDefault[String](defaultObject.idPrefix)
        )(TaskList.apply _)

  implicit def jsonWrites: OWrites[TaskList] =
    (
      (__ \ "items").write[List[TaskListItem]] and
        (__ \ "classes").write[String] and
        (__ \ "attributes").write[Map[String, String]] and
        (__ \ "idPrefix").write[String]
    )(unlift(TaskList.unapply))

}