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

package uk.gov.hmrc.hmrcfrontend.views

import play.api.data.{Field, Form, FormError}
import play.api.i18n.Messages
import play.twirl.api.{Html, HtmlFormat}
import uk.gov.hmrc.govukfrontend.views.Aliases.Empty
import uk.gov.hmrc.govukfrontend.views.ImplicitsSupport
import uk.gov.hmrc.govukfrontend.views.viewmodels.content.{Content, HtmlContent, Text}
import uk.gov.hmrc.govukfrontend.views.viewmodels.dateinput.{DateInput, InputItem}
import uk.gov.hmrc.govukfrontend.views.viewmodels.errorsummary.{ErrorLink, ErrorSummary}
import uk.gov.hmrc.hmrcfrontend.views.viewmodels.charactercount.CharacterCount

trait Implicits {

  implicit class RichHtml(html: Html) {

    def padLeft(padCount: Int = 1, padding: String = " "): Html = {
      val padStr = " " * (if (html.body.isEmpty) 0 else padCount)
      HtmlFormat.fill(collection.immutable.Seq(Html(padStr), html))
    }

    def trim: Html =
      Html(html.toString.trim)

    def ltrim: Html =
      Html(html.toString.ltrim)

    def rtrim: Html =
      Html(html.toString.rtrim)

    /**
      * Based on the behaviour of [[https://mozilla.github.io/nunjucks/templating.html#indent]]
      * @param n
      * @param indentFirstLine
      */
    def indent(n: Int, indentFirstLine: Boolean = false): Html =
      Html(html.toString.indent(n, indentFirstLine))

    def nonEmpty: Boolean =
      html.body.nonEmpty
  }

  implicit class RichString(s: String) {

    def toOption: Option[String] =
      if (s == null || s.isEmpty) None else Some(s)

    def ltrim = s.replaceAll("^\\s+", "")

    def rtrim = s.replaceAll("\\s+$", "")

    /**
      * Indent a (multiline) string <code>n</code> spaces.
      * @param n Number of spaces to indent the string. It can be a negative value, in which case it attempts to unindent
      *          n spaces, as much as possible until it hits the margin.
      * @param indentFirstLine
      * @return
      */
    def indent(n: Int, indentFirstLine: Boolean = false): String = {
      @scala.annotation.tailrec
      def recurse(m: Int, lines: Seq[String]): Seq[String] = m match {
        case 0 => lines
        case m => recurse(math.abs(m) - 1, indent1(math.signum(n), lines))
      }

      val lines         = s.split("\n", -1).toSeq // limit=-1 so if a line ends with \n include the trailing blank line
      val linesToIndent = if (indentFirstLine) lines else if (lines.length > 1) lines.tail else Nil
      val indentedLines = recurse(n, linesToIndent)

      (if (indentFirstLine) indentedLines else (lines.head +: indentedLines)).mkString("\n")
    }

    private def indent1(signal: Int, lines: Seq[String]) = {
      def canUnindent: Boolean =
        lines.forall(_.startsWith(" "))

      if (signal < 0 && canUnindent) {
        lines.map(_.drop(1))
      } else if (signal < 0) {
        lines
      } else {
        lines.map(" " + _)
      }
    }

    /**
      * Implements [[https://mozilla.github.io/nunjucks/templating.html#capitalize]].
      * Unlike Scala's [[scala.collection.immutable.StringLike#capitalize()]] Nunjucks' capitalize converts the first
      * letter to uppercase and the rest to lowercase.
      *
      * @return
      */
    def nunjucksCapitalize: String =
      s.toLowerCase.capitalize

  }

  /**
    * Mapping, folding and getOrElse on Option[String] for non-empty strings.
    * Commonly used in the Twirl components.
    *
    * @param optString
    */
  implicit class RichOptionString(optString: Option[String]) {
    def mapNonEmpty[T](f: String => T): Option[T] =
      optString.filter(_.nonEmpty).map(f)

    def foldNonEmpty[B](ifEmpty: => B)(f: String => B): B =
      optString.filter(_.nonEmpty).fold(ifEmpty)(f)

    def getNonEmptyOrElse[B >: String](default: => B): B =
      optString.filter(_.nonEmpty).getOrElse(default)
  }

  implicit class RichCharacterCount(characterCount: CharacterCount)(implicit val messages: Messages)
      extends ImplicitsSupport[CharacterCount] {

    /**
      * Extension method to allow a Play form Field to be used to add certain parameters in a CharacterCount,
      * specifically errorMessage, id, name, and value. Note these values will only be added from the Field if they are
      * not specifically defined in the CharacterCount object.
      * Form errors will be bound as Text objects.
      *
      * @param field
      * @param messages
      */
    override def withFormField(field: Field): CharacterCount =
      characterCount
        .withName(field)
        .withId(field)
        .withValue(field)
        .withTextErrorMessage(field)

    /**
      * Extension method to allow a Play form Field to be used to populate parameters in a CharacterCount, as per
      * withFormField, with form errors bound as HtmlContent objects.
      *
      * @param field
      */
    override def withFormFieldWithErrorAsHtml(field: Field): CharacterCount =
      characterCount
        .withName(field)
        .withId(field)
        .withValue(field)
        .withHtmlErrorMessage(field)

    private[views] def withName(field: Field): CharacterCount =
      withStringProperty(field.name, characterCount.name, characterCount)((cc, nm) => cc.copy(name = nm))

    private[views] def withId(field: Field): CharacterCount =
      withStringProperty(field.name, characterCount.id, characterCount)((cc, id) => cc.copy(id = id))

    private[views] def withValue(field: Field): CharacterCount =
      withOptStringProperty(field.value, characterCount.value, characterCount)((cc, vl) => cc.copy(value = vl))

    private[views] def withTextErrorMessage(field: Field): CharacterCount =
      withOptTextErrorMessageProperty(field.error, characterCount.errorMessage, characterCount)((cc, errorMsg) =>
        cc.copy(errorMessage = errorMsg)
      )

    private[views] def withHtmlErrorMessage(field: Field): CharacterCount =
      withOptHtmlErrorMessageProperty(field.error, characterCount.errorMessage, characterCount)((cc, errorMsg) =>
        cc.copy(errorMessage = errorMsg)
      )
  }

  implicit class RichDateInput(dateInput: DateInput)(implicit val messages: Messages)
      extends ImplicitsSupport[DateInput] {

    /**
      * Extension method to allow a Play form Field to be used to populate parameters in a DateInput,
      * if they have not already been set to a non-default value. This method assumes that `dateInput.items`
      * will either equal `Seq.empty` or will have exactly three InputItems corresponding to the day, month and year.
      * Form errors will be bound as Text objects.
      *
      * @param field
      */
    override def withFormField(field: Field): DateInput =
      dateInput
        .withId(field)
        .withInputItems(field)
        .withTextErrorMessage(field)

    /**
      * Extension method to allow a Play form Field to be used to populate parameters in a DateInput, as per
      * withFormField, with form errors bound as HtmlContent objects.
      *
      * @param field
      */
    override def withFormFieldWithErrorAsHtml(field: Field): DateInput =
      dateInput
        .withId(field)
        .withInputItems(field)
        .withHtmlErrorMessage(field)

    private[views] def withId(field: Field): DateInput =
      withStringProperty(field.name, dateInput.id, dateInput)((dateInput, id) => dateInput.copy(id = id))

    private[views] def withInputItems(field: Field): DateInput = {
      def errorClass(itemField: Field) =
        if (field.errors.nonEmpty || itemField.errors.nonEmpty) "govuk-input--error" else ""

      def inputItem(inputItem: InputItem, key: String, className: String): InputItem = {
        val defaultInputItem = InputItem.defaultObject
        val classes          = if (inputItem.classes == defaultInputItem.classes) className else inputItem.classes

        inputItem.copy(
          id = if (inputItem.id == defaultInputItem.id) s"${field.name}.$key" else inputItem.id,
          name = if (inputItem.name == defaultInputItem.name) s"${field.name}.$key" else inputItem.name,
          value = if (inputItem.value == defaultInputItem.value) field(key).value else inputItem.value,
          label =
            if (inputItem.label == defaultInputItem.label) Some(messages(s"date.input.$key")) else inputItem.label,
          classes = s"$classes ${errorClass(field(key))}".trim
        )
      }

      val dateInputItems = if (dateInput.items.size == 3) dateInput.items else Seq.fill(3)(InputItem.defaultObject)

      val items = Seq(
        inputItem(dateInputItems(0), "day", className = "govuk-input--width-2"),
        inputItem(dateInputItems(1), "month", className = "govuk-input--width-2"),
        inputItem(dateInputItems(2), "year", className = "govuk-input--width-4")
      )

      dateInput.copy(items = items)
    }

    private[views] def withTextErrorMessage(field: Field): DateInput = {
      val error = Seq(
        field("day").error,
        field("month").error,
        field("year").error,
        field.error
      ).flatten.headOption

      withOptTextErrorMessageProperty(error, dateInput.errorMessage, dateInput)((dateInput, errorMsg) =>
        dateInput.copy(errorMessage = errorMsg)
      )
    }

    private[views] def withHtmlErrorMessage(field: Field): DateInput = {
      val error = Seq(
        field("day").error,
        field("month").error,
        field("year").error,
        field.error
      ).flatten.headOption

      withOptHtmlErrorMessageProperty(error, dateInput.errorMessage, dateInput)((dateInput, errorMsg) =>
        dateInput.copy(errorMessage = errorMsg)
      )
    }
  }

  /**
    * Extension methods to hydrate an [[ErrorSummary]] with the errors found in a Play form.
    *
    * Error messages are hyperlinked to the HTML input elements corresponding to the Play field.
    * For situations where the form field does not correspond to
    * an HTML input element, for example, a composite field such as a date, provide a mapping from the
    * composite field to Play field corresponding to the first HTML input element in the group.
    *
    * For example:
    * {{{
    *   @govukErrorSummary(ErrorSummary().withFormErrorsAsText(dateInputForm, mapping = Map("date" -> "date.day")))
    * }}}
    */
  implicit class RichErrorSummary[T](errorSummary: ErrorSummary)(implicit val messages: Messages) {
    import uk.gov.hmrc.govukfrontend.views.Implicits.RichFormErrors

    def withFormErrorsAsHtml(form: Form[T]): ErrorSummary =
      withFormErrorsAsHtml(form, Map.empty)

    def withFormErrorsAsHtml(form: Form[T], mapping: Map[String, String]): ErrorSummary =
      withForm(form, _.asHtmlErrorLinks, mapping)

    def withFormErrorsAsText(form: Form[T]): ErrorSummary =
      withFormErrorsAsText(form, Map.empty)

    def withFormErrorsAsText(form: Form[T], mapping: Map[String, String]): ErrorSummary =
      withForm(form, _.asTextErrorLinks, mapping)

    private[views] def withForm(
      form: Form[T],
      asErrorLinks: Seq[FormError] => Seq[ErrorLink],
      mapping: Map[String, String]
    ): ErrorSummary =
      errorSummary.withTitle().withErrorList(form, asErrorLinks, mapping)

    private[views] def withErrorList(
      form: Form[T],
      asErrorLinks: Seq[FormError] => Seq[ErrorLink],
      mapping: Map[String, String]
    ): ErrorSummary = {
      val remappedFormErrors = form.errors map { e =>
        e.copy(
          key = mapping.getOrElse(e.key, e.key)
        )
      }
      errorSummary.copy(errorList = asErrorLinks(remappedFormErrors))
    }

    private[views] def withTitle(): ErrorSummary = errorSummary.copy(
      title = if (errorSummary.title == Empty) Text(messages("error.summary.title")) else errorSummary.title
    )
  }
}

object Implicits extends Implicits
