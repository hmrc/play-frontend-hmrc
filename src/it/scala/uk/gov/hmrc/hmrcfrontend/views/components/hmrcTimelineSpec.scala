package uk.gov.hmrc.hmrcfrontend.views.components

import play.twirl.api.HtmlFormat
import uk.gov.hmrc.hmrcfrontend.MessagesSupport
import uk.gov.hmrc.hmrcfrontend.support.TemplateIntegrationSpec
import uk.gov.hmrc.hmrcfrontend.views.html.components._
import uk.gov.hmrc.hmrcfrontend.views.viewmodels.timeline.Generators._

import scala.util.Try

object hmrcTimelineSpec
    extends TemplateIntegrationSpec[Timeline, HmrcTimeline](hmrcComponentName = "hmrcTimeline", seed = None)
    with MessagesSupport {

  override def render(timeline: Timeline): Try[HtmlFormat.Appendable] =
    Try(component(timeline))
}
