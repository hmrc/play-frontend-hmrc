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

package uk.gov.hmrc.govukfrontend.views.viewmodels.table

import org.scalacheck.{Arbitrary, Gen}
import uk.gov.hmrc.govukfrontend.views.viewmodels.Generators._
import uk.gov.hmrc.govukfrontend.views.viewmodels.content.Generators._

object Generators {

  implicit val arbHeadCell: Arbitrary[HeadCell] = Arbitrary {
    for {
      content    <- arbContent.arbitrary
      format     <- Gen.option(genAlphaStr())
      classes    <- genClasses()
      colspan    <- Gen.option(Gen.chooseNum(0, 5))
      rowspan    <- Gen.option(Gen.chooseNum(0, 5))
      attributes <- genAttributes()
    } yield
      HeadCell(
        content    = content,
        format     = format,
        classes    = classes,
        colspan    = colspan,
        rowspan    = rowspan,
        attributes = attributes)
  }

  implicit val arbTableRow: Arbitrary[TableRow] = Arbitrary {
    for {
      content    <- arbContent.arbitrary
      format     <- Gen.option(genAlphaStr())
      classes    <- genClasses()
      colspan    <- Gen.option(Gen.chooseNum(0, 5))
      rowspan    <- Gen.option(Gen.chooseNum(0, 5))
      attributes <- genAttributes()
    } yield
      TableRow(
        content    = content,
        format     = format,
        classes    = classes,
        colspan    = colspan,
        rowspan    = rowspan,
        attributes = attributes)
  }

  implicit val arbTable: Arbitrary[Table] = Arbitrary {
    for {
      nTableRows <- Gen.chooseNum(0, 5)
      tableRowsGen = Gen.listOfN(nTableRows, arbTableRow.arbitrary)
      nRows             <- Gen.chooseNum(0, 5)
      rows              <- Gen.listOfN(nRows, tableRowsGen)
      nHeadCells        <- Gen.chooseNum(0, 5)
      head              <- Gen.option(Gen.listOfN(nHeadCells, arbHeadCell.arbitrary))
      caption           <- Gen.option(genAlphaStr())
      captionClasses    <- genClasses()
      firstCellIsHeader <- Arbitrary.arbBool.arbitrary
      classes           <- genClasses()
      attributes        <- genAttributes()
    } yield
      Table(
        rows              = rows,
        head              = head,
        caption           = caption,
        captionClasses    = captionClasses,
        firstCellIsHeader = firstCellIsHeader,
        classes           = classes,
        attributes        = attributes)
  }

}
