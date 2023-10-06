package uk.gov.hmrc.govukfrontend.views.viewmodels.dateinput

import DateValidationSupport._
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import play.api.data.{Form, FormError}
import play.api.data.Forms.mapping
import play.api.i18n.{Lang, Messages}
import uk.gov.hmrc.helpers.MessagesSupport

import java.time.LocalDate
import scala.List


class DataValidationSupportSpec extends AnyWordSpec with Matchers with MessagesSupport {

  trait Setup {
    val welshMessages: Messages = messagesApi.preferred(Seq(Lang("cy")))
    val englishMessages: Messages = messagesApi.preferred(Seq(Lang("en")))

    case class SomeFormWithDate(dateOfBirth: LocalDate)


    val testForm: Form[SomeFormWithDate] = Form[SomeFormWithDate](
      mapping(
       "dateOfBirth" -> govukDate()
      )(SomeFormWithDate.apply)(SomeFormWithDate.unapply)
    )
  }

  "xyz" should {
    "abc" in new Setup {
      val form: Form[SomeFormWithDate] = testForm.bind(Map(
        "dateOfBirth.day" -> "50",
        "dateOfBirth.month" -> "12",
        "dateOfBirth.year" -> "2023"
      ))
      form.errors should be(List(FormError("dateOfBirth.day",Seq("error.max"),Seq(31))))
    }
  }
}
