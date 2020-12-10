# Add contact HMRC and Welsh information links to standard footer

* Status: accepted
* Date: 2020-12-04

Technical Story: PLATUI-854

## Context and Problem Statement

In the context of classic services' requirement for contact HMRC and Welsh information links in their footer, facing the
 fact that these links are missing from hmrcStandardFooter, should we add them?

The additional links needed are:

* "Contact", linking to https://www.gov.uk/government/organisations/hm-revenue-customs/contact
* "Rhestr o Wasanaethau Cymraeg", linking to https://www.gov.uk/cymraeg

## Decision Drivers

* The need for consistency across HMRC services.
* Our belief that including them is likely to improve the user experience for tax users.
* We can see no good reason for not including them as standard because they are applicable across HMRC services.
* We have a time sensitive opportunity of acting now while teams are in the process of
uplifting their frontend libraries.
* The HMRC design community have been consulted on multiple public Slack channels and two
successive design system working group meetings, with no objections noted.
* Classic services support multiple live services. Not including these links as standard would mean their having to
duplicate these links, and associated English and Welsh content, across tens of repositories.

## Considered Options

* Add the links to hmrcStandardFooter
* Create an hmrcExtendedFooter component containing the additional links
* Do nothing

## Decision Outcome

Chosen option: "Add the links to hmrcStandardFooter", because this 
will benefit tax users, and we have a unique window of opportunity to act now.

### Positive Consequences

* Tax users have better information provided to them
* Teams do not need to duplicate content and URLs across hundreds of repositories
* We can more easily maintain the content and links in a central repository

### Negative Consequences

* Teams currently using a Welsh link as their language toggle will likely need to switch to using one of the standard components
for language switching e.g. hmrcLanguageSelect.
* Teams already including a contact link manually will need to remove it when upgrading. 

## Pros and Cons of the Options

### Add the links to hmrcStandardFooter

* Good, because it's more likely the additional links will become standard benefiting
users
* Good, because we can standardise the content for English and Welsh
* Good, because hyperlinks can be maintained in a central place and do not have to be corrected in 100s of separate services
* Bad, because it's possible some teams may not want a contact or Welsh link for valid business reasons

### Create an hmrcExtendedFooter component containing the additional links

* Good, because new links will be added consistently for teams adopting it
* Good, because we can standardise the text for English and Welsh
* Good, because the hyperlinks can be maintained in a central repository
* Bad, because uptake unlikely to be as high as making the links standard
* Bad, because teams may become confused about which footer to use, increasing delivery friction

### Do nothing

* Good, because teams have more flexibility
* Bad, because tax users will not benefit from these additional information links
* Bad, because teams may add the additional links inconsistently with different text, different URLs, or 
 in a different order
* Bad, because there is no standardisation of the content in English and Welsh
* Bad, because if changes need to be made, they will need to be made in 100s of individual services rather
than in one library
* Bad, because teams will have to rollback these changes if they adopt a new standard footer with the contact and Welsh links
