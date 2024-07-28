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

import com.osmerion.gradle.lwjgl3.internal.capitalized
import org.gradle.api.Action
import org.gradle.api.NamedDomainObjectProvider
import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration
import org.gradle.api.artifacts.dsl.DependencyFactory
import org.gradle.api.provider.Property
import javax.inject.Inject

/**
 * A representation of a native platform.
 *
 * @param name  the name of the platform
 *
 * @since   0.1.0
 */
public open class NativePlatform @Inject internal constructor(
    public val name: String,
    targetName: String,
    project: Project,
    @Suppress("UnstableApiUsage")
    dependencyFactory: DependencyFactory
) {

    internal val configurationImpl: NamedDomainObjectProvider<Configuration> = project.configurations.register("lwjgl${targetName.capitalized()}${name.capitalized()}Impl") {
        isCanBeConsumed = false
        isCanBeResolved = true
    }

    /**
     * The configuration that contains the native artifacts for this platform.
     *
     * @since   0.1.0
     */
    public val configuration: NamedDomainObjectProvider<Configuration> = project.configurations.register("lwjgl${targetName.capitalized()}${name.capitalized()}") {
        isCanBeConsumed = false
        isCanBeResolved = true
        isTransitive = true

        dependencies.addLater(configurationImpl.map {
            @Suppress("UnstableApiUsage")
            dependencyFactory.create(it.incoming.artifactView { lenient(true) }.files)
        })
    }

    /**
     * The classifier of the native artifacts for this platform.
     *
     * @since   0.1.0
     */
    public val artifactClassifier: Property<String> = project.objects.property(String::class.java)

    init {
        artifactClassifier.finalizeValueOnRead()
    }

    internal val matcher: PlatformMatcher = project.objects.newInstance(PlatformMatcher::class.java)

    /**
     * Configures the [PlatformMatcher] using the given [action].
     *
     * @since   0.1.0
     */
    public fun match(action: Action<PlatformMatcher>) {
        action.execute(matcher)
    }

}