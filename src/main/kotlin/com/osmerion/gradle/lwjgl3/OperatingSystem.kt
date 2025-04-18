/*
 * Copyright (c) 2021-2025 Leon Linhart,
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
package com.osmerion.gradle.lwjgl3

import java.util.function.Predicate

/**
 * A custom [OperatingSystem] that tests the given predicate against the
 * `os.name` system property.
 *
 * @param matches   the predicate used to detect if the current host is part of
 *                  the operating system family
 *
 * @since   0.1.0
 */
public fun OperatingSystem(matches: Predicate<String>): OperatingSystem =
    OperatingSystem.Custom(matches)

/**
 * An operating system family.
 *
 * @since   0.1.0
 */
@OptIn(ExperimentalPlatformApi::class)
public sealed class OperatingSystem {

    internal abstract val artifactClassifierComponent: String

    internal companion object {

        val KNOWN_OPERATING_SYSTEMS = listOf(FreeBSD, Linux, MacOS, Windows)

    }

    /**
     * Checks if the given operating system name matches this operating system.
     *
     * @param osName    the name of the operating system to match
     *
     * @return  `true` if the given operating system name matches this operating system,
     *
     * @since   0.5.0
     */
    @ExperimentalPlatformApi
    public abstract fun matches(osName: String): Boolean

    /**
     * The FreeBSD operating system family.
     *
     * @since   0.1.0
     */
    public object FreeBSD : OperatingSystem() {
        override val artifactClassifierComponent: String get() = "freebsd"
        override fun matches(osName: String): Boolean = osName.contains("freebsd", ignoreCase = true)
    }

    /**
     * The Linux operating system family.
     *
     * @since   0.1.0
     */
    public object Linux : OperatingSystem() {
        override val artifactClassifierComponent: String get() = "linux"
        override fun matches(osName: String): Boolean = osName.contains("linux", ignoreCase = true)
    }

    /**
     * The macOS operating system family.
     *
     * @since   0.1.0
     */
    public object MacOS : OperatingSystem() {
        override val artifactClassifierComponent: String get() = "macos"
        override fun matches(osName: String): Boolean =
            osName.contains("mac os x", ignoreCase = true)
                || osName.contains("darwin", ignoreCase = true)
                || osName.contains("osx", ignoreCase = true)

    }

    /**
     * The Windows operating system family.
     *
     * @since   0.1.0
     */
    public object Windows : OperatingSystem() {
        override val artifactClassifierComponent: String get() = "windows"
        override fun matches(osName: String): Boolean = osName.contains("windows", ignoreCase = true)
    }

    internal class Custom(
        private val matches: Predicate<String>
    ) : OperatingSystem() {
        override val artifactClassifierComponent: String = error("This should never be called")
        override fun matches(osName: String): Boolean = matches.test(osName)
    }

}