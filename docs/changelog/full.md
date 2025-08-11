### 0.6.0

_Released 2025 Aug 11_

#### Improvements

- Updated the default LWJGL version to 3.3.6.

#### Breaking Changes

- The plugin now requires at least Gradle 9.0.0 (from 8.0.0).


---

### 0.5.0

_Released 2025 Jan 01_

#### Improvements

- Updated the default LWJGL version to 3.3.5. [[GH-49](https://github.com/Osmerion/gradle-lwjgl3/issues/49)]
- By default, a platform is now implicitly created for the host system (if it is
  supported).
    - This platform can be identified by the `isImplicitHostPlatform` property.
    - The purpose of this platform is to provide more convenient development
      experience. The implicit platform should be filtered when creating
      distributions.
- Experimentally exposed the match functions for `Architecture` and
  `OperatingSystem` as public API.

#### Fixes

- Added missing `@JvmOverloads` annotation to the `freebsdX64` convenience
  function.
- A dependency on the core module is now automatically added to the libraries
  and natives configurations if at least one module is required.
    - Due to this change, it is no longer necessary to manually add the core
      module when using other modules.


---

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