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
package com.osmerion.gradle.lwjgl3.plugins

import org.gradle.api.JavaVersion
import org.gradle.testkit.runner.GradleRunner
import org.junit.jupiter.api.io.TempDir
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.copyToRecursively

class LWJGLPluginTest {

    private companion object {

        @JvmStatic
        private fun provideGradleVersions(): List<String> = buildList {
            // See https://docs.gradle.org/current/userguide/compatibility.html
            val javaVersion = JavaVersion.current()

            add("8.9")

            @Suppress("UnstableApiUsage")
            if (javaVersion >= JavaVersion.VERSION_22) return@buildList

            add("8.8")
            add("8.7")
            add("8.6")
            add("8.5")

            @Suppress("UnstableApiUsage")
            if (javaVersion >= JavaVersion.VERSION_21) return@buildList

            add("8.4")
            add("8.3")

            @Suppress("UnstableApiUsage")
            if (javaVersion >= JavaVersion.VERSION_20) return@buildList

            add("8.2.1")
            add("8.1.1")
            add("8.0.2")
        }

        @JvmStatic
        private fun provideSamples(): List<String> = buildList {
            add("groovy-dsl")
            add("kotlin-dsl")
        }

        @JvmStatic
        private fun provideTestArguments(): List<Arguments> = provideGradleVersions().flatMap { gradleVersion ->
            provideSamples().map { sample -> Arguments.of(gradleVersion, sample) }
        }

    }

    @field:TempDir
    lateinit var projectDir: Path

    @ParameterizedTest
    @MethodSource("provideTestArguments")
    fun test(gradleVersion: String, sample: String) {
        @OptIn(ExperimentalPathApi::class)
        Paths.get("./samples", sample).copyToRecursively(target = projectDir, followLinks = true)

        GradleRunner.create()
            .withArguments("build", "--info", "-S")
            .withGradleVersion(gradleVersion)
            .withPluginClasspath()
            .withProjectDir(projectDir.toFile())
            .forwardOutput()
            .build()
    }

}