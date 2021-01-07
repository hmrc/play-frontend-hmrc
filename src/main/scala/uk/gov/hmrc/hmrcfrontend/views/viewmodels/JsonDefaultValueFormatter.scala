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

package uk.gov.hmrc.hmrcfrontend.views.viewmodels

import play.api.libs.json._

// FIXME: Remove when moving to Play >= 2.6
/***
  * Play 2.5 doesn't have `Json.using[Json.WithDefaultValues]` or `Json.readWithDefault` to deserialise Json with missing paths.
  * This feature was introduced in Play 2.6 so this is a workaround taken from: from [[https://medium.com/@gauravsingharoy/how-to-add-defaults-for-missing-properties-in-scala-play-json-unmarshalling-70ff10b775e3]]
  *
  * @tparam T
  */
trait JsonDefaultValueFormatter[T] {

  def defaultObject: T

  def defaultReads: Reads[T]

  implicit def jsonWrites: OWrites[T]

  // The idea here is to create an instance of the case class with default values and merge its Json representation.
  // with the deserialised Json.
  // The case class must have default values for all fields.
  implicit def jsonReads: Reads[T] = new Reads[T] {
    def reads(json: JsValue): JsResult[T] = {
      val defaultJson = Json.toJson(defaultObject)
      val finalJson   = Seq(defaultJson, json).foldLeft(Json.obj())((obj, a) => obj.deepMerge(a.as[JsObject]))
      defaultReads.reads(finalJson)
    }
  }
}
