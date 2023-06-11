import net.minecrell.pluginyml.bukkit.BukkitPluginDescription
import org.ajoberstar.grgit.Grgit
import java.util.*

plugins {
    java
    `java-library`
    id("com.diffplug.spotless") version "6.18.0"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("org.ajoberstar.grgit") version "5.2.0"
    id("net.minecrell.plugin-yml.bukkit") version "0.5.3"
    idea
}

if (!File("$rootDir/.git").exists()) {
    logger.lifecycle(
            """
    **************************************************************************************
    You need to fork and clone this repository! Don't download a .zip file.
    If you need assistance, consult the GitHub docs: https://docs.github.com/get-started/quickstart/fork-a-repo
    **************************************************************************************
    """.trimIndent()
    ).also { System.exit(1) }
}
var baseVersion by extra("1.0.0")
var extension by extra("")
var snapshot by extra("-SNAPSHOT")

ext {
    val git: Grgit = Grgit.open {
        dir = File("$rootDir/.git")
    }
    val revision = git.head().abbreviatedId
    extension = "%s+%s".format(Locale.ROOT, snapshot, revision)
}

version = "%s%s".format(Locale.ROOT, baseVersion, extension)

repositories {
    mavenCentral()
    maven {
        name = "Paper"
        url = uri("https://papermc.io/repo/repository/maven-public/")
    }
    maven {
        name = "S01 Sonatype"
        url = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
    }
}

dependencies {
    implementation(platform("com.intellectualsites.bom:bom-newest:1.27"))
    compileOnlyApi("io.papermc.paper:paper-api:1.19.3-R0.1-SNAPSHOT")
    compileOnlyApi("com.fastasyncworldedit:FastAsyncWorldEdit-Bukkit")
    implementation("dev.notmyfault.serverlib:ServerLib")
    implementation("org.bstats:bstats-bukkit:3.0.2")
    implementation("org.bstats:bstats-base:3.0.2")
    implementation("io.papermc:paperlib")
}

bukkit {
    name = "BetterGoPaint"
    main = "dev.themeinerlp.bettergopraint.BetterGoPaint"
    authors = listOf("Arcaniax", "TheMeinerLP")
    apiVersion = "1.13"
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


spotless {
    java {
        licenseHeaderFile(rootProject.file("HEADER.txt"))
        targetExclude("**/XMaterial.java")
        target("**/*.java")
    }
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

tasks {
    compileJava {
        options.release.set(17)
        options.encoding = "UTF-8"
    }
    shadowJar {
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

    build {
        dependsOn(shadowJar)
    }
}
