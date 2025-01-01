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
 * A custom [Architecture] that tests the given predicate against the `os.arch`
 * system property.
 *
 * @param matches   the predicate used to detect if the current host is part of
 *                  the architecture type
 *
 * @since   0.1.0
 */
public fun Architecture(matches: Predicate<String>): Architecture =
    Architecture.Custom(matches)

/**
 * A computer architecture.
 *
 * @since   0.1.0
 */
@OptIn(ExperimentalPlatformApi::class)
public sealed class Architecture {

    internal abstract val artifactClassifierComponent: String?

    internal companion object {

        val KNOWN_ARCHITECTURES = listOf(ARM32, ARM64, PowerPC64LE, RISCV64, X86, X86_64)

    }

    /**
     * Checks if the given `arch` matches this architecture.
     *
     * @param arch  the architecture to match
     *
     * @return  `true` if the given `arch` matches this architecture, or `false` otherwise
     *
     * @since   0.5.0
     */
    @ExperimentalPlatformApi
    public abstract fun matches(arch: String): Boolean

    /**
     * The ARM32 architecture.
     *
     * @since   0.1.0
     */
    public object ARM32 : Architecture() {
        override val artifactClassifierComponent: String get() = "arm32"
        override fun matches(arch: String): Boolean = (arch == "arm")
    }

    /**
     * The ARM64 architecture.
     *
     * @since   0.1.0
     */
    public object ARM64 : Architecture() {
        override val artifactClassifierComponent: String get() = "arm64"
        override fun matches(arch: String): Boolean = (arch == "aarch64" || arch == "armv8")
    }

    /**
     * The PowerPC 64 LE architecture.
     *
     * @since   0.3.0
     */
    public object PowerPC64LE : Architecture() {
        override val artifactClassifierComponent: String get() = "ppc64le"
        override fun matches(arch: String): Boolean = (arch == "ppc64le")
    }

    /**
     * The RISC-V 64 architecture
     *
     * @since   0.3.0
     */
    public object RISCV64 : Architecture() {
        override val artifactClassifierComponent: String get() = "riscv64"
        override fun matches(arch: String): Boolean = (arch == "riscv64")
    }

    /**
     * The x86 architecture.
     *
     * @since   0.1.0
     */
    public object X86 : Architecture() {
        override val artifactClassifierComponent: String get() = "x86"
        override fun matches(arch: String): Boolean = (arch == "i386") || (arch == "x86")
    }

    /**
     * The x86-64 architecture.
     *
     * @since   0.1.0
     */
    @Suppress("ClassName")
    public object X86_64 : Architecture() {
        override val artifactClassifierComponent: String? get() = null
        override fun matches(arch: String): Boolean = (arch == "x86_64") || (arch == "amd64")
    }

    internal class Custom(private val matches: Predicate<String>) : Architecture() {
        override val artifactClassifierComponent: String = error("This should never be called")
        override fun matches(arch: String): Boolean = matches.test(arch)
    }

}