/*
 * Copyright 2026 HM Revenue & Customs
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

package uk.gov.hmrc.hmrcfrontend.views.viewmodels.charactercount

import play.api.libs.json._
import uk.gov.hmrc.govukfrontend.views.viewmodels.charactercount.{CountType => GovukCountType}
import uk.gov.hmrc.govukfrontend.views.viewmodels.charactercount.{WordsCountType => GovukWordsCountType}
import uk.gov.hmrc.govukfrontend.views.viewmodels.charactercount.{LengthCountType => GovukLengthCountType}
import uk.gov.hmrc.govukfrontend.views.viewmodels.charactercount.{CharactersCountType => GovukCharactersCountType}

trait CountType {
  val asString: String
  val asGovukCountType: GovukCountType =
    this match {
      case CharactersCountType => GovukCharactersCountType
      case LengthCountType     => GovukLengthCountType
      case WordsCountType      => GovukWordsCountType
    }
}

case object CharactersCountType extends CountType {
  override val asString: String = "characters"
}

case object LengthCountType extends CountType {
  override val asString: String = "length"
}

case object WordsCountType extends CountType {
  override val asString: String = "words"
}

object CountType {
  implicit def reads: Reads[CountType] = new Reads[CountType] {
    override def reads(json: JsValue): JsResult[CountType] = json.validate[String] match {
      case JsSuccess("characters", _) => JsSuccess(CharactersCountType)
      case JsSuccess("length", _)     => JsSuccess(LengthCountType)
      case JsSuccess("words", _)      => JsSuccess(WordsCountType)
      case JsSuccess(other, _)        => JsError(s"countType is $other, must be one of: characters, length, words")
      case jsError: JsError           => jsError
    }
  }

  implicit def writes: Writes[CountType] = new Writes[CountType] {
    override def writes(o: CountType): JsValue = JsString(o.asString)
  }
}
