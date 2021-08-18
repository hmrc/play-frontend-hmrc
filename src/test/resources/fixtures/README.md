# Fixtures

These folders contain the fixtures for our [unit tests](../../../../docs/maintainers/overview.md#unit-tests) which are verified by [TemplateUnitSpec.scala](../../scala/uk/gov/hmrc/govukfrontend/views/TemplateUnitSpec.scala)

## `test-fixtures/`

Generated automatically by the sbt generateUnitTestFixtures task 
from the examples provided by the GOV.UK design system.

## `additional-fixtures/`

Additional fixtures for cases not covered by the GOV.UK design system.
While the inputs are curated manually, the outputs are updated automatically 
by the sbt generateUnitTestFixtures task.

## `patched-fixtures/`

Manually maintained coverage for any scenarios where we have chosen to 
diverge from the govuk components. The inputs and outputs for these fixtures
are generated manually.
