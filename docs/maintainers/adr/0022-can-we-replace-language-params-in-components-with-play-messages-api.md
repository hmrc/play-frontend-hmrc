# Can we replace the string `language` params in `hmrc-frontend` components with Play Messages API?

* Status: rejected
* Date: 2024-04-22

## Context and Problem Statement

At the moment, components in `play-frontend-hmrc` based on components from `hmrc-frontend` can take in a `language` parameter 
if the template contains text strings. These are modelled in the hmrc-frontend library as strings of “en” or “cy”. 
Currently we have a Scala case class that models this, which is a trait Language that can be either `En` or `Cy`.

However, Play framework supports multilingual strings via the Messages API, whereby messages are stored in `messages` 
and `messages.cy` and translated based on the value in the `PLAY_LANG` cookie. A spike was played to see whether it was
possible to replace current implementation of passing a string on "en" or "cy" to a template by using the Messages API.

## Decision Drivers

* This was documented in the spike write-up document in PLATUI-2910. Please see the document linked from that ticket for
  a full write-up.

## Considered Options

* Replace use of `lanaguage` string with use of Play Messages API.

## Decision Outcome

Chosen option: Do nothing at this time. 

This is because this would like be impactful on service teams (although the size of the impact was unknown) with little 
benefit for services unless they want to support more langauges than English and Welsh. Given that making any chane 
would definitely be an impactful breaking changes requiring re-writing of service code by teams, the decision was made
not to undertake the work at this time now that the changes needed are better understood from the spike.

For a fuller write-up of all this, please read the spike write-up document linked in PLATUI-2910.

