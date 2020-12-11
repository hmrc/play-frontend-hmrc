# Use Markdown Architectural Decision Records (MADRs)

* Status: accepted
* Date: 2020-11-02

## Context and Problem Statement

ADRs help newcomers to a project understand the rationale behind past decisions.
Without this understanding they may either:
* blindly accept the decision or
* blindly change it.

If a development team blindly accepts too many decisions it ‘becomes too 
afraid to change anything and the project collapses under its own weight’ (Nygard, 2011).
On the other hand, blinding changing decisions may result in damage or inadvertent introduction
of technical debt.

By recording [architecturally significant](https://docs.arc42.org/tips/9-1/) decisions,
* new team members are able to get up to speed quickly
* external stakeholders can quickly get an overview of the project's architecture and
rationale behind it
* development teams gain the ability to revisit past decisions, decide whether the rationale behind them still 
makes sense and, if appropriate, change course safely while keeping the project lean and conceptually 
coherent.

We want to record architectural decisions made in this project. Which format and structure should these records follow?

## Decision Drivers

* The need to keep the process lightweight
* The need for the ADRs to be readable
* The desire to keep the ADRs close to the code
* Concern around burdening the team with having to provide excessive justification for past decisions
* The desire to keep this methodology primarily as an internal tool to benefit the team in improving the 
quality of its decision-making and not as an ‘approval gate’ used by external stakeholders to
hold the team to account.

## Considered Options

* [MADR](https://adr.github.io/madr/)
* [Michael Nygard's template](http://thinkrelevance.com/blog/2011/11/15/documenting-architecture-decisions)
* [Y-Statements](https://medium.com/olzzio/y-statements-10eb07b5a177)

## Decision Outcome

Chosen option: "MADR", because
* Y-statements are too hard to read
* MADRs break down consequences into positive and negatives
* considered options is a required field.

We will pilot the approach with [hmrc/contact-frontend](https://www.github.com/hmrc/contact-frontend)
and [hmrc/hmrc-deskpro](https://www.github.com/hmrc/hmrc-deskpro), storing
the ADRs in the contact-frontend repository. We will follow the rule of thumb of using
 the frontend repository for a multi-repository service as the location for the ADRs.
 
In order to keep it lightweight, we will only fill in the optional fields in the MADR template if 
they are essential to the meaning of the ADR. 

## Pros and Cons of the Options

See https://www.github.com/hmrc/platui-adr-spike
