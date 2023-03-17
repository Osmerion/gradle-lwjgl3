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
package com.osmerion.gradle.lwjgl3.plugins

import com.osmerion.gradle.lwjgl3.LWJGLConstants
import com.osmerion.gradle.lwjgl3.LWJGLExtension
import com.osmerion.gradle.lwjgl3.internal.applyTo
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPlugin

/**
 * The LWJGL Gradle Plugin.
 *
 * @since   0.1.0
 */
public class LWJGLPlugin : Plugin<Project> {

    override fun apply(target: Project): Unit = applyTo(target) {
        val lwjgl3 = extensions.create("lwjgl3", LWJGLExtension::class.java)

        // TODO Consider restructuring this to be more lazy
        lwjgl3.targets.all target@{
            platforms.all platform@{
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

                            dependencyFactory.create(groupName, artifactName, version, classifier, "jar")
                        }
                    })
                }

                nativesConfigurations.all {
                    dependencies.addLater(provider {
                        if (this@platform.matcher.matchesCurrent) {
                            dependencyFactory.create(files(this@platform.configuration))
                        } else {
                            null
                        }
                    })
                }
            }

            libConfigurations.all {
                dependencies.addAllLater(provider {
                    val groupName = this@target.group.get()
                    val version = this@target.version.get()

                    modules.get().map { lwjglModule ->
                        /*
                         * Kotlin does not guarantee that the string representation returned by
                         * CharSequence#toString() is equal to the CharSequence.
                         */
                        val artifactName = buildString { append(lwjglModule) }
                        dependencyFactory.create(groupName, artifactName, version)
                    }
                })
            }
        }

        val implicitTarget = providers.gradleProperty(LWJGLConstants.PROPERTY_IMPLICIT_TARGET)

        pluginManager.withPlugin("org.gradle.java") {
            if (implicitTarget.map(String::toBoolean).orNull != false) {
                lwjgl3.targets.register("main") {
                    libConfigurations.addLater(configurations.named(JavaPlugin.IMPLEMENTATION_CONFIGURATION_NAME))
                    nativesConfigurations.addLater(configurations.named(JavaPlugin.RUNTIME_ONLY_CONFIGURATION_NAME))
                    nativesConfigurations.addLater(configurations.named(JavaPlugin.TEST_RUNTIME_ONLY_CONFIGURATION_NAME))
                }
            }
        }
    }

}