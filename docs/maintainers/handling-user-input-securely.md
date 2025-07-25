# Handling user input securely

[Cross-site scripting (XSS)](https://owasp.org/www-community/attacks/xss/)
is an attack where a malicious actor executes arbitrary JavaScript in the user's browser,
typically to exfiltrate sensitive data such as cookies or session state,
by including `<script>` tags, or attributes like `onload` that can execute JavaScript.
There are a few ways that you can protect your service from these sorts of attack.

## Content-Security Policy (CSP)
Disabling the use of inline JavaScript, by removing 'unsafe-inline' from your CSP,
will reduce the risk of injected JavaScript from running.

## Sanitising or rejecting user input on submission
The best way to protect your service against malicious input is to sanitise or reject it as soon as it's submitted.
This might be via a form submission, or as path/query parameters in a URL.
Such data should be validated against the most restrictive constraints possible.

Within the Play framework, this can be achieved using custom
[form mappings](https://www.playframework.com/documentation/3.0.x/ScalaForms)
or
[request binders](https://www.playframework.com/documentation/3.0.x/ScalaRequestBinders).
eg. for Forms:
```scala
    val myForm = Form[MyData](
      mapping(
        "username" -> Forms.text.verifying(_.matches("""^[^<>"&]*$""")) // This will reject XSS chars
      )(MyData.apply)(MyData.unapply)
    )
```

## Escaping dynamic data when rendering HTML
Even if data comes from a trusted API, it may have got there via an insecure route, so it should always be treated as unsafe.
When including any dynamic data in HTML pages, it should be escaped.

In Twirl templates, including user data with dynamic statements (`@` notation) is automatically escaped by Play.

When passing data values to components in play-frontend-hmrc, you should use one of the types derived from the [`Content`](/play-frontend-hmrc-play-30/src/main/scala/uk/gov/hmrc/govukfrontend/views/viewmodels/content/Content.scala) trait.

eg.
```scala
    SummaryListRow(
      value = Value(Text(myDataValue))
    )
```

## Messages
It is worth specifically mentioning the use of HTML in messages.
Where possible, restrict the use of HTML tags to Twirl templates, and use messages for plain text.
If messages contain HTML, use `@Messages` to render them inline, or wrap them with `HtmlContent` when passing to a Scala component.
Be extra careful if messages include user-provided data as parameters.

eg.

`messages.conf`:
```hocon
    some.text.message=Welcome {0}
    some.html.message=<b>Welcome {0}</b>
```

Twirl template:
```scala
    @* Safe - auto-escaped by Play *@
    <b>@Messages("text.message", username)</b>

    @* Unsafe - HtmlContent used since message contains HTML, but leads to dangerous use of username *@
    @govUkNotificationBanner(NotificationBanner(content = HtmlContent(Messages("html.message", username))))
```

