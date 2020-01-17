# Repository Maintenance Overview

## Table of Contents

- [Testing](#testing)
- [Upgrading](#upgrading)
- [Translation Decisions](#translation-decisions)
- [Play Versioning](#play-versioning)
- [Useful Links](#useful-links)

## Testing

### Unit Tests

The suite of unit tests runs against a set of test fixtures with data extracted from [hmrc-frontend's yaml documentation](https://github.com/hmrc/hmrc-frontend/blob/master/src/components/page-heading/page-heading.yaml)
for each component. The yaml examples are used in `hmrc-frontend`'s own unit test suite. 

The test fixtures are generated from the release of `hmrc-frontend` [used in the library](/README.md#sbt). 
A script [TODO: document script] generates the fixtures which are manually included in the resources directory under `src/test/resources/fixtures/`.
The unit tests will pick up the fixtures folder matching the version of `hmrc-frontend` in the dependencies.

_Future work: Unit testing the library using a fixed set of test data provides only very basic coverage. if we can improve 
the test coverage via generative testing described on the next section, we could discard the test fixtures completely._ 

### Generative Testing

To ensure (as much as possible) that the implemented templates conform to the `hmrc-frontend` templates, we use generative
testing, via `scalacheck`, to compare the `Twirl` templates output against the `Nunjucks` `hmrc-frontend` templates.
 
The tests run against a `node.js` service used to render the `hmrc-frontend` `Nunjucks` templates,
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
the distribution of the test cases. More information about collecting statistics on generators [here](https://github.com/typelevel/scalacheck/blob/master/doc/UserGuide.md#collecting-generated-test-data)._

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

## Upgrading

[This guide](/docs/maintainers/upgrading.md) describes the process of updating the library when a new version of `hmrc-frontend` is released. 

## Translation Decisions

When writing a new template from an existing `Nunjucks` template it is necessary to make a few translation decisions.

1. validation:

   There is a lack of validation in `Nunjucks` templates to consider when translating `hmrc-frontend` components, but it is important to retain high parity of features.
  
   That said, some Twirl components in the library add validation by using `scala` assertions such as 
 [require](https://www.scala-lang.org/api/current/scala/Predef$.html#require(requirement:Boolean,message:=%3EAny):Unit),
  which means **`Play` controllers should be handling potential `IllegalArgumentException`** thrown from views.

2. representing required and optional parameters (as documented in the `yaml` for a component in `hmrc-frontend`):
   
   Parameters may not always be documented comprehensively as `required` or `optional`, so the disambiguation comes
   from looking at its usage in the template.
   We opted to map optional parameters as `Option` types because it is the most natural mapping.
   
3. mapping from untyped `Javascript` to `Scala`:

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

## Play Versioning

### Play 2.5 / Play 2.6 Cross-Compilation

Regarding [dependency injection for templates](https://www.playframework.com/documentation/2.6.x/ScalaTemplatesDependencyInjection), `Play 2.6`
introduced breaking changes in the syntax of `Twirl` templates.  For this reason, for every `Play 2.6` template implementing a component, we have
 to provide an almost identical `Play 2.5-compatible` template, differing only in the dependency injection declaration.

To automate this manual effort, the library uses an `sbt` task to auto-generate the `Play 2.5` templates from the `Play 2.6` ones:

```sbt
lazy val generatePlay25TemplatesTask = taskKey[Seq[File]]("Generate Play 2.5 templates")
```
  
* this task is a dependency for `twirl-compile-templates` in both `Compile` and `Test` configurations
* the auto-generated `Play 2.5` templates are not version controlled and should not be edited

### Naming Conventions for Injected Templates in Play 2.6

The automatic generation of `Play 2.5` templates works by stripping out the `@this` declaration
from a `Play 2.6` template.
This means that the name of an injected template should match the name of the `Twirl` template file that
implements it.

Ex: Given a hypothetical new component injecting `HmrcInput` we should name the parameter `hmrcInput`.
When the `Play 2.5` auto-generated template gets compiled it is able to find the `hmrcInput` object
that implements the template (defined in the file `hmrcInput.scala.html`).
```scala
@this(hmrcInput: HmrcInput)

@()
@hmrcInput(<params for hmrcInput template>)
```  

The auto-generated `Play 2.5` template will be:
```scala
@()
@hmrcInput(<params for hmrcInput template>)
```

`hmrcInput` is the name of the Scala object that implements the compiled `hmrcInput.scala.html` template.
Had we named the injected component something else, for example `input`, the auto-generated template would fail to compile
since there is no template named `input.scala.html`.

### Backwards Compatibility in Play 2.6 Templates

Due to the aforementioned differences between the `Twirl` compilers in `Play 2.5` and `Play 2.6` and the auto-generation
feature, templates should not be written with backwards incompatible features only introduced in `Play 2.6`, such as
[@if else if](https://github.com/playframework/twirl/issues/33).   

## Useful Links
- [x-frontend-snapshotter](https://github.com/dorightdigital/x-frontend-snapshotter) - provides static test fixtures for `govuk-frontend` and `hmrc-frontend` components in unit tests
- [x-govuk-component-renderer](https://github.com/hmrc/x-govuk-component-renderer) - service that returns HTML for `govuk-frontend` and `hmrc-frontend` component input parameters in the form of JSON objects - useful for confirming Twirl HTML outputs in integration tests
- [hmrc-frontend](https://github.com/hmrc/hmrc-frontend/) - reusable Nunjucks HTML components for HMRC design patterns
- [HMRC Design Patterns](https://design.tax.service.gov.uk/hmrc-design-patterns/) - documentation for the use of `hmrc-frontend` components
- [govuk-frontend](https://github.com/alphagov/govuk-frontend/) - reusable Nunjucks HTML components from GOV.UK
- [play-frontend-govuk](https://github.com/hmrc/play-frontend-govuk/) - Twirl implementation of `govuk-frontend` components
- [GOV.UK Design System](https://design-system.service.gov.uk/components/) - documentation for the use of `govuk-frontend` components
