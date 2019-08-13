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

import play.api.libs.json._
import play.api.libs.functional.syntax._
import play.twirl.api.Html
import uk.gov.hmrc.govukfrontend.views.html.components._


trait ReadsHelpers {
  // TODO: coming soon in Play 2.7.x https://www.playframework.com/documentation/2.7.x/api/scala/play/api/libs/json/Format.html#widen[B%3E:A]:play.api.libs.json.Reads[B]
  implicit class RichReads[A](r: Reads[A]) {
    def widen[B >: A]: Reads[B] = Reads[B] { r.reads }
  }

  implicit val readsHtml: Reads[Html] = new Reads[Html] {
    override def reads(json: JsValue): JsResult[Html] = json match {
      case JsString(html) => JsSuccess(Html(html))
      case _              => JsError("error.expected.string")
    }
  }

  implicit val readsHtmlContent: Reads[HtmlContent] =
    (__ \ "html").read[String].map(HtmlContent(_))

  implicit val readsText: Reads[Text] =
    (__ \ "text").read[String].map(Text)

  implicit val readsContents: Reads[Contents] =
    readsHtmlContent
      .widen[Contents]
      .orElse(readsText.widen[Contents])
      .orElse(Reads.pure[Contents](Empty))

  def readsHtmlOrText(htmlField: String, textField: String): Reads[Contents] =
    (__ \ htmlField)
      .read[String]
      .map(HtmlContent(_))
      .widen[Contents]
      .orElse((__ \ textField).read[String].map(Text).widen[Contents])

  implicit val readsErrorLink: Reads[ErrorLink] = (
    (__ \ "href").readNullable[String] and
      readsContents and
      (__ \ "attributes").readWithDefault[Map[String, String]](Map.empty)
  )(ErrorLink.apply _)

  implicit val readsLegend: Reads[Legend] = (
    readsContents and
      (__ \ "classes").readWithDefault[String]("") and
      (__ \ "isPageHeading").readWithDefault[Boolean](false)
  )(Legend.apply _)

  implicit val readsFooterItem: Reads[FooterItem] = (
    (__ \ "text").readNullable[String] and
      (__ \ "href").readNullable[String] and
      (__ \ "attributes").readWithDefault[Map[String, String]](Map.empty)
  )(FooterItem.apply _)

  implicit val readsFooterMeta: Reads[FooterMeta] = (
    readsContents and
      (__ \ "items").readWithDefault[Seq[FooterItem]](Nil)
  )(FooterMeta.apply _)

  implicit val readsFooterNavigation: Reads[FooterNavigation] =
    Json.using[Json.WithDefaultValues].reads[FooterNavigation]

  implicit val readsHeaderNavigation: Reads[HeaderNavigation] =
    Json.using[Json.WithDefaultValues].reads[HeaderNavigation]
}

