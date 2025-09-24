[Back to README.md](../README.md)

# Using components

Information how to use some of the components like banners, implicit methods can be found in a separate Markdown file.

## Table of Contents

- [Finding Twirl templates for GOV.UK and HMRC design system components](#finding-twirl-templates-for-govuk-and-hmrc-design-system-components)
- [Using the components](#using-the-components)
- [Using `Content`](#using-content)
- [Useful implicits](#useful-implicits)
  - [withFormField](#withformfield)
  - [withHeading and withHeadingAndCaption](#withheading-and-withheadingandcaption)
  - [RichDateInput](#richdateinput)
  - [RichErrorSummary](#richerrorsummary)
  - [RichStringSupport](#richstringsupport)
- [Adding a beta feedback banner](#adding-a-beta-feedback-banner)
- [Adding a User Research Banner](#adding-a-user-research-banner)
- [Helping users report technical issues](#helping-users-report-technical-issues)
- [Adding dynamic character count with Welsh translations to a text input](#adding-dynamic-character-count-with-welsh-translations-to-a-text-input)
- [Adding accessible autocomplete to a select input](#adding-accessible-autocomplete-to-a-select-input)
  - [Transforming a `<select>` element into an Accessible Autocomplete element](#transforming-a-select-element-into-an-accessible-autocomplete-element)
- [Opening links in a new tab](#opening-links-in-a-new-tab)

## Finding Twirl templates for GOV.UK and HMRC design system components

We provide example templates using the Twirl components through a `Chrome` extension. Please refer to the
[extension’s github repository](https://github.com/hmrc/play-frontend-govuk-extension) for installation instructions.

With the extension installed, you can go to the
[GOVUK Design System components](https://design-system.service.gov.uk/components/)
or
[HMRC Design System patterns](https://design.tax.service.gov.uk/hmrc-design-patterns/)
pages, click on a component on the sidebar and see the `Twirl` examples matching the provided `Nunjucks` templates.

[back to top](#using-components)

## Using the components

To use our components and helpers, you will first need to import them from
their corresponding packages in:
* `uk.gov.hmrc.govukfrontend.views.html.components`,
* `uk.gov.hmrc.hmrcfrontend.views.html.components`, or
* `uk.gov.hmrc.hmrcfrontend.views.html.helpers`

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

[back to top](#using-components)

## Using `Content`

When passing data values to components in play-frontend-hmrc, you should use one of the types derived from the [`Content`](/play-frontend-hmrc-play-30/src/main/scala/uk/gov/hmrc/govukfrontend/views/viewmodels/content/Content.scala) trait:
* `Text` - for untrusted data that may need to be escaped (eg. dynamic data from a form input, a URL parameter or a backend API).
  **You should use this type by default**.
* `HtmlContent` - for trusted data that contains embedded HTML (eg. static `Messages` content that includes HTML).
  **Only use this type if you know the value contains trusted data that includes HTML**.

[back to top](#using-components)

## Handling user input securely
See separate file here: [Handling user input securely](/docs/handling-user-input-securely.md)

[back to top](#using-components)

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

[back to top](#using-components)

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

[back to top](#using-components)

### RichDateInput

The implicit class `RichDateInput` provides three extension methods to populate the `DateInput` view model:
* `withDayMonthYearFormField(field: Field)`
* `withDayMonthFormField(field: Field)`
* `withMonthYearFormField(field: Field)`

These methods takes a Play `play.api.data.Field` and enrich the `DateInput` with:
* InputItems corresponding to a combination of the day, month and year fields with
    * ids and names of the form `<name>.day`, `<name>.month` and `<name>.year`  (`<name>` being populated from the `Field` name).
* standard English and Welsh labels
* an id, set to the `Field` name
* values for the nested day, month and year fields
* a Text error message from an implicit `Messages` indexed by the first non-empty:
* `field("day").error`
* `field("month").error`
* `field("year").error`
* `field.error`
* the correct CSS error classes applied to any invalid nested day, month or year field or  to all fields in the case of a global date error.

**These methods will throw an `IllegalArgument` exception if passed a pre-populated set of DateInput `items`. These methods should only be used to create
standard empty items. If your use case is more complex, you may need to construct your own `DateInput`.**

For example, if using the one question per page pattern, the method could be used as follows:

```scala  
@govukDateInput(DateInput(
  hint = Some(Hint(content = Text("date.hint"))), 
  fieldset = Some(Fieldset(
    legend = Some(Legend(
      content = Text(messages("date.heading")), 
      classes = "govuk-fieldset__legend--l", isPageHeading = true)))
  )).withDayMonthYearFormField(dateInputForm("date")))  
```

Setting up form validation for this field might look like:

```scala  
case class DateData(day: String, month: String, year: String)  
case class PageData(date: DateData)  
  
object DateFormBinder {  
 def form: Form[PageData] = Form[PageData](mapping(
   "date" -> mapping(
     "day" -> text.verifying(dayConstraint),
     "month" -> text.verifying(monthConstraint),
     "year"  -> text.verifying(yearConstraint)
   )(DateData.apply)(DateData.unapply)
     .verifying(dateConstraint) )(PageData.apply)(PageData.unapply) )}  
```  

In the code above, `dayConstraint`, `monthConstraint`, `yearConstraint` and `dateConstraint` would be defined  
as per the [Play documentation](https://www.playframework.com/documentation/3.0.x/ScalaCustomValidations) on custom validations.

The controller submit method for this form might look like:

```scala  
def submit() = Action { implicit request =>  
 DateFormBinder.form .bindFromRequest() 
   .fold( 
     formWithError => BadRequest(dateInputPage(formWithError, routes.DateInputController.submit())),
     _ => Redirect(routes.DateInputController.thanks()) 
   )}  
```  
Additionally, there are methods `withDayMonthYearFormFieldWithErrorAsHtml(field: Field)`, `withDayMonthFormFieldWithErrorAsHtml(field: Field)`  and `withMonthYearFormFieldWithErrorAsHtml(field: Field)` which behave as the  above methods with the difference that form errors are bound as instances of `HtmlContent`.

Note that you will need to pass through an implicit `Messages` to your template.

[back to top](#using-components)

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

[back to top](#using-components)

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

[back to top](#using-components)

## Adding a beta feedback banner

If you would like to add a banner to your service stating that your service is in beta, and providing a link to a feedback
form, you can do so use the `StandardBetaBanner` viewmodel to construct a `PhaseBanner`, which is bound to a `GovukPhaseBanner` Twirl template.

The `HmrcStandardPage`, `HmrcStandardHeader` and `HmrcStandardHeader` all have constructor methods that take in an optional
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

```hocon
contact-frontend.serviceId = "<any-service-id>"
```

You can then use the banner as per below (note the injected implicit `ContactFrontendConfig`):

```scala
@import uk.gov.hmrc.hmrcfrontend.config.ContactFrontendConfig
@import uk.gov.hmrc.hmrcfrontend.views.config.StandardBetaBanner
@import uk.gov.hmrc.hmrcfrontend.views.html.helpers.HmrcStandardPage
@import uk.gov.hmrc.hmrcfrontend.views.viewmodels.hmrcstandardpage._
@import config.AppConfig
@import uk.gov.hmrc.anyfrontend.controllers.routes

@this(hmrcStandardPage: HmrcStandardPage, standardBetaBanner: StandardBetaBanner)(implicit cfConfig: ContactFrontendConfig)

@(pageTitle: String, appConfig: AppConfig)(contentBlock: Html)(implicit request: RequestHeader, messages: Messages)

@hmrcStandardPage(
  serviceURLs = ServiceURLs(
    serviceUrl = Some(routes.IndexController.index().url),
    signOutUrl = Some(routes.SignOutController.signOut().url)
  ),
  banners = Banners(phaseBanner = Some(standardBetaBanner())),
  pageTitle = Some(pageTitle),
  isWelshTranslationAvailable = true /* or false if your service has not been translated */
)(contentBlock)
```

[back to top](#using-components)

## Adding a User Research Banner

The User Research Banner is a component used to display a blue banner, containing link text inviting the service user to
take part in user research. The Twirl template is [HmrcUserResearchBanner.scala.html](/play-frontend-hmrc-play-30/src/main/twirl/uk/gov/hmrc/hmrcfrontend/views/components/HmrcUserResearchBanner.scala.html),
and the viewmodel is [UserResearchBanner.scala](/play-frontend-hmrc-play-30/src/main/scala/uk/gov/hmrc/hmrcfrontend/views/viewmodels/userresearchbanner/UserResearchBanner.scala).

The banner contains hard coded content, available in English and Welsh, with translation handled automatically via the
Play `language` from an implicit `request`. It is not possible to change this content, as it has been provided by
Research Services, and needs to be consistent across tax.service.gov.uk.

If your service uses `HmrcStandardPage.scala.html`, you can add the `HmrcUserResearchBanner` as shown:

```scala
@hmrcStandardPage(
  serviceURLs = ???,
  banners = Banners(userResearchBanner = Some(UserResearchBanner(url = "your-user-research-url-here")))
  pageTitle = ???
)(contentBlock)
```

Research Services will tell you what URL to use for your service.

[back to top](#using-components)

## Helping users report technical issues

The [HmrcReportTechnicalIssueHelper](/play-frontend-hmrc-play-30/src/main/twirl/uk/gov/hmrc/hmrcfrontend/views/helpers/HmrcReportTechnicalIssueHelper.scala.html) component
generates a link that allows users to report technical issues with your service.

To configure this helper, add the following configuration to your `application.conf`

```hocon
contact-frontend.serviceId = "<any-service-id>"
```

`serviceId` helps identify your service when members of the public report technical issues.
If your service is *not* already integrating with contact-frontend, we advise choosing an
identifier that is specific to your service and unlikely to be used by any other service, avoiding any special characters
or whitespace.

The component should be added to the bottom of each page in your service. This can be done by defining a reusable block
in your layout template and passing into `HmrcStandardPage` or `GovukLayout` in place of contentBlock:

```scala
@content = {
  @contentBlock
  @hmrcReportTechnicalIssueHelper()
}
```

[back to top](#using-components)

## Adding dynamic character count with Welsh translations to a text input

[HmrcCharacterCount](/play-frontend-hmrc-play-30/src/main/twirl/uk/gov/hmrc/hmrcfrontend/views/components/HmrcCharacterCount.scala.html) is an
implementation of the GOV.UK CharacterCount that translates the dynamic words / characters remaining
text into English or Welsh using the Play framework Message API. You do not need to pass through the
language explicitly to this component, just pass through an implicit Messages.

[back to top](#using-components)

## Adding accessible autocomplete to a select input

`play-frontend-hmrc` contains Javascript and CSS assets to enable `<select>` items with autocomplete to improve their
accessibility. Currently, this is pulling in the Javascript and CSS from the GOV.UK library [accessible-autocomplete](https://github.com/alphagov/accessible-autocomplete).

If you are currently using [accessible-autocomplete](https://github.com/alphagov/accessible-autocomplete), you can replace
your components and routing by using the ones in this library.

You will need to inject the CSS and Javascript into your views as follows:

```scala
@import uk.gov.hmrc.hmrcfrontend.views.html.helpers.HmrcAccessibleAutocompleteCss
@import uk.gov.hmrc.hmrcfrontend.views.html.helpers.HmrcAccessibleAutocompleteJavascript
@import uk.gov.hmrc.hmrcfrontend.views.html.helpers.HmrcStandardPage
@import uk.gov.hmrc.hmrcfrontend.views.viewmodels.hmrcstandardpage._

@this (
  hmrcStandardPage: HmrcStandardPage,
  autocompleteCss: HmrcAccessibleAutocompleteCss,
  autocompleteJavascript: HmrcAccessibleAutocompleteJavascript
)

@(pageTitle: String, contentBlock: Html)(implicit request: RequestHeader, messages: Messages)

@hmrcStandardPage(
  banners = Banners(displayHmrcBanner = true),
  templateOverrides = TemplateOverrides(
    additionalHeadBlock = Some(autocompleteCss()),
    additionalScriptsBlock = Some(autocompleteJavascript())
  ),
  pageTitle = Some(pageTitle)
)(contentBlock)
```

References within your code to the Javascript object `accessibleAutocomplete` should be replaced with `HMRCAccessibleAutocomplete`.

[back to top](#using-components)

### Transforming a `<select>` element into an Accessible Autocomplete element

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

[back to top](#using-components)

### Opening links in a new tab

The [HmrcNewTabLinkHelper](/play-frontend-hmrc-play-30/src/main/twirl/uk/gov/hmrc/hmrcfrontend/views/helpers/HmrcNewTabLinkHelper.scala.html) component
allows you to link to content that opens in a new tab, with protection against reverse tabnapping. It takes in an implicit
`Messages` parameter to translate the content `(opens in new tab)`.

It is a wrapper around the `HmrcNewTabLink`, however this helper means that services do not need to explicitly pass in a
language for internationalization of the link text.

It can be used as follows:

```scala
import uk.gov.hmrc.hmrcfrontend.views.html.helpers.HmrcNewTabLinkHelper
import uk.gov.hmrc.hmrcfrontend.views.viewmodels.newtablinkhelper.NewTabLinkHelper

@this(hmrcNewTabLinkHelper: HmrcNewTabLinkHelper)
@(linkText: String, linkHref: String)(implicit messages: Messages)

@hmrcNewTabLinkHelper(NewTabLinkHelper(
  text = linkText,
  href = Some(linkHref)
))
```

[back to top](#using-components)
