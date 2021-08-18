# Add helper method to lift Play form Field values in GovukRadios

* Status: accepted
* Date: 2021-02-12

Technical Story: PLATUI-296

## Context and Problem Statement

- `play-fronted-govuk` library is not Play Framework aware
- Adding helper methods to the library to utilise Play Framework features will reduce repetition in service code

## Decision Drivers
- Reducing repetition and boilerplate is not only good practice generally, but will important in helping service teams
  migrate off older frontend libraries to `play-frontend-govuk` and `play-frontend-hmrc`
- `play-frontend-govuk` is intended to be a direct port of `govuk-frontend`, and so helpers should live in an
appropriate helper class alongside the view models 
- We would prefer to implicit classes rather than directly modifying the viewmodel case classes, as the viewmodel classes are derived from the govuk-frontend API 
- We want to avoid replicatign the `govuk-frontend` parameter lists in the helpers, to keep the overhead of upgrading the library low

## Considered Options

### Option 1
Add a `.fromField` method to the `Radios` case class that converts a set of parameters including the `Field` to a regular `Radios` case class 

Pros:
- Eliminates need to pass value, errors, id and name
- Simplified interface, less boilerplate

Cons:
- Case class wrapping parameters
- Requires us to keep parameter list in sync with gov.uk and write tests for that
- Doesn't support rich `RadioItems`

### Option 2
Introduce a `radiosHelper.scala.html` Twirl component.
This is similar to the `InputRadios` component in contact-frontend

Pros:
- Eliminates need to pass value, errors, id and name
- No case class wrapping parameters
- Simplified interface, minimal boilerplate

Cons:
- Case class wrapping parameters
- Requires us to keep parameter list in sync with gov.uk and write tests for that
- Doesn't support rich RadioItems

### Option 3
Replace `Radios` with `SimpleRadios`. `SimpleRadios` is an object with an apply method that generates a regular Radios

Pros:
- Eliminates need to pass value, errors, id and name
- Simplified interface, less boilerplate

Cons:
- Case class wrapping parameters
- Requires us to keep parameter list in sync with gov.uk and write tests for that
- Doesn't support rich `RadioItems`

### Option 4
Add a new `radiosWithField.scala.html` template that takes in a `Field` object alongisde a `Radio` object, and maps values from the `Field` into the `GovukRadios` template

Pros:
- Removes some boilerplate for teams
- Means that logic is not repeated between `Form` and template, i.e. makes better use of Play objects already created
- Maps error message, id, name, and checked, from `Field` to template

Cons:
- Unfortunately not able to add additional constructor method to `Radios`, so this interface feels somewhat clunky
- Reasonable amount of logic takes place within `RadiosWithField` template, which has limitations about how much Scala code can be added there
- Not adding as much value as we would like

### Option 5
Add an implicit class `RichRadios`, which adds a `.withFormField` to `Radios`. This creates a builder pattern taking 
in a `Field` and returns an enhanced copy of the `Radios` item.

Pros:
- Extends existing pattern within library of adding implicit extension methods for richness
- Allows `Radios` to remain as direct port of `govuk-frontend` without diverging with new methods
- Adds some useful information, specifically: name, idPrefix, errors, and checked, to a `Radios` or `RadioItem`

## Decision Outcome

Chosen option: Option 5, because it adds useful functionality (class enrichment of `Radios` via implicit 
`RichRadios`, without adding new Twirl templates to maintain, and without adding new methods directly 
to `Radios`, which would cause a divergence between `play-frontend-govuk` and `govuk-template` (undesirable).

### Positive Consequences

* Adds useful optional class enrichment to `Radios`
* Reduces code repetition between `Field` and `Radios` (DRY)
* Pattern is extensible, i.e. similar implicit helpers can be added to other form inputs

### Negative Consequences

* Doesn't add as much value as we would like
* Need to document carefully what behaviour occurs when information provided via both `Radios` and 
  `Field`, i.e. which takes precedence (currently intended to be `Radios` values take precendence)
* Need to document to teams that this helper is available (service developers won't use it if they don't know it's there)
