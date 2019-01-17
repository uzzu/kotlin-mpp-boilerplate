import com.moowork.gradle.node.npm.NpmTask
import com.moowork.gradle.node.task.NodeTask
import org.jetbrains.kotlin.gradle.dsl.KotlinJsOptions
import org.jetbrains.kotlin.gradle.tasks.Kotlin2JsCompile

evaluationDependsOn(":core-common")

plugins {
    id(PluginsId.nodeGradle)
    id(PluginsId.kotlinMultiPlatformJs)
}

dependencies {
    expectedBy(project(":core-common"))
    implementation(Libs.kotlinStdlibJs)
    implementation(Libs.coroutinesCoreJs)
    jsTestDependencies()
}

node {
    version = Versions.node
    download = false
    workDir = file("${rootProject.buildDir}/nodejs")
    nodeModulesDir = rootProject.projectDir
}

tasks {
    val npmSetup by getting
    val compileKotlin2Js by getting(Kotlin2JsCompile::class)
    val compileTestKotlin2Js by getting(Kotlin2JsCompile::class)

    listOf(compileKotlin2Js, compileTestKotlin2Js).forEach {
        it.kotlinOptions {
            moduleKind = "umd"
            sourceMap = true
            sourceMapEmbedSources = "always"
            metaInfo = true
        }
    }

    val installMocha = create("installMocha", NpmTask::class) {
        inputs.property("mochaVersion", Versions.mocha)
        outputs.dir(file("$rootDir/node_modules/mocha"))
        setArgs(
            listOf(
                "install",
                "mocha@${Versions.mocha}",
                "--no-save"
            )
        )
    }

    val populateNodeModules = create("populateNodeModules", Copy::class) {
        dependsOn(npmSetup, compileKotlin2Js, compileTestKotlin2Js, installMocha)

        from(compileKotlin2Js.destinationDir)
        configurations.runtimeClasspath.forEach {
            from(zipTree(it.absolutePath).matching { include("*.js") })
        }
        configurations.testRuntimeClasspath.forEach {
            from(zipTree(it.absolutePath).matching { include("*.js") })
        }

        into("${rootDir}/node_modules")
    }

    val runTestsWithMocha = create("runTestsWithMocha", NodeTask::class) {
        dependsOn(populateNodeModules)
        setScript(file("$rootDir/node_modules/mocha/bin/mocha"))
        setArgs(
            listOf(
                compileTestKotlin2Js.outputFile
            )
        )
    }

    withType<Test> {
        dependsOn(runTestsWithMocha)
    }
}
