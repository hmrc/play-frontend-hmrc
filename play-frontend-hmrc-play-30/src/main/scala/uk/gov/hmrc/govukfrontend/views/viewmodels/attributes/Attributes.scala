/*
 * Copyright 2024 HM Revenue & Customs
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

package uk.gov.hmrc.govukfrontend.views.viewmodels.attributes

import play.twirl.api.Html

// As described in https://github.com/alphagov/govuk-frontend/blob/main/packages/govuk-frontend/src/govuk/macros/attributes.njk

//  By default or using `optional: false`, attributes render as ` name="value"`
//  Using `optional: true`, attributes with empty (`null`, `undefined` or `false`) values are omitted
//  Using `optional: true`, attributes with `true` (boolean) values render `name` only without value

case class Attributes(attributes: Seq[Attribute[_]], additionalAttributes: Map[String, String] = Map.empty) {
  override def toString(): String = {
    val combinedAttributes = attributes ++ additionalAttributes.toSeq.map { case (k, v) => StringAttribute(k, Some(v)) }
    combinedAttributes.foldLeft("")((currentAttr, newAttr) => s"$currentAttr ${newAttr.toString}".trim)
  }

  def asHtml(): Html = Html(toString())
}

trait Attribute[A] {
  val name: String
  val value: Option[A]
  val optional: Boolean
}

case class BooleanAttribute(name: String, value: Option[Boolean], optional: Boolean = false)
    extends Attribute[Boolean] {
  override def toString: String =
    (optional, value) match {
      case (true, Some(false))     => ""
      case (true, Some(true))      => name
      case (true, None)            => ""
      case (false, None)           => s"$name=\"\""
      case (_, Some(definedValue)) => s"$name=\"$definedValue\""
    }
}

case class StringAttribute(name: String, value: Option[String], optional: Boolean = false) extends Attribute[String] {

  override def toString: String =
    (optional, value) match {
      case (_, Some(definedValue)) => s"$name=\"$definedValue\""
      case (true, None)            => ""
      case (false, None)           => s"$name=\"\""
    }
}
