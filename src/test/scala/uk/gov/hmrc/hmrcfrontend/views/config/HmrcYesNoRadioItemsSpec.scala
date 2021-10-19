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

import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.AnyWordSpec
import play.api.i18n.{DefaultLangs, Lang, MessagesApi}
import play.api.test.Helpers.stubMessagesApi
import uk.gov.hmrc.govukfrontend.views.Aliases.Text
import uk.gov.hmrc.govukfrontend.views.viewmodels.radios.RadioItem

class HmrcYesNoRadioItemsSpec extends AnyWordSpec with Matchers {

  private lazy val messagesApi: MessagesApi =
    stubMessagesApi(
      Map(
        "en" -> Map("radios.yesnoitems.yes" -> "Yes", "radios.yesnoitems.no" -> "No"),
        "cy" -> Map("radios.yesnoitems.yes" -> "Iawn", "radios.yesnoitems.no" -> "Na")
      ),
      new DefaultLangs(Seq(Lang("en"), Lang("cy")))
    )

  private val englishMessages = messagesApi.preferred(Seq(Lang("en")))
  private val welshMessages   = messagesApi.preferred(Seq(Lang("cy")))

  "HmrcYesNoRadioItems" must {
    "return two radio items for yes and no, with correct translations for English" in {
      HmrcYesNoRadioItems()(englishMessages) mustBe List(
        RadioItem(content = Text("Yes"), value = Some("true")),
        RadioItem(content = Text("No"), value = Some("false"))
      )
    }

    "return two radio items for yes and no, with correct translations for Welsh" in {
      HmrcYesNoRadioItems()(welshMessages) mustBe List(
        RadioItem(content = Text("Iawn"), value = Some("true")),
        RadioItem(content = Text("Na"), value = Some("false"))
      )
    }
  }
}
