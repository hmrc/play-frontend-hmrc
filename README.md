# play-frontend-hmrc

This library contains all the Twirl components and helpers needed to implement frontend microservices on the HMRC
digital tax platform.

The library transitively depends on [play-frontend-govuk](https://github.com/hmrc/play-frontend-govuk/) which is a Twirl
port of [alphagov/govuk-frontend](https://www.github.com/alphagov/govuk-frontend),
adding to it HMRC-specific Twirl components as well as Play and platform aware helpers that make the process
of implementing frontend microservices straightforward and idiomatic for Scala developers.

The library comprises two packages:

1. components - a set of Twirl templates providing a direct port of the Nunjucks components from
   [hmrc/hmrc-frontend](https://www.github.com/hmrc/hmrc-frontend)
1. helpers
    1. wrappers designed to make using the hmrc-frontend components more straightforward and idiomatic in Scala/Play
    1. a collection of markup snippets required by MDTP microservices

## Table of Contents

- [Getting started](#getting-started)
- [Play framework compatibility](#play-framework-compatibility)
- [HMRC layout - EXPERIMENTAL](#hmrc-layout-experimental)
- [Custom layout](#custom-layout)
- [Standard HMRC header](#standard-hmrc-header)
- [Helping users report technical issues](#helping-users-report-technical-issues)
- [Standard HMRC footer and accessibility statements](#standard-hmrc-footer-and-accessibility-statements)
- [CharacterCount with Welsh language support](#charactercount-with-welsh-language-support)
- [Integrating with tracking consent](#integrating-with-tracking-consent)
- [Warning users before timing them out](#warning-users-before-timing-them-out)
- [Welsh language selection](#welsh-language-selection)
- [RichDateInput](#richdateinput)
- [Adding your own SASS compilation pipeline](#adding-your-own-sass-compilation-pipeline)
- [Play Framework and Scala compatibility notes](#play-framework-and-scala-compatibility-notes)
- [Getting help](#getting-help)
- [Useful links](#useful-links)
- [Owning team readme](#owning-team-readme)
- [License](#license)

## Getting started
1.  Add the version of [play-frontend-hmrc](https://github.com/hmrc/play-frontend-hmrc/releases) specific to your Play version
    in your `project/AppDependencies.scala` file. For example,
    ```sbt
    libraryDependencies += "uk.gov.hmrc" %% "play-frontend-hmrc" % "x.y.z-play-27"
    ```

    The library is cross-compiled for Play 2.6, 2.7, and 2.8. Note, because this library transitively depends on play-frontend-govuk
    there is no need to add this as a separate dependency. If it is already included, it can be removed.

1.  Add routes for hmrc-frontend and govuk-frontend assets in `conf/app.routes`:
    ```scala
    ->         /hmrc-frontend                      hmrcfrontend.Routes
    ```

1. Ensure you have your service name defined in your messages files. For example,
    ```scala
    service.name = Any tax service
    ``` 

1. Do ONE of the following:
    * Create a custom layout template for your pages using `hmrcLayout` as per the section
      [HMRC layout EXPERIMENTAL](#hmrc-layout-experimental), OR
    * Create a custom layout template for your pages using `govukLayout` as per the section
      [Custom layout](#custom-layout)

1.  Optionally, add `TwirlKeys.templateImports` in `build.sbt`:
    ```sbt
        TwirlKeys.templateImports ++= Seq(
          "uk.gov.hmrc.govukfrontend.views.html.components._",
          "uk.gov.hmrc.govukfrontend.views.html.helpers._",
          "uk.gov.hmrc.hmrcfrontend.views.html.components._",
          "uk.gov.hmrc.hmrcfrontend.views.html.helpers._"
        )
    ```

    Adding this removes the need for `@import` statements in your Twirl templates.
    If you prefer not to use this mechanism, import the components in any template that uses them as
    follows:

    ```scala
    @import uk.gov.hmrc.hmrcfrontend.views.html.components._
    ```

### Useful implicits

The following import will summon [implicits](https://github.com/hmrc/play-frontend-hmrc/blob/master/src/main/scala/uk/gov/hmrc/hmrcfrontend/views/Implicits.scala) that provide extension methods on `Play’s` [Html](https://www.playframework.com/documentation/2.6.x/api/scala/play/twirl/api/Html.html) objects.
This includes useful HTML trims, pads, indents and handling HTML emptiness.
```scala
@import uk.gov.hmrc.hmrcfrontend.views.html.components.implicits._
```

### Find working examples

You can find working examples of the use of play-frontend-hmrc in the following actively maintained repositories:
* [tracking-consent-frontend](https://www.github.com/hmrc/tracking-consent-frontend)
* [accessibility-statement-frontend](https://www.github.com/hmrc/accessibility-statement-frontend)
* [contact-frontend](https://www.github.com/hmrc/contact-frontend)

## Play Framework compatibility

The library is cross-compiled for `Play 2.6`, `Play 2.7`, and `Play 2.8`.

The same namespace exposes type aliases prefixed with `Hmrc` (ex: the type `HmrcPageHeading`) so that components can be injected into
a controller or template. It also exposes values of the same name (ex: `HmrcPageHeading`) if you wish to use the component template directly,
though it is preferable to use dependency injection.

Same button using DI:
```scala
@import uk.gov.hmrc.hmrcfrontend.views.html.components._

@this(hmrcPageHeading: HmrcPageHeading)

@()
@hmrcPageHeading(PageHeading(
  text = "Foo",
  section = Some("Section bar")
))
```

### Example Templates

We provide example templates using the Twirl components through a `Chrome` extension. Please refer to the
[extension’s github repository](https://github.com/hmrc/play-frontend-govuk-extension) for installation instructions.

With the extension installed, you should be able to go to the [HMRC Design System](https://design.tax.service.gov.uk/hmrc-design-patterns/),
click on a component on the sidebar and see the `Twirl` examples matching the provided `Nunjucks` templates.

## HMRC layout EXPERIMENTAL

**This is a new feature, and we may make changes to the API based on feedback from service teams. Please do contact via 
Slack #team-plat-ui with any feedback, we very much hope that you will try it out.**

The [hmrcLayout](src/main/twirl/uk/gov/hmrc/hmrcfrontend/views/helpers/hmrcLayout.scala.html) helper
generates a helper layout for your pages including the `hmrcStandardHeader`, `hmrcStandardFooter`, Welsh language 
toggle, and various banners. It can be used in place of your own `Layout.scala.html`, or it can be used to simplify your 
own shared layout.

To use this component,

1. Ensure you have your service name defined in your messages files. For example,
    ```scala
    service.name = Any tax service
   ```
   This is required by the `hmrcStandardHeader`
   
1. Create a file `Layout.scala.html` and use the `hmrcLayout` as demonstrated below (please note that NOT all fields are
   mandatory):

    ```scala 
    @import uk.gov.hmrc.hmrcfrontend.views.html.helpers.HmrcLayout
    @import uk.gov.hmrc.hmrcfrontend.views.config.StandardBetaBanner
    @import views.html.helper.CSPNonce
    @import config.AppConfig

    @this(hmrcLayout: HmrcLayout, standardBetaBanner: StandardBetaBanner)

    @(
      pageTitle: String,
      isWelshTranslationAvailable: Boolean = true)(
      contentBlock: Html)(
      implicit request: RequestHeader, messages: Messages)

    @hmrcLayout(
      pageTitle = Some(pageTitle),
      isWelshTranslationAvailable = isWelshTranslationAvailable,
      signOutUrl = Some(appConfig.signOutUrl),
      displayHmrcBanner = true,
      phaseBanner = Some(standardBetaBanner(url = appConfig.betaFeedbackUrl)),
      nonce = CSPNonce.get,
    )(contentBlock)
    ```
   
1. The parameters that can be passed into the `hmrcLayout` and their default values are as follows:
    ```scala
    @(
      serviceName: Option[String] = None,           // This will be bound to the govukTemplate
      pageTitle: Option[String] = None,             // This will be bound to the govukTemplate
      isWelshTranslationAvailable: Boolean = false, // Setting to true will display the language toggle
      signOutUrl: Option[String] = None,            // Passing value will display the sign out link
      userResearchBannerUrl: Option[String] = None, // Passing value will display the UserResearchBanner
      displayHmrcBanner: Boolean = false,           // Setting to true will display the HMRC banner
      phaseBanner: Option[PhaseBanner] = None,      // Passing value will display alpha or beta banner
      additionalHeadBlock: Option[Html] = None,     // Passing value will add additional content in head
      additionalScriptsBlock: Option[Html] = None,  // Passing value will add additional scripts between the footer and end of body
      nonce: Option[String] = None,                 // Passing value will bind in head and scripts
      mainContentLayout: Option[Html => Html] = Some(defaultMainContent(_)) // Passing value will allow custom styling of main content
    )(contentBlock: Html)(implicit request: RequestHeader, messages: Messages)
    ```
    
## Custom layout

If you don't wish to use the `hmrcLayout`, you can create a layout template using `govukLayout` that you can use in all of your service pages. For example:

    @import uk.gov.hmrc.hmrcfrontend.views.html.helpers.{HmrcStandardFooter, HmrcStandardHeader, HmrcHead, HmrcScripts}

    @this(
      govukLayout: GovukLayout,
      hmrcHead: HmrcHead,
      hmrcStandardHeader: HmrcStandardHeader,
      hmrcStandardFooter: HmrcStandardFooter,
      hmrcScripts: HmrcScripts
    )

    @(pageTitle: String)(contentBlock: Html)(implicit request: RequestHeader, messages: Messages)

    @govukLayout(
      pageTitle = Some(pageTitle),
      headBlock = Some(hmrcHead()),
      headerBlock = Some(hmrcStandardHeader(
        serviceUrl = Some(controllers.routes.IndexController.index().url)
      )),
      scriptsBlock = Some(hmrcScripts()),
      footerBlock = Some(hmrcStandardFooter())
    )(contentBlock)

From Play 2.7 when using the CSPFilter the nonce can be passed to hmrcHead as follows:

   ```
    @govukLayout(
        ...
        headBlock = Some(hmrcHead(nonce = CSPNonce.get)),
        ...
    )(contentBlock)
   ```

## Standard HMRC header

The [hmrcStandardHeader](src/main/twirl/uk/gov/hmrc/hmrcfrontend/views/helpers/hmrcStandardHeader.scala.html) helper
generates the GOV.UK header standardised for HMRC with Welsh translations, and a screen-reader friendly option to
include the HMRC banner for services that require it.

To use this component,

1. Ensure you have your service name defined in your messages files. For example,
    ```scala
    service.name = Any tax service
    ``` 

1. If your service requires users to sign in, identify the URL that users should use to sign out of your service. If you have a
   dedicated sign out controller you can use its reverse controller. This url should be passed to the
   `signOutUrl` parameter.

1. Pass the output of hmrcStandardHeader into the headerBlock parameter of govukLayout in your main template. For example,

```scala
@govukLayout(
  pageTitle = pageTitle,
  headBlock = Some(hmrcHead(nonce = CSPNonce.get)),
  headerBlock = Some(hmrcStandardHeader(
    serviceUrl = Some(controllers.routes.IndexController.index().url),
    signOutUrl = Some(controllers.routes.SignOutController.signOut().url)
  )),
  beforeContentBlock = Some(languageSelect()),
  footerBlock = Some(hmrcStandardFooter()),
  scriptsBlock = Some(hmrcScripts(nonce = CSPNonce.get))
)(contentBlock)
```

hmrcStandardHeader can also be configured to generate the GOV.UK phase banner, HMRC user research
recruitment banner and the [HMRC banner](https://design.tax.service.gov.uk/hmrc-design-patterns/hmrc-banner/)
inside the HEADER tag. For example,

```
hmrcStandardHeader(
  serviceUrl = Some(controllers.routes.IndexController.index().url),
  signOutUrl = Some(controllers.routes.SignOutController.signOut().url),
  phaseBanner = Some(standardBetaBanner(url = appConfig.feedbackUrl)),
  userResearchBanner = Some(UserResearchBanner(url = appConfig.userResearchUrl)),
  displayHmrcBanner = true
)
```

In the exceptional case that you have a frontend microservice that has a dynamic service name, for example, because it hosts
more than one public-facing service, you can override the service name using the `serviceName` parameter.

## Helping users report technical issues

The [hmrcReportTechnicalIssueHelper](src/main/twirl/uk/gov/hmrc/hmrcfrontend/views/helpers/hmrcReportTechnicalIssueHelper.scala.html) component
generates a link that allows users to report technical issues with your service.

To configure this helper, add the following configuration to your `application.conf`

```
contact-frontend.serviceId = "<any-service-id>"
```

serviceId helps identify your service when members of the public report technical issues.
If your service is *not* already integrating with contact-frontend, we advise choosing an
identifier that is specific to your service and unlikely to be used by any other service, avoiding any special characters
or whitespace.

The component should be added to the bottom of each page on your service by including it in
your service’s main page template,

```scala
@import uk.gov.hmrc.hmrcfrontend.views.html.helpers._

@this(hmrcReportTechnicalIssueHelper: HmrcReportTechnicalIssueHelper)

...

@hmrcReportTechnicalIssueHelper()
```

## Standard HMRC footer and accessibility statements

The [hmrcStandardFooter](src/main/twirl/uk/gov/hmrc/hmrcfrontend/views/helpers/hmrcStandardFooter.scala.html) helper
generates the standard Gov.UK footer including the standard list of footer links for HMRC.

To configure this helper to link to the new
[Accessibility Statement service](https://www.github.com/hmrc/accessibility-statement-frontend), provide the key
`accessibility-statement.service-path` in your `application.conf` file. This key is the path to your
accessibility statement under https://www.tax.service.gov.uk/accessibility-statement.

For example, if your accessibility statement is https://www.tax.service.gov.uk/accessibility-statement/discounted-icecreams,
this property must be set to `/discounted-icecreams` as follows:

```
accessibility-statement.service-path = "/discounted-icecreams"
```

In the exceptional case that you need to link to an accessibility statement not hosted
within accessibility-statement-frontend, the default behaviour can be overridden by supplying an
`accessibilityStatementUrl` parameter to `hmrcStandardFooter`.

## CharacterCount with Welsh language support

The [hmrcCharacterCount](src/main/twirl/uk/gov/hmrc/hmrcfrontend/views/components/hmrcCharacterCount.scala.html) is an
implementation of the GOV.UK CharacterCount that translates the dynamic words / characters remaining
text into English or Welsh using the Play framework Message API. You do not need to pass through the
language explicitly to this component, just pass through an implicit Messages.
```
@import uk.gov.hmrc.hmrcfrontend.views.viewmodels.charactercount.CharacterCount

@this(hmrcCharacterCount: HmrcCharacterCount)

@(implicit messages: Messages)
@hmrcCharacterCount(CharacterCount(
    name = "some-component-name",
    id = "some-component-id",
    maxWords = Some(1000),
    label = Label(content = Text("Please enter your text")),
))
```

## Integrating with tracking consent

If you intend to use Google Analytics or Optimizely to measure usage of your service, you will need to integrate with
[tracking-consent-frontend](https://www.github.com/hmrc/tracking-consent-frontend). The
[hmrcTrackingConsentSnippet](src/main/twirl/uk/gov/hmrc/hmrcfrontend/views/helpers/hmrcTrackingConsentSnippet.scala.html)
component generates the necessary HTML SCRIPT tags that must be injected into the HEAD element for every page on your service.

If you are using the `hmrcHead` component to integrate with play-frontend-hmrc, then this component is already being
added to your microservice – all that is additionally required is a configuration key to set the GTM container (see below).

Before integrating, it is important to remove any existing snippets relating to GTM or Optimizely. Tracking consent
manages the enabling of these third-party solutions based on the user’s tracking preferences. If they are not removed
there is a risk the user’s tracking preferences will not be honoured.

Configure your service’s GTM container in `conf/application.conf`. For example, if you have been
instructed to use GTM container `a`, the configuration would appear as:

```
tracking-consent-frontend {
  gtm.container = "a"
}
```

`gtm.container` can be one of: `transitional`, `a`, `b`, `c`, `d`, `e`, `f` or `sdes`. Consult with the CIPSAGA team
to identify which GTM container you should be using in your service.

If your page template is already using hmrcHead then no further work is required. Otherwise, add hmrcTrackingConsentSnippet
above any other assets imported in your HEAD element. For example,

    ```scala
    @this(hmrcTrackingConsentSnippet: HmrcTrackingConsentSnippet)
    
    @()(implicit appConfig: AppConfig, messages: Messages)
    @hmrcTrackingConsentSnippet()
    ...
    ```

From Play 2.7 when using the CSPFilter the nonce can be passed to tracking consent as follows:

```
@import views.html.helper.CSPNonce
...
@hmrcTrackingConsentSnippet(nonce = CSPNonce.get)
```

## Warning users before timing them out

In order to meet the accessibility [WCAG 2.1 Principle 2: Operable](https://www.w3.org/TR/WCAG21/#operable) you must
provide users with enough time to read and use content. In particular, WCAG 2.1 Success Criterion 2.2.1 (Timing Adjustable)
requires that users are able to turn off, adjust or extend the time limit, giving them at least 20 seconds to perform this with
a simple action.

On MDTP, users are, by default, automatically timed out of any authenticated service after 15 minutes
of inactivity. This mechanism, implemented in [SessionTimeoutFilter](https://github.com/hmrc/bootstrap-play/blob/master/bootstrap-frontend-src/main/scala/uk/gov/hmrc/play/bootstrap/frontend/filters/SessionTimeoutFilter.scala),
clears all non-allow-listed session keys after the timeout duration has elapsed. Services can override this default by adjusting the
`session.timeout` configuration key in `conf/application.conf`.

The [hmrcTimeoutDialogHelper](src/main/twirl/uk/gov/hmrc/hmrcfrontend/views/helpers/hmrcTimeoutDialogHelper.scala.html)
component helps services meet this accessibility obligation by delivering an accessible timeout warning
inside a modal dialog a configurable number of seconds before they are due to be timed out. The dialog warns the user with the message
'For your security, we will sign you out in X minutes.' which is updated every minute until 60 seconds are remaining, at
which point it counts down in seconds. For screen-reader users, an audible message counts down in 20 second increments.

Users are then given the option to 'Stay signed in', which extends their session by the timeout duration, or 'Sign out'
returning them to the supplied `signOutUrl`.

### How to integrate with the timeout dialog

The instructions below assume you have set up play-frontend-hmrc as indicated above.

1. Identify the `signOutUrl` that will be used when users click 'Sign Out' on the timeout dialog. A sensible choice for this is the
   url that is already supplied as the `signOutUrl` parameter to the `hmrcStandardHeader` component, which controls the sign
   out link in the GOV.UK header. See related guidance above.

1. Update your page template to include the hmrcTimeoutDialogHelper in the HEAD element, supplying the signOutUrl as
   a parameter. For example,

    ```scala
    @signOutUrl = @{ Some(controllers.routes.SignOutController.signOut().url) }

    @govukLayout(
      pageTitle = Some(pageTitle),
      headBlock = Some(hmrcHead(
        headBlock = Some(hmrcTimeoutDialogHelper(signOutUrl = signOutUrl)),
        nonce = CSPNonce.get
      )),
      headerBlock = Some(hmrcStandardHeader(
        serviceUrl = Some(controllers.routes.IndexController.index().url),
        signOutUrl = signOutUrl
      )),
      scriptsBlock = Some(hmrcScripts(nonce = CSPNonce.get)),
      footerBlock = Some(hmrcStandardFooter())
    )(contentBlock)
    ```

#### Customisation ####
By default, the timeout dialog will redirect to the supplied signOutUrl if they do nothing after the timeout duration
has elapsed. If you wish users to be redirected to a different URL, a separate `timeoutUrl` can be supplied.

If your service has a timeout duration different to that configured in the `session.timeout` configuration key
used by bootstrap-play, it can be overridden using the `timeout` parameter. Likewise, the
number of seconds warning can be adjusted using the `countdown` parameter.

If you need to perform special logic to keep the user’s session alive, the default keep alive mechanism can
be overridden using the `keepAliveUrl` parameter. This must be a side effect free endpoint that implements
HTTP GET and can be called via an XHR request from the timeout dialog Javascript code. A good practice is to
have a dedicated controller and route defined for this so its use for this purpose is explicit. This url
will be supplied in the `keepAliveUrl` parameter to `hmrcTimeoutDialog`. Do not use `#` in case the current URL
does not implement HTTP GET.

| Parameter      | Description                                                   | Example |
| -------------- | ------------------------------------------------------------- | ------- |
| `signOutUrl`   | The url that will be used when users click 'Sign Out'         | Some(controllers.routes.SignOutController.signOut().url) |
| `timeoutUrl`   | The url that the timeout dialog will redirect to following timeout. Defaults to the `signOutUrl`. | Some(controllers.routes.TimeoutController.timeOut().url) |
| `keepAliveUrl` | A endpoint used to keep the user’s session alive | Some(controllers.routes.KeepAliveController.keepAlive().url)
| `timeout`      | The timeout duration where this differs from `session.timeout` | 1800 |
| `countdown`    | The number of seconds before timeout the dialog is displayed. The default is 120.| 240 |

The timeout dialog’s content can be customised using the following parameters:

| Parameter             | Description                                                   | Example |
| --------------------- | ------------------------------------------------------------- | ------- |
| `title`               | The text to use as a title for the dialog                     | Some(messages("hmrc-timeout-dialog.title")) |
| `message`             | The message displayed to the user                             | Some(messages("hmrc-timeout-dialog.message")) |
| `messageSuffix`       | Any additional text to be displayed after the timer           | Some(messages("hmrc-timeout-dialog.message-suffix")) |
| `keepAliveButtonText` | The text on the button that keeps the user signed in          | Some(messages("hmrc-timeout-dialog.keep-alive-button-text")) |
| `signOutButtonText`   | The text for the link which takes the user to a sign out page | Some(messages("hmrc-timeout-dialog.sign-out-button-text")) |

## Welsh language selection

To support toggling between the English and Welsh language, add the `hmrcLanguageSelectHelper` in the
beforeContentBlock parameter of `govukLayout` as follows:

```scala
@govukLayout(
    pageTitle = Some(pageTitle),
    headBlock = Some(hmrcHead(nonce = CSPNonce.get)),
    headerBlock = Some(hmrcStandardHeader(
      serviceUrl = Some(controllers.routes.IndexController.index().url)
    )),
    beforeContentBlock = Some(hmrcLanguageSelectHelper()),
    scriptsBlock = Some(hmrcScripts(nonce = CSPNonce.get)),
    footerBlock = Some(hmrcStandardFooter())
)(contentBlock)
```

Internally, this functionality relies on a controller that uses the `Referer` header in order to
determine where to redirect the user when they toggle between languages. In the exceptional case
that the browser fails to send the `Referer` header, users are redirected to
`https://www.gov.uk/government/organisations/hm-revenue-customs`. If desired, this behaviour can be overridden by
setting the `language.fallback.url` configuration key in `application.conf`.

## RichDateInput

The implicit class `RichDateInput` provides an extension method `withFormField(field: play.api.data.Field)`
for the `DateInput` view model from [play-frontend-govuk](https://www.github.com/hmrc/play-frontend-govuk).

This method takes a Play `Field` and enriches the `DateInput` with:
* three InputItems corresponding to the day, month and year fields with
  * ids and names of the form `<name>.day`, `<name>.month` and `<name>.year`
  * standard English and Welsh labels
* an id, set to the `Field` name
* values for the nested day, month and year fields
* an error message from an implicit `Messages` indexed by the first non-empty:
  * `field("day").error`
  * `field("month").error`
  * `field("year").error`
  * `field.error`
* the correct CSS error classes applied to any invalid nested day, month or year field or
to all fields in the case of a global date error.

This helper assumes the Field passed to it consists of three [nested fields](https://www.playframework.com/documentation/2.8.x/ScalaForms#Nested-values)
indexed by the keys `day`, `month` and `year`.

For example, if using the one question per page pattern, the method could be used as follows:

```scala
@govukDateInput(DateInput(
  hint = Some(Hint(content = Text("date.hint"))),
  fieldset = Some(Fieldset(
    legend = Some(Legend(
    content = Text(messages("date.heading")),
    classes = "govuk-fieldset__legend--l",
    isPageHeading = true)))
  )
).withFormField(dateInputForm("date")))
```

Setting up form validation for this field might look like:

```scala
case class DateData(day: String, month: String, year: String)
case class PageData(date: DateData)

object DateFormBinder {
  def form: Form[PageData] = Form[PageData](
    mapping(
      "date" -> mapping(
        "day"   -> text.verifying(dayConstraint),
        "month" -> text.verifying(monthConstraint),
        "year"  -> text.verifying(yearConstraint)
      )(DateData.apply)(DateData.unapply).verifying(dateConstraint)
    )(PageData.apply)(PageData.unapply)
  )
}
```

In the code above, `dayConstraint`, `monthConstraint`, `yearConstraint` and `dateConstraint` would be defined
as per the [Play documentation](https://www.playframework.com/documentation/2.8.x/ScalaCustomValidations) on custom 
validations.

The controller submit method for this form might look like:

```scala
def submit() = Action { implicit request =>
    DateFormBinder.form
      .bindFromRequest()
      .fold(
        formWithError => BadRequest(dateInputPage(formWithError, routes.DateInputController.submit())),
        _ => Redirect(routes.DateInputController.thanks())
      )
}
```

If a value is passed though to the input `.apply()` method during construction, it will NOT be overwritten by the
`Field` values. These are only used if the object parameters are not set to the default parameters.

Note that you will need to pass through an implicit `Messages` to your template.

## Adding your own SASS compilation pipeline

This library will manage SASS compilation for you. However, should you need for any reason to add your own SASS
compilation pipeline, follow the [steps detailed here](https://github.com/hmrc/play-frontend-hmrc/blob/master/docs/maintainers/sass-compilation.md).

## Play Framework and Scala compatibility notes
This library is currently compatible with:
* Scala 2.12
* Play 2.6, Play 2.7, Play 2.8

## Getting help

Please report any issues with this library in Slack at `#team-plat-ui`.

## Useful Links

- [hmrc-frontend](https://github.com/hmrc/hmrc-frontend/) - reusable Nunjucks HTML components for HMRC design patterns
- [HMRC Design Patterns](https://design.tax.service.gov.uk/hmrc-design-patterns/) - documentation for the use of `hmrc-frontend` components
- [play-frontend-govuk](https://github.com/hmrc/play-frontend-govuk/) - Twirl implementation of `govuk-frontend` components
- [govuk-frontend](https://github.com/alphagov/govuk-frontend/) - reusable Nunjucks HTML components from GOV.UK
- [GOV.UK Design System](https://design-system.service.gov.uk/components/) - documentation for the use of `govuk-frontend` components
- [GOV.UK Design System Chrome extension](https://github.com/hmrc/play-frontend-govuk-extension) - `Chrome` extension to add a Twirl tab for each example in the GOV.UK Design System

## Owning team README
Rationale for code and translation decisions, dependencies, as well as instructions for team members maintaining this repository can be found [here](/docs/maintainers/overview.md).

## License

This code is open source software licensed under the [Apache 2.0 License]("http://www.apache.org/licenses/LICENSE-2.0.html").
