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

package uk.gov.hmrc.hmrcfrontend.views.viewmodels.addtoalist

import org.scalacheck.{Arbitrary, Gen}
import uk.gov.hmrc.hmrcfrontend.views.viewmodels.Generators.genAlphaStr
import uk.gov.hmrc.hmrcfrontend.views.viewmodels.language.Generators.arbLanguage

object Generators {
  implicit val arbAddToAList: Arbitrary[AddToAList] = Arbitrary {
    for {
      language   <- arbLanguage.arbitrary
      hintText   <- Gen.option(genAlphaStr())
      formAction <- Gen.option(genAlphaStr())
      itemSize   <- Gen.oneOf(Short, Long)
      itemType   <- Gen.option(arbItemType.arbitrary)
      itemList   <- genListItem()
    } yield AddToAList(
      language = language,
      hintText = hintText,
      itemSize = itemSize,
      formAction = formAction,
      itemType = itemType,
      itemList = itemList
    )
  }

  implicit val arbItemType: Arbitrary[ItemType] = Arbitrary {
    for {
      singular <- genAlphaStr()
      plural   <- genAlphaStr()
    } yield ItemType(singular = singular, plural = plural)
  }

  implicit val arbListItem: Arbitrary[ListItem] = Arbitrary {
    for {
      name      <- genAlphaStr()
      changeUrl <- genAlphaStr()
      removeUrl <- genAlphaStr()
    } yield ListItem(name = name, changeUrl = changeUrl, removeUrl = removeUrl)
  }

  def genListItem(n: Int = 5): Gen[Seq[ListItem]] = for {
    sz    <- Gen.chooseNum(0, n)
    items <- Gen.listOfN[ListItem](sz, arbListItem.arbitrary)
  } yield items
}
