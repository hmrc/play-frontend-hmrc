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
- [Helping users report technical issues](#helping-users-report-technical-issues)
- [Accessibility statement links](#accessibility-statement-links)
- [CharacterCount with Welsh language support](#charactercount-with-welsh-language-support)
- [Integrating with tracking consent](#integrating-with-tracking-consent)
- [Warning users before timing them out](#warning-users-before-timing-them-out)
- [RichDateInput](#richdateinput)
- [RichErrorSummary](#richerrorsummary)
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
    libraryDependencies += "uk.gov.hmrc" %% "play-frontend-hmrc" % "x.y.z-play-28"
    ```

    The library is cross-compiled for Play 2.6, 2.7, and 2.8. Note, because this library transitively depends on play-frontend-govuk
    there is no need to add this as a separate dependency. If it is already included, it can be removed.

1.  Add route for hmrc-frontend assets in `conf/app.routes`:
    ```scala
    ->         /hmrc-frontend                      hmrcfrontend.Routes
    ```

1. Ensure you have your service name defined in your messages files. For example,
    ```scala
    service.name = Any tax service
    ``` 

   This is required by `hmrcStandardHeader`. If you have a dynamic service name you can skip this step and pass the
   serviceName into `hmrcLayout` or `hmrcStandardHeader`.

1. Create a custom layout template for your pages using `hmrcLayout` as per the section
      [HMRC layout](#hmrc-layout)

1. Then to use our components and helpers in your templates, you will need to import the component or helper from its
   package `uk.gov.hmrc.(govuk|hmrc)frontend.views.html.components` or `uk.gov.hmrc.hmrcfrontend.views.html.helpers`.

   For most components the parameters that can be provided are encapsulated in a viewmodel, comprised of case classes
   that live within a subpackage of `uk.gov.hmrc(govuk|hmrc)frontend.views.viewmodels` and are aliased for use
   under `uk.gov.hmrc(govuk|hmrc)frontend.views.html.components`.

   ```scala
   /*
   Import all components, helpers, viewmodels, and implicits. Most succinct import method, but may require additional 
   imports to resolve ambiguous import compilation errors for some viewmodels. Will also cause unused import warnings.
   */
   @import uk.gov.hmrc.hmrcfrontend.views.html.components._
   @import uk.gov.hmrc.hmrcfrontend.views.html.helpers._
   @import uk.gov.hmrc.hmrcfrontend.views.html.components.implicits._                  

   /*
   Import specific components and viewmodels, and all implicits. Avoids possibility of ambiguous import compilation 
   errors and unused import warnings.
   */
   @import uk.gov.hmrc.hmrcfrontend.views.html.components.GovukRadios                                  /* component */
   @import uk.gov.hmrc.hmrcfrontend.views.html.components.{Radios, Fieldset, Legend, Text, RadioItem}  /* viewmodel case classes */
   @import uk.gov.hmrc.hmrcfrontend.views.html.components.implicits._

   @this(govukRadios: GovukRadios)

   @(myForm: Form[_])

   @govukRadios(Radios(
     fieldset = Some(Fieldset(
       legend = Some(Legend(
         content = Text("Where do you live?"),
         classes = "govuk-fieldset__legend--l",
         isPageHeading = true
       ))
     )),
     items = Seq(
       RadioItem(
         content = Text("England"),
         value = Some("england")
       ), 
       RadioItem(
         content = Text("Scotland"),
         value = Some("scotland")
       ), 
       RadioItem(
         content = Text("Wales"),
         value = Some("wales")
       ), 
       RadioItem(
         content = Text("Northern Ireland"),
         value = Some("northern-ireland")
       )
     )
   ).withFormField(myForm("whereDoYouLive")))  /* wires up things like checked status of inputs from a play form field */
   ```

   > **Notice: Recommended usage pattern changed as of July 2021**
   > We no longer recommend the use of `TwirlKeys.templateImports` configuration. Unused imports triggered excessive 
   > warning messages which needed to be suppressed or could cause alert fatigue. However, that might also hide 
   > meaningful feedback like deprecation warnings.

### HMRC layout

The [hmrcLayout](src/main/twirl/uk/gov/hmrc/hmrcfrontend/views/helpers/HmrcLayout.scala.html) helper
generates a layout for your pages including the `hmrcStandardHeader`, `hmrcStandardFooter`, Welsh language
toggle, and various banners.

To use this component,

1. Create a layout template `Layout.scala.html` as follows:

    ```scala 
    @import uk.gov.hmrc.hmrcfrontend.views.html.helpers.HmrcLayout
    @import uk.gov.hmrc.hmrcfrontend.views.config.StandardBetaBanner
    @import views.html.helper.CSPNonce
    @import config.AppConfig
    @import uk.gov.hmrc.anyfrontend.controllers.routes
    
    @this(hmrcLayout: HmrcLayout, standardBetaBanner: StandardBetaBanner)

    @(pageTitle: String)(contentBlock: Html)(implicit request: RequestHeader, messages: Messages)

    @hmrcLayout(
      pageTitle = Some(pageTitle),
      isWelshTranslationAvailable = true, /* or false if your service has not been translated */
      serviceUrl = Some(routes.IndexController.index().url),
      signOutUrl = Some(routes.SignOutController.signOut().url),
      phaseBanner = Some(standardBetaBanner(url = appConfig.betaFeedbackUrl)),
      nonce = CSPNonce.get,
    )(contentBlock)
    ```

1. The parameters that can be passed into the `hmrcLayout` and their default values are as follows:

   | Parameter                     | Description                                                       | Example                                                   |
   | ----------------------------- | ----------------------------------------------------------------- | --------------------------------------------------------- |
   | `pageTitle`                   | This will be bound to govukLayout                                 |                                                           |
   | `isWelshTranslationAvailable` | Setting to true will display the language toggle                  | true                                                      |
   | `serviceUrl`                  | This will be bound to hmrcStandardHeader                          | Some(routes.IndexController.index().url)                  |
   | `signOutUrl`                  | Passing a value will display the sign out link                    | Some(routes.SignOutController.signOut().url)              |
   | `userResearchBannerUrl`       | Passing a value will display the user research banner             | Some(UserResearchBanner(url = appConfig.userResearchUrl)) |
   | `accessibilityStatementUrl`   | Passing a value will override the accessibility statement URL in the [footer](#accessibility-statement-links)                 ||
   | `backLinkUrl`                 | Passing a value will display a back link                          | Some(routes.ServiceController.start().url)                |
   | `displayHmrcBanner`           | Setting to true will display the [HMRC banner](https://design.tax.service.gov.uk/hmrc-design-patterns/hmrc-banner/)          ||
   | `phaseBanner`                 | Passing a value will display alpha or beta banner.                | Some(standardBetaBanner(url = appConfig.betaFeedbackUrl)) |
   | `additionalHeadBlock`         | Passing a value will add additional content in the HEAD element   |                                                           |
   | `additionalScriptsBlock`      | Passing a value will add additional scripts at the end of the BODY|                                                           |
   | `beforeContentBlock`          | Passing a value will add content between the header and the main element. This content will override any `isWelshTranslationAvailable` or `backLinkUrl` parameter.||
   | `nonce`                       | This will be bound to hmrcHead, hmrcScripts and govukTemplate     |                                                           |
   | `mainContentLayout`           | Passing value will override the default two thirds layout         |                                                           |
   | `serviceName`                 | Pass a value only if your service has a dynamic service name      |                                                           |

### Useful implicits

The following import will summon [implicits](https://github.com/hmrc/play-frontend-hmrc/blob/master/src/main/scala/uk/gov/hmrc/hmrcfrontend/views/Implicits.scala) that provide extension methods on `Play’s` [Html](https://www.playframework.com/documentation/2.6.x/api/scala/play/twirl/api/Html.html) objects.
This includes useful HTML trims, pads, indents and handling HTML emptiness.
```scala
@import uk.gov.hmrc.hmrcfrontend.views.html.components.implicits._
```

#### RichDateInput

The implicit class `RichDateInput` provides an extension method `withFormField(field: play.api.data.Field)`
for the `DateInput` view model from [play-frontend-govuk](https://www.github.com/hmrc/play-frontend-govuk).

This method takes a Play `Field` and enriches the `DateInput` with:
* three InputItems corresponding to the day, month and year fields with
    * ids and names of the form `<name>.day`, `<name>.month` and `<name>.year`
    * standard English and Welsh labels
* an id, set to the `Field` name
* values for the nested day, month and year fields
* a Text error message from an implicit `Messages` indexed by the first non-empty:
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

Additionally, there is a second method `withFormFieldWithErrorAsHtml(field: play.api.data.Field)` which behaves as the
`withFormField` method with the difference that form errors are bound as instances of `HtmlContent`.

Note that you will need to pass through an implicit `Messages` to your template.

#### RichErrorSummary

The implicit class `RichErrorSummary` provides extension methods `withFormErrorsAsText` and
`withFormErrorsAsHtml` to hydrate an `ErrorSummary` with the standard
'There is a problem' title in English and Welsh and the errors found in a Play form.

If your form is simple with no nested field values, and you are using the `withFormField` extension methods to
hydrate your form inputs, the helper can be used simply as follows:

```scala
@if(form.errors.nonEmpty) {
    @govukErrorSummary(ErrorSummary().withFormErrorsAsText(form))
}
```

This will construct an ErrorSummary with errors hyperlinked according to the form error keys. For example an error on
the `name` field will be linked to its corresponding input via the href `#name`. This relies on each HTML input having
their id set to their field name.

If you have a form with nested field values and are performing validation on composite fields, such as in the DateInput
example above, you will need to map any errors on the composite field to the field corresponding to the first HTML
input element in the group:

```scala
@if(form.errors.nonEmpty) {
    @govukErrorSummary(ErrorSummary().withFormErrorsAsText(form, mapping = Map("date" -> "date.day")))
}
```

This will ensure all errors are clickable is consistent with GDS
[guidance](https://design-system.service.gov.uk/components/error-summary/)

Note, these methods will not overwrite any existing `ErrorSummary` properties. For example, if you manually pass in a
non-empty title, it will not be overwritten.

To use this class you will need to have an implicit `Messages` in scope.

### Example Templates

We provide example templates using the Twirl components through a `Chrome` extension. Please refer to the
[extension’s github repository](https://github.com/hmrc/play-frontend-govuk-extension) for installation instructions.

With the extension installed, you should be able to go to the [HMRC Design System](https://design.tax.service.gov.uk/hmrc-design-patterns/),
click on a component on the sidebar and see the `Twirl` examples matching the provided `Nunjucks` templates.

### Find working examples

You can find working examples of the use of play-frontend-hmrc in the following actively maintained repositories:
* [tracking-consent-frontend](https://www.github.com/hmrc/tracking-consent-frontend)
* [accessibility-statement-frontend](https://www.github.com/hmrc/accessibility-statement-frontend)
* [contact-frontend](https://www.github.com/hmrc/contact-frontend)
* [help-frontend](https://www.github.com/hmrc/help-frontend)

## Helping users report technical issues

The [hmrcReportTechnicalIssueHelper](src/main/twirl/uk/gov/hmrc/hmrcfrontend/views/helpers/HmrcReportTechnicalIssueHelper.scala.html) component
generates a link that allows users to report technical issues with your service.

To configure this helper, add the following configuration to your `application.conf`

```
contact-frontend.serviceId = "<any-service-id>"
```

`serviceId` helps identify your service when members of the public report technical issues.
If your service is *not* already integrating with contact-frontend, we advise choosing an
identifier that is specific to your service and unlikely to be used by any other service, avoiding any special characters
or whitespace.

The component should be added to the bottom of each page in your service. This can be done by defining a reusable block 
in your layout template and passing into `hmrcLayout` or `govukLayout` in place of contentBlock:

    ```scala
    @content = {
      @contentBlock
      @hmrcReportTechnicalIssueHelper()
    }
    ```

## Accessibility statement links

[hmrcStandardFooter](src/main/twirl/uk/gov/hmrc/hmrcfrontend/views/helpers/HmrcStandardFooter.scala.html),
included as part of `hmrcLayout`, generates the standard GOV.UK footer including the standardised list of footer 
links for tax services.

To configure this helper to link to the
[accessibility statement service](https://www.github.com/hmrc/accessibility-statement-frontend), provide the key
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

The [hmrcCharacterCount](src/main/twirl/uk/gov/hmrc/hmrcfrontend/views/components/HmrcCharacterCount.scala.html) is an
implementation of the GOV.UK CharacterCount that translates the dynamic words / characters remaining
text into English or Welsh using the Play framework Message API. You do not need to pass through the
language explicitly to this component, just pass through an implicit Messages.
```
@import uk.gov.hmrc.hmrcfrontend.views.html.components.CharacterCount

@this(hmrcCharacterCount: HmrcCharacterCount)

@(implicit messages: Messages)
@hmrcCharacterCount(CharacterCount(
    name = "some-component-name",
    id = "some-component-id",
    maxWords = Some(1000),
    label = Label(content = Text("Please enter your text")),
))
```

There is also a helper method `withFormField(field: Field)` that allows a Play forms Field to be passed through when creating an instance of `play-frontend-govuk` form input,
which will enrich the input with the following:
* Using the `Field` name for the input name
* Using the `Field` name for the input id
* Using the `Field` error message to create a `Text` error messages
* Using the `Field` value as pre-filled value

If a value is passed though to the input `.apply()` method during construction, it will NOT be overwritten by the
`Field` values. These are only used if the object parameters are not set to the default parameters.

Additionally, there is a second method `withFormFieldWithErrorAsHtml(field: play.api.data.Field)` which behaves as the
`withFormField` method with the difference that form errors are bound as instances of `HtmlContent`.

## Integrating with tracking consent

If you intend to use Google Analytics or Optimizely to measure usage of your service, you will need to integrate with
[tracking-consent-frontend](https://www.github.com/hmrc/tracking-consent-frontend). The
[hmrcHead](src/main/twirl/uk/gov/hmrc/hmrcfrontend/views/helpers/HmrcHead.scala.html)
helper generates the necessary HTML SCRIPT tags that must be injected into the HEAD element for every page on your 
service provided it is configured correctly as below.

Before integrating, it is important to remove any existing snippets relating to GTM, GA or Optimizely. If they are not removed
there is a risk the user’s tracking preferences will not be honoured correctly.

Configure your service’s GTM container in `conf/application.conf`. For example, if you have been
instructed to use GTM container `a`, the configuration would appear as:

```
tracking-consent-frontend {
  gtm.container = "a"
}
```

`gtm.container` can be one of: `a`, `b`, `c`, `d`, `e`, `f` or `sdes`. Consult with the CIPSAGA team
to identify which GTM container you should be using in your service.

## Warning users before timing them out

In order to meet the accessibility [WCAG 2.1 Principle 2: Operable](https://www.w3.org/TR/WCAG21/#operable) you must
provide users with enough time to read and use content. In particular, WCAG 2.1 Success Criterion 2.2.1 (Timing Adjustable)
requires that users are able to turn off, adjust or extend the time limit, giving them at least 20 seconds to perform this with
a simple action.

On MDTP, users are, by default, automatically timed out of any authenticated service after 15 minutes
of inactivity. This mechanism, implemented in [SessionTimeoutFilter](https://github.com/hmrc/bootstrap-play/blob/master/bootstrap-frontend-src/main/scala/uk/gov/hmrc/play/bootstrap/frontend/filters/SessionTimeoutFilter.scala),
clears all non-allow-listed session keys after the timeout duration has elapsed. Services can override this default by adjusting the
`session.timeout` configuration key in `conf/application.conf`.

The [hmrcTimeoutDialogHelper](src/main/twirl/uk/gov/hmrc/hmrcfrontend/views/helpers/HmrcTimeoutDialogHelper.scala.html)
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

1. Update your layout template to pass in the `hmrcTimeoutDialogHelper` in the HEAD element, supplying the signOutUrl as
   a parameter. For example if using `hmrcLayout`, pass `Some(hmrcTimeoutDialogHelper(signOutUrl = signOutUrl))` in the 
   `additionalHeadBlock` parameter.

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
| `signOutUrl`   | The url that will be used when users click 'Sign Out'         | Some(routes.SignOutController.signOut().url) |
| `timeoutUrl`   | The url that the timeout dialog will redirect to following timeout. Defaults to the `signOutUrl`. | Some(routes.TimeoutController.timeOut().url) |
| `keepAliveUrl` | A endpoint used to keep the user’s session alive | Some(routes.KeepAliveController.keepAlive().url)
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
