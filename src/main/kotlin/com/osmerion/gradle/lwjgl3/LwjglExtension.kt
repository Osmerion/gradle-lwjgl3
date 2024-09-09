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
package com.osmerion.gradle.lwjgl3

import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import javax.inject.Inject

/**
 * The LWJGL project extension.
 *
 * @since   0.1.0
 */
public open class LwjglExtension @Inject internal constructor(
    objectFactory: ObjectFactory
) {

    /**
     * The group name of the GAV coordinates for the LWJGL artifacts.
     *
     * Defaults to [LwjglConstants.DEFAULT_GROUP_NAME].
     *
     * @since   0.4.0
     */
    public val group: Property<String> = objectFactory.property(String::class.java)

    /**
     * The version of the GAV coordinates for the LWJGL artifacts.
     *
     * Defaults to [LwjglConstants.DEFAULT_VERSION].
     *
     * @since   0.4.0
     */
    public val version: Property<String> = objectFactory.property(String::class.java)

    /**
     * Returns the targets of this project.
     *
     * @since   0.1.0
     */
    public val targets: NamedDomainObjectContainer<LwjglTarget> = objectFactory.domainObjectContainer(LwjglTarget::class.java) {
        objectFactory.newInstance(LwjglTarget::class.java, it, this)
    }

    init {
        group.finalizeValueOnRead()
        group.convention(LwjglConstants.DEFAULT_GROUP_NAME)

        version.finalizeValueOnRead()
        version.convention(LwjglConstants.DEFAULT_VERSION)
    }

}