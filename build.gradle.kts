import net.minecrell.gradle.licenser.LicenseExtension

plugins {
    id("java")
    id("net.minecrell.licenser") version "0.4.1"
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

repositories {
    mavenCentral()
    jcenter()
    maven {
        name = "Spigot"
        url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots")
    }
    maven {
        name = "Mojang"
        url = uri("https://libraries.minecraft.net/")
    }
    maven {
        name = "WorldEdit"
        url = uri("https://maven.enginehub.org/repo/")
    }
}

dependencies {
    "compileOnly"("org.spigotmc:spigot-api:1.16.2-R0.1-SNAPSHOT")
    "implementation"("com.mojang:authlib:1.5.25")
    "implementation"("com.sk89q.worldedit:worldedit-core:7.2.0")
    "implementation"("com.sk89q.worldedit:worldedit-bukkit:7.2.0")
}

version = "3.0.0"

configure<LicenseExtension> {
    header = rootProject.file("HEADER")
    include("**/*.java")
    exclude("**/XMaterial.java")

}

tasks.named<Copy>("processResources") {
    filesMatching("plugin.yml") {
        expand("version" to project.version)
    }
}
