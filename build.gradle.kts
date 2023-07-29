import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.ajoberstar.grgit.Grgit
import net.minecrell.pluginyml.bukkit.BukkitPluginDescription
import com.diffplug.gradle.spotless.SpotlessPlugin

plugins {
    java
    `java-library`

    id("com.diffplug.spotless") version "6.18.0"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("org.ajoberstar.grgit") version "5.2.0"
    id("net.minecrell.plugin-yml.bukkit") version "0.6.0"

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
    maven {
        name = "S01 Sonatype"
        url = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
    }
}

dependencies {
    implementation(platform("com.intellectualsites.bom:bom-newest:1.27"))
    compileOnlyApi("io.papermc.paper:paper-api:1.19.3-R0.1-SNAPSHOT")
    compileOnly("com.mojang:authlib:1.5.25")
    compileOnlyApi("com.fastasyncworldedit:FastAsyncWorldEdit-Bukkit")
    implementation("dev.notmyfault.serverlib:ServerLib")
    implementation("org.bstats:bstats-bukkit:3.0.2")
    implementation("org.bstats:bstats-base:3.0.2")
    implementation("io.papermc:paperlib")
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
            include(dependency("io.papermc:paperlib:1.0.8"))
        }
    }
    minimize()
}

spotless {
    java {
        licenseHeaderFile(rootProject.file("HEADER.txt"))
        targetExclude("**/XMaterial.java")
        target("**/*.java")
    }
}

tasks.named<Copy>("processResources") {
    filesMatching("plugin.yml") {
        expand("version" to project.version)
    }
}

tasks.named("build").configure {
    dependsOn("shadowJar")
}
