plugins {
    `java-library`
}

repositories {
    mavenCentral()
}

dependencies {
    api("org.checkerframework:checker-qual:3.21.4")
    api("com.google.errorprone:error_prone_annotations:2.13.1")
    api("org.jspecify:jspecify:0.2.0")
}

tasks.withType<JavaCompile> {
    // options.release.set(11)
}
