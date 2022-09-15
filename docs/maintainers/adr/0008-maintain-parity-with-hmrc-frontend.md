# Maintain 1-to-1 parity between govuk-frontend and play-frontend-govuk, and hmrc-frontend and play-frontend-hmrc

* Status: accepted
* Date: 2021-03-19

## Context and Problem Statement

`play-frontend-govuk` and `play-frontend-hmrc` provide Scala / Play / Twirl implementations of the components provided 
as Nunjucks in `govuk-frontend` and `hmrc-frontend`, using the assets provided by those libraries. How much should the 
play-frontend implementations diverge from their “base” repositories?

## Decision Drivers 

* Need to create frontend components that are easy to use by Scala developers on the platform
* Need to create a library that has a clear and quick upgrade path when new versions of `govuk-frontend` and 
  `hmrc-frontend` are released
* Need to have a robust testing strategy for library developers to have faith in when upgrading

## Considered Options

* Option 1: Provide a direct Scala implementation of the Nunjucks / YAML examples from `govuk-frontend` and 
  `hmrc-frontend`
* Option 2: Provide our own opinionated methods to create Scala versions of the frontend components that diverge from 
  the above
* Option 3: As per option 1, but with some additional helper methods added for removing repetition for teams

## Decision Outcome

Chosen option: Option 3, because it allows for quick and continuous upgrading to follow GDS and HMRC design system 
changes, allows for a robust testing strategy of multiple implementations of the templates thanks to YAML provided by 
GDS.

### Positive Consequences

* Design of case classes to follow GDS / HMRC design system means PlatUI as library maintainers do not have to create 
  viewmodel structure from scratch every time
* Adding new components can follow a clear and straightforward path
* Robust test strategy can be developed using Nunjucks components and Twirl templates using a parser

### Negative Consequences

* Feedback suggests that some developers do not find the API to be intuitive
* Separate decisions need to be made on handling multilingual support
* Enrichment of library needs to be done via separate Twirl helpers 
  (see https://github.com/hmrc/play-frontend-hmrc/blob/main/docs/maintainers/adr/0001-play-frontend-hmrc-mirrors-hmrc-frontend.md)

