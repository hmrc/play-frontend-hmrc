package uk.gov.hmrc.govukfrontend.support

object Utils {

  implicit class RichString(url: String) {

    def +/(otherUrl: String): String =
      if (otherUrl.startsWith("/")) url + otherUrl else url + "/" + otherUrl
  }
}
