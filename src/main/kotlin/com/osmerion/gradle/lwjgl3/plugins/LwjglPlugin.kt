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
package com.osmerion.gradle.lwjgl3.plugins

import com.osmerion.gradle.lwjgl3.LwjglConstants
import com.osmerion.gradle.lwjgl3.LwjglExtension
import com.osmerion.gradle.lwjgl3.internal.applyTo
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPlugin

/**
 * The LWJGL Gradle Plugin.
 *
 * @since   0.1.0
 */
public class LwjglPlugin : Plugin<Project> {

    override fun apply(target: Project): Unit = applyTo(target) {
        val lwjgl3 = extensions.create("lwjgl3", LwjglExtension::class.java)

        lwjgl3.targets.all target@{
            libConfiguration.get().dependencies.addAllLater(provider {
                val groupName = this@target.group.get()
                val version = this@target.version.get()

                modules.get().map { lwjglModule ->
                    /*
                     * Kotlin does not guarantee that the string representation returned by
                     * CharSequence#toString() is equal to the CharSequence.
                     */
                    val artifactName = buildString { append(lwjglModule) }

                    @Suppress("UnstableApiUsage")
                    dependencyFactory.create(groupName, artifactName, version)
                }
            })

            platforms.all platform@{
                nativesConfiguration.get().dependencies.addLater(provider {
                    if (this@platform.matcher.matchesCurrent) {
                        @Suppress("UnstableApiUsage")
                        dependencyFactory.create(files(this@platform.configuration))
                    } else {
                        null
                    }
                })
            }

            platforms.configureEach platform@{
                configurationImpl.configure {
                    dependencies.addAllLater(provider {
                        val groupName = this@target.group.get()
                        val version = this@target.version.get()

                        modules.get().map { lwjglModule ->
                            /*
                             * Kotlin does not guarantee that the string representation returned by
                             * CharSequence#toString() is equal to the CharSequence.
                             */
                            val artifactName = buildString { append(lwjglModule) }
                            val classifier = this@platform.artifactClassifier.get()

                            @Suppress("UnstableApiUsage")
                            dependencyFactory.create(groupName, artifactName, version, classifier, "jar")
                        }
                    })
                }
            }
        }

        val implicitTarget = providers.gradleProperty(LwjglConstants.PROPERTY_IMPLICIT_TARGET)

        pluginManager.withPlugin("org.gradle.java") {
            if (implicitTarget.map(String::toBoolean).orNull != false) {
                val lwjgl3MainTarget = lwjgl3.targets.register("main")

                configurations.named(JavaPlugin.IMPLEMENTATION_CONFIGURATION_NAME) {
                    extendsFrom(lwjgl3MainTarget.get().libConfiguration.get())
                }

                configurations.named(JavaPlugin.RUNTIME_ONLY_CONFIGURATION_NAME) {
                    extendsFrom(lwjgl3MainTarget.get().nativesConfiguration.get())
                }
            }
        }
    }

}