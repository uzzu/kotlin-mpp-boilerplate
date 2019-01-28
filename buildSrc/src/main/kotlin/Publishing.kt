import org.gradle.api.Named
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project
import org.gradle.api.file.DuplicatesStrategy
import org.gradle.api.file.SourceDirectorySet
import org.gradle.api.plugins.ExtensionAware
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.jvm.tasks.Jar
import org.gradle.kotlin.dsl.GroovyBuilderScope
import org.gradle.kotlin.dsl.TaskContainerScope
import org.gradle.kotlin.dsl.create

private const val majorVersion: Int = 0
private const val minorVersion: Int = 1
private val patchVersion = 0
private const val coreModuleName = "core"

val Project.publishingArtifactVersion: String
    get() = "$majorVersion.$minorVersion.$patchVersion"

val Project.publishingArtifactId: String
    get() = project.rootProject.name.let {
        // ArtifactId becomes like below:
        // :core -> ${rootProject.name}
        // :core-foo -> ${rootProject.name}-foo
        project.name.replace(coreModuleName, it)
    }

data class SourceJarProject(
    val name: String,
    val isAndroid: Boolean = false
)

fun TaskContainerScope.createSourceJar(isAndroid: Boolean = false, vararg sourceJarProjects: SourceJarProject): Jar =
    create("sourceJar", Jar::class) {
        dependsOn("classes")
        classifier = "sources"
        setDuplicatesStrategy(DuplicatesStrategy.EXCLUDE)

        val targetSrc = project.mainSourceDirectorySet(isAndroid)
        from(targetSrc)

        sourceJarProjects
            .map { project.project(it.name).mainSourceDirectorySet(it.isAndroid) }
            .forEach { from(it) }
    }

private fun Project.mainSourceDirectorySet(isAndroid: Boolean = false): SourceDirectorySet =
    if (isAndroid) {
        androidMainSourceDirectorySet
    } else {
        kotlinSourceDirectorySet
    }


private val Project.kotlinSourceDirectorySet: SourceDirectorySet
    get() = sourceSets.getByName("main").withGroovyBuilder { getProperty("kotlin") as SourceDirectorySet }

private val Project.androidMainSourceDirectorySet: SourceDirectorySet
    get() = (this as ExtensionAware).extensions.getByName("kotlin").withGroovyBuilder {
        @Suppress("unchecked_cast")
        val sourceSets = withGroovyBuilder { getProperty("sourceSets") } as NamedDomainObjectContainer<Named>
        val mainSourceSet = sourceSets.getByName("main")
        mainSourceSet.withGroovyBuilder { getProperty("kotlin") } as SourceDirectorySet
    }

private inline fun <T> Any.withGroovyBuilder(builder: GroovyBuilderScope.() -> T): T =
    GroovyBuilderScope.of(this).builder()

private val Project.sourceSets: SourceSetContainer
    get() = (this as ExtensionAware).extensions.getByName("sourceSets") as SourceSetContainer
