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
 * Constants used by this version of the LWJGL3 Gradle plugin.
 *
 * @since   0.1.0
 */
/*
 * We're not actually declaring "const" elements because they are not
 * necessarily constant across versions of the plugin.
 */
@Suppress("MayBeConstant")
public object LWJGLConstants {

    /**
     * The default group name of the coordinates for LWJGL artifacts.
     *
     * @see LWJGLTarget.group
     *
     * @since   0.1.0
     */
    public val DEFAULT_GROUP_NAME: String = "org.lwjgl"

    /**
     * The default LWJGL version.
     *
     * @see LWJGLTarget.version
     *
     * @since   0.1.0
     */
    public val DEFAULT_VERSION: String = "3.3.2"

    /**
     * The name of the property that can be used to configure if the plugin
     * should create an implicit target (in some circumstances).
     *
     * The value of the property should either be `true` or `false` if set.
     *
     * @since   0.1.0
     */
    public val PROPERTY_IMPLICIT_TARGET: String = "com.osmerion.lwjgl3.implicit-target"

}