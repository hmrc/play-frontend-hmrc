This fixture tests govukTemplate, which in govuk-frontend is not a component per se, but a template that sits outside of the components package.

The opengraph image in our implementation in play-frontend-hmrc is not yet customisable,
and may in fact be broken, as it serves up a relative URL instead of an absolute one.

We've chosen not to implement/fix this yet, to reduce the complexity of the govuk-frontend v5 uplift.
