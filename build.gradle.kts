import org.cadixdev.gradle.licenser.LicenseExtension
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.ajoberstar.grgit.Grgit

plugins {
    id("java")
    id("java-library")
    id("org.cadixdev.licenser") version "0.5.1"
    id("com.github.johnrengelman.shadow") version "6.1.0"
    id("org.ajoberstar.grgit") version "4.1.0"
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
    maven {
        name = "Spigot"
        url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots")
    }
    maven {
        name = "Paper"
        url = uri("https://papermc.io/repo/repository/maven-public/")
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
    compileOnlyApi("com.sk89q.worldedit:worldedit-core:7.2.5")
    compileOnly("com.sk89q.worldedit:worldedit-bukkit:7.2.3")
    implementation("org.incendo.serverlib:ServerLib:2.0.0")
    implementation("org.bstats:bstats-bukkit:2.2.1")
    implementation("org.bstats:bstats-base:2.2.1")
    implementation("io.papermc:paperlib:1.0.6")
}

var rootVersion by extra("3.0.1")
var buildNumber by extra("")
ext {
    val git: Grgit = Grgit.open {
        dir = File("$rootDir/.git")
    }
    val commit: String? = git.head().abbreviatedId
    buildNumber = if (project.hasProperty("buildnumber")) {
        project.properties["buildnumber"] as String
    } else {
        commit.toString()
    }
}

version = String.format("%s-%s", rootVersion, buildNumber)

tasks.named<ShadowJar>("shadowJar") {
    archiveClassifier.set(null as String?)
    dependencies {
        relocate("org.incendo.serverlib", "net.arcaniax.gopaint.serverlib") {
            include(dependency("org.incendo.serverlib:ServerLib:2.0.0"))
        }
        relocate("org.bstats", "net.arcaniax.gopaint.metrics") {
            include(dependency("org.bstats:bstats-base"))
            include(dependency("org.bstats:bstats-bukkit"))
        }
        relocate("io.papermc.lib", "com.arcaniax.gopaint.paperlib") {
            include(dependency("io.papermc:paperlib:1.0.6"))
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
