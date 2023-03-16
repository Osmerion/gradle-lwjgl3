# LWJGL3 Gradle Plugin

[![License](https://img.shields.io/badge/license-BSD-blue.svg?style=flat-square&label=License)](https://github.com/Osmerion/lwjgl3-gradle-plugin/blob/master/LICENSE)
[![Maven Central](https://img.shields.io/maven-central/v/com.osmerion.gradle.lwjgl3/gradle-lwjgl3.svg?style=flat-square&label=Maven%20Central)](https://maven-badges.herokuapp.com/maven-central/com.osmerion.gradle.lwjgl3/gradle-lwjgl3)
[![Gradle Plugin Portal](https://img.shields.io/maven-metadata/v.svg?style=flat-square&&label=Gradle%20Plugin%20Portal&logo=Gradle&metadataUrl=https%3A%2F%2Fplugins.gradle.org%2Fm2%2Fcom%2Fosmerion%2Flwjgl3%2com.osmerion.lwjgl3.gradle.plugin%2Fmaven-metadata.xml)](https://plugins.gradle.org/plugin/com.osmerion.lwjgl3)
![Gradle](https://img.shields.io/badge/Gradle-7.6-green.svg?style=flat-square&color=1ba8cb&logo=Gradle)
![Java](https://img.shields.io/badge/Java-8-green.svg?style=flat-square&color=b07219&logo=Java)

A Gradle plugin to simplify working with [LWJGL 3](https://lwjgl.org).


## Usage

> **Note**: The documentation of this plugin is written in [Gradle's Kotlin DSL](https://docs.gradle.org/current/userguide/kotlin_dsl.html).
The plugin can also be used with Groovy build scripts and all concepts still
apply but the exact syntax may differ.

### Applying the Plugin

```kotlin
plugins {
    id("com.osmerion.lwjgl3") version "0.1.0"
}
```


### Targets

The plugin introduces the concept of "targets" to enable using multiple
different LWJGL setups (different versions, modules, etc.) within a single
Gradle project. In the following example, a target is registered and linked to
the appropriate configurations.

```kotlin
lwjgl3 {
    targets.register("main") {
        libConfigurations.addLater(configurations.named(JavaPlugin.IMPLEMENTATION_CONFIGURATION_NAME))
        nativesConfigurations.addLater(configurations.named(JavaPlugin.RUNTIME_ONLY_CONFIGURATION_NAME))
        nativesConfigurations.addLater(configurations.named(JavaPlugin.TEST_RUNTIME_ONLY_CONFIGURATION_NAME))
    }
}
```

The configurations in `libConfigurations` will have access to the library
artifacts and the configurations in `nativesConfigurations` will have access to
the artifacts containing the native libraries for LWJGL for the host.

> **Note**: By default, a `main` target as defined above is automatically
> registered when the `org.gradle.java` plugin is detected. To disable this
> behavior, set the property `com.osmerion.lwjgl3.implicit-target` to `false`.


### Modules

LWJGL consists of many modules which contain bindings to different native
libraries. Modules can be added to a target using the `useModule` and
`useModules` functions as follows:

```kotlin
lwjgl3 {
    targets.named("main") {
        useModule(LWJGLModules.Core)
    }
}
```

Predefined accessors for known LWJGL modules are available in the `LWJGLModules`
class.

#### Custom Modules

In addition to using the predefined module accessors, it is also possible to use
custom modules. This can be useful when working with forked versions of LWJGL or
for new modules for which no predefined accessors are available yet.

```kotlin
lwjgl3 {
    targets.named("main") {
        useModule("lwjgl-glfw")
    }
}
```


### Platforms

As LWJGL provides bindings to native libraries, it is also necessary to declare
which native platforms should be included in a target. 

!TODO

#### Custom Platforms

!TODO

### Configuring the LWJGL Artifacts

The plugin can further be customized to use a different version of LWJGL by
configuring the `version` property of a target. Similarly, the `group` property
can be used to configure the group name portion of the GAV coordinates for the
LWJGL artifacts.

```kotlin
lwjgl3 {
    targets.named("main") {
        group.set("org.lwjgl")
        version.set("3.3.1")
    }
}
```


## Compatibility Map

| LWJGL3 Gradle Plugin | Supported Gradle versions |
|----------------------|---------------------------|
| 0.1.0                | 7.6+                      |


## Plugin Defaults

| Plugin version | Default LWJGL version |
|----------------|-----------------------|
| 0.1.0          | 3.3.1                 |


## Building from source

### Setup

This project uses [Gradle's toolchain support](https://docs.gradle.org/8.0.2/userguide/toolchains.html)
to detect and select the JDKs required to run the build. Please refer to the
build scripts to find out which toolchains are requested.

An installed JDK 1.8 (or later) is required to use Gradle.

### Building

Once the setup is complete, invoke the respective Gradle tasks using the
following command on Unix/macOS:

    ./gradlew <tasks>

or the following command on Windows:

    gradlew <tasks>

Important Gradle tasks to remember are:
- `clean`                   - clean build results
- `build`                   - assemble and test the Java library
- `publishToMavenLocal`     - build and install all public artifacts to the
                              local maven repository

Additionally `tasks` may be used to print a list of all available tasks.


## License

This plugin is available under the terms of the [3-Clause BSD license](https://spdx.org/licenses/BSD-3-Clause.html).

```
Copyright (c) 2021-2023 Leon Linhart,
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

1. Redistributions of source code must retain the above copyright notice,
   this list of conditions and the following disclaimer.

2. Redistributions in binary form must reproduce the above copyright notice,
   this list of conditions and the following disclaimer in the documentation
   and/or other materials provided with the distribution.

3. Neither the name of the copyright holder nor the names of its contributors
   may be used to endorse or promote products derived from this software
   without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
POSSIBILITY OF SUCH DAMAGE.
```