# Parsing and code generation within `play-frontend-hmrc` and `x-govuk-component-renderer`

## Overview
As mentioned in [Repository Maintenance Overview](overview.md) and [Upgrading play-frontend-hmrc](upgrading.md), the
tests within `play-frontend-hmrc` rely on generating HTML code based on the Nunjucks and YAML in `govuk-frontend` and 
`hmrc-frontend`.

## Use cases

### Generating unit tests fixtures

The HTML of the test fixtures is not strictly generated within `play-frontend-hmrc`. Rather than are rendered within 
x-govuk-component-renderer and are returned as a single blob of JSON from `x-govuk-component-renderer`.

```mermaid
    sequenceDiagram
        play-frontend-hmrc->>x-govuk-component-renderer: generateGovukFixtures / generateHmrcFixtures <br> GET /snapshot/$frontend/$version
        x-govuk-component-renderer->>hmrc-frontend / govuk-frontend: retrieve YAML examples from <br> files in node_modules
        hmrc-frontend / govuk-frontend->>x-govuk-component-renderer: 
        x-govuk-component-renderer->>x-govuk-component-renderer: parse YAML examples into JSON
        x-govuk-component-renderer->>play-frontend-hmrc: return single JSON blob of <br> frontend library examples
        play-frontend-hmrc->>play-frontend-hmrc: write JSON examples to <br> local fixtures directory
```

The above task is run as a one-off when upgrading. Once the test fixtures have been generated for a release, the 
`x-govuk-component-renderer` is NOT called during the run of the tests via `sbt test`.

### Running integration tests

For integration tests, the Scalacheck library is used within `play-frontend-hmrc` to dynamically generate Scala viewmodel
instances. For each generated viewmodel, the Scala is then converted to JSON and `POST`-ed to `x-govuk-component-renderer`.
`x-govuk-component-renderer` will return a JSON containing the HTML rendered by the Nunjucks template for those JSON values,
which the integration test will compare to the HTML generated by the Scala viewmodel and Twirl template. The dynamically
generated HTML for both Nunjucks and Twirl templates is not persisted once an individual test has run.

```mermaid
    sequenceDiagram
        play-frontend-hmrc->>play-frontend-hmrc: sbt it/test
        play-frontend-hmrc->>play-frontend-hmrc: generate Scala viewmodel for an <br>integration test using Scalacheck
        play-frontend-hmrc->>x-govuk-component-renderer: Convert Scala to JSON <br> POST /component/$libraryName/$libraryVersion/$componentName
        x-govuk-component-renderer->>hmrc-frontend / govuk-frontend: retrieve Nunjucks template from <br> files in node_modules
        hmrc-frontend / govuk-frontend->>x-govuk-component-renderer: 
        x-govuk-component-renderer->>x-govuk-component-renderer: render Nunjucks template <br> with parameter
        x-govuk-component-renderer->>play-frontend-hmrc: return JSON containing HTML 
```
