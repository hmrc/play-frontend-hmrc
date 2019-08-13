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

import org.jsoup.Jsoup
import play.api.libs.functional.syntax._
import play.api.libs.json._
import uk.gov.hmrc.govukfrontend.views.html.components._

class footerSpec
    extends RenderHtmlSpec(
      Seq(
        "footer-default",
        "footer-GOV.UK",
        "footer-with-custom-meta",
        "footer-with-custom-meta2",
        "footer-with-meta",
        "footer-with-meta-links-and-meta-content",
        "footer-with-navigation"
      )
    ) {

  "footer" should {
    "have a role of contentinfo" in {
      val footerHtml = Footer()
      val component  = Jsoup.parse(footerHtml.body).select(".govuk-footer")
      component.attr("role") shouldBe "contentinfo"
    }

    "render attributes correctly" in {
      val footerHtml = Footer(attributes = Map("data-test-attribute" -> "value", "data-test-attribute-2" -> "value-2"))
      val component  = Jsoup.parse(footerHtml.body).select(".govuk-footer")
      component.attr("data-test-attribute")   shouldBe "value"
      component.attr("data-test-attribute-2") shouldBe "value-2"
    }

    "render classes" in {
      val footerHtml = Footer(classes = "app-footer--custom-modifier")
      val component  = Jsoup.parse(footerHtml.body).select(".govuk-footer")
      assert(component.hasClass("app-footer--custom-modifier"))
    }

    "render custom container classes" in {
      val footerHtml = Footer(containerClasses = "app-width-container")
      val component  = Jsoup.parse(footerHtml.body).select(".govuk-footer")
      val container  = component.select(".govuk-width-container")
      assert(container.hasClass("app-width-container"))
    }

    "render custom meta text" in {
      val footerHtml = Footer(meta = Some(FooterMeta(contents = Text("GOV.UK Prototype Kit <strong>v7.0.1</strong>"))))
      val component  = Jsoup.parse(footerHtml.body).select(".govuk-footer")
      val custom     = component.select(".govuk-footer__meta-custom")
      custom.text() shouldBe "GOV.UK Prototype Kit <strong>v7.0.1</strong>"
    }

    "render custom meta html" in {
      val footerHtml =
        Footer(meta = Some(FooterMeta(contents = HtmlContent("GOV.UK Prototype Kit <strong>v7.0.1</strong>"))))
      val component = Jsoup.parse(footerHtml.body).select(".govuk-footer")
      val custom    = component.select(".govuk-footer__meta-custom")
      custom.text() shouldBe "GOV.UK Prototype Kit v7.0.1"
    }

    "render attributes in meta links" in {
      val items = Seq(
        FooterItem(
          text       = Some("meta item 1"),
          href       = Some("#1"),
          attributes = Map("data-attribute" -> "my-attribute", "data-attribute-2" -> "my-attribute-2")))

      val footerHtml =
        Footer(meta = Some(FooterMeta(items = items)))

      val metaLink = Jsoup.parse(footerHtml.body).select(".govuk-footer__meta .govuk-footer__link")
      metaLink.attr("data-attribute")   shouldBe "my-attribute"
      metaLink.attr("data-attribute-2") shouldBe "my-attribute-2"
    }

    "render attributes in navigation items" in {
      val items = Seq(
        FooterItem(
          text       = Some("Navigation item 1"),
          href       = Some("#1"),
          attributes = Map("data-attribute" -> "my-attribute", "data-attribute-2" -> "my-attribute-2")))

      val footerHtml =
        Footer(navigation = Seq(FooterNavigation(items = items)))

      val navLink = Jsoup.parse(footerHtml.body).select(".govuk-footer__list .govuk-footer__link")
      navLink.attr("data-attribute")   shouldBe "my-attribute"
      navLink.attr("data-attribute-2") shouldBe "my-attribute-2"
    }
  }

  override implicit val reads: Reads[HtmlString] = (
    (__ \ "meta").readNullable[FooterMeta] and
      (__ \ "navigation").readWithDefault[Seq[FooterNavigation]](Nil) and
      (__ \ "containerClasses").readWithDefault[String]("") and
      (__ \ "classes").readWithDefault[String]("") and
      (__ \ "attributes").readWithDefault[Map[String, String]](Map.empty)
  )((meta, navigation, containerClasses, classes, attributes) =>
    HtmlString(Footer.apply(meta, navigation, containerClasses, classes, attributes)))
}
