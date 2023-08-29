# Reasons for current patched tests

### 1. GovukHeader.scala.html tests

We have had to patch the header examples to include a relative assets' path for the img elements src attribute, as this path is calculated in the twirl template via the calculateAssetPath method, which uses an assetPath property that can not yet be overridden.

* header-attributes
* header-classes
* header-custom-homepage-url
* header-default
* header-full-width
* header-full-width-with-navigation
* header-navigation-item-with-attributes
* header-navigation-item-with-html
* header-navigation-item-with-html-as-text
* header-navigation-item-with-html-without-link
* header-navigation-item-with-text-without-link
* header-with-custom-menu-button-label
* header-with-custom-navigation-label
* header-with-large-navigation
* header-with-navigation
* header-with-product-name
* header-with-service-name
* header-with-service-name-and-navigation
* header-with-service-name-but-no-service-url
