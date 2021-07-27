# play-frontend-govuk

play-frontend-govuk is a Scala Twirl implementation of the 
[govuk-frontend](https://github.com/alphagov/govuk-frontend/) components library as 
  documented in the [GOV.UK Design System](https://design-system.service.gov.uk/components/). 

## Table of Contents

- [Background](#background)
- [Getting started](#getting-started)
- [Getting help](#getting-help)
- [Contributing](#contributing)
- [Useful links](#useful-links)
- [Owning team readme](#owning-team-readme)
- [License](#license)

## Background

This library provides `Twirl` versions of the Nunjucks components implemented in the 
[govuk-frontend](https://www.npmjs.com/package/govuk-frontend)
NPM package. Additionally, it provides a layout that wraps `GovukTemplate` used by all GOV.UK frontends.
The following figure illustrates the components and their dependencies (zoom in for a better view).

![components](docs/images/govukcomponents.svg)

## Getting started

### Installation

For most developers, this library should be brought in via [play-frontend-hmrc](https://github.com/hmrc/play-frontend-hmrc). 
This means that you do **NOT** need to add this library as a dependency of your project. Instead, follow the 
instructions to add [play-frontend-hmrc](https://github.com/hmrc/play-frontend-hmrc) to your project, and that library 
will manage asset compilations, routes, etc.

If you do need to use this library directly, follow 
the [standalone instructions](docs/standalone.md).

### Using the components
To use the components and all the [types](src/main/scala/uk/gov/hmrc/govukfrontend/views/Aliases.scala) 
needed to construct them, import the following:

```scala
@import uk.gov.hmrc.govukfrontend.views.html.components._
```

You can then use the components in your templates like this:

```scala
@this(govukButton: GovukButton)

@()
@govukButton(Button(
  disabled = true,
  content = Text("Disabled button")
))
```

### Useful implicits
The following import will summon [implicits](src/main/scala/uk/gov/hmrc/govukfrontend/views/Implicits.scala) that provide extension methods on `Play's` [FormError](https://www.playframework.com/documentation/2.6.x/api/scala/play/api/data/FormError.html) 
to convert between `Play's` form errors and view models used by `GovukErrorMessage` and `GovukErrorSummary` (E.g. **form.errors.asTextErrorLinks**, **form.errors.asTextErrorMessageForField**): 
```scala
@import uk.gov.hmrc.govukfrontend.views.html.components.implicits._

...
    @formWithCSRF(action = routes.NameController.onSubmit(mode)) {

    @if(form.errors.nonEmpty) {
      @errorSummary(ErrorSummary(errorList = form.errors.asTextErrorLinks, title = Text(messages("error.summary.title"))))
    }

      @input(Input(id = "value", name = "value", value = form.value,
        errorMessage = form.errors.asTextErrorMessageForField(fieldKey = "value"),
        label = Label(isPageHeading = true, classes = "govuk-label--l", content = Text(messages("name.heading"))))) 
 }
...
```

It also provides extension methods on `Play's` [Html](https://www.playframework.com/documentation/2.6.x/api/scala/play/twirl/api/Html.html) 
objects. This includes HTML trims, pads, indents and handling HTML emptiness.

### withFormField

An extension method `withFormField(field: play.api.data.Field)` exists for the following classes:
* CharacterCount
* Checkboxes
* Input
* Radios
* Select
* Textarea
* DateInput (provided as part of [play-frontend-hmrc](https://github.com/hmrc/play-frontend-hmrc#richdateinput))

This method allows a Play forms Field to be passed through when creating an instance of `play-frontend-govuk` form input,
which will enrich the input with the following:
* Using the `Field` name for the input name
* Using the `Field` name for the input id or idPrefix
* Using the `Field` error message to create a `Text` error messages
* Using the `Field` value as pre-filled value (for `CharacterCount`, `Input`, `Textarea`) or pre-selected value
  (`Checkboxes`, `Radios`, `Select`)

The methods can be used as methods in a Twirl template as demonstrated below:
```
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

### GovukLayout
`GovukLayout` provides a convenient way of setting up a view with standard structure and the GOV.UK design elements. 

The `GovukLayout` by default renders the main `contentBlock` of the page in 
[two thirds width content](https://design-system.service.gov.uk/styles/layout/).
This means that the `contentBlock` is wrapped in the following styling:

```html
<div class="govuk-grid-row">
  <div class="govuk-grid-column-two-thirds">
    /// contentBlock HTML is inserted here
  </div>
</div>
```

However, if you wish to override the styling of the main content, you can do so by passing in an optional `mainContentLayout`
parameter of type `Option[Html => Html]`, which will apply wrapping content around your `contentBlock`. 

If you wish to create a layout with [two thirds, one third styling](https://design-system.service.gov.uk/styles/layout/)
(for example if your page has a sidebar), there is a helper `twoThirdsOneThirdMainContent.scala.html` which can be used
as follows:

```html
@import views.html.helper.CSPNonce

@this(
        govukLayout: GovukLayout,
        head: Head,
        scripts: Scripts,
        twoThirdsOneThirdMainContent: TwoThirdsOneThirdMainContent
)

@(pageTitle: Option[String] = None,
        beforeContentBlock: Option[Html] = None
)(contentBlock: Html)(implicit request: Request[_], messages: Messages)

@sidebar = {
    <h2 class="govuk-heading-xl">This is my sidebar</h2>
    <p class="govuk-body">There is my sidebar content</p>
}

@govukLayout(
    pageTitle = pageTitle,
    headBlock = Some(head()),
    beforeContentBlock = beforeContentBlock,
    footerItems = Seq(FooterItem(href = Some("https://www.gov.uk/help"), text = Some("Help using GOV.UK"))),
    bodyEndBlock = Some(scripts()),
    mainContentLayout = Some(twoThirdsOneThirdMainContent(sidebar)),
    cspNonce = CSPNonce.get
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
[extension's github repository](https://github.com/hmrc/play-frontend-govuk-extension) for installation instructions.

With the extension installed, you should be able to go to the [GOV.UK Design System](https://design-system.service.gov.uk/components/), 
click on a component on the sidebar and see the `Twirl` examples matching the provided `Nunjucks` templates.

## Getting help

Please report any issues with this library in Slack at `#team-plat-ui`.

## Contributing

PlatUI and this repository is responsible for providing a means to access GOV.UK components in Play/Twirl.
However, the underlying components, their design and surrounding user research are owned by GDS under GOV.UK.

If you would like to propose a feature, you should raise this with GDS and the wider design community.

If you have an issue with the underlying govuk-frontend implementation, you can [create an issue](https://github.com/alphagov/govuk-frontend/issues/new) for them to address directly.
You could also look at raising a feature request there too.

Should GOV.UK accept your feature request, it will then be available in Twirl once the appropriate upgrade is made in this repository to match that version upgrade.

## Useful Links

- [govuk-frontend](https://github.com/alphagov/govuk-frontend/) - reusable Nunjucks HTML components from GOV.UK
- [GOV.UK Design System](https://design-system.service.gov.uk/components/) - documentation for the use of `govuk-frontend` components
- [play-frontend-hmrc](https://github.com/hmrc/play-frontend-hmrc/) - Twirl implementation of `hmrc-frontend` components
- [hmrc-frontend](https://github.com/hmrc/hmrc-frontend/) - reusable Nunjucks HTML components for HMRC design patterns
- [HMRC Design Patterns](https://design.tax.service.gov.uk/hmrc-design-patterns/) - documentation for the use of `hmrc-frontend` components
- [GOV.UK Design System Chrome extension](https://github.com/hmrc/play-frontend-govuk-extension) - `Chrome` extension to add a Twirl tab for each example in the GOV.UK Design System

## Owning team README
Rationale for code and translation decisions, dependencies, as well as instructions for team members maintaining this repository can be found [here](/docs/maintainers/overview.md).

## License

This code is open source software licensed under the [Apache 2.0 License]("http://www.apache.org/licenses/LICENSE-2.0.html").
