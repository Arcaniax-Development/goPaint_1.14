import org.cadixdev.gradle.licenser.LicenseExtension
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.ajoberstar.grgit.Grgit
import net.minecrell.pluginyml.bukkit.BukkitPluginDescription

plugins {
    java
    `java-library`

    id("org.cadixdev.licenser") version "0.6.1"
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("org.ajoberstar.grgit") version "5.0.0"
    id("net.minecrell.plugin-yml.bukkit") version "0.5.1"

    idea
    eclipse
}

the<JavaPluginExtension>().toolchain {
    languageVersion.set(JavaLanguageVersion.of(17))
}

tasks.compileJava.configure {
    options.release.set(8)
}

configurations.all {
    attributes.attribute(TargetJvmVersion.TARGET_JVM_VERSION_ATTRIBUTE, 17)
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

repositories {
    mavenCentral()
    maven {
        name = "Paper"
        url = uri("https://papermc.io/repo/repository/maven-public/")
    }
    maven {
        name = "Mojang"
        url = uri("https://libraries.minecraft.net/")
    }
}

dependencies {
    implementation(platform("com.intellectualsites.bom:bom-1.18.x:1.10"))
    compileOnlyApi("io.papermc.paper:paper-api:1.18.1-R0.1-SNAPSHOT")
    compileOnly("com.mojang:authlib:1.5.25")
    compileOnly("com.fastasyncworldedit:FastAsyncWorldEdit-Core")
    compileOnly("com.fastasyncworldedit:FastAsyncWorldEdit-Bukkit")
    implementation("dev.notmyfault.serverlib:ServerLib:2.3.1")
    implementation("org.bstats:bstats-bukkit:3.0.0")
    implementation("org.bstats:bstats-base:3.0.0")
    implementation("io.papermc:paperlib:1.0.7")
}

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

version = String.format("%s-%s", rootProject.version, buildNumber)

bukkit {
    name = "goPaint"
    main = "net.arcaniax.gopaint.GoPaintPlugin"
    authors = listOf("Arcaniax")
    apiVersion = "1.13"
    version = project.version.toString()
    depend = listOf("WorldEdit")
    website = "https://www.spigotmc.org/resources/27717/"

    commands {
        register("gopaint") {
            description = "goPaint command"
            aliases = listOf("gp")
        }
    }

    permissions {
        register("gopaint.use") {
            default = BukkitPluginDescription.Permission.Default.OP
        }
        register("gopaint.admin") {
            default = BukkitPluginDescription.Permission.Default.FALSE
        }
        register("gopaint.world.bypass") {
            default = BukkitPluginDescription.Permission.Default.FALSE
        }
    }
}

tasks.named<ShadowJar>("shadowJar") {
    archiveClassifier.set(null as String?)
    dependencies {
        relocate("org.incendo.serverlib", "net.arcaniax.gopaint.serverlib") {
            include(dependency("dev.notmyfault.serverlib:ServerLib:2.3.1"))
        }
        relocate("org.bstats", "net.arcaniax.gopaint.metrics") {
            include(dependency("org.bstats:bstats-base"))
            include(dependency("org.bstats:bstats-bukkit"))
        }
        relocate("io.papermc.lib", "net.arcaniax.gopaint.paperlib") {
            include(dependency("io.papermc:paperlib:1.0.7"))
        }
    }
    minimize()
}

configure<LicenseExtension> {
    header.set(resources.text.fromFile(file("HEADER.txt")))
    include("**/*.java")
    exclude("**/XMaterial.java")
    newLine.set(false)
}

tasks.named<Copy>("processResources") {
    filesMatching("plugin.yml") {
        expand("version" to project.version)
    }
}

tasks.named("build").configure {
    dependsOn("shadowJar")
}
