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

    public class Custom(private val matches: Predicate<String>) : OperatingSystem() {
        override fun matches(osName: String): Boolean = matches.test(osName)
    }

}