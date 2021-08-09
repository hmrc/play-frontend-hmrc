package uk.gov.hmrc.hmrcfrontend.views.config

import play.twirl.api.HtmlFormat
import uk.gov.hmrc.govukfrontend.views.viewmodels.content.HtmlContent
import uk.gov.hmrc.govukfrontend.views.viewmodels.label.Label

object HmrcPageHeadingLabel {
  def apply(heading: String, section: String): Label =
    Label(
      isPageHeading = true,
      content = HtmlContent(s"""${HtmlFormat.escape(
        heading
      )} <span class="govuk-caption-xl hmrc-caption-xl">${HtmlFormat.escape(section)}</span>"""),
      classes = "hmrc-page-heading govuk-!-margin-top-0 govuk-!-margin-bottom-2"
    )
}
