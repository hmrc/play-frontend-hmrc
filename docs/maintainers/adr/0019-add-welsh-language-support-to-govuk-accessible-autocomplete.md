# Add Welsh language support to the GOVUK accessible autocomplete wrapper

* Status: accepted
* Date: 2023-11-09

Technical Story: PLATUI-2638

## Context and Problem Statement

In PLATUI-1830 we added an implicit HMRC helper to wire up a `select` element to the
[GOVUK accessible-autocomplete](https://github.com/alphagov/accessible-autocomplete) component.
This was intended as a minimal implementation, to prevent teams having to reimplement their own divergent solutions.
It was later realised that, as with other GOVUK components, the accessible-autocomplete only provides content in English.
Now that GOVUK have modified the component to expose an internationalisation API, we had the option to add Welsh translations.
Translations have been provided by the accessible-autocomplete wrapper in hmrc-frontend, which now exposes a data-language attribute.

## Decision Drivers

* The need for services to consistently provide Welsh content, for compliance with the
[WCAG Language of Parts](https://www.w3.org/WAI/WCAG21/Understanding/language-of-parts.html) success criteria 
* The need for components to justify their cost of maintenance

## Considered Options

* Do nothing, and leave service teams to provide the language parameter to the component
* Add idiomatic Play language support to the HMRC accessible-autocomplete wrapper in this library

## Decision Outcome

Chosen option: "Add idiomatic Play language support to the HMRC accessible-autocomplete wrapper in this library",
because we can easily do this in a way that is consistent with language support in other components,
and doing so will reduce the work required from service teams.

### Positive Consequences

* The `asAccessibleAutocomplete` extension method in `RichSelect` will be language-aware via Play's `Messages`,
so services can continue using the provided accessible-autocomplete component and get Welsh translations for free
* Services will not fail DAC assessments due to missing Welsh translations, reducing the cost of re-engineering and re-testing

### Negative Consequences

* Using `asAccessibleAutocomplete` now requires a `Messages` object in implicit scope, which could be breaking,
but in practice most views will already have a `Messages` object
* Expectations may be set that we will update our accessible-autocomplete wrapper to support any future enhancements,
which may or may not be the case

## Links

* Relates to [ADR-0016](0016-use-implicit-messages-to-drive-welsh-translations-in-govuk-twirl-components.md)
