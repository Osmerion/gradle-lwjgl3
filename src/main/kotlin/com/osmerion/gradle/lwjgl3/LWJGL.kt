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

/**
 * Type-safe abstractions for LWJGL modules.
 *
 * @since   0.1.0
 */
public object LWJGL {

    private data class LWJGLModule(
        val artifactName: String
    ) : CharSequence by artifactName

    /**
     * LWJGL
     *
     * @since   0.1.0
     */
    @JvmStatic
    public val Core: CharSequence = LWJGLModule(
        artifactName = "lwjgl"
    )

    /**
     * LWJGL - Assimp bindings
     *
     * @since   0.1.0
     */
    @JvmStatic
    public val Assimp: CharSequence = LWJGLModule(
        artifactName = "lwjgl-assimp"
    )

    /**
     * LWJGL - bgfx bindings
     *
     * @since   0.1.0
     */
    @JvmStatic
    public val BGFX: CharSequence = LWJGLModule(
        artifactName = "lwjgl-bgfx"
    )

    /**
     * LWJGL - CUDA bindings
     *
     * @since   0.1.0
     */
    @JvmStatic
    public val CUDA: CharSequence = LWJGLModule(
        artifactName = "lwjgl-cuda"
    )

    /**
     * LWJGL - EGL bindings
     *
     * @since   0.1.0
     */
    @JvmStatic
    public val EGL: CharSequence = LWJGLModule(
        artifactName = "lwjgl-egl"
    )

    /**
     * LWJGL - FMOD bindings
     *
     * @since   0.1.0
     */
    @JvmStatic
    public val FMOD: CharSequence = LWJGLModule(
        artifactName = "lwjgl-fmod"
    )

    /**
     * LWJGL - FreeType bindings
     *
     * @since   0.1.0
     */
    @JvmStatic
    public val FreeType: CharSequence = LWJGLModule(
        artifactName = "lwjgl-freetype"
    )

    /**
     * LWJGL - GLFW bindings
     *
     * @since   0.1.0
     */
    @JvmStatic
    public val GLFW: CharSequence = LWJGLModule(
        artifactName = "lwjgl-glfw"
    )

    /**
     * LWJGL - HarfBuzz bindings
     *
     * @since   0.1.0
     */
    @JvmStatic
    public val HarfBuzz: CharSequence = LWJGLModule(
        artifactName = "lwjgl-harfbuzz"
    )

    /**
     * LWJGL - hwloc bindings
     *
     * @since   0.1.0
     */
    @JvmStatic
    public val HWLOC: CharSequence = LWJGLModule(
        artifactName = "lwjgl-hwloc"
    )

    /**
     * LWJGL - JAWT bindings
     *
     * @since   0.1.0
     */
    @JvmStatic
    public val JAWT: CharSequence = LWJGLModule(
        artifactName = "lwjgl-jawt"
    )

    /**
     * LWJGL - jemalloc bindings
     *
     * @since   0.1.0
     */
    @JvmStatic
    public val Jemalloc: CharSequence = LWJGLModule(
        artifactName = "lwjgl-jemalloc"
    )

    /**
     * LWJGL - KTX (Khronos Texture) bindings
     *
     * @since   0.1.0
     */
    @JvmStatic
    public val KTX: CharSequence = LWJGLModule(
        artifactName = "lwjgl-ktx"
    )

    /**
     * LWJGL - libdivide bindings
     *
     * @since   0.1.0
     */
    @JvmStatic
    public val LibDivide: CharSequence = LWJGLModule(
        artifactName = "lwjgl-libdivide"
    )

    /**
     * LWJGL - LLVM/Clang bindings
     *
     * @since   0.1.0
     */
    @JvmStatic
    public val LLVM: CharSequence = LWJGLModule(
        artifactName = "lwjgl-llvm"
    )

    /**
     * LWJGL - LMDB bindings
     *
     * @since   0.1.0
     */
    @JvmStatic
    public val LMDB: CharSequence = LWJGLModule(
        artifactName = "lwjgl-lmdb"
    )

    /**
     * LWJGL - LZ4 bindings
     *
     * @since   0.1.0
     */
    @JvmStatic
    public val LZ4: CharSequence = LWJGLModule(
        artifactName = "lwjgl-lz4"
    )

    /**
     * LWJGL - Meow hash bindings
     *
     * @since   0.1.0
     */
    @JvmStatic
    public val Meow: CharSequence = LWJGLModule(
        artifactName = "lwjgl-meow"
    )

    /**
     * LWJGL - meshoptimizer bindings
     *
     * @since   0.1.0
     */
    @JvmStatic
    public val MeshOptimizer: CharSequence = LWJGLModule(
        artifactName = "lwjgl-meshoptimizer"
    )

    /**
     * LWJGL - NanoVG bindings
     *
     * @since   0.1.0
     */
    @JvmStatic
    public val NanoVG: CharSequence = LWJGLModule(
        artifactName = "lwjgl-nanovg"
    )

    /**
     * LWJGL - Native File Dialog bindings
     *
     * @since   0.1.0
     */
    @JvmStatic
    public val NativeFileDialog: CharSequence = LWJGLModule(
        artifactName = "lwjgl-nfd"
    )

    /**
     * LWJGL - Nuklear bindings
     *
     * @since   0.1.0
     */
    @JvmStatic
    public val Nuklear: CharSequence = LWJGLModule(
        artifactName = "lwjgl-nuklear"
    )

    /**
     * LWJGL - ODBC bindings
     *
     * @since   0.1.0
     */
    @JvmStatic
    public val ODBC: CharSequence = LWJGLModule(
        artifactName = "lwjgl-odbc"
    )

    /**
     * LWJGL - OpenAL bindings
     *
     * @since   0.1.0
     */
    @JvmStatic
    public val OpenAL: CharSequence = LWJGLModule(
        artifactName = "lwjgl-openal"
    )

    /**
     * LWJGL - OpenCL bindings
     *
     * @since   0.1.0
     */
    @JvmStatic
    public val OpenCL: CharSequence = LWJGLModule(
        artifactName = "lwjgl-opencl"
    )

    /**
     * LWJGL - OpenGL bindings
     *
     * @since   0.1.0
     */
    @JvmStatic
    public val OpenGL: CharSequence = LWJGLModule(
        artifactName = "lwjgl-opengl"
    )

    /**
     * LWJGL - OpenGL ES bindings
     *
     * @since   0.1.0
     */
    @JvmStatic
    public val OpenGLES: CharSequence = LWJGLModule(
        artifactName = "lwjgl-opengles"
    )

    /**
     * LWJGL - OpenVR bindings
     *
     * @since   0.1.0
     */
    @JvmStatic
    public val OpenVR: CharSequence = LWJGLModule(
        artifactName = "lwjgl-openvr"
    )

    /**
     * LWJGL - OpenXR bindings
     *
     * @since   0.1.0
     */
    @JvmStatic
    public val OpenXR: CharSequence = LWJGLModule(
        artifactName = "lwjgl-openxr"
    )

    /**
     * LWJGL - Opus bindings
     *
     * @since   0.1.0
     */
    @JvmStatic
    public val Opus: CharSequence = LWJGLModule(
        artifactName = "lwjgl-opus"
    )

    /**
     * LWJGL - OVR bindings
     *
     * @since   0.1.0
     */
    @JvmStatic
    public val OVR: CharSequence = LWJGLModule(
        artifactName = "lwjgl-ovr"
    )

    /**
     * LWJGL - par_shapes bindings
     *
     * @since   0.1.0
     */
    @JvmStatic
    public val ParShapes: CharSequence = LWJGLModule(
        artifactName = "lwjgl-par"
    )

    /**
     * LWJGL - Remotery bindings
     *
     * @since   0.1.0
     */
    @JvmStatic
    public val Remotery: CharSequence = LWJGLModule(
        artifactName = "lwjgl-remotery"
    )

    /**
     * LWJGL - rpmalloc bindings
     *
     * @since   0.1.0
     */
    @JvmStatic
    public val Rpmalloc: CharSequence = LWJGLModule(
        artifactName = "lwjgl-rpmalloc"
    )

    /**
     * LWJGL - Shaderc bindings
     *
     * @since   0.1.0
     */
    @JvmStatic
    public val Shaderc: CharSequence = LWJGLModule(
        artifactName = "lwjgl-shaderc"
    )


    /**
     * LWJGL - SPIRV-Cross bindings
     *
     * @since   0.1.0
     */
    @JvmStatic
    public val SPVC: CharSequence = LWJGLModule(
        artifactName = "lwjgl-spvc"
    )

    /**
     * LWJGL - SSE bindings
     *
     * @since   0.1.0
     */
    @JvmStatic
    public val SSE: CharSequence = LWJGLModule(
        artifactName = "lwjgl-sse"
    )

    /**
     * LWJGL - stb bindings
     *
     * @since   0.1.0
     */
    @JvmStatic
    public val STB: CharSequence = LWJGLModule(
        artifactName = "lwjgl-stb"
    )

    /**
     * LWJGL - Tiny OpenEXR bindings
     *
     * @since   0.1.0
     */
    @JvmStatic
    public val TinyEXR: CharSequence = LWJGLModule(
        artifactName = "lwjgl-tinyexr"
    )

    /**
     * LWJGL - Tiny File Dialogs bindings
     *
     * @since   0.1.0
     */
    @JvmStatic
    public val TinyFD: CharSequence = LWJGLModule(
        artifactName = "lwjgl-tinyfd"
    )

    /**
     * LWJGL - AMD Tootle bindings
     *
     * @since   0.1.0
     */
    @JvmStatic
    public val Tootle: CharSequence = LWJGLModule(
        artifactName = "lwjgl-tootle"
    )

    /**
     * LWJGL - Vulkan Memory Allocator bindings
     *
     * @since   0.1.0
     */
    @JvmStatic
    public val VMA: CharSequence = LWJGLModule(
        artifactName = "lwjgl-vma"
    )

    /**
     * LWJGL - Vulkan bindings
     *
     * @since   0.1.0
     */
    @JvmStatic
    public val Vulkan: CharSequence = LWJGLModule(
        artifactName = "lwjgl-vulkan"
    )

    /**
     * LWJGL - xxHash bindings
     *
     * @since   0.1.0
     */
    @JvmStatic
    public val XXHash: CharSequence = LWJGLModule(
        artifactName = "lwjgl-xxhash"
    )

    /**
     * LWJGL - Yoga bindings
     *
     * @since   0.1.0
     */
    @JvmStatic
    public val Yoga: CharSequence = LWJGLModule(
        artifactName = "lwjgl-yoga"
    )

    /**
     * LWJGL - Zstandard bindings
     *
     * @since   0.1.0
     */
    @JvmStatic
    public val Zstd: CharSequence = LWJGLModule(
        artifactName = "lwjgl-zstd"
    )

}