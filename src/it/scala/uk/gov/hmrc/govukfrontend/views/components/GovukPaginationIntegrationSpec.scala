/*
 * Copyright 2019 HM Revenue & Customs
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

package uk.gov.hmrc.govukfrontend.views.components

import uk.gov.hmrc.govukfrontend.support.TemplateIntegrationSpec
import uk.gov.hmrc.govukfrontend.views.html.components._
import uk.gov.hmrc.govukfrontend.views.viewmodels.pagination.Generators._

object GovukPaginationIntegrationSpec
//    extends TemplateIntegrationSpec[Pagination, GovukPagination](
//      govukComponentName = "govukPagination",
//      seed = None,
//      maximumCompression = true
//    )

// TODO: This test is currently failing due to a bug in the govuk-frontend with the positioning of a closing
//  double-quote. The fix has been merged to main but not released yet - once the next bug fix release of
//  govuk-frontend is released, this test should be un-commented: https://github.com/alphagov/govuk-frontend/pull/3156.
