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
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.NamedDomainObjectProvider
import org.gradle.api.artifacts.Configuration
import org.gradle.api.artifacts.ConfigurationContainer
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import org.gradle.api.provider.SetProperty
import javax.inject.Inject

/**
 * A _target_ serves as abstraction to handle multiple LWJGL setups (for
 * example, in different source sets) in a single Gradle project.
 *
 * @see LwjglExtension.targets
 *
 * @since   0.1.0
 */
public abstract class LwjglTarget @Inject constructor(
    public val name: String,
    lwjglExtension: LwjglExtension,
    objectFactory: ObjectFactory,
    configurations: ConfigurationContainer
) {

    /**
     * The group name of the GAV coordinates for the LWJGL artifacts.
     *
     * Defaults to [LwjglExtension.group].
     *
     * @since   0.1.0
     */
    public val group: Property<String> = objectFactory.property(String::class.java)

    /**
     * The version of the GAV coordinates for the LWJGL artifacts.
     *
     * Defaults to [LwjglExtension.version].
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
        group.convention(lwjglExtension.group)

        version.finalizeValueOnRead()
        version.convention(lwjglExtension.version)

        modules.finalizeValueOnRead()
    }

    /**
     * The configuration for this target providing the library artifacts.
     *
     * @since   0.1.0
     */
    public val libConfiguration: NamedDomainObjectProvider<Configuration> = configurations.register("lwjgl${name.capitalized()}Libs")

    /**
     * The configuration for this target providing the artifacts containing the
     * native libraries for the platform matching the host.
     *
     * @since   0.1.0
     */
    public val nativesConfiguration: NamedDomainObjectProvider<Configuration> = configurations.register("lwjgl${name.capitalized()}Natives")

    /**
     * The platforms included in this target.
     *
     * @since   0.1.0
     */
    public val platforms: NamedDomainObjectContainer<NativePlatform> = objectFactory.domainObjectContainer(NativePlatform::class.java) { name ->
        objectFactory.newInstance(NativePlatform::class.java, name, this@LwjglTarget.name)
    }

    /**
     * Adds a platform with the given name and configuration.
     *
     * @param name      the name for the platform
     * @param action    the action to configure the platform
     *
     * @since   0.1.0
     */
    public fun platform(name: String, action: Action<NativePlatform>): NamedDomainObjectProvider<NativePlatform> =
        platforms.register(name, action)

    /**
     * Adds a platform configured to match FreeBSD x64.
     *
     * @param name  the platform name (default `FreeBsdX64`)
     *
     * @since   0.3.0
     */
    @JvmOverloads
    public fun freebsdX64(name: String = "FreeBsdX64"): NamedDomainObjectProvider<NativePlatform> = platform(name) {
        artifactClassifier.convention("natives-freebsd")

        match {
            os.set(OperatingSystem.FreeBSD)
            arch.set(Architecture.X86_64)
        }
    }

    /**
     * Adds a platform configured to match Windows AArch32.
     *
     * @param name  the platform name (default `LinuxARM32`)
     *
     * @since   0.1.0
     */
    @JvmOverloads
    public fun linuxARM32(name: String = "LinuxARM32"): NamedDomainObjectProvider<NativePlatform> = platform(name) {
        artifactClassifier.convention("natives-linux-arm32")

        match {
            os.set(OperatingSystem.Linux)
            arch.set(Architecture.ARM32)
        }
    }

    /**
     * Adds a platform configured to match Linux AArch64.
     *
     * @param name  the platform name (default `LinuxARM64`)
     *
     * @since   0.1.0
     */
    @JvmOverloads
    public fun linuxARM64(name: String = "LinuxARM64"): NamedDomainObjectProvider<NativePlatform> = platform(name) {
        artifactClassifier.convention("natives-linux-arm64")

        match {
            os.set(OperatingSystem.Linux)
            arch.set(Architecture.ARM64)
        }
    }

    /**
     * Adds a platform configured to match Linux PowerPC 64 LE.
     *
     * @param name  the platform name (default `LinuxPPC64LE`)
     *
     * @since   0.3.0
     */
    @JvmOverloads
    public fun linuxPPC64LE(name: String = "LinuxPPC64LE"): NamedDomainObjectProvider<NativePlatform> = platform(name) {
        artifactClassifier.convention("natives-linux-ppc64le")

        match {
            os.set(OperatingSystem.Linux)
            arch.set(Architecture.PowerPC64LE)
        }
    }

    /**
     * Adds a platform configured to match Linux RISC-V 64.
     *
     * @param name  the platform name (default `LinuxRISCV64`)
     *
     * @since   0.3.0
     */
    @JvmOverloads
    public fun linuxRISCV64(name: String = "LinuxRISCV64"): NamedDomainObjectProvider<NativePlatform> = platform(name) {
        artifactClassifier.convention("natives-linux-riscv64")

        match {
            os.set(OperatingSystem.Linux)
            arch.set(Architecture.RISCV64)
        }
    }

    /**
     * Adds a platform configured to match Linux x64.
     *
     * @param name  the platform name (default `LinuxX64`)
     *
     * @since   0.1.0
     */
    @JvmOverloads
    public fun linuxX64(name: String = "LinuxX64"): NamedDomainObjectProvider<NativePlatform> = platform(name) {
        artifactClassifier.convention("natives-linux")

        match {
            os.set(OperatingSystem.Linux)
            arch.set(Architecture.X86_64)
        }
    }

    /**
     * Adds a platform configured to match macOS AArch64.
     *
     * @param name  the platform name (default `MacOsARM64`)
     *
     * @since   0.1.0
     */
    @JvmOverloads
    public fun macosARM64(name: String = "MacOsARM64"): NamedDomainObjectProvider<NativePlatform> = platform(name) {
        artifactClassifier.convention("natives-macos-arm64")

        match {
            os.set(OperatingSystem.MacOS)
            arch.set(Architecture.ARM64)
        }
    }

    /**
     * Adds a platform configured to match macOS x64.
     *
     * @param name  the platform name (default `MacOsX64`)
     *
     * @since   0.1.0
     */
    @JvmOverloads
    public fun macosX64(name: String = "MacOsX64"): NamedDomainObjectProvider<NativePlatform> = platform(name) {
        artifactClassifier.convention("natives-macos")

        match {
            os.set(OperatingSystem.MacOS)
            arch.set(Architecture.X86_64)
        }
    }

    /**
     * Adds a platform configured to match Windows AArch64.
     *
     * @param name  the platform name (default `WinARM64`)
     *
     * @since   0.1.0
     */
    @JvmOverloads
    public fun windowsARM64(name: String = "WinARM64"): NamedDomainObjectProvider<NativePlatform> = platform(name) {
        artifactClassifier.convention("natives-windows-arm64")

        match {
            os.set(OperatingSystem.Windows)
            arch.set(Architecture.ARM64)
        }
    }

    /**
     * Adds a platform configured to match Windows x64.
     *
     * @param name  the platform name (default `WinX64`)
     *
     * @since   0.1.0
     */
    @JvmOverloads
    public fun windowsX64(name: String = "WinX64"): NamedDomainObjectProvider<NativePlatform> = platform(name) {
        artifactClassifier.convention("natives-windows")

        match {
            os.set(OperatingSystem.Windows)
            arch.set(Architecture.X86_64)
        }
    }

    /**
     * Adds a platform configured to match Windows x86.
     *
     * @param name  the platform name (default `WinX86`)
     *
     * @since   0.1.0
     */
    @JvmOverloads
    public fun windowsX86(name: String = "WinX86"): NamedDomainObjectProvider<NativePlatform> = platform(name) {
        artifactClassifier.convention("natives-windows-x86")

        match {
            os.set(OperatingSystem.Windows)
            arch.set(Architecture.X86)
        }
    }

}