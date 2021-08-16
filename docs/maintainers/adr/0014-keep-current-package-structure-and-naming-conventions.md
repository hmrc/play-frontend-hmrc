# Keep current package structure and naming conventions

* Status: accepted
* Date: 2021-08-12

Technical Story: PLATUI-1294

## Context and Problem Statement

In the context of the first major version release of play-frontend-hmrc, are there any fundamental
changes we would like to make to the package structure and naming conventions of the library?

## Decision Drivers

* Major changes are painful but will only get more painful the longer they are left.
* No teams have, to our knowledge, complained or provided any negative feedback around 
  the naming conventions used in the play-frontend libraries.
* The benefits of any name changes need to be weighed against the cost and disruption to 
  service teams.
* Deprecation notices alert teams that a feature is going away without forcing an immediate
  breaking change. Deprecation notices left in too long create compilation noise and alert 
  fatigue for teams leading to a risk that they will be ignored.
* Teams with a practice of removing all compilation warnings will be forced to remove all deprecated references 
  on their next library upgrade.
* Repetition of suffixes/prefixes in the same source file makes it more verbose and harder to read.
* Poor naming and a lack of consistency negatively affect conceptual integrity and increase architectural
  entropy that over time increases the cost of maintenance.
* Poor naming confuses developers, increasing lead times to production and increases the risk of bugs in
  tax services.
* A reduction in duplication will make maintaining the play-frontend libraries easier and reduce the lead times for 
  making changes available to teams.

## Considered Options

* Option 1: Do nothing
* Option 2: Rename all view model classes with a suffix such as 'Model' and optionally
  a prefix Govuk or Hmrc.
* Option 3: Remove all view model aliases
* Option 4: Group components and view models by component not type.

## Decision Outcome

Chosen option: option 1, do nothing, because the cost of change is very high while the
theoretical benefits are extremely difficult to quantify.

## Pros and Cons of the Options

### Option 1

* Good, because it will not introduce long-standing deprecation noise for 
  service teams.
* Good, because teams will not have any requirement to update 
  their code.
* Good, because PlatUI's support overhead is not increased by the need for teams to update
  names.
* Good, because Twirl templates are not made more verbose.
* Good, because teams can continue to use wildcard imports to import
  components and view models if they wish.
* Good, because we are not making costly changes whose benefits are not easy
  to measure.
* Bad, because view model names continue to be unintuitive, and this 
  quirk may misdirect newcomers and complicate the on-boarding of new 
  developers.
* Bad, because view model aliases continue to need to be added for every new 
  component, increasing cost of porting new components to play-frontend-hmrc.
* Bad, because IDE 'go to declaration' functionality redirects developers to 
  the alias as opposed to the actual view model declaration.

### Option 2

* Good, because view models have a name that better reflects what they are.
* Good, because less chance of name clashes between play-frontend-govuk and 
  play-frontend-hmrc when using wildcard imports (applicable if a prefix is also added).
* Bad, because at least 18,000+ lines of code will need to be eventually changed 
  across the platform.
* Bad, because deprecation notices for the old names are likely to need to stay in for a 
  very long time causing excessive compilation noise and alert fatigue.
* Bad, because it will increase the verbosity and reduce the 
  readability of Twirl templates, excessively so if a prefix is also introduced.
* Bad, because we’ve not had any feedback from service developers that the 
  current naming is causing problems.
* Bad, because it’s hard to measure how much of a problem the existing names 
  are causing service teams.

### Option 3

* Good, because reduced duplication resulting in fewer steps needed to add 
  new components implying a reduced lead time for adding new components to 
  play-frontend-hmrc.
* Good, because the risk of missing view model aliases that may require 
  subsequent re-work is reduced.
* Good, because better ergonomics for service developers. IDE 
  ‘go to declaration’ functionality goes to the actual view model definition 
  as opposed to the alias.
* Good, because reduced confusion for service and platform developers 
  around which is the preferred type to use resulting in faster on-boarding of 
  new developers.
* Bad, because we’d be removing a facility that is widely used and is still a
  recommended option for teams.
* Bad, because teams will no longer be able to use wildcard imports to
  import view models. Additional view model imports would need adding for each
  wildcard import. There are
  approximately 2000 existing wildcard imports across the platform.
* Bad, because we have no user research to know if this is a change that
  would be welcomed and no easy way to measure how much confusion having the aliases is
  causing service teams, if any.
* Bad, because we’re not sure if aliases add enough overhead to development 
  to be worth removing.
* Bad, because teams will start to see deprecation notices if they are 
  using the aliased view models.

### Option 4

* Good, because it potentially improves the discoverability of components and the view models
  they depend on.
* Good, because it removes need for type aliases and the need for global view model name 
  uniqueness.
* Bad, because due to quirks of the Twirl compiler, we need to nest everything under 
  the `views.html` package which is unintuitive, unless a workaround can be found.
* Bad, because there are view models like `Text` and `HtmlContent` that don’t fit neatly into 
  this structure.
* Bad, because teams preferring wild-card imports are likely to be unhappy about 
  having to expand to multiple imports. Most views require at least 
  2 components, for example, an error summary and an input component.
* Bad, because it does not conform to regular repository organisation. Ordinarily 
  view models are separated from components at the top level. This potentially *reduces*
  discoverability for developers expecting this type of organisation.
* Bad, because it’s unclear where play-frontend-hmrc helpers, for example hmrcStandardFooter, 
  would sit in this re-organisation. Would we remove the distinction between helpers 
  and components?
* Bad, because all existing wildcard import statements would need expanding. There are 
  approximately 2000 wildcard imports across the platform.
* Bad, because all existing direct view model references would need changing. There are 
  approximately 1500 such references.
* Bad, because all existing direct component references would need changing. There are
  approximately 500 such references.
* Bad, because it’s hard to measure how much of a problem not grouping by components is 
  causing service teams.
