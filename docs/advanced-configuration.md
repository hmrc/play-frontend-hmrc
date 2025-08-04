[Back to README.md](../README.md)

# Advanced configuration

- [Adding your own SASS compilation pipeline](#adding-your-own-sass-compilation-pipeline)
- [Configuring non-HMRC projects to resolve play-frontend-hmrc artefacts](#configuring-non-hmrc-projects-to-resolve-play-frontend-hmrc-artefacts)
- [Using the Tudor Crown on GOV.UK and HMRC components](#using-the-tudor-crown-on-govuk-and-hmrc-components)

## Adding your own SASS compilation pipeline

This library will manage SASS compilation for you. However, should you need for any reason to add your own SASS
compilation pipeline, follow the [steps detailed here](docs/maintainers/sass-compilation.md).

## Configuring non-HMRC projects to resolve play-frontend-hmrc artefacts

HMRC services get this configuration via the [sbt-auto-build library](https://github.com/hmrc/sbt-auto-build/blob/1bb9f5437ed5c2027b4c967585a2dd9a9a6740d0/src/main/scala/uk/gov/hmrc/SbtAutoBuildPlugin.scala#L55), external consumers will need to add the repository below to their SBT config themselves:

```scala
resolvers += MavenRepository("HMRC-open-artefacts-maven2", "https://open.artefacts.tax.service.gov.uk/maven2")
```

## Using the Tudor Crown on GOV.UK and HMRC components

As of February 2024, there is a requirement for Government departments to use the Tudor Crown logo for HRH King Charles
III. This new logo has been added into both the [govuk-frontend](https://github.com/alphagov/govuk-frontend) and
[hmrc-frontend](https://github.com/hmrc/hmrc-frontend) libraries. Additionally, the `hmrc-frontend` library has an updated
HMRC Crest roundel incorporating the Tudor Crown.  The Tudor Crown is shown by default in `v8.5.0` and higher of `play-frontend-hmrc`.
