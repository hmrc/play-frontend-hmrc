package uk.gov.hmrc.govukfrontend.views.viewmodels.tasklist

import uk.gov.hmrc.govukfrontend.views.viewmodels.content.{Content, Empty}


case class TaskListItemTitle(
    content: Content = Empty,
    classes: String = ""
)

object TaskListItemTitle {

}