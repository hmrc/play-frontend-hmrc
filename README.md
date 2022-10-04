# play-frontend-hmrc

This library contains all the Twirl components and helpers needed to implement frontend microservices on the HMRC
tax platform.

play-frontend-hmrc is a Scala Twirl implementation of 
[govuk-frontend](https://www.github.com/alphagov/govuk-frontend) and
[hmrc-frontend](https://www.github.com/hmrc/hmrc-frontend), adding to it Play, HMRC and tax platform-specific 
components and helpers that make the process
of implementing frontend microservices straightforward and idiomatic for Scala developers.

## Table of Contents

- [Getting started](#getting-started)
- [Using the components](#using-the-components)
- [Useful implicits](#useful-implicits)
- [Using the HMRC layout](#using-the-hmrc-layout)
- [Helping users report technical issues](#helping-users-report-technical-issues)
- [Adding a beta feedback banner](#adding-a-beta-feedback-banner)
- [Adding a User Research Banner](#adding-a-user-research-banner)  
- [Opening links in a new tab](#opening-links-in-a-new-tab)
- [Accessibility statement links](#accessibility-statement-links)
- [CharacterCount with Welsh language support](#charactercount-with-welsh-language-support)
- [Integrating with tracking consent](#integrating-with-tracking-consent)
- [Warning users before timing them out](#warning-users-before-timing-them-out)
- [Display a caption above a page heading label or legend](#hmrcpageheadinglabel-and-hmrcpageheadinglegend)
- [Adding a sidebar to your Layout](#adding-a-sidebar-to-your-layout)
- [Adding accessible-autocomplete CSS and Javascript](#adding-accessible-autocomplete-css-and-javascript)
- [Adding your own SASS compilation pipeline](#adding-your-own-sass-compilation-pipeline)
- [Play Framework and Scala compatibility notes](#play-framework-and-scala-compatibility-notes)
- [Troubleshooting](#troubleshooting)  
- [Getting help](#getting-help)
- [Resolve play-frontend-hmrc artefacts](#resolve-play-frontend-hmrc-artefacts)
- [Useful links](#useful-links)
- [Owning team readme](#owning-team-readme)
- [License](#license)

## Getting started
1.  Add the version of [play-frontend-hmrc](https://github.com/hmrc/play-frontend-hmrc/releases) specific to your Play version
    in your `project/AppDependencies.scala` file. For example,
    ```sbt
    libraryDependencies += "uk.gov.hmrc" %% "play-frontend-hmrc" % "x.y.z-play-28"
    ```

1.  Add a route for the hmrc-frontend static assets in `conf/app.routes`:
    ```scala
    ->         /hmrc-frontend                      hmrcfrontend.Routes
    ```

1. Define your service name in your messages files. For example,
    ```scala
    service.name = Any tax service
    ``` 

   If you have a dynamic service name you can skip this step and pass the
   serviceName into `hmrcLayout` or `hmrcStandardHeader`.

1. Create a layout template for your pages using [HMRC layout](#hmrc-layout)

1. Problems with styling? Check our [Troubleshooting](#troubleshooting) section.

## Using the components

To use our components and helpers, you will first need to import them from 
their corresponding packages in `uk.gov.hmrc.govukfrontend.views.html.components`,
`uk.gov.hmrc.hmrcfrontend.views.html.components` or `uk.gov.hmrc.hmrcfrontend.views.html.helpers`.

For most components, their parameters are encapsulated within view models, case classes
that live within a subpackage of `uk.gov.hmrc(govuk|hmrc)frontend.views.viewmodels` and are aliased for use
under `uk.gov.hmrc(govuk|hmrc)frontend.views.html.components`.

The following will import all components, helpers, view models, and implicits. This is the most succinct import method, 
but may require additional imports to resolve ambiguous import compilation errors for some view models. It may 
also cause unused import warnings.

```scala
@import uk.gov.hmrc.govukfrontend.views.html.components._
@import uk.gov.hmrc.govukfrontend.views.html.components.implicits._
```

Alternatively, you can import components and view models individually to avoid the possibility of ambiguous
import compilation errors and unused import warnings.

```scala
@import uk.gov.hmrc.govukfrontend.views.html.components.GovukRadios                                  /* component */
@import uk.gov.hmrc.govukfrontend.views.html.components.{Radios, Fieldset, Legend, Text, RadioItem}  /* viewmodel case classes */
@import uk.gov.hmrc.govukfrontend.views.html.components.implicits._
```

You can then use the components in your templates as follows:

```scala
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

## Useful implicits

The following imports will summon implicit classes that include extension methods for HTML trims, pads, indents, 
handling HTML emptiness and wiring Play forms.

```scala
@import uk.gov.hmrc.govukfrontend.views.html.components.implicits._
@import uk.gov.hmrc.hmrcfrontend.views.html.components.implicits._
````

### withFormField

An extension method `withFormField(field: play.api.data.Field)` exists for the following classes:
* CharacterCount
* Checkboxes
* Input
* Radios
* Select
* Textarea
* DateInput

This method allows a Play forms Field to be passed through when creating an instance of a form input,
which will enrich the input with the following:
* Using the `Field` name for the input name
* Using the `Field` name for the input id or idPrefix
* Using the `Field` error message to create a `Text` error messages
* Using the `Field` value as pre-filled value (for `CharacterCount`, `Input`, `Textarea`) or pre-selected value
  (`Checkboxes`, `Radios`, `Select`)

The methods can be used as methods in a Twirl template as demonstrated below:

```scala
@import uk.gov.hmrc.govukfrontend.views.html.components._
@import uk.gov.hmrc.govukfrontend.views.html.components.implicits._

@this(govukInput)

@(label: String, field: Field)(implicit messages: Messages)

@govukInput(
  Input(
    label = Label(classes = labelClasses, content = Text(label))
  ).withFormField(field)
)
```

If a value is passed though to the input `.apply()` method during construction, it will NOT be overwritten by the
`Field` values. These are only used if the object parameters are not set to the default parameters.

Note that you will need to pass through an implicit `Messages` to your template.

Additionally, there is a second method `withFormFieldWithErrorAsHtml(field: play.api.data.Field)` which behaves as the
`withFormField` method with the difference that form errors are bound as instances of `HtmlContent`.

### withHeading and withHeadingAndCaption

Extension methods `withHeading(heading: Content)` and `withHeadingAndSectionCaption(heading: Content, sectionCaption: Content)`
exist for the following classes:
* CharacterCount
* Checkboxes
* Input
* Radios
* Select
* Textarea
* DateInput

These methods allow either `Text` or `HtmlContent` content to be passed through to the form inputs and set as either 
label or fieldset legend, depending on the underlying component. The methods will also concatenate and apply styling for 
the content as below:

| Parameter              | Styling applied                                                                                                    |
| -----------------------| ------------------------------------------------------------------------------------------------------------------ |
| `heading` for `Legend` | `<h1 class="govuk-fieldset__heading hmrc-page-heading govuk-!-margin-top-0 govuk-!-margin-bottom-0">$content</h1>` |
| `heading` for `Label ` | `govuk-label--xl hmrc-page-heading govuk-!-margin-top-0 govuk-!-margin-bottom-2`                                   |
| `sectionCaption`       | `<span class="govuk-caption-xl hmrc-caption-xl">$sectionCaption</span>`                                            |

### RichDateInput

The implicit class `RichDateInput` provides an extension method `withFormField(field: play.api.data.Field)`
for the `DateInput` view model.

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

### RichErrorSummary

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

### RichStringSupport

The implicit class `RichStringSupport` hydrates the basic `String` class with a series of extension helper methods to 
convert a `String` to a component without the need for nesting in case classes, or for wrapping as `Text`.

These methods are called as standard extension methods, for example: 

```scala
import uk.gov.hmrc.govukfrontend.views.html.components.implicits._ 

"This is a string for a fieldset legend".toFieldset
```

will return:

```scala
Fieldset(
  legend = Some(Legend(content = Text("This is a string for a fieldset legend")))
)
```

The following implicit conversions exist for a `String`:
* `toHint`
* `toText`
* `toKey`
* `toHeadCell`
* `toFieldset`
* `toTag`
* `toLabel`
* `toLegend`

## Using the HMRC layout

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

    @(pageTitle: String, appConfig: AppConfig)(contentBlock: Html)(implicit request: RequestHeader, messages: Messages)

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
      | `isWelshTranslationAvailable` | Setting to true will display the language toggle                  | `true`                                                      |
      | `serviceUrl`                  | This will be bound to hmrcStandardHeader                          | `Some(routes.IndexController.index().url)`                 |
      | `signOutUrl`                  | Passing a value will display the sign out link                    | `Some(routes.SignOutController.signOut().url)`             |
      | `userResearchBannerUrl`       | Passing a value will display the user research banner             | `Some(UserResearchBanner(url = appConfig.userResearchUrl))` |
      | `accessibilityStatementUrl`   | Passing a value will override the accessibility statement URL in the [footer](#accessibility-statement-links)                 ||
      | `backLinkUrl`                 | Passing a value will display a back link                          | `Some(routes.ServiceController.start().url)`               |
      | `backLink`                    | If you need to add custom attributes, pass a `BackLink` viewmodel instead | `Some(BackLink(href = ..., attributes = ...))`        |
      | `displayHmrcBanner`           | Setting to true will display the [HMRC banner](https://design.tax.service.gov.uk/hmrc-design-patterns/hmrc-banner/)          ||
      | `phaseBanner`                 | Passing a value will display alpha or beta banner.                | `Some(standardBetaBanner(url = appConfig.betaFeedbackUrl))` |
      | `additionalHeadBlock`         | Passing a value will add additional content in the HEAD element   |                                                           |
      | `additionalScriptsBlock`      | Passing a value will add additional scripts at the end of the BODY|                                                           |
      | `beforeContentBlock`          | Passing a value will add content between the header and the main element. This content will override any `isWelshTranslationAvailable`, `backLink` or `backLinkUrl` parameter.||
      | `nonce`                       | This will be bound to hmrcHead, hmrcScripts and govukTemplate     |                                                           |
      | `mainContentLayout`           | Passing value will override the default two thirds layout         |                                                           |
      | `serviceName`                 | Pass a value only if your service has a dynamic service name      |                                                           |
      | `additionalBannersBlock`      | Pass extra html into the header, intended for custom banners.     | `Some(attorneyBanner)`                                     |
      | `pageLayout`                  | Allow internal services to use a full width layout.               | `Some(fixedWidthPageLayout(_))`                             |
      | `headerContainerClasses`      | Allow internal services to use a full width header.               | `"govuk-width-container"`                                  |

### FullWidthPageLayout should only be used by internal services

The default fixed width layout is for public services.

## HmrcPageHeadingLabel and HmrcPageHeadingLegend

These helpers let you use a label or legend as a page heading with a section (caption) displayed above it.

For example, how you could use HmrcPageHeadingLabel with a govukInput:
```scala
@import uk.gov.hmrc.govukfrontend.views.html.components.{GovukInput, Input, Hint, Text, Text}
@import uk.gov.hmrc.govukfrontend.views.html.components.implicits._
@import uk.gov.hmrc.hmrcfrontend.views.config.{HmrcPageHeadingLabel, HmrcSectionCaption}

@this(govukInput: GovukInput)

@(myForm: Form[_])(implicit messages: Messages)

@govukInput(
  Input(
    label = HmrcPageHeadingLabel(content = Text("What is your name?"), caption = HmrcSectionCaption(Text("Personal details"))),
    hint = Some(Hint(content = Text("This example shows a page heading inside a <label>")))
  ).withFormField(myForm("whatIsYourName"))
)
```

For example, how you could use HmrcPageHeadingLegend with govukRadios:
```scala
@import uk.gov.hmrc.govukfrontend.views.html.components.{Radios, RadioItem, Fieldset, Hint, Text}
@import uk.gov.hmrc.govukfrontend.views.html.components.implicits._
@import uk.gov.hmrc.hmrcfrontend.views.config.HmrcPageHeadingLegend

@this(govukInput: GovukRadios)

@(myForm: Form[_])(implicit messages: Messages)

@govukRadios(Radios(
  fieldset = Some(Fieldset(
    legend = Some(HmrcPageHeadingLegend(content = Text("Where do you live?"), caption = HmrcSectionCaption(Text("Personal details")))
  )), 
  hint = Some(Hint(content = Text("This example shows a page heading inside a <legend>"))),
  items = List("England", "Scotland", "Wales", "Northern Ireland") map { place =>
    RadioItem(
      content = Text(place),
      value = Some(place)
    )
  }).withFormField(myForm("whereDoYouLive")))
```

### Adding a sidebar to your Layout

The `HmrcLayout` by default renders the main `contentBlock` of the page in
[two thirds width content](https://design-system.service.gov.uk/styles/layout/).

However, if you wish to override the styling of the main content, you can do so by passing in an optional `mainContentLayout`
parameter of type `Option[Html => Html]`, which will apply wrapping content around your `contentBlock`.

If you wish to create a layout with [two thirds, one third styling](https://design-system.service.gov.uk/styles/layout/)
(for example if your page has a sidebar), there is a helper `TwoThirdsOneThirdMainContent.scala.html` which can be used
as follows:

```html
@import uk.gov.hmrc.hmrcfrontend.views.html.helpers.HmrcLayout
@import uk.gov.hmrc.govukfrontend.views.html.components.TwoThirdsOneThirdMainContent
@import views.html.helper.CSPNonce

@this(
    hmrcLayout: HmrcLayout,
    twoThirdsOneThirdMainContent: TwoThirdsOneThirdMainContent
)

@(pageTitle: String, isWelshTranslationAvailable: Boolean = true)(contentBlock: Html)(implicit request: RequestHeader, messages: Messages)

@sidebar = {
  <h2 class="govuk-heading-xl">This is my sidebar</h2>
  <p class="govuk-body">There is my sidebar content</p>
}

@hmrcLayout(
  pageTitle = Some(pageTitle),
  nonce = CSPNonce.get,
  isWelshTranslationAvailable = isWelshTranslationAvailable,
  displayHmrcBanner = true,
  mainContentLayout = Some(twoThirdsOneThirdMainContent(sidebar))
)(contentBlock)

```

Alternatively, you can declare any template and pass it through as a function or partially applied function that has the
signature `Html => Html`.

For example, you  can add a template `WithSidebarOnLeft.scala.html` as below:

```html
@this()

@(sidebarContent: Html)(mainContent: Html)

<div class="govuk-grid-row">
    <div class="govuk-grid-column-one-third">
        @sidebarContent
    </div>
    <div class="govuk-grid-column-two-thirds">
      @mainContent
    </div>
</div>
```

You can then inject this into your `Layout.scala.html` and partially apply the function as above.

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

    @content = {
      @contentBlock
      @hmrcReportTechnicalIssueHelper()
    }

## Adding a beta feedback banner

If you would like to add a banner to your service stating that your service is in beta, and providing a link to a feedback
form, you can do so use the `StandardBetaBanner` viewmodel to construct a `PhaseBanner`, which is bound to a `GovukPhaseBanner` Twirl template.

The `HmrcLayout`, `HmrcStandardHeader` and `HmrcStandardHeader` all have constructor methods that take in an optional 
`PhaseBanner` and then bind to the appropriate template.

For developers wanting to implement a beta feedback banner in your service, these steps should be followed:
1. Inject an instance of `StandardBetaBanner` into your template
1. Call the `apply` method with either:
    1. An explicit `url` parameter, OR
    1. An implicit instance of `ContactFrontendConfig` injected into your template
    
If you pass an implicit, injected instance of `ContactFrontendConfig` to your `StandardBetaBanner`, then the beta feedback
banner will be bound with a URL pointing to beta feedback form in the `contact-frontend` microservice, together with
URL parameters `referrerUrl` and `service`. 

As with [Helping users report technical issues](#helping-users-report-technical-issues), add the following to your 
`application.conf`:

```
contact-frontend.serviceId = "<any-service-id>"
```

You can then use the banner as per below (note the injected implicit `ContactFrontendConfig`):

    ```scala 
    @import uk.gov.hmrc.hmrcfrontend.views.html.helpers.HmrcLayout
    @import uk.gov.hmrc.hmrcfrontend.views.config.StandardBetaBanner
    @import views.html.helper.CSPNonce
    @import config.AppConfig
    @import uk.gov.hmrc.hmrcfrontend.config.ContactFrontendConfig
    @import uk.gov.hmrc.anyfrontend.controllers.routes
    
    @this(hmrcLayout: HmrcLayout, standardBetaBanner: StandardBetaBanner)(implicit cfConfig: ContactFrontendConfig)

    @(pageTitle: String, appConfig: AppConfig)(contentBlock: Html)(implicit request: RequestHeader, messages: Messages)

    @hmrcLayout(
      pageTitle = Some(pageTitle),
      isWelshTranslationAvailable = true, /* or false if your service has not been translated */
      serviceUrl = Some(routes.IndexController.index().url),
      signOutUrl = Some(routes.SignOutController.signOut().url),
      phaseBanner = Some(standardBetaBanner()),
      nonce = CSPNonce.get,
    )(contentBlock)
    ```
## Adding a User Research Banner

The User Research Banner is a component used to display a blue banner, containing link text inviting the service user to 
take part in user research. The Twirl template is [HmrcUserResearchBanner.scala.html](https://github.com/hmrc/play-frontend-hmrc/blob/main/src/main/twirl/uk/gov/hmrc/hmrcfrontend/views/components/HmrcUserResearchBanner.scala.html), 
and the viewmodel is [UserResearchBanner.scala](https://github.com/hmrc/play-frontend-hmrc/blob/main/src/main/scala/uk/gov/hmrc/hmrcfrontend/views/viewmodels/userresearchbanner/UserResearchBanner.scala).

The banner contains hard coded content, available in English and Welsh, with translation handled automatically via the 
Play `language` from an implicit `request`. It is not possible to change this content, as it has been provided by 
Research Services, and needs to be consistent across tax.service.gov.uk. 

If your service uses `HmrcLayout.scala.html`, you can add the `HmrcUserResearchBanner` as shown:

    @hmrcLayout(
      pageTitle = ...
      ...
      userResearchBannerUrl = Some("your-user-research-url-here")
    )(contentBlock)

Research Services will tell you what URL to use for your service.

## Opening links in a new tab

The [HmrcNewTabLinkHelper](src/main/twirl/uk/gov/hmrc/hmrcfrontend/views/helpers/HmrcNewTabLinkHelper.scala.html) component
allows you to link to content that opens in a new tab, with protection against reverse tabnapping. It takes in an implicit
`Messages` parameter to translate the content `(opens in a new tab)`.

It is a wrapper around the `HmrcNewTabLink`, however this helper means that services do not need to explicitly pass in a
language for internationalization of the link text.

It can be used as follows:

    import uk.gov.hmrc.hmrcfrontend.views.html.helpers.HmrcNewTabLinkHelper
    import uk.gov.hmrc.hmrcfrontend.views.viewmodels.newtablinkhelper.NewTabLinkHelper

    @this(hmrcNewTabLinkHelper: HmrcNewTabLinkHelper)
    @(linkText: String, linkHref: String)(implicit messages: Messages)

    @hmrcNewTabLinkHelper(NewTabLinkHelper(
      text = linkText,
      href = Some(linkHref)
    ))

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

[hmrcCharacterCount](src/main/twirl/uk/gov/hmrc/hmrcfrontend/views/components/HmrcCharacterCount.scala.html) is an
implementation of the GOV.UK CharacterCount that translates the dynamic words / characters remaining
text into English or Welsh using the Play framework Message API. You do not need to pass through the
language explicitly to this component, just pass through an implicit Messages.

```scala
@import uk.gov.hmrc.hmrcfrontend.views.html.components._
@import uk.gov.hmrc.hmrcfrontend.views.html.components.implicits._

@this(hmrcCharacterCount: HmrcCharacterCount)

@(label: String, maxWords: Int, field: Field)(implicit messages: Messages)

@hmrcCharacterCount(CharacterCount(
    label = Label(content = Text(label)),
    maxWords = Some(maxWords)
)).withFormField(field)
```

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

### Adding GTM to internal services

If you would like to add GTM to an internal service, you can do so using the [HmrcInternalHead](https://github.com/hmrc/play-frontend-hmrc/blob/main/src/main/twirl/uk/gov/hmrc/hmrcfrontend/views/helpers/HmrcInternalHead.scala.html) 
helper, which will add the GTM snippet in the `<head>` block. It should be used as demonstrated below in your own 
`Layout.scala`. You will need to pass through a CSP nonce as demonstrated in the example to allow the GTM script.

```scala
@import views.html.helper.CSPNonce
@import uk.gov.hmrc.govukfrontend.views.html.components.GovukLayout
@import uk.gov.hmrc.hmrcfrontend.views.html.helpers.HmrcInternalHead
@import uk.gov.hmrc.hmrcfrontend.views.html.components.HmrcInternalHeader
@import uk.gov.hmrc.hmrcfrontend.views.viewmodels.internalheader.InternalHeader

@this(
        govukLayout: GovukLayout,
        hmrcInternalHead: HmrcInternalHead,
        hmrcInternalHeader: HmrcInternalHeader
)
@(pageTitle: Option[String] = None)(contentBlock: Html)(implicit request: Request[_], messages: Messages)

@govukLayout(
  pageTitle = pageTitle,
  headBlock = Some(hmrcInternalHead(nonce = CSPNonce.get)),
  headerBlock = Some(hmrcInternalHeader(InternalHeader()))
)(contentBlock)

```

## Warning users before timing them out

In order to meet the accessibility [WCAG 2.1 Principle 2: Operable](https://www.w3.org/TR/WCAG21/#operable) you must
provide users with enough time to read and use content. In particular, WCAG 2.1 Success Criterion 2.2.1 (Timing Adjustable)
requires that users are able to turn off, adjust or extend the time limit, giving them at least 20 seconds to perform this with
a simple action.

On MDTP, users are, by default, automatically timed out of any authenticated service after 15 minutes
of inactivity. This mechanism, implemented in [SessionTimeoutFilter](https://github.com/hmrc/bootstrap-play/blob/main/bootstrap-frontend-src/main/scala/uk/gov/hmrc/play/bootstrap/frontend/filters/SessionTimeoutFilter.scala),
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

1. Identify the `signOutUrl` that should be used when users click 'Sign Out' on the timeout dialog. Your service may already be 
   supplying a `signOutUrl` parameter to the `hmrcStandardHeader` component, which controls the sign out link in the GOV.UK 
   header. Reusing this value may be a sensible choice. Refer to guidance above to understand how this argument is used by the 
   timeout dialog.

1. Update your layout template to pass in the `hmrcTimeoutDialogHelper` in the HEAD element, supplying the signOutUrl as
   a parameter. For example if using `hmrcLayout`, pass `Some(hmrcTimeoutDialogHelper(signOutUrl = signOutUrl))` in the 
   `additionalHeadBlock` parameter.

#### Synchronise session between tabs ####

Additionally, services can choose to opt-in to behaviour to synchronise session extension between different HMRC tabs
(using the `BroadcastChannel` API in browsers). In practical terms, this means that if a user sees a timeout dialog in
an active tab, and clicks to extend their session, then the timeout dialogs that have also opted into this behaviour in any background tabs will also restart the countdowns until they display their timeout warning.

This behaviour is currently flagged **off** (`false`) by default. To enable, you can either explicitly pass `Some(true)`
to the `HmrcTimeoutDialogHelper`, or you can add a boolean `true` or `false` to your `application.conf` with the key 
`hmrc-timeout-dialog.enableSynchroniseTabs`.

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

| Parameter         | Description                                                   | Example |
| ----------------- | ------------------------------------------------------------- | ------- |
| `signOutUrl`      | The url that will be used when users click 'Sign Out'         | Some(routes.SignOutController.signOut().url) |
| `timeoutUrl`      | The url that the timeout dialog will redirect to following timeout. Defaults to the `signOutUrl`. | Some(routes.TimeoutController.timeOut().url) |
| `keepAliveUrl`    | A endpoint used to keep the user’s session alive | Some(routes.KeepAliveController.keepAlive().url)
| `timeout`         | The timeout duration where this differs from `session.timeout` | 1800 |
| `countdown`       | The number of seconds before timeout the dialog is displayed. The default is 120.| 240 |
| `synchroniseTabs` | Allow the timeout dialog to use the `BroadcastChannel` to communicate session activity to other background tabs. Defaults to `None`, i.e. not enabled.| Some(true) |

The timeout dialog’s content can be customised using the following parameters:

| Parameter             | Description                                                   | Example |
| --------------------- | ------------------------------------------------------------- | ------- |
| `title`               | The text to use as a title for the dialog                     | Some(messages("hmrc-timeout-dialog.title")) |
| `message`             | The message displayed to the user                             | Some(messages("hmrc-timeout-dialog.message")) |
| `messageSuffix`       | Any additional text to be displayed after the timer           | Some(messages("hmrc-timeout-dialog.message-suffix")) |
| `keepAliveButtonText` | The text on the button that keeps the user signed in          | Some(messages("hmrc-timeout-dialog.keep-alive-button-text")) |
| `signOutButtonText`   | The text for the link which takes the user to a sign out page | Some(messages("hmrc-timeout-dialog.sign-out-button-text")) |

## Adding accessible-autocomplete CSS and Javascript

`play-frontend-hmrc` contains Javascript and CSS assets to enable `<select>` items with autocomplete to improve their 
accessibility. Currently, this is pulling in the Javascript and CSS from the GOV.UK library [accessible-autocomplete](https://github.com/alphagov/accessible-autocomplete).

If you are currently using [accessible-autocomplete](https://github.com/alphagov/accessible-autocomplete), you can replace 
your components and routing by using the ones in this library.

You will need to inject the CSS and Javascript into your views as follows:
```
@import uk.gov.hmrc.hmrcfrontend.views.html.helpers.HmrcLayout
@import uk.gov.hmrc.hmrcfrontend.views.html.helpers.HmrcAccessibleAutocompleteCss
@import uk.gov.hmrc.hmrcfrontend.views.html.helpers.HmrcAccessibleAutocompleteJavascript
@import views.html.helper.CSPNonce

@this(
    hmrcLayout: HmrcLayout,
    autcompleteCss: HmrcAccessibleAutocompleteCss,
    autocompleteJavascript: HmrcAccessibleAutocompleteJavascript
)

@(pageTitle: String, contentBlock: Html)(implicit request: RequestHeader, messages: Messages)

@hmrcLayout(
  pageTitle = Some(pageTitle),
  additionalHeadBlock = Some(autocompleteCss(CSPNonce.get)),
  nonce = CSPNonce.get,
  displayHmrcBanner = true,
  additionalScriptsBlock = Some(autocompleteJavascript(CSPNonce.get))
)(contentBlock)
```

References within your code to the Javascript object `accessibleAutocomplete` should be replaced with `HMRCAccessibleAutocomplete`.

### Transforming a Select element into an Accessible Autocomplete element

With the above accessible-autocomplete CSS and Javascript, you can transform your Select element into an
AccessibleAutocomplete element using the `asAccessibleAutocomplete` implicit helper method on a `Select`, as follows.

```scala
@import uk.gov.hmrc.govukfrontend.views.Implicits.RichSelect

@govukSelect(Select(...).asAccessibleAutocomplete())
```
This will set up an accessible autocomplete element with default values set as below
```text
defaultValue = ""
showAllValues = false
autoselect = false
```
More information on these values can be found [here](https://www.npmjs.com/package/accessible-autocomplete) under the `API documentation` heading

If you wish to change these values you can do so by providing an `AccessibleAutocomplete` model into the `asAccessibleAutocomplete` method as seen below
```scala
@govukSelect(Select(...).asAccessibleAutocomplete(Some(
  AccessibleAutocomplete(
    defaultValue = Some("United Kingdom"),
    showAllValues = true,
    autoSelect = true)
)))
```

There is a caveat currently with the `defaultValue` property, to use this option you will need to ensure
that you have a placeholder item in your select element that has an empty value. You will also need to make sure none of
your select items have the selected property set on them. See example below.

```scala
@govukSelect(Select(
        id = "sort",
        name = "sort",
        items = Seq(
            SelectItem(
                text = "Placeholder text"
            ),
            SelectItem(
                value = Some("published"),
                text = "Recently published"
            ),
            SelectItem(
                value = Some("updated"),
                text = "Recently updated"
            )
        ),
        label = Label(
            content = Text("Sort by")
        )
    ).asAccessibleAutocomplete(Some(
        AccessibleAutocomplete(
            defaultValue = Some("Recently updated"),
            showAllValues = true,
            autoSelect = false)
    )))
```

More information on `defaultValue` property can be found [here](https://www.npmjs.com/package/accessible-autocomplete) under the `Null options` heading

A preferred way would be to select a default value using the `selected` attribute on an select item instead of using the `defaultValue` property, as seen below.

```scala
@govukSelect(Select(
        id = "sort",
        name = "sort",
        items = Seq(
            SelectItem(
                value = Some("published"),
                text = "Recently published"
            ),
            SelectItem(
                value = Some("updated"),
                text = "Recently updated"
                selected = true
            )
        ),
        label = Label(
            content = Text("Sort by")
        )
    ).asAccessibleAutocomplete(Some(
        AccessibleAutocomplete(
            showAllValues = true,
            autoSelect = false)
    )))
```

## Adding your own SASS compilation pipeline

This library will manage SASS compilation for you. However, should you need for any reason to add your own SASS
compilation pipeline, follow the [steps detailed here](docs/maintainers/sass-compilation.md).

## Play Framework and Scala compatibility notes
This library is currently compatible with:
* Scala 2.12 and Scala 2.13
* Play 2.8

## Example Templates

We provide example templates using the Twirl components through a `Chrome` extension. Please refer to the
[extension's github repository](https://github.com/hmrc/play-frontend-govuk-extension) for installation instructions.

With the extension installed, you should be able to go to the [GOV.UK Design System](https://design-system.service.gov.uk/components/),
click on a component on the sidebar and see the `Twirl` examples matching the provided `Nunjucks` templates.

## HmrcAccountMenu considered experimental

The account menu component is a port
from [assets-frontend](http://hmrc.github.io/assets-frontend/components/account-header/index.html), this pattern is
currently being iterated outside this repository by the PTA team and might be subject to breaking API changes.


### Component Configuration

This component provides default menu links but also provides a way to 
override the links via configuration.

If you wish to override the default links you will need to have the below configuration in your application.conf file.

One thing to note is that the `href` is built up from concatenating `host + href` values.

```
pta-account-menu {
  account-home = {
    host = ${pta-account-menu.pertax-frontend.host}
    href = ${pta-account-menu.account-home.host}"/personal-account-custom"
  }

  messages = {
    host = ${pta-account-menu.pertax-frontend.host}
    href = ${pta-account-menu.messages.host}"/personal-account/messages-custom"
  }

  check-progress = {
    host = ${pta-account-menu.tracking-frontend.host}
    href = ${pta-account-menu.check-progress.host}"/track-custom"
  }

  your-profile = {
    host = ${pta-account-menu.pertax-frontend.host}
    href = ${pta-account-menu.your-profile.host}"/personal-account/your-profile-custom"
  }
  
  business-tax-account = {
    host = ${pta-account-menu.business-tax-account-frontend.host}
    href = ${pta-account-menu.business-tax-account.host}"/business-account"
  }
}
```

If you wish to override the pertax-frontend host value you can do so by setting the `pta-account-menu.pertax-frontend.host` value.

```
pta-account-menu {
  pertax-frontend.host = "http://pertax-frontend-host"
}
```

NOTE: If you override the `platform.frontend.host` configuration value, it will take precedence over the `pta-account-menu.pertax-frontend.host` value.

You may also wish to override the host per service value, this can be done by the below configuration.

```
pta-account-menu {
  messages.host             = "http://localhost:12346"
  account-home.host         = "http://localhost:12347"
  your-profile.host         = "http://localhost:12348"
  check-progress.host       = "http://localhost:12345"
  business-tax-account.host = "http://localhost:12349"
}
```

### Component Usage

To use the links from configuration you can use the helper method `withUrlsFromConfig()` on the `AccountMenu` instance. The configuration will need to be supplied implicitly 
as seen in the example below.

```
@this(
  hmrcAccountMenu: HmrcAccountMenu
)(implicit accountMenuConfig: AccountMenuConfig)

@hmrcAccountMenu(
  AccountMenu().withUrlsFromConfig()
)    
```

To set the message count on the `AccountMessages` link, you can use the helper method `withMessagesCount` as below.

```
@hmrcAccountMenu(
  AccountMenu().withMessagesCount(10)
)
```

There is also an optional `Business tax account` link provided in the `AccountMenu`, by default it is set to `None` and wont be displayed. 
If you do wish to use it there is default configuration for it, and it will also be pulled in via the `withUrlsFromConfig()` helper method.  

## Troubleshooting

If you are adding HTML elements to your page such as `<h1>` or `<p>`, you will need to add the CSS classes for the [GDS
Transport fonts](https://design-system.service.gov.uk/styles/typography/) from the GOV.UK Design System. A full list of 
the CSS classes can be found at https://design-system.service.gov.uk/styles/typography/.

These styles have been applied to the component supplied in `play-frontend-hmrc`, but you will need to manually add the 
styles to your service's own HTML elements. 

## Getting help

Please report any issues with this library in Slack at `#team-plat-ui`.

## Resolve play-frontend-hmrc artefacts

HMRC services get this configuration via the [sbt-auto-build library](https://github.com/hmrc/sbt-auto-build/blob/1bb9f5437ed5c2027b4c967585a2dd9a9a6740d0/src/main/scala/uk/gov/hmrc/SbtAutoBuildPlugin.scala#L55), external consumers will need to add the repository below to their SBT config themselves:

```scala
resolvers += MavenRepository("HMRC-open-artefacts-maven2", "https://open.artefacts.tax.service.gov.uk/maven2")
```

## Useful Links

- [HMRC Design Patterns](https://design.tax.service.gov.uk/hmrc-design-patterns/) - documentation for the use of `hmrc-frontend` components
- [hmrc-frontend](https://github.com/hmrc/hmrc-frontend/) - reusable Nunjucks HTML components for HMRC design patterns
- [GOV.UK Design System](https://design-system.service.gov.uk/components/) - documentation for the use of `govuk-frontend` components
- [govuk-frontend](https://github.com/alphagov/govuk-frontend/) - reusable Nunjucks HTML components from GOV.UK
- [GOV.UK Design System Chrome extension](https://github.com/hmrc/play-frontend-govuk-extension) - `Chrome` extension to add a Twirl tab for each example in the GOV.UK Design System

## Owning team README
Rationale for code and translation decisions, dependencies, as well as instructions for team members maintaining this repository can be found [here](/docs/maintainers/overview.md).

## License

This code is open source software licensed under the [Apache 2.0 License]("http://www.apache.org/licenses/LICENSE-2.0.html").
