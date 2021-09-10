# Introduction of implicit conversions

* Status: accepted
* Date: 2021-09-09

Technical Story: PLATUI-1035

## Context and Problem Statement

Frequently occurring usage patterns of play-frontend-hmrc components require repetitious boilerplate
involving excessively nested case class instantiations. This has driven many teams to create wrappers to 
simplify the construction of components, which are then copied and pasted from service to service.

For example, in order to add a legend to a GovukRadio the following boilerplate is required:

```scala
Some(Fieldset(
 legend = Some(Legend(
   content = Text("message.key.for.legend"),
   classes = "govuk-fieldset__legend--l",
   isPageHeading = true
 ))
))
```

In the above example, the only thing that changes between instantiations is the `message.key.for.legend`, everything
else remains the same.

Should we solve this problem through the introduction of implicit conversions that will automatically wrap simple
objects such as Strings with the additional boilerplate necessary for commonly occurring cases?  

## Decision Drivers

* The need for API consistency in play-frontend-hmrc.
* The mixed sentiments towards implicit conversions in the Scala community and slight risk the feature may be removed 
  entirely in a future Scala version:
  * See https://www.rallyhealth.com/coding/implicit-implications-part-2-implicit-conversions,
    https://contributors.scala-lang.org/t/can-we-wean-scala-off-implicit-conversions/4388
* The risk of unintended side effects if these conversions are added unilaterally 
  without service developers needing to specifically opt in to them.
* The repeated invocation of the `Messages` apply method to populate component content parameters, e.g. 
  Hint, Key, Label, Legend.
* The difficulty of debugging code involving implicit conversions without turning on advanced IDE features that
  show implicit hints.

## Considered Options

* Option 1: tackle via extension methods and helpers without introducing implicit conversions
* Option 2: tackle via implicit conversions that, for example, could allow a string to be used in place of a 
  deeply nested case class.
* Option 3: as option 1 but with additional message key lookups.
* Option 4: as option 2 but with additional message key lookups.

## Decision Outcome

Chosen option: Option 1, because it solves the problem, is consistent with how we have been doing things, maintains
the internal consistency of play-frontend-hmrc and has less risk of breaking services.

## Pros and Cons of the Options

### Option 1

* Good, because there is less risk of unintended negative side effects
* Good, because no requirement to add additional import into every module
* Good, because it solves the problem using mechanisms we are already using
* Good, because we are not introducing a feature that would be difficult to remove
  at a later date.
* Good, because the conversions are explicit and easier to debug and intuit

### Option 2

* Good, because there is less boilerplate needed to instantiate components.
* Bad, because it makes code harder to debug and intuit.
* Bad, because there is a risk of unintended and unexpected side-effects that may be difficult to debug.
* Bad, because we do not have implicit conversions anywhere else in our library so this would be a change of direction.

### Option 3

* Good, for the same reasons as option 1 
* Bad, for the same reasons as option 1
* Good, because less boilerplate required in services
* Bad, because it makes the play-frontend-hmrc API inconsistent and harder to work with. It's less obvious if
a message key will be de-referenced without looking it up.

### Option 4

* Good, for the same reasons as option 2
* Bad, for the same reasons as option 2
* Good, because least boilerplate required in services
* Bad, because it makes the play-frontend-hmrc API inconsistent and harder to work with. It's less obvious if
  a message key will be de-referenced without looking it up.
