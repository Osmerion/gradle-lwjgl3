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

public data class LWJGLModule(
    internal val artifactName: String
) {

    public companion object {

        @JvmStatic
        public val Core: LWJGLModule = LWJGLModule(
            artifactName = "lwjgl"
        )

        @JvmStatic
        public val Assimp: LWJGLModule = LWJGLModule(
            artifactName = "lwjgl-assimp"
        )

        @JvmStatic
        public val BGFX: LWJGLModule = LWJGLModule(
            artifactName = "lwjgl-bgfx"
        )

        @JvmStatic
        public val CUDA: LWJGLModule = LWJGLModule(
            artifactName = "lwjgl-cuda"
        )

        @JvmStatic
        public val EGL: LWJGLModule = LWJGLModule(
            artifactName = "lwjgl-egl"
        )

        @JvmStatic
        public val FMOD: LWJGLModule = LWJGLModule(
            artifactName = "lwjgl-fmod"
        )

        @JvmStatic
        public val FreeType: LWJGLModule = LWJGLModule(
            artifactName = "lwjgl-freetype"
        )

        @JvmStatic
        public val GLFW: LWJGLModule = LWJGLModule(
            artifactName = "lwjgl-glfw"
        )

        @JvmStatic
        public val OpenAL: LWJGLModule = LWJGLModule(
            artifactName = "lwjgl-openal"
        )

        @JvmStatic
        public val OpenCL: LWJGLModule = LWJGLModule(
            artifactName = "lwjgl-opencl"
        )

        @JvmStatic
        public val OpenGL: LWJGLModule = LWJGLModule(
            artifactName = "lwjgl-opengl"
        )

        @JvmStatic
        public val STB: LWJGLModule = LWJGLModule(
            artifactName = "lwjgl-stb"
        )

    }

}