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

import cats.data.EitherT
import play.twirl.api.Html

// As described in https://github.com/alphagov/govuk-frontend/blob/main/packages/govuk-frontend/src/govuk/macros/attributes.njk

//  By default or using `optional: false`, attributes render as ` name="value"`
//  Using `optional: true`, attributes with empty (`null`, `undefined` or `false`) values are omitted
//  Using `optional: true`, attributes with `true` (boolean) values render `name` only without value

case class Attributes(attributes: Seq[Attribute], additionalAttributes: Map[String, String] = Map.empty) {
  override def toString(): String = {
    val combinedAttributes = attributes ++ additionalAttributes.toSeq.map { case (k, v) => Attribute(k, Some(Left(v))) }
    combinedAttributes.foldLeft("")((currentAttr, newAttr) => s"$currentAttr ${newAttr.toString}".trim)
  }
}

case class Attribute(name: String, value: Option[Either[String, Boolean]], optional: Boolean = false) {

  override def toString: String =
    if (optional) {
      value match {
        case Some(Right(true))        => name
        case Some(Right(false))       => ""
        case Some(Left(definedValue)) => s"$name=\"$definedValue\""
        case None                     => ""
      }
    } else {
      value match {
        case Some(Right(definedValue)) => s"$name=\"$definedValue\""
        case Some(Left(definedValue))  => s"$name=\"$definedValue\""
        case None                      => s"$name=\"\""
      }
    }
}

case class EitherTAttribute(name: String, value: EitherT[Option, String, Boolean], isOptional: false) {

  override def toString: String =
    if (isOptional) {
      value.value match {
        case Some(Right(true))        => name
        case Some(Right(false))       => ""
        case Some(Left(definedValue)) => s"$name=\"$definedValue\""
        case None                     => ""
      }
    } else {
      value.value match {
        case Some(Right(definedValue)) => s"$name=\"$definedValue\""
        case Some(Left(definedValue))  => s"$name=\"$definedValue\""
        case None                      => s"$name=\"\""
      }
    }
}
