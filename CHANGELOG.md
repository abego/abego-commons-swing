# CHANGELOG

## 0.12.0

### New

#### Types

- ActionUtil
- ActionWithEventHandler
- ComponentListenerAdapter
- DimensionUtil
- ImageIconUtil
- KeyStrokeUtil

#### Methods

- ComponentUtil#findWindow
- JComponentUtil#onJComponentBecomesVisible

### Changes (Possibly incompatible)

- TreePathUtil is now final

### Bug Fixes

- NPE in findWindow when component is not in Window
- JTextComponentUtil#isValidSelection off-by-one error

### Deprecation

- BoxModelBorder (alternative not yet published)

### Improvements

- KeyStrokeUtil#acceleratorText: more readable text for Mac

## 0.11.0

- new release process
