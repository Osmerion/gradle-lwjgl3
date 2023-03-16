/*
 * Copyright (c) 2023 Leon Linhart
 * All rights reserved.
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
public fun Architecture(matches: Predicate<String>): Architecture =
    Architecture.Custom(matches)

/**
 * TODO doc
 *
 * @since   0.1.0
 */
public sealed class Architecture {

    internal abstract fun matches(arch: String): Boolean

    public object ARM32 : Architecture() {
        override fun matches(arch: String): Boolean = (arch == "arm")
    }

    public object ARM64 : Architecture() {
        override fun matches(arch: String): Boolean = (arch == "aarch64" || arch == "armv8")
    }

    public object X86 : Architecture() {
        override fun matches(arch: String): Boolean = (arch == "i386") || (arch == "x86")
    }

    @Suppress("ClassName")
    public object X86_64 : Architecture() {
        override fun matches(arch: String): Boolean = (arch == "x86_64") || (arch == "amd64")
    }

    public class Custom(private val matches: Predicate<String>) : Architecture() {
        override fun matches(arch: String): Boolean = matches.test(arch)
    }

}