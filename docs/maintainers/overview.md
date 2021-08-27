# Repository Maintenance Overview

## Table of Contents

- [Testing](#testing)
- [Upgrading](#upgrading)
- [Translation Decisions](#translation-decisions)
- [Architectural decision records](#architectural-decision-records)
- [Useful Links](#useful-links)

## Testing

### Unit Tests

The unit tests work against three sets of fixtures specific to the govuk-frontend and hmrc-frontend 
libraries.

To regenerate the test fixtures, you will need the template renderer running locally (see below).
Then run ```sbt generateGovukFixtures``` and ```sbt generateHmrcFixtures```. The template renderer does
not need to be running when running the unit tests themselves.

#### (govuk|hmrc)-frontend/test-fixtures/

Fixtures derived from the [hmrc-frontend](https://github.com/hmrc/hmrc-frontend) or
[govuk-frontend](https://github.com/hmrc/govuk-frontend) YAML documentation.
These examples are used in `hmrc-frontend` and `govuk-frontend`'s own unit tests.
They are populated automatically by the sbt `generate(Govuk|Hmrc)Fixtures` task.

#### (govuk|hmrc)-frontend/additional-fixtures/

Additional fixtures for cases not covered by examples provided in govuk-frontend or hmrc-frontend.
The inputs are created manually. The outputs are updated automatically by the sbt `generate(Govuk|Hmrc)Fixtures` task.

#### (govuk|hmrc)-frontend/patched-fixtures/

Manually maintained coverage for any scenarios where we have chosen to
diverge from the GOV.UK or HMRC components. The inputs and outputs for these fixtures
are generated manually.

### Generative Testing

To ensure (as much as possible) that the implemented templates conform to the `(govuk|hmrc)-frontend` templates, we use generative
testing, via `scalacheck`, to compare the `Twirl` templates output against the `Nunjucks` `(govuk|hmrc)-frontend` templates.
 
The tests run against a `node.js` service used to render the templates,
so you'll need to install it first.
To install `node.js` via `nvm` please follow the instructions [here](https://github.com/nvm-sh/nvm#installation-and-update).

To start the service before running integration tests:
```bash
git clone git@github.com:hmrc/x-govuk-component-renderer.git

cd x-govuk-component-renderer

npm install

npm start
```

Once the service is started on port 3000, you can run the integration tests:
```sbt
sbt it:test
```

_Note: The integration tests output produces a bit of noise as the library outputs statistics about the generators to check
the distribution of the test cases. More information about collecting statistics on generators 
[here](https://github.com/typelevel/scalacheck/blob/master/doc/UserGuide.md#collecting-generated-test-data)._

The integration tests are automatically run as part of the build and publish pipeline with the component renderer 
run as a sidecar. For this reason, it's important to merge any changes to the component renderer before merging corresponding
changes to the library.

#### Reproducing Failures (Deterministic Testing)
In case of a test failure, the test reporter outputs a `org.scalacheck.rng.Seed` encoded in Base-64 that can be passed back to the failing test to reproduce it.
More information about this feature [here](https://gist.github.com/non/aeef5824b3f681b9cfc141437b16b014).

Ex:
```scala
object hmrcPageHeadingIntegrationSpec
    extends TemplateIntegrationSpec[PageHeading](
      hmrcComponentName = "hmrcPageHeading", seed = Some("Aw2mVetq64JgBXG2hsqNSIwFnYLc0798R7Ey9XIZr6M=")) // pass the seed and re-run
```

Upon a test failure, the test reporter prints out a link to a diff file in `HTML` to easily compare the
markup for the failing test case against the expected markup. The diff is presented as if the `original` file was
the Twirl template output and the `new` file was the Nunjucks template output (expected result). 

```scala
Diff between Twirl and Nunjucks outputs (please open diff HTML file in a browser): file:///Users/foo/dev/hmrc/play-frontend-hmrc/target/hmrcPageHeading-diff-2b99bb2a-98d4-48dc-8088-06bfe3008021.html
```

### Running all tests for all supported Scala and Play versions

Prior to merging to trunk, it is a good idea to run all the tests against all supported versions of Scala and Play.
These checks will also be performed automatically as part of the build pipeline before publishing.

To achieve this, run:

```bash
PLAY_VERSION=2.6 sbt clean +test +it:test && \
PLAY_VERSION=2.7 sbt clean +test +it:test && \
PLAY_VERSION=2.8 sbt clean +test +it:test
```

## Upgrading

[This guide](/docs/maintainers/upgrading.md) describes the process of updating the library when a new 
version of `govuk-frontend` or `hmrc-frontend` is released. 

## Translation Decisions

When writing a new template from an existing `Nunjucks` template it is necessary to make a few translation decisions.

### Validation

There is a lack of validation in `Nunjucks` templates to consider when translating `(govuk|hmrc)-frontend` components, but 
it is important to retain high parity of features.

That said, some Twirl components in the library add validation by using `scala` assertions such as 
[require](https://www.scala-lang.org/api/current/scala/Predef$.html#require(requirement:Boolean,message:=%3EAny):Unit),
which means **`Play` controllers should be handling potential `IllegalArgumentException`** thrown from views.

### Required and optional parameters
   
Parameters may not always be documented comprehensively as `required` or `optional` in the YAML documentation, so 
the disambiguation comes from looking at its usage in the template.

We opted to map optional parameters as `Option` types because it is the most natural mapping.
   
### Mapping from untyped Javascript to typed Scala

`Javascript` makes liberal use of boolean-like types, the so called `truthy` and `falsy` values.
Special care is needed to translate conditions involving these types correctly.

Ex: the following `Nunjucks` snippet where name is a `string` 

```nunjucks
{% if params.name %} name="{{ params.name }}"{% endif %}
``` 

would not render anything if `name` was `""` since it is 
a [falsy value](https://developer.mozilla.org/en-US/docs/Glossary/Falsy).

It can be mapped to `name: Option[String]` in `Scala` and translated to `Twirl` as:

```scala
@name.filter(_.nonEmpty).map { name => name="@name" }

// instead of the following which would render `name=""` 
// if name had the value Some("")
@name.map { name => name="@name@ }
```

Another example is the representation of `Javascript`'s `undefined`, which maps nicely to `Scala`'s `None`.
The need to represent `undefined` sometimes gives rise to unusual types like `Option[List[T]]`.
The most correct type here would be `Option[NonEmptyList[T]]` but we opted not to 
use [refinement types](https://github.com/fthomas/refined) yet.

To date, the following conventions have been followed:

* `classes` are of type `String`, normally defaulting to `""`, rather than `Option[String]` defaulting to `None`
* boolean attributes are `Option`-wrapped only if the Nunjucks template distinguishes between `null`/`undefined`
  values and `true`/`false` values
* `id` can be either `Option[String]` or `String` - FIXME

## Architectural decision records 

We are using MADRs to record architecturally significant decisions in this library. To find out more
visit [MADR](https://github.com/adr/madr)

See our [architectural decision log](adr/index.md) (ADL) for a list of past decisions.

Decisions relating to `play-frontend-govuk`, which is now part of this library can be found
[here](play-frontend-govuk-adr/index.md)

### How to create a new ADR

1. Install [Node](https://nodejs.org/en/download/) if you do not have this already. Node includes
   npm.

1. Install `adr-log` if you do not have this already

    ```shell script
    npm install -g adr-log
    ```

1. Copy [template.md](adr-template.md) as NNNN-title-of-decision.md, and fill
in the fields. Do not feel you have to fill in all the fields, only fill in fields
that are strictly necessary. Some decisions will merit more detail than others.

1. To re-generate the table of contents, run

    ```shell script
    ./generate-adl.sh
    ```

## Useful Links
- [x-govuk-component-renderer](https://github.com/hmrc/x-govuk-component-renderer) - service that returns HTML for `govuk-frontend` and `hmrc-frontend` component input parameters in the form of JSON objects - useful for confirming Twirl HTML outputs in integration tests
- [GOV.UK Design System](https://design-system.service.gov.uk/components/) - documentation for the use of `govuk-frontend` components
- [govuk-frontend](https://github.com/alphagov/govuk-frontend/) - reusable Nunjucks HTML components from GOV.UK
- [hmrc-frontend](https://github.com/hmrc/hmrc-frontend/) - reusable Nunjucks HTML components for HMRC design patterns
- [HMRC Design Patterns](https://design.tax.service.gov.uk/hmrc-design-patterns/) - documentation for the use of `hmrc-frontend` components
