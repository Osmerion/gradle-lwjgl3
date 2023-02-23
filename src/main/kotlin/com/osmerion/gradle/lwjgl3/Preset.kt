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

import com.osmerion.gradle.lwjgl3.LWJGLModule.Companion.Assimp
import com.osmerion.gradle.lwjgl3.LWJGLModule.Companion.Core
import com.osmerion.gradle.lwjgl3.LWJGLModule.Companion.GLFW
import com.osmerion.gradle.lwjgl3.LWJGLModule.Companion.OpenAL
import com.osmerion.gradle.lwjgl3.LWJGLModule.Companion.OpenGL
import com.osmerion.gradle.lwjgl3.LWJGLModule.Companion.STB

public enum class Preset(
    private val modules: Set<LWJGLModule>
) : Collection<LWJGLModule> by modules {
    None(Core),
    Everything(),
    GettingStarted(),
    MinimalOpenGL(Core, Assimp, GLFW, OpenAL, OpenGL, STB),
    MinimalOpenGLES,
    MinimalVulkan;

    constructor(vararg modules: LWJGLModule): this(setOf(*modules))

}