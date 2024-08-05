This fixture is excluded because the input items contain falsey values which are filtered out by the Nunjucks code, but which our test helper deserialises as 'empty' instances, due to all fields being optional.

Ideally, the JSON reader for the items would filter out any empty instances, perhaps by comparing them to the `defaultObject` of the given class.

As of govuk-frontend v5.4.1, the name for this fixture was updated from radios-with-falsey-items to radios-with-falsy-items
