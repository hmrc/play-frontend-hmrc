/*
 * Copyright 2020 HM Revenue & Customs
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

package uk.gov.hmrc.govukfrontend.views.viewmodels

import play.api.libs.json._
import play.twirl.api.Html

import scala.collection.Seq
import scala.util.Try

object CommonJsonFormats {
  val readsFormGroupClasses: Reads[String] =
    (__ \ "formGroup" \ "classes").read[String].orElse(Reads.pure(""))

  val writesFormGroupClasses: OWrites[String] = new OWrites[String] {
    override def writes(classes: String): JsObject =
      Json.obj("formGroup" -> Json.obj("classes" -> classes))
  }

  val readsCountMessageClasses: Reads[String] =
    (__ \ "countMessage" \ "classes").read[String].orElse(Reads.pure(""))

  val writesCountMessageClasses: OWrites[String] = new OWrites[String] {
    override def writes(classes: String): JsObject =
      Json.obj("countMessage" -> Json.obj("classes" -> classes))
  }

  implicit val htmlWrites: Writes[Html] = new Writes[Html] {
    def writes(o: Html): JsString = JsString(o.body)
  }

  implicit val htmlReads: Reads[Html] = new Reads[Html] {
    def reads(json: JsValue): JsResult[Html] = json match {
      case JsString(s) => JsSuccess(Html(s))
      case _ => JsError("error.expected.jsstring")
    }
  }

  val readsConditionalHtml: Reads[Option[Html]] =
    (__ \ "conditional" \ "html").readNullable[String].map(_.map(Html(_))).orElse(Reads.pure(None))

  val writesConditionalHtml: OWrites[Option[Html]] = new OWrites[Option[Html]] {
    override def writes(o: Option[Html]): JsObject =
      o.map(o => Json.obj("conditional" -> Json.obj("html" -> o.body))).getOrElse(Json.obj())
  }

  val attributesReads: Reads[Map[String, String]] = new Reads[Map[String, String]] {
    override def reads(json: JsValue): JsResult[Map[String, String]] = {
      val keyValueTuples = json.as[JsObject].keys.map { key =>
        val maybeValue: Option[String] = (json \ key).asOpt[String].orElse {
          (json \ key).asOpt[Int].map(_.toString).orElse {
            (json \ key).asOpt[Boolean].map(_.toString)
          }
        }
        maybeValue.map(v => (key, v))
      }
      JsSuccess(keyValueTuples.flatten.toMap)
    }
  }

  val forgivingOptStringReads: Reads[Option[String]] = new Reads[Option[String]] {
    override def reads(json: JsValue): JsResult[Option[String]] = {
      val maybeString = json.asOpt[String].orElse {
        json.asOpt[Int].map(_.toString).orElse {
          json.asOpt[Boolean].map(_.toString)
        }
      }
      JsSuccess(maybeString)
    }
  }

  val forgivingStringReads: Reads[String] = new Reads[String] {
    override def reads(json: JsValue): JsResult[String] = {
      val maybeString = json.asOpt[String].orElse {
        json.asOpt[Int].map(_.toString).orElse {
          json.asOpt[Boolean].map(_.toString)
        }
      }
      maybeString match {
        case Some(validString) => JsSuccess(validString)
        case _ => JsError("error.expected.jsstring")
      }
    }
  }

  def forgivingSeqReads[T](implicit readsT: Reads[T]): Reads[Seq[T]] = new Reads[Seq[T]] {
    override def reads(json: JsValue): JsResult[Seq[T]] = {
      json.validate[Seq[JsValue]].map { jsValues =>
        forgivingSeqValidates(jsValues)(readsT)
      }
    }
  }

  private def forgivingSeqValidates[T](jsValues: Seq[JsValue])
                                      (implicit readsT: Reads[T]): Seq[T] = {
    jsValues flatMap { jsValue =>
      val maybeValidated: Option[JsResult[T]] =
        Try(jsValue.validate[T](readsT)).toOption
      maybeValidated.flatMap(_.asOpt)
    }
  }
}
