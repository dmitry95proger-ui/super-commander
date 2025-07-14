import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.compose.compose

plugins {
    kotlin("jvm")
    id("org.jetbrains.compose")
    //kotlin("jvm") version "1.8.10"
   // id("org.jetbrains.compose") version "1.4.6"
}

group = "com.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

dependencies {
    // Note, if you develop a library, you should use compose.desktop.common.
    // compose.desktop.currentOs should be used in launcher-sourceSet
    // (in a separate module for demo project and in testMain).
    // With compose.desktop.common you will also lose @Preview functionality
    implementation(compose.desktop.currentOs)
    implementation(compose.animation)
    implementation("net.java.dev.jna:jna:5.12.1")
    implementation(compose.material3)
    implementation(compose.material)
    //implementation ("com.airbnb.android:lottie-compose:5.2.0")
    implementation("net.coobird:thumbnailator:0.4.13")
        //implementation("io.coil-kt:coil-compose:2.4.0")
    //implementation("io.coil-kt.coil3:coil-compose:3.1.0")
    //implementation("com.github.cgwilliams:compose-image-loader:1.10.0")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-swing:1.7.3")

}

tasks.jar {

    duplicatesStrategy = DuplicatesStrategy.EXCLUDE // Или другой подход: PICK_FIRST, FAIL

    manifest {
        attributes["Main-Class"] = "MainKt"
    }
    from({
        configurations.runtimeClasspath.get().filter { it.name.endsWith(".jar") }.map { zipTree(it) }
    })
}

kotlin {
    sourceSets{
        dependencies {

            //implementation("org.jetbrains.androidx.navigation:navigation-compose:2.8.0-alpha10")
        }
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "SuperCommander"
            packageVersion = "1.0.0"
        }
    }
}
