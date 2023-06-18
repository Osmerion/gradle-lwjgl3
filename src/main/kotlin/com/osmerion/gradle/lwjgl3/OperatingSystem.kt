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
package com.osmerion.gradle.lwjgl3

import java.util.function.Predicate

/**
 * TODO doc
 *
 * @param matches
 *
 * @since   0.1.0
 */
public fun OperatingSystem(matches: Predicate<String>): OperatingSystem =
    OperatingSystem.Custom(matches)

/**
 * TODO doc
 *
 * @since   0.1.0
 */
public sealed class OperatingSystem {

    internal abstract fun matches(osName: String): Boolean

    public object FreeBSD : OperatingSystem() {
        override fun matches(osName: String): Boolean = osName.contains("freebsd", ignoreCase = true)
    }

    public object Linux : OperatingSystem() {
        override fun matches(osName: String): Boolean = osName.contains("linux", ignoreCase = true)
    }

    public object MacOS : OperatingSystem() {

        override fun matches(osName: String): Boolean =
            osName.contains("mac os x", ignoreCase = true)
                || osName.contains("darwin", ignoreCase = true)
                || osName.contains("osx", ignoreCase = true)

    }

    public object Windows : OperatingSystem() {
        override fun matches(osName: String): Boolean = osName.contains("windows", ignoreCase = true)
    }

    internal class Custom(private val matches: Predicate<String>) : OperatingSystem() {
        override fun matches(osName: String): Boolean = matches.test(osName)
    }

}