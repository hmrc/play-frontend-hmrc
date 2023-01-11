In govuk-frontend, `item.selected` can be undefined, so they have chosen to use `item.selected` if it's set, otherwise select the item whose value matches the passed `value`.

In play-frontend-hmrc, we model `selected` as a Boolean, so we give the top-level `value` precedence over any `item.selected`.
Otherwise, teams would need to consistently set both `item.selected` and `value`, which defeats the object of passing a `value`.
