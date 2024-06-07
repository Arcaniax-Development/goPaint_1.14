import io.papermc.hangarpublishplugin.model.Platforms
import net.minecrell.pluginyml.bukkit.BukkitPluginDescription
import xyz.jpenilla.runpaper.task.RunServer
import kotlin.system.exitProcess

plugins {
    id("idea")
    id("java")
    id("java-library")
    id("olf.build-logic")

    alias(libs.plugins.spotless)
    alias(libs.plugins.minotaur)
    alias(libs.plugins.shadow)
    alias(libs.plugins.hangar.publish.plugin)
    alias(libs.plugins.plugin.yml.bukkit)
    alias(libs.plugins.run.paper)
}

if (!File("$rootDir/.git").exists()) {
    logger.lifecycle("""
    **************************************************************************************
    You need to fork and clone this repository! Don't download a .zip file.
    If you need assistance, consult the GitHub docs: https://docs.github.com/get-started/quickstart/fork-a-repo
    **************************************************************************************
    """.trimIndent()
    ).also { exitProcess(1) }
}

allprojects {
    group = "net.onelitefeather.bettergopaint"
    version = property("projectVersion") as String // from gradle.properties
}
group = "net.onelitefeather.bettergopaint"

val supportedMinecraftVersions = listOf(
        "1.20",
        "1.20.1",
        "1.20.2",
        "1.20.3",
        "1.20.4",
        "1.20.5",
        "1.20.6"
)

repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/")
}

dependencies {
    // Paper / Spigot
    compileOnly(libs.paper)
    // Fawe / WorldEdit
    implementation(platform(libs.fawe.bom))
    compileOnlyApi(libs.fawe.bukkit)
    // Utils
    implementation(libs.serverlib)
    implementation(libs.paperlib)
    // Material Utils
    implementation(libs.xseries) { isTransitive = false }
    // Stats
    implementation(libs.bstats)
    // Commands
    implementation(libs.cloud.annotations)
    implementation(libs.cloud.minecraft.extras)
    implementation(libs.cloud.paper)
    annotationProcessor(libs.cloud.annotations)
}

bukkit {
    name = "BetterGoPaint"
    main = "net.onelitefeather.bettergopaint.BetterGoPaint"
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


spotless {
    java {
        licenseHeaderFile(rootProject.file("HEADER.txt"))
        targetExclude("**/XMaterial.java")
        target("**/*.java")
    }
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

tasks {
    jar {
        archiveClassifier.set("unshaded")
    }
    compileJava {
        options.release.set(21)
        options.encoding = "UTF-8"
    }
    shadowJar {
        archiveClassifier.set("")
        dependencies {
            relocate("com.cryptomorin.xseries", "${rootProject.group}.xseries")
            relocate("org.incendo.serverlib", "${rootProject.group}.serverlib")
            relocate("org.bstats", "${rootProject.group}.metrics")
            relocate("io.papermc.lib", "${rootProject.group}.paperlib")
        }
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

val branch = rootProject.branchName()
val baseVersion = project.version as String
val isRelease = !baseVersion.contains('-')
val isMainBranch = branch == "master"
if (!isRelease || isMainBranch) { // Only publish releases from the main branch
    val suffixedVersion = if (isRelease) baseVersion else baseVersion + "+" + System.getenv("GITHUB_RUN_NUMBER")
    val changelogContent = if (isRelease) {
        "See [GitHub](https://github.com/OneLiteFeatherNET/BetterGoPaint) for release notes."
    } else {
        val commitHash = rootProject.latestCommitHash()
        "[$commitHash](https://github.com/OneLiteFeatherNET/BetterGoPaint/commit/$commitHash) ${rootProject.latestCommitMessage()}"
    }
    hangarPublish {
        publications.register("BetterGoPaint") {
            version.set(suffixedVersion)
            channel.set(if (isRelease) "Release" else if (isMainBranch) "Snapshot" else "Alpha")
            changelog.set(changelogContent)
            apiKey.set(System.getenv("HANGAR_SECRET"))
            id.set("BetterGoPaint")
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
        versionType.set(if (isRelease) "release" else if (isMainBranch) "beta" else "alpha")
        versionNumber.set(suffixedVersion)
        versionName.set(suffixedVersion)
        changelog.set(changelogContent)
        uploadFile.set(tasks.shadowJar.flatMap { it.archiveFile })
        gameVersions.addAll(supportedMinecraftVersions)
        loaders.add("paper")
        loaders.add("bukkit")
        loaders.add("folia")
    }
}
