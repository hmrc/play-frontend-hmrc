# Repository Maintenance Overview

## Table of Contents

- [Testing](#testing)
- [Upgrading](#upgrading)
- [Translation Decisions](#translation-decisions)
- [Play Versioning](#play-versioning)
- [Useful Links](#useful-links)

## Testing

### Unit Tests

The unit tests work against two sets of fixtures. 

The first set in `src/test/resources/fixtures/test-fixtures` are derived from data extracted 
from [govuk-frontend's yaml documentation](https://github.com/alphagov/govuk-frontend/blob/master/src/govuk/components/button/button.yaml)
for each component. The yaml examples are used in `govuk-frontend`'s own unit test suite.

An additional, manually created, set of fixtures in `src/test/resources/fixtures/additional-fixtures` captures test
cases that are not covered by the published examples. For example, the layout and template components
do not have published examples in `govuk-frontend` so they are placed in this directory.

To regenerate the test fixtures, you will need the template renderer running locally (see below). 
Then run ```sbt generateUnitTestFixtures```. The template renderer does not need to be running while the unit
tests are executing.

### Integration Tests (Generative Testing)

To ensure (as much as possible) that the implemented templates conform to the `govuk-frontend` templates, we use generative
testing, via `scalacheck`, to compare the `Twirl` templates output against the `Nunjucks` `govuk-frontend` templates.
 
These tests run against a `node.js` based [service](https://github.com/hmrc/x-govuk-component-renderer) which renders the `govuk-frontend` `Nunjucks` templates as Hmtl snippets.
In order to run these tests locally this service will need to be run manually. 

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
the distribution of the test cases. More information about collecting statistics on generators [here](https://github.com/typelevel/scalacheck/blob/master/doc/UserGuide.md#collecting-generated-test-data)._

The integration tests are automatically run as part of the build and publish pipeline with the component renderer 
run as a sidecar. For this reason, it's important to merge any changes to the component renderer before merging corresponding
changes to the library.

#### Reproducing Failures (Deterministic Testing)
In case of a test failure, the test reporter outputs a `org.scalacheck.rng.Seed` encoded in Base-64 that can be passed back to the failing test to reproduce it.
More information about this feature [here](https://gist.github.com/non/aeef5824b3f681b9cfc141437b16b014).

Ex:
```scala
object govukBackLinkTemplateIntegrationSpec
    extends TemplateIntegrationSpec[BackLink](
      govukComponentName = "govukBackLink", seed = Some("Aw2mVetq64JgBXG2hsqNSIwFnYLc0798R7Ey9XIZr6M=")) // pass the seed and re-run
```

Upon a test failure, the test reporter prints out a link to a diff file in `HTML` to easily compare the
markup for the failing test case against the expected markup. The diff is presented as if the `original` file was
the Twirl template output and the `new` file was the Nunjucks template output (expected result). 

```scala
Diff between Twirl and Nunjucks outputs (please open diff HTML file in a browser): file:///Users/foo/dev/hmrc/play-frontend-govuk/target/govukBackLink-diff-2b99bb2a-98d4-48dc-8088-06bfe3008021.html
```

### Running all tests for all supported Scala and Play versions

Prior to merging to master, it is a good idea to run all the tests against all supported versions of Scala and Play.
These checks will also be performed automatically as part of the build pipeline before publishing.

To achieve this, run:

```bash
PLAY_VERSION=2.5 sbt clean +test +it:test && \
PLAY_VERSION=2.6 sbt clean +test +it:test && \
PLAY_VERSION=2.7 sbt clean +test +it:test
```

## Upgrading

[This guide](/docs/maintainers/upgrading.md) describes the process of updating the library when a new version of `hmrc-frontend` is released. 

## Translation Decisions

When writing a new template from an existing `Nunjucks` template it is necessary to make a few translation decisions.

1. validation:

   The lack of validation in the `govuk-frontend` `Nunjucks` templates sometimes poses some difficulties and it is better to
   [raise issues](https://github.com/alphagov/govuk-frontend/issues/1557) to confirm assumptions about the validity of parameters
   that could break parity of features.
  
   That said, some Twirl components in the library add validation by using `scala` assertions such as 
   [require](https://www.scala-lang.org/api/current/scala/Predef$.html#require(requirement:Boolean,message:=%3EAny):Unit),
   which means **`Play` controllers should be handling potential `IllegalArgumentException`** thrown from views.

1. representing required and optional parameters (as documented in the `yaml` for a component in `govuk-frontend`):
   
   In some instances a parameter is documented incorrectly as `required` when it is `optional`, so the disambiguation comes
   from looking at its usage in the template.
   We opted to map optional parameters as `Option` types because it is the most natural mapping.
   
1. mapping from untyped `Javascript` to `Scala`:

   `Javascript` makes liberal use of boolean-like types, the so called `truthy` and `falsy` values.
   Special care is needed to translate conditions involving these types correctly.
   
   Ex: the following `Nunjucks` snippet where name is a `string` 
   
   ```nunjucks
   {% if params.name %} name="{{ params.name }}"{% endif %}
   ``` 
   
   would not render anything if `name` was `""` since it is a [falsy value](https://developer.mozilla.org/en-US/docs/Glossary/Falsy).
    
    It can be mapped to `name: Option[String]` in `Scala` and translated to `Twirl` as: 
   ```scala
   @name.filter(_.nonEmpty).map { name => name="@name" }
   
   // instead of the following which would render `name=""` 
   // if name had the value Some("")
   @name.map { name => name="@name@ }
   ```
   
   Another example is the representation of `Javascript`'s `undefined`, which maps nicely to `Scala`'s `None`.
   The need to represent `undefined`  sometimes gives rise to unusual types like `Option[List[T]]`.
   The most correct type here would be `Option[NonEmptyList[T]]` but we opted not to use [refinement types](https://github.com/fthomas/refined) yet.
   
   To date, the following conventions have been followed for view model case class fields:
   
   * `classes` are of type `String`, normally defaulting to `""`, rather than `Option[String]` defaulting to `None`
   * boolean attributes are `Option`-wrapped only if the Nunjucks template distinguishes between `null`/`undefined` 
     values and `true`/`false` values
   * `id` can be either `Option[String]` or `String` - FIXME
   
## How to create a new ADR

1. Install [Node](https://nodejs.org/en/download/) if you do not have this already. Node includes
   npm.

1. Install `adr-log` if you do not have this already

    ```shell script
    npm install -g adr-log
    ```

1. Copy [template.md](adr/template.md) as NNNN-title-of-decision.md, and fill
   in the fields. Do not feel you have to fill in all the fields, only fill in fields
   that are strictly necessary. Some decisions will merit more detail than others.

1. To re-generate the table of contents, run

    ```shell script
    ./generate-adl.sh

## Useful Links
- [x-govuk-component-renderer](https://github.com/hmrc/x-govuk-component-renderer) - service that returns HTML for `govuk-frontend` and `hmrc-frontend` component input parameters in the form of JSON objects - useful for confirming Twirl HTML outputs in integration tests
- [govuk-frontend](https://github.com/alphagov/govuk-frontend/) - reusable Nunjucks HTML components from GOV.UK
- [GOV.UK Design System](https://design-system.service.gov.uk/components/) - documentation for the use of `govuk-frontend` components
- [hmrc-frontend](https://github.com/hmrc/hmrc-frontend/) - reusable Nunjucks HTML components for HMRC design patterns
- [play-frontend-hmrc](https://github.com/hmrc/play-frontend-hmrc/) - Twirl implementation of `hmrc-frontend` components
- [HMRC Design Patterns](https://design.tax.service.gov.uk/hmrc-design-patterns/) - documentation for the use of `hmrc-frontend` components
