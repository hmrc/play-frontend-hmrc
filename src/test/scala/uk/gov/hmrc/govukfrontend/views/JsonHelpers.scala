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
import uk.gov.hmrc.govukfrontend.views.html.components.implicits._
import scala.util.matching.Regex

/**
  * Json (de)serialisation of inputs to Nunjucks templates and Twirl view models.
  */
trait JsonHelpers {
  // FIXME: To remove. Coming soon in Play 2.7.x https://www.playframework.com/documentation/2.7.x/api/scala/play/api/libs/json/Format.html#widen[B%3E:A]:play.api.libs.json.Reads[B]
  implicit class RichReads[A](r: Reads[A]) {
    def widen[B >: A]: Reads[B] = Reads[B] { r.reads }
  }

  /**
    * Parse fields with unknown type to [[String]]
    *
    * @param jsPath
    */
  implicit class RichJsPath(jsPath: JsPath) {
    def readsJsValueToString: Reads[String] =
      jsPath
        .read[JsValue]
        .map {
          case JsString(s) => s
          case x           => Json.stringify(x)
        }
  }

  implicit val readsContent: Reads[Content] =
    readsHtmlOrText((__ \ "html"), (__ \ "text"))

  implicit def writesContent(htmlField: String = "html", textField: String = "text"): OWrites[Content] =
    new OWrites[Content] {
      override def writes(content: Content): JsObject = content match {
        case Empty              => Json.obj()
        case HtmlContent(value) => Json.obj(htmlField -> value.body)
        case Text(value)        => Json.obj(textField -> value)
      }
    }

  def readsHtmlOrText(htmlJsPath: JsPath, textJsPath: JsPath): Reads[Content] =
    readsHtmlContent(htmlJsPath)
      .widen[Content]
      .orElse(readsText(textJsPath).widen[Content])
      .orElse(Reads.pure[Content](Empty))

  def readsHtmlContent(jsPath: JsPath = (__ \ "html")): Reads[HtmlContent] =
    jsPath.readsJsValueToString.map(HtmlContent(_))

  def readsText(jsPath: JsPath = (__ \ "text")): Reads[Text] =
    jsPath.readsJsValueToString.map(Text)

  implicit val readsErrorLink: Reads[ErrorLink] = (
    (__ \ "href").readNullable[String] and
      readsContent and
      (__ \ "attributes").readWithDefault[Map[String, String]](Map.empty)
  )(ErrorLink.apply _)

  implicit val readsLegend: Reads[Legend] = (
    readsContent and
      (__ \ "classes").readWithDefault[String]("") and
      (__ \ "isPageHeading").readWithDefault[Boolean](false)
  )(Legend.apply _)

  implicit val readsFooterItem: Reads[FooterItem] = (
    (__ \ "text").readNullable[String] and
      (__ \ "href").readNullable[String] and
      (__ \ "attributes").readWithDefault[Map[String, String]](Map.empty)
  )(FooterItem.apply _)

  implicit val readsFooterMeta: Reads[Meta] = (
    (__ \ "visuallyHiddenTitle").readNullable[String] and
      readsContent and
      (__ \ "items").readWithDefault[Seq[FooterItem]](Nil)
  )(Meta.apply _)

  implicit val readsFooterNavigation: Reads[FooterNavigation] =
    Json.using[Json.WithDefaultValues].reads[FooterNavigation]

  implicit val readsHeaderNavigation: Reads[HeaderNavigation] =
    Json.using[Json.WithDefaultValues].reads[HeaderNavigation]

  implicit val readsTagParams: Reads[TagParams] = (
    readsContent and
      (__ \ "classes").readWithDefault[String]("") and
      (__ \ "attributes").readWithDefault[Map[String, String]](Map.empty)
  )(TagParams.apply _)

  implicit val readsFieldsetParams: Reads[FieldsetParams] =
    Json.using[Json.WithDefaultValues].reads[FieldsetParams]

  implicit val readsHintParams: Reads[HintParams] = (
    (__ \ "id").readNullable[String] and
      (__ \ "classes").readWithDefault[String]("") and
      (__ \ "attributes").readWithDefault[Map[String, String]](Map.empty) and
      readsContent
  )(HintParams.apply _)

  // FIXME: Hacky Reads to deal with incorrect implementation of govuk-frontend 'error-message' component. Raise PR to fix it.
  // Converts the ambiguously documented visuallyHiddenText parameter from govuk-frontend to the correct type.
  // The original govuk-frontend implementation will show any truthy value as the hidden text when visuallyHiddenText is set to it.
  // [[https://github.com/alphagov/govuk-frontend/blob/v2.11.0/src/components/error-message/template.test.js#L82]]
  // When visuallyHiddenText is a falsy value we want to hide the text
  // If it is not provided we default to "Error"
  implicit val readsVisuallyHiddenText: Reads[VisuallyHiddenText] = (
    (__ \ "visuallyHiddenText")
      .readWithDefault[JsValue](JsString("Error"))
      .map {
        case JsNull                => HideText
        case JsString("")          => HideText
        case JsString(text)        => ShowText(text)
        case JsFalse               => HideText
        case JsNumber(n) if n == 0 => HideText
        case x                     => ShowText(x.toString) // not intended behaviour but that is how govuk-frontend behaves
      }
  )

  implicit val readsErrorMessageParams: Reads[ErrorMessageParams] = (
    (__ \ "classes").readWithDefault[String]("") and
      (__ \ "attributes").readWithDefault[Map[String, String]](Map.empty) and
      readsVisuallyHiddenText and
      readsContent
  )(ErrorMessageParams.apply _)

  implicit val readsLabelParams: Reads[LabelParams] = (
    (__ \ "for").readNullable[String] and
      (__ \ "isPageHeading").readWithDefault[Boolean](false) and
      (__ \ "classes").readWithDefault[String]("") and
      (__ \ "attributes").readWithDefault[Map[String, String]](Map.empty) and
      readsContent
  )(LabelParams.apply _)

  implicit val readsRadioItem: Reads[RadioItem] = (
    readsContent and
      (__ \ "id").readNullable[String] and
      (__ \ "value").readNullable[String] and
      (__ \ "label").readNullable[LabelParams] and
      (__ \ "hint").readNullable[HintParams] and
      (__ \ "divider").readNullable[String] and
      (__ \ "checked").readWithDefault[Boolean](false) and
      (__ \ "conditional" \ "html").readNullable[String].map(_.map(Html(_))).orElse(Reads.pure(None)) and
      (__ \ "disabled").readWithDefault[Boolean](false) and
      (__ \ "attributes").readWithDefault[Map[String, String]](Map.empty)
  )(RadioItem.apply _)

  implicit val readsKey: Reads[Key] = (
    readsContent and
      (__ \ "classes").readWithDefault[String]("")
  )(Key.apply _)

  implicit val readsValue: Reads[Value] = (
    readsContent and
      (__ \ "classes").readWithDefault[String]("")
  )(Value.apply _)

  implicit val readsActionItem: Reads[ActionItem] = (
    (__ \ "href").readWithDefault[String]("") and
      readsContent and
      (__ \ "visuallyHiddenText").readNullable[String] and
      (__ \ "classes").readWithDefault[String]("")
  )(ActionItem.apply _)

  implicit val readsActions: Reads[Actions] =
    Json.using[Json.WithDefaultValues].reads[Actions]

  implicit val readsRow: Reads[SummaryListRow] =
    Json.using[Json.WithDefaultValues].reads[SummaryListRow]

  implicit val readsRegex: Reads[Regex] = new Reads[Regex] {
    override def reads(json: JsValue): JsResult[Regex] = json match {
      case JsString(s) => JsSuccess(new Regex(s))
      case _           => JsError(JsonValidationError("error.expected.jsstring"))
    }
  }

  implicit val readsInputParams: Reads[InputParams] = (
    (__ \ "id").readNullable[String] and
      (__ \ "name").read[String] and
      (__ \ "label").readNullable[String] and
      (__ \ "value").readNullable[String] and
      (__ \ "autocomplete").readNullable[String] and
      (__ \ "pattern").readNullable[Regex] and
      (__ \ "classes").readWithDefault[String]("") and
      (__ \ "attributes").readWithDefault[Map[String, String]](Map.empty)
  )(InputParams.apply _)

  implicit val readsSection: Reads[Section] = (
    readsHtmlOrText((__ \ "heading" \ "html"), (__ \ "heading" \ "text")) and
      readsHtmlOrText((__ \ "summary" \ "html"), (__ \ "summary" \ "text")) and
      readsHtmlOrText((__ \ "content" \ "html"), (__ \ "content" \ "text")) and
      (__ \ "expanded").readWithDefault[Boolean](false)
  )(Section.apply _)

  val readsFormGroupClasses: Reads[String] =
    (__ \ "formGroup" \ "classes").read[String].orElse(Reads.pure(""))

  implicit val readsBreadcrumbsItem: Reads[BreadcrumbsItem] = (
    readsContent and
      (__ \ "href").readNullable[String] and
      (__ \ "attributes").readWithDefault[Map[String, String]](Map.empty)
  )(BreadcrumbsItem.apply _)

  implicit val readsCheckboxItem: Reads[CheckboxItem] = (
    readsContent and
      (__ \ "id").readNullable[String] and
      (__ \ "name").readNullable[String] and
      (__ \ "value").read[String] and
      (__ \ "label").readNullable[LabelParams] and
      (__ \ "hint").readNullable[HintParams] and
      (__ \ "checked").readWithDefault[Boolean](false) and
      (__ \ "conditional" \ "html").readNullable[String].map(_.map(Html(_))).orElse(Reads.pure(None)) and
      (__ \ "disabled").readWithDefault[Boolean](false) and
      (__ \ "attributes").readWithDefault[Map[String, String]](Map.empty)
  )(CheckboxItem.apply _)

  implicit val readsSelectItem: Reads[SelectItem] = (
    (__ \ "value").readsJsValueToString.map(_.toOption) and
      (__ \ "text").read[String] and
      (__ \ "selected").readWithDefault[Boolean](false) and
      (__ \ "disabled").readWithDefault[Boolean](false) and
      (__ \ "attributes").readWithDefault[Map[String, String]](Map.empty)
  )(SelectItem.apply _)

  implicit val readsTableRow: Reads[TableRow] = (
    readsContent and
      (__ \ "format").readNullable[String] and
      (__ \ "classes").readWithDefault[String]("") and
      (__ \ "colspan").readNullable[Int] and
      (__ \ "rowspan").readNullable[Int] and
      (__ \ "attributes").readWithDefault[Map[String, String]](Map.empty)
  )(TableRow.apply _)

  implicit val readsHeadCell: Reads[HeadCell] = (
    readsContent and
      (__ \ "format").readNullable[String] and
      (__ \ "classes").readWithDefault[String]("") and
      (__ \ "colspan").readNullable[Int] and
      (__ \ "rowspan").readNullable[Int] and
      (__ \ "attributes").readWithDefault[Map[String, String]](Map.empty)
  )(HeadCell.apply _)

  implicit val readsTabPanel: Reads[TabPanel] = (
    readsContent and
      (__ \ "attributes").readWithDefault[Map[String, String]](Map.empty)
  )(TabPanel.apply _)

  implicit val readsTabItem: Reads[TabItem] = (
    (__ \ "id").readNullable[String] and
      (__ \ "label").read[String] and
      (__ \ "attributes").readWithDefault[Map[String, String]](Map.empty) and
      (__ \ "panel").read[TabPanel]
  )(TabItem.apply _)
}

object JsonHelpers extends  JsonHelpers
