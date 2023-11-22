In govuk-frontend, `item.checked` can be undefined, so they have chosen to use `item.checked` if it's set, otherwise check the item(s) whose value are in the passed `values`.

In play-frontend-hmrc, we model `checked` as a Boolean, so we give the top-level `values` precedence over any `item.checked`.
Otherwise, teams would need to consistently set both `item.checked` and `values`, which defeats the object of passing a list of `values`.
