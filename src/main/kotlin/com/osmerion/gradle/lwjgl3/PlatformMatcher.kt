/*
 * Copyright (c) 2023 Leon Linhart
 * All rights reserved.
 */
package com.osmerion.gradle.lwjgl3

import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import javax.inject.Inject

public open class PlatformMatcher @Inject internal constructor(
    objectFactory: ObjectFactory
) {

    public val os: Property<OperatingSystem> = objectFactory.property(OperatingSystem::class.java)
    public val arch: Property<Architecture> = objectFactory.property(Architecture::class.java)

    init {
        os.finalizeValueOnRead()
        arch.finalizeValueOnRead()
    }

    public val matchesCurrent: Boolean by lazy {
        val osName = System.getProperty("os.name")
        os.get().matches(osName)
    }

}