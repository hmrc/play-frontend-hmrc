We've pragmatically decided to temporarily exclude this fixture, as the component returns non-deterministic output under Scala 2.12

This is because
* the GovukCharacterCount has been refactored to remove its outer <div> and pass classes and attributes to the GovukTextArea's form-group <div> instead
* the form-group can itself take optional attributes
* the form group attributes are modelled as a Map, so ordering is not guaranteed

Whilst we could possibly engineer the models to produce consistent output under all Scala versions, this would
* introduce accidental complexity into the production code, purely to satisfy tests (ordering does not matter in the browser)
* probably be unnecessary once support for Play 2.8, and so Scala 2.12 is dropped
