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
plugins {
    id("com.osmerion.base-conventions")
    `maven-publish`
    signing
}

publishing {
    repositories {
        val sonatypeUsername: String? by project
        val sonatypePassword: String? by project
        val stagingRepositoryId: String? by project

        if (sonatypeUsername != null && sonatypePassword != null && stagingRepositoryId != null) {
            maven {
                url = uri("https://s01.oss.sonatype.org/service/local/staging/deployByRepositoryId/$stagingRepositoryId/")

                credentials {
                    username = sonatypeUsername
                    password = sonatypePassword
                }
            }
        }
    }
    publications.withType<MavenPublication>().configureEach {
        pom {
            name.set(project.name)
            url.set("https://github.com/Osmerion/gradle-lwjgl3")

            licenses {
                license {
                    name.set("BSD-3-Clause")
                    url.set("https://github.com/Osmerion/gradle-lwjgl3/blob/master/LICENSE")
                    distribution.set("repo")
                }
            }

            developers {
                developer {
                    id.set("TheMrMilchmann")
                    name.set("Leon Linhart")
                    email.set("themrmilchmann@gmail.com")
                    url.set("https://github.com/TheMrMilchmann")
                }
            }

            scm {
                connection.set("scm:git:git://github.com/Osmerion/gradle-lwjgl3.git")
                developerConnection.set("scm:git:git://github.com/Osmerion/gradle-lwjgl3.git")
                url.set("https://github.com/Osmerion/gradle-lwjgl3.git")
            }
        }
    }
}

signing {
    // Only require signing when publishing to a non-local maven repository
    setRequired { gradle.taskGraph.allTasks.any { it is PublishToMavenRepository } }

    val signingKey: String? by project
    val signingPassword: String? by project
    useInMemoryPgpKeys(signingKey, signingPassword)

    sign(publishing.publications)
}