This fixture is excluded because our
GOVUK [`CharacterCount` viewmodel](src/main/scala/uk/gov/hmrc/govukfrontend/views/viewmodels/charactercount/CharacterCount.scala)
doesn't currently support the `spellcheck` attribute, so there's no way to wire it through to the underlying `textarea`.
