/*
 * Copyright 2023 HM Revenue & Customs
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

package uk.gov.hmrc.govukfrontend.views.viewmodels.pagination

import org.scalacheck.Arbitrary.arbBool
import org.scalacheck.{Arbitrary, Gen}
import uk.gov.hmrc.govukfrontend.views.viewmodels.Generators._

object Generators {

  implicit val arbPaginationLink: Arbitrary[PaginationLink] = Arbitrary {
    for {
      href       <- genNonEmptyAlphaStr
      text       <- Gen.option(genNonEmptyAlphaStr)
      labelText  <- Gen.option(genNonEmptyAlphaStr)
      attributes <- genAttributes()
    } yield PaginationLink(
      href = href,
      text = text,
      labelText = labelText,
      attributes = attributes
    )
  }

  implicit val arbPaginationItem: Arbitrary[PaginationItem] = Arbitrary {
    for {
      href               <- genNonEmptyAlphaStr
      number             <- Gen.option(genNonEmptyAlphaStr)
      visuallyHiddenText <- Gen.option(genNonEmptyAlphaStr)
      current            <- Gen.option(arbBool.arbitrary)
      ellipsis           <- Gen.option(arbBool.arbitrary)
      attributes         <- genAttributes()
    } yield PaginationItem(
      href = href,
      number = number,
      visuallyHiddenText = visuallyHiddenText,
      current = current,
      ellipsis = ellipsis,
      attributes = attributes
    )
  }

  def isValidPaginationItem(paginationItem: PaginationItem): Boolean =
    (paginationItem.number, paginationItem.ellipsis, paginationItem.current) match {
      case (Some(n), None, _) if n.nonEmpty => true
      case (None, Some(true), None)         => true
      case _                                => false
    }

  implicit val arbPagination: Arbitrary[Pagination] = Arbitrary {
    for {
      nItems        <- Gen.chooseNum(1, 5)
      items         <- Gen.option(Gen.listOfN(nItems, arbPaginationItem.arbitrary.suchThat(isValidPaginationItem)))
      previous      <- Gen.option(arbPaginationLink.arbitrary)
      next          <- Gen.option(arbPaginationLink.arbitrary)
      landmarkLabel <- Gen.option(genNonEmptyAlphaStr)
      classes       <- genClasses()
      attributes    <- genAttributes()
    } yield Pagination(
      items = items,
      previous = previous,
      next = next,
      landmarkLabel = landmarkLabel,
      classes = classes,
      attributes = attributes
    )
  }
}
