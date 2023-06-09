# Populate language parameter from implicit messages

* Status: accepted
* Date: 2020-03-11

Technical Story: PLATUI-531

## Context and Problem Statement

In [ADR-0001](0001-play-frontend-hmrc-mirrors-hmrc-frontend.md) we established
the principle that play-frontend-hmrc should mirror hmrc-frontend
in providing a pure Twirl port of the underlying hmrc-frontend Nunjucks library. We decided that the only
exception to that would be a helpers package containing wrappers that enhance the underlying components with
platform and Play framework aware functionality.

One common enhancement has been to populate the language parameter found on many of hmrc-frontend's
components with the language associated with the Messages object in implicit scope. Requiring consuming
services to manually provide this parameter is a form of repetitious boilerplate we are actively
aiming to minimise.

In PLATUI-531, a story to develop a version of govukCharacterCount supporting the Welsh
language, we faced the need to create and maintain a wrapper for hmrcCharacterCount whose sole purpose would be to supply
this parameter. Given [ADR-0003](0003-use-the-suffix-helpers-for-helper-components.md), this wrapper would have to be named
hmrcCharacterCountHelper, a rather non-intuitive name bearing in mind we would generally be expecting
teams to use this component rather than the more appropriately named hmrcCharacterCount.

We therefore made a joint decision to allow as an exception the passing through of an implicit Messages to Twirl
components, making them Play framework aware, as a pragmatic exception to allow us to maintain our current testing
strategy whilst not making a confusing experience for service teams using the library.

## Decision Drivers

* the need for component naming to be unsurprising and intuitive
* the need for components to justify their cost of maintenance
* play-frontend-hmrc should be Play-framework aware and not require manual wiring of parameters
that can be found in implicit scope
* the importance of maintaining our test strategy and for it not to be undermined.

## Considered Options

* Allow components to take an implicit Messages object and used to populate the language parameter
* Do nothing, i.e. continue to use the pattern of helpers even when the only additional parameter
is the implicit language

## Decision Outcome

Chosen option: "Allow components to take an implicit Messages object", because we can do this without
undermining our test strategy and doing so will improve the developer experience.

### Positive Consequences

* There will be a single hmrcCharacterCount component with a meaningful name. There is less potential for confusion around deciding which of two similarly named components to use. 
* The hmrcCharacterCount component and any future similar components will behave in a way that a Scala developer using the Play framework would expect in its use of internationalisation features.

* There would be no need to create and maintain a separate wrapper for hmrcCharacterCount and
future similar components.
* Naming clashes requiring the use of the `Helper` component name suffix minimised

### Negative Consequences

* hmrcCharacterCount would only be usable with a Messages object in implicit scope
* hmrcCharacterCount would have functionality not present in the underlying Nunjucks component,
requiring additional tests
* A decision has to be made regarding the logic if service teams also pass through a language -
which should take precedence? Is this intuitive?
* Having made an exception for language, we have made a pragmatic decision to reduce the purity
of the library vision, which could make it harder to argue against future creep of the library

## Links

* Refines [ADR-0001](0001-play-frontend-hmrc-mirrors-hmrc-frontend.md)
* Refines [ADR-0003](0003-use-the-suffix-helpers-for-helper-components.md)
