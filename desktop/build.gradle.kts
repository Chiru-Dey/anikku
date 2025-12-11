import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("jvm")
    alias(libs.plugins.compose)
    alias(libs.plugins.kotlinx.serialization)
}

group = "eu.kanade.tachiyomi"
version = "1.0.0"

dependencies {
    // Compose Desktop
    implementation(compose.desktop.currentOs)
    implementation(compose.material3)
    implementation(compose.materialIconsExtended)
    implementation(compose.components.resources)

    // Desktop-specific dependencies from catalog
    implementation(desktopLibs.compose.desktop.runtime)
    implementation(desktopLibs.compose.material3.desktop)

    // Video Player - VLCJ
    implementation(desktopLibs.bundles.vlcj)

    // JNA for native calls
    implementation(desktopLibs.bundles.jna)

    // Dependency Injection - Koin
    implementation(desktopLibs.bundles.koin)

    // HTTP Client - Ktor
    implementation(desktopLibs.bundles.ktor.client)

    // Image Loading - Coil 3
    implementation(desktopLibs.bundles.coil3)

    // Settings Storage
    implementation(desktopLibs.bundles.multiplatform.settings)

    // Logging
    implementation(desktopLibs.kermit)

    // File Utilities
    implementation(desktopLibs.commons.io)

    // Windows Notifications
    implementation(desktopLibs.jpowershell)

    // Coroutines
    implementation(desktopLibs.bundles.kotlinx.coroutines)

    // Serialization
    implementation(desktopLibs.kotlinx.serialization.json)

    // DateTime
    implementation(desktopLibs.kotlinx.datetime)

    // Project modules (will be added as they're converted to KMP)
    // implementation(projects.core.common)
    // implementation(projects.domain)
    // implementation(projects.data)
    // implementation(projects.sourceApi)
    // implementation(projects.presentationCore)
    // implementation(projects.i18n)
}

kotlin {
    jvmToolchain(17)
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "Anikku"
            packageVersion = "1.0.0"
            description = "Anikku - Anime Player and Library Manager"
            copyright = "© 2024 Anikku Contributors"
            vendor = "Anikku"

            windows {
                iconFile.set(project.file("src/main/resources/icon.ico"))
                menuGroup = "Anikku"
                perUserInstall = true
                dirChooser = true
                shortcut = true
                menu = true
                upgradeUuid = "anikku-desktop-app"
            }

            linux {
                iconFile.set(project.file("src/main/resources/icon.png"))
            }

            macOS {
                iconFile.set(project.file("src/main/resources/icon.icns"))
            }
        }

        buildTypes.release.proguard {
            configurationFiles.from(project.file("proguard-rules.pro"))
        }
    }
}

// Task to run the desktop application during development
tasks.register("runDesktop") {
    dependsOn("run")
}
