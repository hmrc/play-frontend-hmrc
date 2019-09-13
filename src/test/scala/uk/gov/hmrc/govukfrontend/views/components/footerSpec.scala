/*
 * Copyright 2019 HM Revenue & Customs
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

package uk.gov.hmrc.govukfrontend.views.components

import play.api.libs.functional.syntax._
import play.api.libs.json._
import play.twirl.api.Html
import uk.gov.hmrc.govukfrontend.views.html.components._

class footerSpec extends RenderHtmlSpec("govukFooter") {

  "footer" should {
    "have a role of contentinfo" in {
      val component = Footer().select(".govuk-footer")
      component.attr("role") shouldBe "contentinfo"
    }

    "render attributes correctly" in {
      val component = Footer(attributes = Map("data-test-attribute" -> "value", "data-test-attribute-2" -> "value-2"))
        .select(".govuk-footer")
      component.attr("data-test-attribute")   shouldBe "value"
      component.attr("data-test-attribute-2") shouldBe "value-2"
    }

    "render classes" in {
      val component = Footer(classes = "app-footer--custom-modifier").select(".govuk-footer")
      assert(component.hasClass("app-footer--custom-modifier"))
    }

    "render custom container classes" in {
      val component = Footer(containerClasses = "app-width-container").select(".govuk-footer")
      val container = component.select(".govuk-width-container")
      assert(container.hasClass("app-width-container"))
    }

    "render custom meta text" in {
      val component =
        Footer(meta = Some(Meta(content = Text("GOV.UK Prototype Kit <strong>v7.0.1</strong>"))))
          .select(".govuk-footer")
      val custom = component.select(".govuk-footer__meta-custom")
      custom.text() shouldBe "GOV.UK Prototype Kit <strong>v7.0.1</strong>"
    }

    "render custom meta html" in {
      val component =
        Footer(meta = Some(Meta(content = HtmlContent("GOV.UK Prototype Kit <strong>v7.0.1</strong>"))))
          .select(".govuk-footer")
      val custom = component.select(".govuk-footer__meta-custom")
      custom.text() shouldBe "GOV.UK Prototype Kit v7.0.1"
    }

    "render attributes in meta links" in {
      val items = Seq(
        FooterItem(
          text       = Some("meta item 1"),
          href       = Some("#1"),
          attributes = Map("data-attribute" -> "my-attribute", "data-attribute-2" -> "my-attribute-2")))

      val metaLink =
        Footer(meta = Some(Meta(items = items))).select(".govuk-footer__meta .govuk-footer__link")
      metaLink.attr("data-attribute")   shouldBe "my-attribute"
      metaLink.attr("data-attribute-2") shouldBe "my-attribute-2"
    }

    "render attributes in navigation items" in {
      val items = Seq(
        FooterItem(
          text       = Some("Navigation item 1"),
          href       = Some("#1"),
          attributes = Map("data-attribute" -> "my-attribute", "data-attribute-2" -> "my-attribute-2")))

      val navLink =
        Footer(navigation = Seq(FooterNavigation(items = items))).select(".govuk-footer__list .govuk-footer__link")
      navLink.attr("data-attribute")   shouldBe "my-attribute"
      navLink.attr("data-attribute-2") shouldBe "my-attribute-2"
    }
  }

  override implicit val reads: Reads[Html] = (
    (__ \ "meta").readNullable[Meta] and
      (__ \ "navigation").readWithDefault[Seq[FooterNavigation]](Nil) and
      (__ \ "containerClasses").readWithDefault[String]("") and
      (__ \ "classes").readWithDefault[String]("") and
      (__ \ "attributes").readWithDefault[Map[String, String]](Map.empty)
  )(Footer.apply _)
}
