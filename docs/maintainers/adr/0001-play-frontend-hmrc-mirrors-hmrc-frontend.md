# play-frontend-hmrc mirrors hmrc-frontend

* Status: accepted
* Date: 2020-10-19

## Context and Problem Statement

The need to provide an HMRC footer translated into Welsh (PLATUI-752)

## Decision Drivers

* the lack of support for Welsh in the GDS govukFooter 
component
* the desire to maintain play-frontend-hmrc as a pure port of hmrc-frontend
with some non-presentational helpers to aid use in Scala/Play - conceptual integrity
* the desire to maintain hmrc-frontend as the source of truth for all presentational
markup - separation of concerns

## Considered Options

* Add localised content into the existing hmrcFooter helper in play-frontend-hmrc, see 
  [https://github.com/hmrc/play-frontend-hmrc/pull/25](https://github.com/hmrc/play-frontend-hmrc/pull/25)
* Maintain parity between hmrc-frontend and play-frontend-hmrc
  except for helpers that will not contain significant 
  presentational markup

## Decision Outcome

Chosen option "maintain parity" because we want hmrc-frontend to be the source of truth for presentation and
 maintain the separation of concerns between hmrc-frontend and 
 play-frontend-hmrc

### Positive Consequences

* Maintain separation of concerns between hmrc-frontend and play-frontend-hmrc
* Maintain conceptual integrity for play-frontend-hmrc
* Be able to test markup using existing test strategy

### Negative Consequences

* We will need to add and maintain a new hmrcFooter component in
hmrc-frontend and play-frontend-hmrc providing a mirror of govukFooter with localised content,

* The new hmrcFooter component in hmrc-frontend will not be able to make use of any I18n features,

* We will need to create a new `helpers` package within play-frontend-hmrc to clearly demarcate them
from presentational components,

* We will need to redesign the existing hmrcFooter helper to wrap the new hmrcFooter component
 and move to the helpers package,

* The new hmrcFooter component will need to be deprecated when GDS provide a localised version
of govukFooter,

* We will need to liaise and get approval from the HMRC design system team for adding
hmrcFooter to the hmrc/design-system and hmrc/hmrc-frontend

* We will be adding features that are unlikely to be useful or used by designers because
 at the prototyping phase content is not stable enough for translation into Welsh
