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

package uk.gov.hmrc.govukfrontend.views.components

import uk.gov.hmrc.govukfrontend.views.PreProcessor
import uk.gov.hmrc.govukfrontend.views.html.components.govukHeader

object HeaderPreProcessor extends PreProcessor {

  /***
    * The Twirl template implementation for [[govukHeader]] uses a different url for `img src` (based
    * either on reverse routes or config) than the govuk-frontend
    * template (which uses the passed in <code>assetsPath</code>), therefore before comparing, we eliminate this difference.
    *
    * @param html
    * @return
    */
  override def preProcess(html: String): String = {
    val regex = """image src=.*? xlink"""
    html.replaceAll(regex, """image src="" xlink""")
  }
}
