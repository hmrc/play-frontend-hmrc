package uk.gov.hmrc.helpers

import org.jsoup.Jsoup
import uk.gov.hmrc.helpers.views.TemplateDiff.templateDiffPath

trait DiffReporter[T] {

  def reportDiff(
    preProcessedTwirlHtml: String,
    preProcessedNunjucksHtml: String,
    templateParams: T,
    templateParamsJson: String,
    componentName: String
  ): Unit = {

    val diffPath =
      templateDiffPath(
        twirlOutputHtml = preProcessedTwirlHtml,
        nunJucksOutputHtml = preProcessedNunjucksHtml,
        diffFilePrefix = Some(componentName)
      )

    println(s"Diff between Twirl and Nunjucks outputs (please open diff HTML file in a browser): file://$diffPath\n")

    println("-" * 80)
    println("Twirl")
    println("-" * 80)

    val formattedTwirlHtml = Jsoup.parseBodyFragment(preProcessedTwirlHtml).body.html
    println(s"\nTwirl output:\n$formattedTwirlHtml\n")

    println(s"\nparameters: ")
    pprint.pprintln(templateParams, width = 80, height = 500)

    println("-" * 80)
    println("Nunjucks")
    println("-" * 80)

    val formattedNunjucksHtml = Jsoup.parseBodyFragment(preProcessedNunjucksHtml).body.html
    println(s"\nNunjucks output:\n$formattedNunjucksHtml\n")

    println(s"\nparameters: ")
    println(templateParamsJson)
  }

}
