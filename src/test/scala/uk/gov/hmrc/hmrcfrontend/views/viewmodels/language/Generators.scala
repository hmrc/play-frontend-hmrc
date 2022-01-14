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

package uk.gov.hmrc.hmrcfrontend.views.viewmodels.language

import org.scalacheck.{Arbitrary, Gen}
import uk.gov.hmrc.hmrcfrontend.views.viewmodels.Generators._

object Generators {

  implicit val arbLanguage: Arbitrary[Language] = Arbitrary(Gen.oneOf(Cy, En))

  val arbLangLinkVal: Arbitrary[(Language, String)] = Arbitrary {
    for {
      language <- arbLanguage.arbitrary
      value    <- genAlphaStr()
    } yield (language, value)
  }

  def genLanguageLinks(nLinks: Int = 5): Gen[Array[(Language, String)]] = for {
    sz    <- Gen.chooseNum(0, nLinks)
    links <- Gen.listOfN[(Language, String)](sz, arbLangLinkVal.arbitrary)
  } yield links.toArray

  implicit val arbLanguageToggle: Arbitrary[LanguageToggle] = Arbitrary {
    for {
      links <- genLanguageLinks()
    } yield LanguageToggle(links: _*)
  }

  implicit val arbLanguageSelect: Arbitrary[LanguageSelect] = Arbitrary {
    for {
      language      <- arbLanguage.arbitrary
      languageLinks <- genLanguageLinks()
    } yield LanguageSelect(language = language, languageLinks = languageLinks: _*)
  }
}
