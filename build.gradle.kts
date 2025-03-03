import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `java-test-fixtures`

    id("org.springframework.boot") version "3.2.4"
    id("io.spring.dependency-management") version "1.1.4"

    id("org.asciidoctor.jvm.convert") version "4.0.2"

    kotlin("jvm") version "1.9.23"
    kotlin("plugin.spring") version "1.9.23"
    kotlin("plugin.jpa") version "1.9.23"

}

group = "com.asap"
version = ""

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

val kotestVersion = "5.8.1"
val mockkVersion = "1.13.10"
extra["springModulithVersion"] = "1.2.1"


val asciidoctorExt = "asciidoctorExt"
configurations.create(asciidoctorExt) {
    extendsFrom(configurations.testImplementation.get())
}


dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    // spring test
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testFixturesImplementation("org.springframework.boot:spring-boot-starter-test")

    // jpa
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    //mysql
    runtimeOnly("com.mysql:mysql-connector-j")

    // security
    implementation("org.springframework.boot:spring-boot-starter-security")
    testImplementation("org.springframework.security:spring-security-test")

    // kotest
    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")  // Test Framework
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion") // Assertions Library
    testImplementation("io.kotest:kotest-property:$kotestVersion") // Property Testing

    // mockk
    testImplementation("io.mockk:mockk:${mockkVersion}")
    testFixturesImplementation("io.mockk:mockk:${mockkVersion}")

    // fixture monkey
    testImplementation("com.navercorp.fixturemonkey:fixture-monkey-starter-kotlin:1.0.16")
    testFixturesImplementation("com.navercorp.fixturemonkey:fixture-monkey-starter-kotlin:1.0.16")

    // JWT
    implementation("io.jsonwebtoken:jjwt-api:0.12.5")
    implementation("io.jsonwebtoken:jjwt-impl:0.12.5")
    implementation("io.jsonwebtoken:jjwt-jackson:0.12.5")

    // caffeine
    implementation("com.github.ben-manes.caffeine:caffeine:3.1.8")

    // RestDocs
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
    asciidoctorExt("org.springframework.restdocs:spring-restdocs-asciidoctor")
    testFixturesImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")

    // webclient
//    if(System.getProperty("os.name") == "Mac OS X" && System.getProperty("os.arch") == "aarch64")
    runtimeOnly("io.netty:netty-resolver-dns-native-macos:4.1.109.Final:osx-aarch_64")
    implementation("org.springframework.boot:spring-boot-starter-webflux")


    // logger
    implementation("io.github.oshai:kotlin-logging-jvm:5.1.0")

    // kotlin coroutine
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.8.0")


    // spring modulith
    implementation("org.springframework.modulith:spring-modulith-starter-core")
    implementation("org.springframework.modulith:spring-modulith-starter-jpa")

}



dependencyManagement {
    imports {
        mavenBom("org.springframework.modulith:spring-modulith-bom:${property("springModulithVersion")}")
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}

val snippetDir = file("build/generated-snippets")

tasks.test {
    outputs.dir(snippetDir)
    useJUnitPlatform()
}

tasks.asciidoctor {
    inputs.dir(snippetDir)
    dependsOn(tasks.test)
    configurations(asciidoctorExt)
    baseDirFollowsSourceFile()
}

tasks.bootJar{
    dependsOn(tasks.asciidoctor)
    from("build/docs/asciidoc"){
        into("static/docs")
    }
}

tasks.register("copyDocs", Copy::class){
    dependsOn(tasks.bootJar)
    from("build/docs/asciidoc")
    into("src/main/resources/static/docs")
}

tasks.build {
    dependsOn(tasks.getByName("copyDocs"))
}