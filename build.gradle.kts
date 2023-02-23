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
@Suppress("DSL_SCOPE_VIOLATION") // See https://github.com/gradle/gradle/issues/22797
plugins {
    `java-gradle-plugin`
    alias(libs.plugins.binary.compatibility.validator)
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.plugin.samwithreceiver)
    id("com.osmerion.maven-publish-conventions")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
    }

    withJavadocJar()
    withSourcesJar()
}

kotlin {
    explicitApi()

    target {
        compilations.all {
            compilerOptions.configure {
                apiVersion.set(org.jetbrains.kotlin.gradle.dsl.KotlinVersion.KOTLIN_1_4)
                languageVersion.set(org.jetbrains.kotlin.gradle.dsl.KotlinVersion.KOTLIN_1_8)
            }
        }
    }
}

gradlePlugin {
    plugins {
        create("lwjgl") {
            id = "com.osmerion.lwjgl3"
            implementationClass = "com.osmerion.gradle.lwjgl3.plugins.LWJGLPlugin"
        }
    }
}

samWithReceiver {
    annotation("org.gradle.api.HasImplicitReceiver")
}