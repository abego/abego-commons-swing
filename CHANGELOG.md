# CHANGELOG

All notable changes to this project will be documented in this file.

This project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).
Breaking changes are written in **bold**.

## [Unreleased]

## [1.0.0] - 2023-10-09

### Changed

- updated dependencies, esp. use abego-commons version 1.0.0

### Removed

- `.idea` directory no longer included in Git repo.

## [0.12.0] - 2023-03-19

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

## [0.11.0] - 2021-04-09

- new release process
