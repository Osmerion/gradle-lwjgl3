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

import org.gradle.api.Action
import org.gradle.api.DomainObjectSet
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.NamedDomainObjectProvider
import org.gradle.api.artifacts.Configuration
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import org.gradle.api.provider.SetProperty
import javax.inject.Inject

/**
 * TODO doc
 *
 * @since   0.1.0
 */
public abstract class LWJGLTarget @Inject constructor(
    public val name: String,
    private val objectFactory: ObjectFactory
) {

    /**
     * The group name of the GAV coordinates for the LWJGL artifacts.
     *
     * Defaults to [LWJGLConstants.DEFAULT_GROUP_NAME].
     *
     * @since   0.1.0
     */
    public val group: Property<String> = objectFactory.property(String::class.java)

    /**
     * The version of the GAV coordinates for the LWJGL artifacts.
     *
     * Defaults to [LWJGLConstants.DEFAULT_VERSION].
     *
     * @since   0.1.0
     */
    public val version: Property<String> = objectFactory.property(String::class.java)

    /**
     * The LWJGL modules to include for this target.
     *
     * @since   0.1.0
     */
    public val modules: SetProperty<CharSequence> = objectFactory.setProperty(CharSequence::class.java)

    init {
        group.finalizeValueOnRead()
        group.convention(LWJGLConstants.DEFAULT_GROUP_NAME)

        version.finalizeValueOnRead()
        version.convention(LWJGLConstants.DEFAULT_VERSION)

        modules.finalizeValueOnRead()
    }

    public val libConfigurations: DomainObjectSet<Configuration> = objectFactory.domainObjectSet(Configuration::class.java)
    public val nativesConfigurations: DomainObjectSet<Configuration> = objectFactory.domainObjectSet(Configuration::class.java)

    public val platforms: NamedDomainObjectContainer<NativePlatform> = objectFactory.domainObjectContainer(NativePlatform::class.java) { name ->
        objectFactory.newInstance(NativePlatform::class.java, name, this@LWJGLTarget.name)
    }

    public fun platform(name: String, action: Action<NativePlatform>): NamedDomainObjectProvider<NativePlatform> =
        platforms.register(name, action)

    public fun linuxARM32(name: String = "LinuxARM32"): NamedDomainObjectProvider<NativePlatform> = platform(name) {
        artifactClassifier.convention("natives-linux-arm32")

        match {
            os.set(OperatingSystem.Linux)
            arch.set(Architecture.ARM32)
        }
    }

    public fun linuxARM64(name: String = "LinuxARM64"): NamedDomainObjectProvider<NativePlatform> = platform(name) {
        artifactClassifier.convention("natives-linux-arm64")

        match {
            os.set(OperatingSystem.Linux)
            arch.set(Architecture.ARM64)
        }
    }

    public fun linuxX64(name: String = "LinuxX64"): NamedDomainObjectProvider<NativePlatform> = platform(name) {
        artifactClassifier.convention("natives-linux")


        match {
            os.set(OperatingSystem.Linux)
            arch.set(Architecture.X86_64)
        }
    }

    public fun macosARM64(name: String = "MacOsARM64"): NamedDomainObjectProvider<NativePlatform> = platform(name) {
        artifactClassifier.convention("natives-macos-arm64")

        match {
            os.set(OperatingSystem.MacOS)
            arch.set(Architecture.ARM64)
        }
    }

    public fun macosX64(name: String = "MacOsX64"): NamedDomainObjectProvider<NativePlatform> = platform(name) {
        artifactClassifier.convention("natives-macos-arm64")

        match {
            os.set(OperatingSystem.MacOS)
            arch.set(Architecture.X86_64)
        }
    }

    public fun windowsARM64(name: String = "WinARM64"): NamedDomainObjectProvider<NativePlatform> = platform(name) {
        artifactClassifier.convention("natives-windows-arm64")

        match {
            os.set(OperatingSystem.Windows)
            arch.set(Architecture.ARM64)
        }
    }

    public fun windowsX64(name: String = "WinX64"): NamedDomainObjectProvider<NativePlatform> = platform(name) {
        artifactClassifier.convention("natives-windows")

        match {
            os.set(OperatingSystem.Windows)
            arch.set(Architecture.X86_64)
        }
    }

    public fun windowsX86(name: String = "WinX86"): NamedDomainObjectProvider<NativePlatform> = platform(name) {
        artifactClassifier.convention("natives-windows-x86")

        match {
            os.set(OperatingSystem.Windows)
            arch.set(Architecture.X86)
        }
    }

}