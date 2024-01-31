package uk.gov.hmrc.govukfrontend.views.viewmodels.tasklist

import uk.gov.hmrc.govukfrontend.views.viewmodels.tag.Tag
import uk.gov.hmrc.govukfrontend.views.viewmodels.content.{Content, Empty}

case class TaskListItemStatus(
    tag: Option[Tag] = None,
    content: Content = Empty,
    classes: String = ""
)

object TaskListItemStatus {

}
