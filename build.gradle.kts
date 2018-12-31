import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

buildscript {
    repositories {
        google()
        mavenCentral()
        maven(url = "https://plugins.gradle.org/m2/")
        jcenter()
    }
    dependencies {
        classpath(PluginClasspath.kotlin)
        classpath(PluginClasspath.android)
        classpath(PluginClasspath.androidJunit5)
        classpath(PluginClasspath.kotlinNative)
    }

}
// endregion

plugins {
    base
    id(PluginsId.ktlint)
    id(PluginsId.buildTimeTracker)
}

allProjectsRepositories()

allprojects {
    tasks {
        withType<Test> {
            testLogging {
                showStandardStreams = true
                events("passed", "failed")
            }
        }
    }
}

subprojects {
    apply {
        plugin(PluginsId.ktlint)
    }

    ktlint {
        verbose.set(true)
        android.set(true)
        outputToConsole.set(true)
        reporters.set(listOf(ReporterType.CHECKSTYLE))
        ignoreFailures.set(true)
        ruleSets.set(listOf())
    }
}

buildtimetracker {
    reporters {
        register("summary") {
            options["ordered"] = "true"
            options["barstyle"] = "ascii"
            options["shortenTaskNames"] = "false"
        }
    }
}
