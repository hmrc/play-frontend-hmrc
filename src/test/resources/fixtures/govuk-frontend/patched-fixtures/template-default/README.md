The default value for the headerBlock is govukHeader() in the twirl component and the assetPath that you provide to 
GovukTemplate is not passed into GovukHeader, so the single asset on line 50 is rendered with an incorrect path.

For now we need to manually fix the assetPath in output.txt to include /govuk.    

on line 50

```<img src="/assets/images/govuk-logotype-crown.png" class="govuk-header__logotype-crown-fallback-image" width="36" height="32">```

becomes 

```<img src="/assets/govuk/images/govuk-logotype-crown.png" class="govuk-header__logotype-crown-fallback-image" width="36" height="32">```
