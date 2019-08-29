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

package uk.gov.hmrc.govukfrontend.views

import play.api.libs.functional.syntax._
import play.api.libs.json._
import play.twirl.api.Html
import uk.gov.hmrc.govukfrontend.views.html.components._

/**
  * Reads used in all test fixtures go here
  */
trait ReadsHelpers {
  // FIXME: To remove. Coming soon in Play 2.7.x https://www.playframework.com/documentation/2.7.x/api/scala/play/api/libs/json/Format.html#widen[B%3E:A]:play.api.libs.json.Reads[B]
  implicit class RichReads[A](r: Reads[A]) {
    def widen[B >: A]: Reads[B] = Reads[B] { r.reads }
  }

  implicit val readsHtml: Reads[Html] = new Reads[Html] {
    override def reads(json: JsValue): JsResult[Html] = json match {
      case JsString(html) => JsSuccess(Html(html))
      case _              => JsError("error.expected.string")
    }
  }

  implicit val readsContents: Reads[Contents] =
    readsHtmlOrText("html", "text")

  def readsHtmlOrText(htmlField: String, textField: String): Reads[Contents] =
    readsHtmlContent(htmlField)
      .widen[Contents]
      .orElse(readsText(textField).widen[Contents])
      .orElse(Reads.pure[Contents](Empty))

  def readsHtmlContent(htmlField: String = "html"): Reads[HtmlContent] =
    (__ \ htmlField).read[String].map(HtmlContent(_))

  def readsText(textField: String = "text"): Reads[Text] =
    (__ \ textField).read[String].map(Text)

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

  implicit val readsTagParams: Reads[TagParams] = (
    readsContents and
      (__ \ "classes").readWithDefault[String]("") and
      (__ \ "attributes").readWithDefault[Map[String, String]](Map.empty)
  )(TagParams.apply _)

  implicit val readsFieldsetParams: Reads[FieldsetParams] =
    Json.using[Json.WithDefaultValues].reads[FieldsetParams]

  implicit val readsHintParams: Reads[HintParams] = (
    (__ \ "id").readNullable[String] and
      (__ \ "classes").readWithDefault[String]("") and
      (__ \ "attributes").readWithDefault[Map[String, String]](Map.empty) and
      readsContents
  )(HintParams.apply _)

  // FIXME: Hacky Reads to deal with incorrect implementation of govuk-frontend 'error-message' component. Raise PR to fix it.
  // Converts the ambiguously typed visuallyHiddenText argument from govuk-frontend to the correct type.
  // The original govuk-frontend implementation will show 'true' as the hidden text when visuallyHiddenText is 'true'.
  // [[https://github.com/alphagov/govuk-frontend/blob/v2.11.0/src/components/error-message/template.test.js#L82]]
  // When visuallyHiddenText is a falsy value (we consider only "" or 'false') means we want to hide the text
  implicit val readsVisuallyHiddenText: Reads[VisuallyHiddenText] =
    ((__ \ "visuallyHiddenText").readWithDefault[String]("Error").map(Left(_): Either[String, Boolean]) |
      (__ \ "visuallyHiddenText").readWithDefault[Boolean](true).map(Right(_): Either[String, Boolean]))
      .map(toVisuallyHiddenText)

  private def toVisuallyHiddenText(x: Either[String, Boolean]): VisuallyHiddenText =
    x match {
      case Left("")     => HideText
      case Left(text)   => ShowText(text)
      case Right(false) => HideText
      case Right(true) =>
        throw new IllegalArgumentException(
          """
            |Passed boolean 'true' as the visually hidden text which is not the likely intended behaviour.
            |""".stripMargin
        )
    }

  implicit val readsErrorMessageParams: Reads[ErrorMessageParams] = (
    (__ \ "classes").readWithDefault[String]("") and
      (__ \ "attributes").readWithDefault[Map[String, String]](Map.empty) and
      readsVisuallyHiddenText and
      readsContents
  )(ErrorMessageParams.apply _)

  implicit val readsLabelParams: Reads[LabelParams] = (
    (__ \ "for").readNullable[String] and
      (__ \ "isPageHeading").readWithDefault[Boolean](false) and
      (__ \ "classes").readWithDefault[String]("") and
      (__ \ "attributes").readWithDefault[Map[String, String]](Map.empty) and
      readsContents
  )(LabelParams.apply _)

  case class Conditional(html: String)
  object Conditional {
    implicit val readsConditional = Json.reads[Conditional]
  }

  implicit val readsRadioItem: Reads[RadioItem] = (
    readsContents and
      (__ \ "id").readNullable[String] and
      (__ \ "value").readNullable[String] and
      (__ \ "label").readNullable[LabelParams] and
      (__ \ "hint").readNullable[HintParams] and
      (__ \ "divider").readNullable[String] and
      (__ \ "checked").readWithDefault[Boolean](false) and
      (__ \ "conditional").readNullable[Conditional].map(_.map(conditional => Html(conditional.html))) and
      (__ \ "disabled").readWithDefault[Boolean](false) and
      (__ \ "attributes").readWithDefault[Map[String, String]](Map.empty)
  )(RadioItem.apply _)

}
