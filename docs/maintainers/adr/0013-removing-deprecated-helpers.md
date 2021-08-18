# Removing deprecated helpers

* Status: accepted
* Date: 2020-06-24

## Context and Problem Statement

Since launching `play-frontend-hmrc` we have included deprecated static helpers which were required for Play 2.5 projects.

## Decision Drivers

* There is complexity involved in maintaining multiple interfaces for the same behaviour
* these helpers have been deprecated for 2 years, they were only added originally for Play 2.5 users
* the platform opinion is to use DI
* soft advice from Play has been to use DI since Play 2.6 (https://www.playframework.com/documentation/2.8.x/ScalaDependencyInjection)
* Guice DI is completely standard in the Play Framework and not new - all services will be using Guice
* some teams may regard the deprecation notices as small print

## Considered Options

* Do nothing, keep supporting static helpers alongside DI 
* Remove static helpers and only support usage via DI 

## Decision Outcome

* we should not re-instate static helpers for Play 2.8
* we should remove them in version 1.0.0
* we should publish a blog article sooner rather than later that we will be removing them in 1.0.0

### Positive Consequences

* Our codebase becomes easier to maintain
* We have to use DI in our tests which makes our tests more accurate to how these components will be used

### Negative Consequences

* Anyone who is using the static helpers will have to update - that's why we're including this in version 1.0.0