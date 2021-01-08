/*
 * Copyright 2021 HM Revenue & Customs
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

package uk.gov.hmrc.hmrcfrontend.views.config

import javax.inject.Inject
import play.api.i18n.Messages
import play.api.mvc.RequestHeader
import uk.gov.hmrc.hmrcfrontend.views.viewmodels.footer.FooterItem
import uk.gov.hmrc.hmrcfrontend.config.AccessibilityStatementConfig

class HmrcFooterItems @Inject()(accessibilityStatementConfig: AccessibilityStatementConfig) {
  def get(implicit messages: Messages, request: RequestHeader): Seq[FooterItem] =
    Seq(
      footerItemForKey("cookies"),
      accessibilityLink,
      footerItemForKey("privacy"),
      footerItemForKey("termsConditions"),
      footerItemForKey("govukHelp"),
      footerItemForKey("contact"),
      footerItemForKey("welshHelp")
    ).flatten

  private def accessibilityLink(implicit messages: Messages, request: RequestHeader): Option[FooterItem] =
    accessibilityStatementConfig.url
      .map(
        href => FooterItem(Some(messages("footer.accessibility.text")), Some(href))
      )

  private def footerItemForKey(item: String)(implicit messages: Messages): Option[FooterItem] =
    Some(
      FooterItem(
        text = Some(messages(s"footer.$item.text")),
        href = Some(messages(s"footer.$item.url"))
      ))
}
