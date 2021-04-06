import org.cadixdev.gradle.licenser.LicenseExtension
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("java")
    id("java-library")
    id("org.cadixdev.licenser") version "0.5.1"
    id("com.github.johnrengelman.shadow") version "6.1.0"
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = sourceCompatibility
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
    maven {
        name = "IntellectualSites 3rd Party"
        url = uri("https://mvn.intellectualsites.com/content/repositories/thirdparty")
    }
}

dependencies {
    compileOnlyApi("org.spigotmc:spigot-api:1.16.5-R0.1-SNAPSHOT")
    compileOnly("com.mojang:authlib:1.5.25")
    compileOnlyApi("com.sk89q.worldedit:worldedit-core:7.2.3")
    compileOnly("com.sk89q.worldedit:worldedit-bukkit:7.2.3")
    implementation("de.notmyfault:serverlib:1.0.1")
    implementation("org.bstats:bstats-bukkit:2.2.1")
    implementation("org.bstats:bstats-base:2.2.1")
}

var rootVersion by extra("3.0.1")
var buildNumber by extra("")

if (project.hasProperty("buildnumber")) {
    buildNumber = project.properties["buildnumber"] as String
} else {
    var index = "local"
    buildNumber = index.toString()
}

version = String.format("%s-%s", rootVersion, buildNumber)

tasks.named<ShadowJar>("shadowJar") {
    archiveClassifier.set(null as String?)
    dependencies {
        relocate("de.notmyfault", "net.arcaniax.gopaint") {
            include(dependency("de.notmyfault:serverlib:1.0.1"))
        }
        relocate("org.bstats", "net.arcaniax.gopaint.metrics") {
            include(dependency("org.bstats:bstats-base"))
            include(dependency("org.bstats:bstats-bukkit"))
        }
    }
    minimize()
}

configure<LicenseExtension> {
    header = rootProject.file("HEADER")
    include("**/*.java")
    exclude("**/XMaterial.java")
    newLine = false
}

tasks.named<Copy>("processResources") {
    filesMatching("plugin.yml") {
        expand("version" to project.version)
    }
}

tasks.named("build").configure {
    dependsOn("shadowJar")
}
