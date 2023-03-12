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

import org.gradle.api.DomainObjectSet
import org.gradle.api.artifacts.Configuration
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import javax.inject.Inject

public abstract class LWJGLTarget @Inject constructor(
    public val name: String,
    objectFactory: ObjectFactory
) {

    public val group: Property<String> = objectFactory.property(String::class.java)
    public val version: Property<String> = objectFactory.property(String::class.java)

    init {
        group.finalizeValueOnRead()
        version.finalizeValueOnRead()
    }

    public val libConfigurations: DomainObjectSet<Configuration> = objectFactory.domainObjectSet(Configuration::class.java)
    public val nativesConfigurations: DomainObjectSet<Configuration> = objectFactory.domainObjectSet(Configuration::class.java)

    public val modules: MutableSet<LWJGLModule> = mutableSetOf()

    public fun useModules(vararg modules: LWJGLModule) {
        this.modules += modules
    }

    public fun useModules(modules: Collection<LWJGLModule>) {
        this.modules += modules
    }

    public val platforms: DomainObjectSet<NativePlatform> = objectFactory.domainObjectSet(NativePlatform::class.java)

    public fun defaultPlatforms() {
        linuxX64()

        windowsARM64()
        windowsX86()
        windowsX86_64()
    }

    public fun custom(name: String, artifactClassifier: String) {
        platforms.add(NativePlatform(name, artifactClassifier))
    }

    public fun linuxX64() {

    }

    public fun windowsARM64(name: String = "WinARM64") {
        platforms.add(NativePlatform(name, artifactClassifier = "natives-windows-arm64"))
    }

    public fun windowsX86(name: String = "WinX86") {
        platforms.add(NativePlatform(name, artifactClassifier = "natives-windows-x86"))
    }

    @Suppress("FunctionName")
    public fun windowsX86_64(name: String = "WinX86_64") {
        platforms.add(NativePlatform(name, artifactClassifier = "natives-windows"))
    }

}