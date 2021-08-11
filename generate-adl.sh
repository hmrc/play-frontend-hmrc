#!/usr/bin/env bash
$(cd docs/maintainers/adr && npm_config_yes=true npx adr-log -d ./ -i index.md)
