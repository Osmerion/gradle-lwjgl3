/*
 * Copyright (c) 2021-2024 Leon Linhart,
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its contributors
 *    may be used to endorse or promote products derived from this software
 *    without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
import io.github.themrmilchmann.gradle.toolchainswitches.ExperimentalToolchainSwitchesApi
import io.github.themrmilchmann.gradle.toolchainswitches.inferLauncher
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion

plugins {
    alias(buildDeps.plugins.binary.compatibility.validator)
    alias(buildDeps.plugins.gradle.toolchain.switches)
    alias(buildDeps.plugins.java.gradle.plugin)
    alias(buildDeps.plugins.kotlin.jvm)
    alias(buildDeps.plugins.kotlin.plugin.samwithreceiver)
    alias(buildDeps.plugins.plugin.publish)
    id("com.osmerion.maven-publish-conventions")
    `jvm-test-suite`
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(22)
    }

    withJavadocJar()
    withSourcesJar()
}

kotlin {
    explicitApi()

    compilerOptions {
        apiVersion = KotlinVersion.KOTLIN_1_8
        languageVersion = KotlinVersion.KOTLIN_1_8

        jvmTarget = JvmTarget.JVM_1_8

        freeCompilerArgs.add("-Xjdk-release=1.8")
    }
}

gradlePlugin {
    compatibility {
        minimumGradleVersion = "8.0"
    }

    website = "https://github.com/Osmerion/gradle-lwjgl3"
    vcsUrl = "https://github.com/Osmerion/gradle-lwjgl3.git"

    plugins {
        register("lwjgl") {
            id = "com.osmerion.lwjgl3"
            displayName = "LWJGL3 Gradle Plugin"
            description = "A Gradle plugin to simplify working with LWJGL 3."
            tags.addAll("dependency-management", "lwjgl", "lwjgl3")

            implementationClass = "com.osmerion.gradle.lwjgl3.plugins.LWJGLPlugin"
        }
    }
}

samWithReceiver {
    annotation("org.gradle.api.HasImplicitReceiver")
}

@Suppress("UnstableApiUsage")
testing {
    suites {
        withType<JvmTestSuite>().configureEach {
            useJUnitJupiter()

            dependencies {
                implementation(platform(buildDeps.junit.bom))
                implementation(buildDeps.junit.jupiter.api)
                implementation(buildDeps.junit.jupiter.params)
                runtimeOnly(buildDeps.junit.jupiter.engine)
            }
        }

        val test = named("test")

        register<JvmTestSuite>("functionalTest") {
            dependencies {
                implementation(gradleTestKit())
                runtimeOnly(layout.files(tasks.named("pluginUnderTestMetadata")))
            }

            targets.configureEach {
                testTask.configure {
                    shouldRunAfter(test)
                }
            }
        }
    }
}

tasks {
    withType<JavaCompile>().configureEach {
        options.release = 8
    }

    withType<Test>().configureEach {
        @OptIn(ExperimentalToolchainSwitchesApi::class)
        javaLauncher.set(inferLauncher(default = project.javaToolchains.launcherFor {
            languageVersion = JavaLanguageVersion.of(8)
        }))

        /*
         * The tests are extremely memory-intensive which causes spurious CI
         * failures. To work around this, we don't enable parallel execution by
         * default if we're in CI.
         *
         * See https://github.com/TheMrMilchmann/gradle-ecj/issues/11
         * See https://github.com/gradle/gradle/issues/12247
         */
        val defaultExecutionMode = providers.environmentVariable("CI")
            .map(String::toBoolean)
            .orElse(false)
            .map { if (it) "same_thread" else "concurrent" }

        inputs.property("junit.jupiter.execution.parallel.mode.default", defaultExecutionMode)

        systemProperty("junit.jupiter.execution.parallel.enabled", true)

        doFirst {
            systemProperty("junit.jupiter.execution.parallel.mode.default", defaultExecutionMode.get())
        }
    }

    validatePlugins {
        enableStricterValidation = true
    }
}

val emptyJar = tasks.register<Jar>("emptyJar") {
    destinationDirectory = layout.buildDirectory.dir("emptyJar")
    archiveBaseName = "com.osmerion.lwjgl3.gradle.plugin"
}

publishing {
    publications.withType<MavenPublication>().configureEach {
        if (name == "lwjglPluginMarkerMaven") {
            artifact(emptyJar)
            artifact(emptyJar) { classifier = "javadoc" }
            artifact(emptyJar) { classifier = "sources" }
        }

        pom {
            name = "LWJGL3 Gradle Plugin"
            description = "A Gradle plugin to simplify working with LWJGL 3."
        }
    }
}

dependencies {
    compileOnlyApi(kotlin("stdlib"))
}