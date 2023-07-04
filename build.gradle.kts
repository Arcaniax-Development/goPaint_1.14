import io.papermc.hangarpublishplugin.model.Platforms
import net.minecrell.pluginyml.bukkit.BukkitPluginDescription
import org.ajoberstar.grgit.Grgit
import xyz.jpenilla.runpaper.task.RunServer
import java.util.*

plugins {
    java
    `java-library`
    id("com.diffplug.spotless") version "6.18.0"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("org.ajoberstar.grgit") version "5.2.0"
    id("net.minecrell.plugin-yml.bukkit") version "0.5.3"
    id("xyz.jpenilla.run-paper") version "2.1.0"
    idea

    id("io.papermc.hangar-publish-plugin") version "0.0.5"
    id("com.modrinth.minotaur") version "2.+"
    id("org.jetbrains.changelog") version "2.0.0"
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
group = "dev.themeinerlp.bettergopaint"

val minecraftVersion = "1.20.1"
val supportedMinecraftVersions = listOf(
    "1.16.5",
    "1.17",
    "1.17.1",
    "1.18",
    "1.18.1",
    "1.18.2",
    "1.19",
    "1.19.1",
    "1.19.2",
    "1.19.3",
    "1.19.4",
    "1.20",
    "1.20.1",
)

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
    // Paper / Spigot
    compileOnly("io.papermc.paper:paper-api:$minecraftVersion-R0.1-SNAPSHOT")
    // Fawe / WorldEdit
    implementation(platform("com.intellectualsites.bom:bom-newest:1.27"))
    compileOnlyApi("com.fastasyncworldedit:FastAsyncWorldEdit-Bukkit")
    // Utils
    implementation("dev.notmyfault.serverlib:ServerLib")
    implementation("io.papermc:paperlib")
    // Material Utils
    implementation("com.github.cryptomorin:XSeries:9.4.0") { isTransitive = false }
    // Stats
    implementation("org.bstats:bstats-bukkit:3.0.2")
    // Commands
    implementation("cloud.commandframework:cloud-annotations:1.8.3")
    implementation("cloud.commandframework:cloud-minecraft-extras:1.8.3")
    implementation("cloud.commandframework:cloud-paper:1.8.3")
    annotationProcessor("cloud.commandframework:cloud-annotations:1.8.3")
    implementation("me.lucko:commodore:2.2")

}

bukkit {
    name = "BetterGoPaint"
    main = "dev.themeinerlp.bettergopaint.BetterGoPaint"
    authors = listOf("Arcaniax", "TheMeinerLP")
    apiVersion = "1.13"
    depend = listOf("FastAsyncWorldEdit")
    website = "https://github.com/OneLiteFeatherNET/BetterGoPaint"
    softDepend = listOf("goPaint")

    commands {
        register("gopaint") {
            description = "goPaint command"
            aliases = listOf("gp")
        }
    }

    permissions {
        register("bettergopaint.command.admin.reload") {
            default = BukkitPluginDescription.Permission.Default.OP
        }
        register("bettergopaint.use") {
            default = BukkitPluginDescription.Permission.Default.OP
        }
        register("bettergopaint.admin") {
            default = BukkitPluginDescription.Permission.Default.FALSE
        }
        register("bettergopaint.world.bypass") {
            default = BukkitPluginDescription.Permission.Default.FALSE
        }
    }
}

changelog {
    version.set(baseVersion)
    path.set("${project.projectDir}/CHANGELOG.md")
    itemPrefix.set("-")
    keepUnreleasedSection.set(true)
    unreleasedTerm.set("[Unreleased]")
    groups.set(listOf("Added", "Changed", "Deprecated", "Removed", "Fixed", "Security"))
}

hangarPublish {
    publications.register("BetterGoPaint") {
        version.set(project.version.toString())
        channel.set(System.getenv("HANGAR_CHANNEL"))
        changelog.set(
            project.changelog.renderItem(
                project.changelog.getOrNull(baseVersion) ?: project.changelog.getUnreleased()
            )
        )
        apiKey.set(System.getenv("HANGAR_SECRET"))
        owner.set("TheMeinerLP")
        slug.set("BetterGoPaint")

        platforms {
            register(Platforms.PAPER) {
                jar.set(tasks.shadowJar.flatMap { it.archiveFile })
                platformVersions.set(supportedMinecraftVersions)
            }
        }
    }
}

modrinth {
    token.set(System.getenv("MODRINTH_TOKEN"))
    projectId.set("qf7sNg9A")
    versionNumber.set(version.toString())
    versionType.set(System.getenv("MODRINTH_CHANNEL"))
    uploadFile.set(tasks.shadowJar as Any)
    gameVersions.addAll(supportedMinecraftVersions)
    loaders.add("paper")
    loaders.add("bukkit")
    loaders.add("folia")
    changelog.set(
        project.changelog.renderItem(
            project.changelog.getOrNull(baseVersion) ?: project.changelog.getUnreleased()
        )
    )
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
            relocate("com.cryptomorin.xseries", "$group.xseries")
            relocate("org.incendo.serverlib", "$group.serverlib")
            relocate("org.bstats", "$group.metrics")
            relocate("io.papermc.lib", "$group.paperlib")
        }
        minimize()
    }

    build {
        dependsOn(shadowJar)
    }

    supportedMinecraftVersions.forEach { serverVersion ->
        register<RunServer>("run-$serverVersion") {
            minecraftVersion(serverVersion)
            jvmArgs("-DPaper.IgnoreJavaVersion=true", "-Dcom.mojang.eula.agree=true")
            group = "run paper"
            runDirectory.set(file("run-$serverVersion"))
            pluginJars(rootProject.tasks.shadowJar.map { it.archiveFile }.get())
        }
    }
}
