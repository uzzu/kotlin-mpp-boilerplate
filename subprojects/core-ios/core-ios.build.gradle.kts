import org.jetbrains.kotlin.gradle.plugin.experimental.internal.KotlinNativeMainComponent
import org.jetbrains.kotlin.gradle.plugin.experimental.internal.OutputKind
import org.jetbrains.kotlin.gradle.tasks.KotlinNativeCompile

evaluationDependsOn(":core-common")

plugins {
    id(PluginsId.kotlinMultiPlatformNative)
}

dependencies {
    expectedBy(project(":core-common"))
    implementation(Libs.coroutinesCoreNative)
}

sourceSets {
    getByName("main") {
        component(Action {
            // https://github.com/JetBrains/kotlin-native/issues/1807
            targets = listOf("ios_x64", "ios_arm64")
            extraOpts("-Xuse-experimental=kotlin.Experimental")
            if (this is KotlinNativeMainComponent) {
                outputKinds.set(listOf(OutputKind.FRAMEWORK))
            }
        })
    }
    getByName("test") {
        component(Action {
            targets = listOf("ios_x64")
            extraOpts("-tr", "-Xuse-experimental=kotlin.Experimental")
        })
    }
}

tasks {
    "test" {
        enabled = false
        dependsOn("compileTestDebugKotlinNative")
        doLast {
            val compileTestDebugKotlinNative by getting
            val executable = compileTestDebugKotlinNative.withGroovyBuilder {
                getProperty("outputFile") as File
            }.absolutePath
            exec {
                commandLine("xcrun", "simctl", "spawn", "iPhone 8", executable)
            }
        }
    }
}

// https://github.com/willowtreeapps/assertk/pull/112
afterEvaluate {
    tasks {
        "compileTestDebugKotlinNative" {
            enabled = false
        }
    }
}
