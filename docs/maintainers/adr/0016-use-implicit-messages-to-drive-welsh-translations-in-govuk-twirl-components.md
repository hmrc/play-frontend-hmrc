# Use implicit messages to drive Welsh translations in GOVUK Twirl components

* Status: accepted
* Date: 2020-06-08

Technical Story: PLATUI-2344

## Context and Problem Statement

In [ADR-0001](0001-play-frontend-hmrc-mirrors-hmrc-frontend.md) we established
the principle that play-frontend-hmrc should mirror hmrc-frontend
in providing a pure Twirl port of the underlying hmrc-frontend Nunjucks library. We decided that the only
exception to that would be a helpers package containing wrappers that enhance the underlying components with
platform and Play framework aware functionality.

In PLATUI-2344, we needed to add Welsh translations for the default content ("Success" or "Important") in the
[GovukNotificationBanner](/src/main/twirl/uk/gov/hmrc/govukfrontend/views/components/GovukNotificationBanner.scala.html)
component, to save teams having to handle translation concerns in their own code and then pass in the translated title.

We therefore made a joint decision to allow as an exception the passing through of an implicit Messages to Twirl
components, making them Play framework aware, as a pragmatic exception to allow us to maintain our current testing
strategy whilst not making a confusing experience for service teams using the library.

This is similar to the decision made in [ADR-0007](0007-bind-implicit-language-to-twirl-component.md),
but for GOVUK components, which do not take a `lang` parameter but instead contain hardcoded English content. 

## Decision Drivers

* The need for component naming to be unsurprising and intuitive
* The need for components to justify their cost of maintenance
* play-frontend-hmrc should be Play-framework aware and not require manual wiring of parameters
that can be found in implicit scope
* The importance of maintaining our test strategy and for it not to be undermined

## Considered Options

* Allow components to take an implicit Messages object and use this to switch between English and Welsh default content in the GOVUK template
* Create new Play-aware helpers which wrap each GOVUK component, and pass the translated text via existing parameters
* Create new Play-aware helpers which wrap each GOVUK component, and update GOVUK components to expose new parameters for content that requires translation

## Decision Outcome

Chosen option: "Allow components to take an implicit Messages object", because we can do this without
undermining our test strategy and doing so will improve the developer experience.

### Positive Consequences

* The `GovukNotificationBanner` will be language-aware via Play's `messages`, so teams won't have to handle translation except for bespoke content
* The `GovukNotificationBanner` component and any future similar components will behave in a way that a Scala developer using the Play framework would expect in its use of internationalisation features
* There is no need to create and maintain a separate wrapper for `GovukNotificationBanner` and other similar components
* Naming clashes requiring the use of the `Helper` component name suffix are minimised
* Existing GOVUK-example-based tests remain valid

### Negative Consequences

* `GovukNotificationBanner` is only usable with a Messages object in implicit scope
* `GovukNotificationBanner` has functionality not present in the underlying Nunjucks component, requiring additional tests

## Links

* Refines [ADR-0001](0001-play-frontend-hmrc-mirrors-hmrc-frontend.md)
* Relates to [ADR-0007](0007-bind-implicit-language-to-twirl-component.md)
