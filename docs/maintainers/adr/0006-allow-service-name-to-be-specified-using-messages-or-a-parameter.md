# Allow service name to be specified using messages or a parameter

* Status: accepted
* Date: 2021-02-08

Technical Story: PLATUI-905

## Context and Problem Statement

Nearly all services running on MDTP need to include a link to their landing page in the 
GOV.UK header. Exceptionally, frontend microservices may either need to omit the service name
or be able to configure it based on the path. For example, in the case of shared frontend
microservices (contact-frontend, accessibility-statement-frontend) or microservices that host 
more than one public-facing service (view-external-guidance-frontend, gform-frontend).
  
When integrating with play-frontend-hmrc we want to 
minimise boilerplate for service teams while providing flexibility to cope with edge 
cases.

Services using the govukLayout component in play-frontend-govuk and not overriding the `headerBlock`
parameter, specify the service name in the
`service.name` message in `conf/messages`, providing any Welsh translation in `conf/messages.cy`.
Based on a Github search, most services overriding the headerBlock with, for example, `hmrcHeader`
or `govukHeader` are passing the service name from `messages('service.name')`. However, this
mechanism is awkward for services not requiring a service name – they have to override with a blank message – 
and services needing more than one are unable to use this functionality. This pattern
assumes a 1:1 correspondence between frontend microservices and public-facing
tax services.

Should we continue to support this pattern to reduce boilerplate for the majority of frontend
services, insist on explicitly passing the service name or support some combination of the above?

## Decision Drivers

* Minimising of boilerplate and lines of code needed in frontend microservices
* Providing flexibility for frontend microservices that do not have a 1:1 correspondence with
public-facing services.
* The need to keep things simple and unsurprising

## Considered Options

* Option 1: hard-wire the service name from the `service.name` message. An empty or omitted message results
 in no service name rendered in the GOV.UK header.
* Option 2: hard-wire the service name from messages but allow it to be overridden by a `serviceName` parameter of type `Some[String]`.
Passing None results in the service name taken from messages.
* Option 3: support specifying the service name with a `serviceName` parameter of type `Some[String]` only. Passing None, the default, 
results in a blank service name.

## Decision Outcome

Chosen option 2 because reducing boilerplate for the majority of services is important, we should support
more unusual services and not dictate an architecture that assumes a 1:1 correspondence
between frontend microservices and public-facing services.
