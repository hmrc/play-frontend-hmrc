/*
 * Copyright 2022 HM Revenue & Customs
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

package uk.gov.hmrc.hmrcfrontend.views.viewmodels.reporttechnicalissue

import org.scalacheck.{Arbitrary, Gen}
import uk.gov.hmrc.hmrcfrontend.views.viewmodels.Generators._
import uk.gov.hmrc.hmrcfrontend.views.viewmodels.language.Generators.arbLanguage

object Generators {

  implicit val arbReportTechnicalIssue: Arbitrary[ReportTechnicalIssue] = Arbitrary {
    for {
      text        <- genAlphaStr()
      language    <- arbLanguage.arbitrary
      classes     <- Gen.option(genClasses())
      baseUrl     <- Gen.option(genAlphaStr())
      referrerUrl <- Gen.option(genAlphaStr())
    } yield ReportTechnicalIssue(
      serviceCode = text,
      language = language,
      classes = classes,
      baseUrl = baseUrl,
      referrerUrl = referrerUrl
    )
  }
}
