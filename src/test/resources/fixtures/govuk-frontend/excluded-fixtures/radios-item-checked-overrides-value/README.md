In govuk-frontend, `item.checked` can be undefined, so they have chosen to use `item.checked` if it's set, otherwise check the item whose value matches the passed `value`.

In play-frontend-hmrc, we model `checked` as a Boolean, so we give the top-level `value` precedence over any `item.checked`.
Otherwise, teams would need to consistently set both `item.checked` and `value`, which defeats the object of passing a `value`.
