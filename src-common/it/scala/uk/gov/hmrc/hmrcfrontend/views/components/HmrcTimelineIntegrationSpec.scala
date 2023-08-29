package uk.gov.hmrc.hmrcfrontend.views.components

import uk.gov.hmrc.helpers.MessagesSupport
import uk.gov.hmrc.hmrcfrontend.support.TemplateIntegrationSpec
import uk.gov.hmrc.hmrcfrontend.views.html.components._
import uk.gov.hmrc.hmrcfrontend.views.viewmodels.timeline.Generators._

object HmrcTimelineIntegrationSpec
    extends TemplateIntegrationSpec[Timeline, HmrcTimeline](hmrcComponentName = "hmrcTimeline", seed = None)
    with MessagesSupport
