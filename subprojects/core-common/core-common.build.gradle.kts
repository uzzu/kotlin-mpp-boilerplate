plugins {
    id(PluginsId.kotlinMultiPlatformCommon)
}

dependencies {
    implementation(Libs.kotlinStdlibCommon)
    implementation(Libs.coroutinesCoreCommon)
    multiPlatformCommonTestDependencies()
}
