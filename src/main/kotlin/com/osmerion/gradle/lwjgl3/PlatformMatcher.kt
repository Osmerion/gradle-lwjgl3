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

import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import javax.inject.Inject

/**
 * A predicate responsible for matching a platform.
 *
 * @since   0.1.0
 */
public open class PlatformMatcher @Inject internal constructor(
    objectFactory: ObjectFactory
) {

    /**
     * The matched operating system family.
     *
     * @since   0.1.0
     */
    public val os: Property<OperatingSystem> = objectFactory.property(OperatingSystem::class.java)

    /**
     * The matched architecture.
     *
     * @since   0.1.0
     */
    public val arch: Property<Architecture> = objectFactory.property(Architecture::class.java)

    init {
        os.finalizeValueOnRead()
        arch.finalizeValueOnRead()
    }

    /**
     * Returns whether the current platform is matched.
     *
     * @since   0.1.0
     */
    @OptIn(ExperimentalPlatformApi::class)
    public val matchesCurrent: Boolean by lazy {
        val osName = System.getProperty("os.name")
        val osArch = System.getProperty("os.arch")

        os.get().matches(osName) && arch.get().matches(osArch)
    }

}