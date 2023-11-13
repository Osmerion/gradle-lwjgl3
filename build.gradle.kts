/*
 * Copyright (c) 2021-2023 Leon Linhart,
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
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.binary.compatibility.validator)
    alias(libs.plugins.gradle.plugin.functional.test)
    alias(libs.plugins.gradle.plugin.unit.test)
    alias(libs.plugins.gradle.toolchain.switches)
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.plugin.samwithreceiver)
    alias(libs.plugins.plugin.publish)
    id("com.osmerion.maven-publish-conventions")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }

    withJavadocJar()
    withSourcesJar()
}

kotlin {
    explicitApi()

    target {
        compilations.all {
            compilerOptions.configure {
                apiVersion = KotlinVersion.KOTLIN_1_8
                languageVersion = KotlinVersion.KOTLIN_1_8
                jvmTarget = JvmTarget.JVM_1_8
            }
        }

        compilations.named("main") {
            compilerOptions.configure {
                @Suppress("DEPRECATION")
                apiVersion = KotlinVersion.KOTLIN_1_4

                /*
                 * 1.4 is deprecated, but we need it to stay compatible with old
                 * Gradle versions anyway. Thus, we suppress the compiler's
                 * warning.
                 */
                freeCompilerArgs.add("-Xsuppress-version-warnings")
            }
        }
    }
}

gradlePlugin {
    compatibility {
        minimumGradleVersion = "7.6"
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

tasks {
    withType<JavaCompile>().configureEach {
        options.release = 8
    }

    withType<Test>().configureEach {
        useJUnitPlatform()

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
}

val emptyJar = tasks.register<Jar>("emptyJar") {
    destinationDirectory = buildDir.resolve("emptyJar")
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

    testImplementation(kotlin("stdlib"))
    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.junit.jupiter.api)
    testImplementation(libs.junit.jupiter.params)
    testRuntimeOnly(libs.junit.jupiter.engine)

    functionalTestImplementation(kotlin("stdlib"))
    functionalTestImplementation(platform(libs.junit.bom))
    functionalTestImplementation(libs.junit.jupiter.api)
    functionalTestImplementation(libs.junit.jupiter.params)
    functionalTestRuntimeOnly(libs.junit.jupiter.engine)
}