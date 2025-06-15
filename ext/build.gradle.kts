import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
    id("maven-publish")
    kotlin("plugin.serialization") version "1.9.22"
    id("com.gradleup.shadow") version "8.3.0"
    id("com.google.protobuf")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    val libVersion: String by project
    compileOnly("com.github.brahmkshatriya:echo:$libVersion")
    compileOnly("org.jetbrains.kotlin:kotlin-stdlib:2.1.0")

    testImplementation("junit:junit:4.13.2")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.10.1")
    testImplementation("com.github.brahmkshatriya:echo:$libVersion")
}

// Extension properties goto `gradle.properties` to set values

val extType: String by project
val extId: String by project
val extClass: String by project

val extIconUrl: String? by project
val extName: String by project
val extDescription: String? by project

val extAuthor: String by project
val extAuthorUrl: String? by project

val extRepoUrl: String? by project
val extUpdateUrl: String? by project

val gitHash = execute("git", "rev-parse", "HEAD").take(7)
val gitCount = execute("git", "rev-list", "--count", "HEAD").toInt()
val verCode = gitCount
val verName = gitHash

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            groupId = "dev.brahmkshatriya.echo.extension"
            artifactId = extId
            version = verName

            from(components["java"])
        }
    }
}

tasks {
    val shadowJar by getting(ShadowJar::class) {
        archiveBaseName.set(extId)
        archiveVersion.set(verName)
        manifest {
            attributes(
                mapOf(
                    "Extension-Id" to extId,
                    "Extension-Type" to extType,
                    "Extension-Class" to extClass,

                    "Extension-Version-Code" to verCode,
                    "Extension-Version-Name" to verName,

                    "Extension-Icon-Url" to extIconUrl,
                    "Extension-Name" to extName,
                    "Extension-Description" to extDescription,

                    "Extension-Author" to extAuthor,
                    "Extension-Author-Url" to extAuthorUrl,

                    "Extension-Repo-Url" to extRepoUrl,
                    "Extension-Update-Url" to extUpdateUrl
                )
            )
        }
    }
}

fun execute(vararg command: String): String {
    val processBuilder = ProcessBuilder(*command)
    val hashCode = command.joinToString().hashCode().toString()
    val output = File.createTempFile(hashCode, "")
    processBuilder.redirectOutput(output)
    val process = processBuilder.start()
    process.waitFor()
    return output.readText().dropLast(1)
}