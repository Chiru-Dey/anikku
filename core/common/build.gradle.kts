plugins {
    id("mihon.library")
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("com.github.ben-manes.versions")
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
            }
        }
    }
    
    jvm("desktop") {
        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
            }
        }
    }
    
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(projects.i18n)
                
                api(libs.logcat)
                api(libs.rxjava)
                
                api(libs.okhttp.core)
                api(libs.okhttp.logging)
                api(libs.okhttp.brotli)
                api(libs.okhttp.dnsoverhttps)
                api(libs.okio)
                
                api(kotlinx.coroutines.core)
                api(kotlinx.serialization.json)
                api(kotlinx.serialization.json.okio)
                
                implementation(libs.jsoup)
                implementation(libs.natural.comparator)
                implementation(libs.bundles.js.engine)
            }
        }
        
        val androidMain by getting {
            dependencies {
                implementation(projects.i18nSy)
                
                implementation(libs.image.decoder)
                implementation(libs.unifile)
                implementation(libs.libarchive)
                implementation(libs.preferencektx)
                implementation(aniyomilibs.ffmpeg.kit)
                implementation(sylibs.xlog)
                implementation(sylibs.exifinterface)
                implementation(libs.injekt)
                implementation(aniyomilibs.torrentserver)
            }
        }
        
        val desktopMain by getting {
            dependencies {
                // Desktop-specific dependencies
                implementation(desktopLibs.commons.io)
                implementation(desktopLibs.kermit)
            }
        }
        
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
    
    compilerOptions {
        freeCompilerArgs.addAll(
            "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
            "-opt-in=kotlinx.serialization.ExperimentalSerializationApi",
        )
    }
}

android {
    namespace = "eu.kanade.tachiyomi.core.common"
    
    sourceSets {
        named("main") {
            manifest.srcFile("src/androidMain/AndroidManifest.xml")
            res.srcDirs("src/androidMain/res")
        }
    }
}

