# play-frontend-hmrc

This library provides a Play/Twirl implementation of `hmrc-frontend`.

[hmrc-frontend](https://github.com/hmrc/hmrc-frontend) contains the code and documentation for patterns specifically designed for HMRC.

[GOV.UK Frontend](https://github.com/alphagov/govuk-frontend) and the [GOV.UK Design System](https://design-system.service.gov.uk/) contains the code and documentation for design patterns designed to be used by all government departments.

The two sets of code and documentation are separate but used together.

See [HMRC Design Patterns](https://design.tax.service.gov.uk/hmrc-design-patterns/) for examples of what the design patterns look like and guidance on how to use them in your service.

## Table of Contents

- [Background](#background)
- [Getting Started](#getting-started)
- [Usage](#usage)
- [Dependencies](#dependencies)
- [Getting Help](#getting-help)
- [Contributing](#contributing)
- [Useful Links](#useful-links)
- [Owning Team Readme](#owning-team-readme)
- [License](#license)

## Background

This library provides `Twirl` basic building blocks as originally implemented in the [hmrc-frontend](https://github.com/hmrc/hmrc-frontend/)
library. Additionally, we plan to include more helpers built on top of `Play's` own helpers and the basic components.

## Getting started
1>  Add [Twirl](https://github.com/hmrc/play-frontend-hmrc/releases) library in the App dependencies.
```sbt
//build.sbt for Play 2.5
libraryDependencies += "uk.gov.hmrc" %% "play-frontend-hmrc" % "x.y.z-play-25"
//or Play 2.6
libraryDependencies += "uk.gov.hmrc" %% "play-frontend-hmrc" % "x.y.z-play-26"
```

2>  Add SASS assets to app/assets/stylesheets in application.scss to inherit / extend hmrc-frontend style assets / elements, e.g.:
```
$hmrc-assets-path: "/app-name-here/assets/lib/hmrc-frontend/hmrc/";

@import "lib/hmrc-frontend/hmrc/all";

.app-reference-number {
  display: block;
  font-weight: bold;
}
```

3>  Add hmrc-frontend routing redirection in app.routes:
```scala
->         /hmrc-frontend                      hmrcfrontend.Routes
```

4>  Add TwirlKeys.templateImports in build.sbt:
```sbt
    TwirlKeys.templateImports ++= Seq(
      "uk.gov.hmrc.hmrcfrontend.views.html.components._"
    )
```

5>  You may want to consider mixing in the [play-frontend-govuk](https://github.com/hmrc/play-frontend-govuk/) library as a dependency to use GovukLayout to create standard views out of the box
```scala
@govukLayout(
    pageTitle = pageTitle,
    headBlock = Some(head()),
    beforeContentBlock = beforeContentBlock,
    footerItems = Seq(FooterItem(href = Some("https://govuk-prototype-kit.herokuapp.com/"), text = Some("GOV.UK Prototype Kit v9.1.0"))),
    bodyEndBlock = Some(scripts()))(contentBlock)
```

A reference implementation can be found in [play-mtp-twirl-frontend](https://github.com/hmrc/play-mtp-twirl-frontend)

### Using hmrc-frontend Components in Twirl

To use the [hmrc-frontend](https://github.com/hmrc/hmrc-frontend/) `Twirl` [components](https://github.com/hmrc/play-frontend-hmrc/blob/master/src/main/play-26/uk/gov/hmrc/hmrcfrontend/views/html/components/package.scala) 
and all the [types](https://github.com/hmrc/play-frontend-hmrc/blob/master/src/main/scala/uk/gov/hmrc/hmrcfrontend/views/Aliases.scala) needed to construct them, import the following:
```scala
@import uk.gov.hmrc.hmrcfrontend.views.html.components._
```

### Twirl HTML helper methods
The following import will summon [implicits](https://github.com/hmrc/play-frontend-hmrc/blob/master/src/main/scala/uk/gov/hmrc/hmrcfrontend/views/Implicits.scala) that provide extension methods on `Play's` [Html](https://www.playframework.com/documentation/2.6.x/api/scala/play/twirl/api/Html.html) objects.
This includes HTML trims, pads, indents and handling HTML emptiness.
```scala
@import uk.gov.hmrc.hmrcfrontend.views.html.components.implicits._
```

## Usage

The library is cross-compiled for `Play 2.5` and `Play 2.6`, the main difference between the two versions being that the latter
supports dependency injection of Twirl templates.

### Play 2.5

The namespace `uk.gov.hmrc.hmrcfrontend.views.html.components` exposes the components' templates as values with the prefix
`Hmrc`, ex: an `hmrcPageHeading` is available as `HmrcPageHeading`.

Ex: a page heading with a corresponding section
```scala
@import uk.gov.hmrc.hmrcfrontend.views.html.components._

@()
@HmrcPageHeading(PageHeading(
  text = "Foo",
  section = Some("Section bar")
))
```

### Play 2.6

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

### Accessibility Statements

The [hmrcStandardFooter](src/main/play-26/twirl/uk/gov/hmrc/hmrcfrontend/views/helpers/hmrcStandardFooter.scala.html) component,
 generates the standard Gov.UK footer including the standard list of footer links.

To configure this component to link to the new 
[Accessibility Statement service](https://www.github.com/hmrc/accessibility-statement-frontend), provide the key 
`accessibility-statement.service-path` in your `application.conf`. This key is the path to your 
accessibility statement under https://www.tax.service.gov.uk/accessibility-statement.
 
For example, if your accessibility statement is https://www.tax.service.gov.uk/accessibility-statement/discounted-icecreams, 
this property must be set to `/discounted-icecreams` as follows:

```
accessibility-statement.service-path = "/discounted-icecreams"
```

### Example Templates

We intend to provide example templates using the Twirl components through a `Chrome` extension.

This is currently done for govuk-frontend components [here](https://github.com/hmrc/play-frontend-govuk-extension), but yet to be implemented
for [hmrc-frontend](https://github.com/hmrc/hmrc-frontend/).

With the extension installed, you would then be able to go to the [HMRC Design System](https://design.tax.service.gov.uk/hmrc-design-patterns/), 
click on a component on the sidebar and see the `Twirl` examples matching the provided `Nunjucks` templates.

## Dependencies

### sbt

The library depends on an `hmrc-frontend` artifact published as a webjar.

```sbt
"org.webjars.npm" % "hmrc-frontend" % "x.y.z"
```

We do not currently automate the publishing of the webjar so it has to be manually published from [WebJars](https://www.webjars.org) after an `hmrc-frontend` release.

## Getting help

Please report any issues with this library in Slack at `#team-plat-ui`.

## Contributing

### Design patterns

If you need a pattern that does not appear in the HMRC Design Patterns, you can [contribute a new one](https://github.com/hmrc/design-patterns/issues/new).

### Features and issues

If you would like to propose a feature or raise an issue with HMRC Frontend, [create an issue](https://github.com/hmrc/hmrc-frontend/issues/new).

You can also create a pull request to contribute to HMRC Frontend. See our [contribution process and guidelines for HMRC Frontend](https://github.com/hmrc/hmrc-frontend/blob/master/CONTRIBUTING.md) before you create a pull request.
This change will then be available in Twirl once the appropriate upgrade is made in this repository to match that version upgrade.

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
