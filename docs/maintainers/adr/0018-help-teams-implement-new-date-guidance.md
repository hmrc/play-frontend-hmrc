# Help teams implement new date guidance

* Status: accepted
* Deciders: platui and nick wilson
* Date: 2023-10-31

Technical Story: PLATUI-2532

## Context and Problem Statement

The GOV.UK design system now advises that services accept month names written out in full or abbreviated form (for
example, ‘january’ or ‘jan’) as some users may enter months in this way.

Some users with dyscalculia may struggle to convert month names into numbers, but accepting full or abbreviated month
names may help these users.

DIAS has started relaying this advice to teams during accessibility audits. If every team implements this independently
there's potential for error or inconsistency and possibly unnecessary duplicated effort.

We were asked if there was anything we could do to help teams implement the new guidance.

## Considered Options

* Option 1: do nothing
* Option 2: blog about the new guidance and offer support
* Option 3: provide a mapping in play-frontend-hmrc
* Option 4: update the existing govukDate mapping in the frontend scaffolding maintained by the centre tech leads

## Decision Outcome

Chosen option 4: to update the frontend scaffolding, because we weren't satisfied with what we came up with during our
research and experiments into providing something in play-frontend-hmrc.

After talking with Nick Wilson, one of the maintainers of the frontend scaffolding from #team-scaffolders on slack, he
is interested in taking on the work to update the scaffolding and writing a blog post about it to advise service teams.

## Pros and Cons of the Options

### Option 1: do nothing

* Bad, because we've had feedback from service developers during recent research that this is difficult to implement

### Option 2: blog about the new guidance and offer support

* Bad, because we might prompt more duplication of effort / inconsistency if we don't have any example to share and this
  prompts everyone to start implementing it independently at the same time

### Option 3: provide a mapping in play-frontend-hmrc

* Bad, because we don't currently provide mappings so there's a high upfront cost for us in starting to (lots of
  decisions, and a wrong one could mean we bottleneck teams waiting for us to do stuff)
* Bad, because we've seen quite a few services with business rules coupled to the "is a valid date" rules - so migrating
  to our thing might lead to accidental regressions, could be an impractical amount of effort, and we might not be able
  to satisfy enough peoples requirements to make it worthwhile for us to maintain
* Bad, because teams might trust our implementation too much, miss some impactful consequence as a result, and would
  probably assume that they don't need to do their own user testing. For example: how should the date be displayed on a
  check your answers page? If a user then tries to edit it, does it need to be edited in the format it was entered? And, 
  if the user enters "jan" but the input is sent as 01, does that impact auditing in any way?
* Bad, because the work to migrate from teams current implementations to our one might be more work than uplifting their
  existing code using an example reference implementation
* Bad, because I don't think we have clear consensus on what the API could be after our experiments, so we'd need to
  invest more resources experimenting, and it might slip into being too costly for the value it brings

### Option 4: update the existing govukDate mapping in the frontend scaffolding maintained by the centre tech leads

* Good, because there's no upfront work for us (since Nick has volunteered to)
* Good, because it might be the least work / most practical option for teams to implement the guidance in their
  services (amortised across everyone)
* Good, because it gets the scaffolding more widely known
* Good, because it should reduce the amount of duplicated effort if people can refer to this example
* Good, because we can still revisit option 3 in the future, but with a hopefully clearer picture of its value / how it
  should look
* Good, because we can use feedback / pain points / emergent patterns from teams implementing the date validation to
  inform any potential library-provided solution, instead of committing to the wrong thing now

## Links

* [frontend scaffolding](https://github.com/hmrc/hmrc-frontend-scaffold.g8)
* [localDate formatter within scaffolding that powers the mapping provided](https://github.com/hmrc/hmrc-frontend-scaffold.g8/blob/main/src/main/g8/app/forms/mappings/LocalDateFormatter.scala)
