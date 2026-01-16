# Optional properties in viewmodels

* Status: rejected
* Date: 2025-01-16

## Context and Problem Statement

In `play-frontend-hmrc`, we are trying to create Scala-equivalent case classes of models defined as YAML for Nunjucks in 
`govuk-frontend`. We also do the same for `hmrc-frontend`, although in `hmrc-frontend` we have control over the structure 
of the model for Nunjucks.

In the `govuk-frontend` models, many of the properties are optional for the viewmodels. In `play-frontend-hmrc`, we have 
not agreed formally a preferred approach for this, and therefore we have inconsistent approaches to handling optional 
properties. It seems that the model inconsistencies have occurred due to different developers taking different approaches, 
all of which work technically but are not aligned conceptually.

There was a ticket to document the inconsistencies, and decide if this was worth further development work.

## Decision Drivers

* Are the current inconsistencies confusing for service developers?
* Are the current inconsistencies confusing for PlatUI developers?
* Can we make any changes in a non-breaking way?

## Considered Options

Option 1: Change the viewmodels so that optional properties are modelled consistently (this would be a major breaking change)
Option 2: Leave the viewmodels as they are, document how we should model components in the future
Option 3: Leave the viewmodels as they are, agree how to model optional properties next time there is a new component 

## Decision Outcome

Chosen option: Option 3: Leave the viewmodels as they are, agree how to model optional properties next time there is a 
new component.

Discussion in the team was that trying to align the models consistently now would be a major breaking change for services
developers. This is currently hard to jsutify as a cost in developer time, as we haven't got sufficient evidence that the
inconsistencies are causing problems for service developers. We **do not** know the the current situation is causing a
problem, but we **do** know that changing the models **would** be a change requiring lots of developer work in service 
teams. 

At the time, there will not be a team decision on how to model future components, because it would make sense to do this 
when we next have a new component.
