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

import com.osmerion.gradle.lwjgl3.LwjglExtension
import org.gradle.api.plugins.JavaPlugin
import org.gradle.testfixtures.ProjectBuilder
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

class LwjglPluginUnitTest {

    /*
     * Disabled for now because there is no way to set Gradle properties when
     * using the project builder. See:
     * - https://github.com/gradle/gradle/issues/17638
     * - https://github.com/gradle/gradle/issues/20690
     */

    @Test
    @Disabled
    fun `Test implicit main target disabled`() {
        // TODO set com.osmerion.lwjgl3.implicit-target
        val project = ProjectBuilder.builder().build()

        project.pluginManager.apply(LwjglPlugin::class.java)
        val lwjgl3 = project.extensions.getByType(LwjglExtension::class.java)

        assertEquals(0, lwjgl3.targets.asMap.size)
        project.pluginManager.apply(JavaPlugin::class.java)
        assertEquals(0, lwjgl3.targets.asMap.size)
    }

    @Test
    @Disabled
    fun `Test implicit main target enabled explicitly`() {
        // TODO set com.osmerion.lwjgl3.implicit-target
        val project = ProjectBuilder.builder().build()

        project.pluginManager.apply(LwjglPlugin::class.java)
        val lwjgl3 = project.extensions.getByType(LwjglExtension::class.java)

        assertEquals(0, lwjgl3.targets.asMap.size)
        project.pluginManager.apply(JavaPlugin::class.java)
        assertEquals(1, lwjgl3.targets.asMap.size)
    }

    @Test
    fun `Test implicit main target enabled by default`() {
        val project = ProjectBuilder.builder().build()
        project.pluginManager.apply(LwjglPlugin::class.java)

        val lwjgl3 = project.extensions.getByType(LwjglExtension::class.java)

        assertEquals(0, lwjgl3.targets.asMap.size)
        project.pluginManager.apply(JavaPlugin::class.java)
        assertEquals(1, lwjgl3.targets.asMap.size)
    }

}