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

import com.osmerion.gradle.lwjgl3.LWJGLExtension
import com.osmerion.gradle.lwjgl3.internal.applyTo
import com.osmerion.gradle.lwjgl3.internal.capitalized
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPlugin

public class LWJGLPlugin : Plugin<Project> {

    override fun apply(target: Project): Unit = applyTo(target) {
        val lwjgl3 = extensions.create("lwjgl3", LWJGLExtension::class.java)

        lwjgl3.targets.configureEach target@{
            platforms.all platform@{
                val platformNativesConfigurationImpl = configurations.register("lwjgl${this@target.name.capitalized()}${name.capitalized()}Impl") {
                    isCanBeConsumed = false
                    isCanBeResolved = true

                    dependencies.addAllLater(provider {
                        val groupName = this@target.group.orElse(lwjgl3.group).get()
                        val version = this@target.version.orElse(lwjgl3.version).get()

                        modules.map { lwjglModule ->
                            dependencyFactory.create(groupName, lwjglModule.artifactName, version, this@platform.artifactClassifier, "jar")
                        }
                    })
                }

                val platformNativesConfiguration = configurations.register("lwjgl${this@target.name.capitalized()}${name.capitalized()}") {
                    isCanBeConsumed = false
                    isCanBeResolved = true
                    isTransitive = true

                    dependencies.addLater(platformNativesConfigurationImpl.map {
                        dependencyFactory.create(it.incoming.artifactView { lenient(true) }.files)
                    })
                }

                // TODO only map the target platform
                nativesConfigurations.all {
                    extendsFrom(platformNativesConfiguration.get())
                }
            }

            libConfigurations.all {
                dependencies.addAllLater(provider {
                    val groupName = this@target.group.orElse(lwjgl3.group).get()
                    val version = this@target.version.orElse(lwjgl3.version).get()

                    modules.map { lwjglModule ->
                        dependencyFactory.create(groupName, lwjglModule.artifactName, version)
                    }
                })
            }
        }

        pluginManager.withPlugin("org.gradle.java") {
            val mainTarget = lwjgl3.targets.create("main")
            mainTarget.libConfigurations.add(configurations.getByName(JavaPlugin.IMPLEMENTATION_CONFIGURATION_NAME))
            mainTarget.nativesConfigurations.addAll(listOf(
                configurations.getByName(JavaPlugin.RUNTIME_ONLY_CONFIGURATION_NAME),
                configurations.getByName(JavaPlugin.TEST_RUNTIME_ONLY_CONFIGURATION_NAME)
            ))
        }
    }

}