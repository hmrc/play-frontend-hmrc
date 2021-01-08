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

import play.api.libs.json.{JsPath, JsString, JsValue, Json, Reads}

trait JsonImplicits {
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
}

object JsonImplicits extends JsonImplicits
