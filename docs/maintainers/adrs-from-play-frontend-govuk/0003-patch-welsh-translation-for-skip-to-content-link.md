# Patch a welsh translation for skip to content link into govukTemplate

* Status: accepted
* Date: 2021-06-10

Technical Story: PLATUI-1206

## Context and Problem Statement

We previously decided to keep inputs and outputs of play-frontend-govuk components a 1-to-1 match with govuk-frontend 
nunjucks example implementations.

Generally components from govuk-frontend accept their copy as input, however the skip link in govukTemplate is 
hard-coded which is causing accessibility failures for services.

This is an accessibility failure because a page translated into welsh may cause assistive technology to mispronounce the 
content of the english skip link because it has no explicit language attribute.

## Decision Drivers

* Because it's an accessibility failure we want to fix this as a priority quickly
* And we would like for this fix to be available as widely as possible without requiring template changes from services
* At the same time we don't want to reduce test coverage / diverge from our current testing strategy

## Considered Options

* Option 1: apply our fix directly to govukTemplate
* Option 2: fork govukTemplate in play-frontend-hmrc

## Decision Outcome

We've chosen option 1 because unlike other components, we don't have any automatically generated fixtures or integration 
tests for govukTemplate. We maintain test fixtures for the templates manually in the additional fixtures folder. This 
makes it possible for us to patch this change into govukTemplate with minimal maintenance cost without reducing our test 
coverage / diverging far from our current testing strategy.

## Pros and Cons considered

### Option 1

* Good, because fix will apply whichever abstraction services are using (govukTemplate or govukLayout)
* Good, because fix won't need any template changes from services
* Bad, because we have diverged from govukTemplate which makes integrating upstream changes possibly more complicated

### Option 2

* Good, because we avoid introducing complexity by exception to our existing strategy 
* Bad, because it would require a template change from services after upgrading
* Bad, because we've increased the complexity for services by adding an additional choice to their development process
* Bad, because we would have more to maintain

## Links

* [ADR-0002](0002-maintain-parity-with-govuk-frontend.md) previously agreed implementation strategy