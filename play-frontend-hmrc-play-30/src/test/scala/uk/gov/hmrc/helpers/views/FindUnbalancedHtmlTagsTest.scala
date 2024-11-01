package uk.gov.hmrc.helpers.views

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.prop.TableDrivenPropertyChecks
import uk.gov.hmrc.helpers.views.FindUnbalancedHtmlTags._

class FindUnbalancedHtmlTagsTest extends AnyFunSuite with TableDrivenPropertyChecks {

  private val tagsAreBalancedCases = Table(
    ("input", "expected result"),
    ("", HtmlTagsAreBalanced),
    ("<br/>", HtmlTagsAreBalanced),
    ("<p>Hello</p>", HtmlTagsAreBalanced),
    ("<div><p>Hello</p></div>", HtmlTagsAreBalanced),
    ("<!DOCTYPE html><html><body></body></html>", HtmlTagsAreBalanced),
    ("""<html><head><meta test="foo" value="bar"></head><body><img src="test" /></body></html>""", HtmlTagsAreBalanced)
  )

  private val unclosedCases = Table(
    ("input", "expected result"),
    ("<p>Unclosed", Unclosed(Seq("p"))),
    ("<div><p>Unclosed</div>", Unclosed(Seq("p"))),
    ("<div><p>Unclosed<span>Nested</div>", Unclosed(Seq("span"))), // can only get multiple if we are at end of input
    ("<html><head><meta></head>", Unclosed(Seq("html")))
  )

  private val unopenedCases = Table(
    ("input", "expected result"),
    ("</p>", Unopened("p")),
    ("</div></p>", Unopened("div")),
    ("<div></span></div>", Unopened("span"))
  )

  private val malformedCases = Table(
    ("input", "expected result"),
    ("<p<bold>Malformed</bold><p>", Malformed("<p<bold")),
    ("<p>Malformed<p", Malformed("<p")),
    ("<!--<b>test</b>--><p>test</p>", Malformed("<!--<b")) // doesn't handle html in comments
  )

  forAll(
    Table(
      ("group name", "test cases"),
      ("Tags are balanced", tagsAreBalancedCases),
      ("Includes unclosed tags", unclosedCases),
      ("Includes unopened tags", unopenedCases),
      ("Includes malformed html tags", malformedCases)
    )
  ) { (groupName, testCases) =>
    test(groupName) {
      forAll(testCases) { (input, expectedResult) =>
        assert(FindUnbalancedHtmlTags.check(input) == expectedResult)
      }
    }
  }
}
