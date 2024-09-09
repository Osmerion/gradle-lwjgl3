### 0.4.0

_Released 2024 Sep 09_

#### Improvements

- Added support for setting the LWJGL group and version in the `lwjgl3` project
  extension.
    - If specified, targets will now inherit the group and version from the
      `lwjgl3` extension.

#### Breaking Changes

- Updated the minimum required Gradle version to 8.0 (from 7.6).
- `LWJGL` has been replaced with `Lwjgl` in class names to follow Kotlin naming
  conventions.


---

### 0.3.0

_Released 2024 Jul 28_

#### Improvements

- Added a predefined accessor for the msdfgen bindings. [[GH-24]](https://github.com/Osmerion/gradle-lwjgl3/issues/24)
- Added platform and architecture presets for FreeBSD x64.
- Added platform and architecture presets for RISC-V 64.
- Added platform and architecture presets for PowerPC 64 LE.
- Updated the default LWJGL version to 3.3.4. [[GH-23]](https://github.com/Osmerion/gradle-lwjgl3/issues/23)

#### Fixes

- Corrected invalid GitHub links in published POM.


---

### 0.2.0

_Released 2023 Sep 17_

#### Improvements

- Updated the default LWJGL version to [3.3.3](https://github.com/LWJGL/lwjgl3/releases/tag/3.3.3).


---

### 0.1.0

_Released 2023 Jul 03_

#### Overview

A Gradle plugin to simplify working with [LWJGL 3](https://lwjgl.org).