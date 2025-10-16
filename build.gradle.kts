plugins {
    java
    id("org.springframework.boot") version "3.4.2"
    id("io.spring.dependency-management") version "1.1.7"
//    id("com.google.protobuf") version "0.9.4"
}

group = "ru.quest_bot"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

val postgresqlVersion = "42.7.2"
val javaxAnnotationVersion = "1.3.2"
val grpcVersion = "1.58.0"
val protobufVersion = "3.24.0"
val openfeignVersion = "4.2.0"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-amqp")

    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.springframework.boot:spring-boot-starter-cache")
    implementation("com.fasterxml.jackson.core:jackson-databind")


    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.postgresql:postgresql:$postgresqlVersion")
    runtimeOnly("org.postgresql:postgresql")


    implementation("javax.annotation:javax.annotation-api:$javaxAnnotationVersion")

//    //REST
//    implementation("org.springframework.boot:spring-boot-starter-web")
//    implementation("org.springframework.cloud:spring-cloud-starter-openfeign:$openfeignVersion")
//
//    // gRPC
//    implementation("net.devh:grpc-spring-boot-starter:3.0.0.RELEASE")
//    implementation("io.grpc:grpc-protobuf:$grpcVersion")
//    implementation("io.grpc:grpc-stub:$grpcVersion")
//    implementation("com.google.protobuf:protobuf-java:$protobufVersion")
//    runtimeOnly("io.grpc:grpc-netty-shaded:$grpcVersion")

    annotationProcessor("org.projectlombok:lombok")
    compileOnly("org.projectlombok:lombok")


    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

// Настройка protobuf
//protobuf {
//    protoc {
//        artifact = "com.google.protobuf:protoc:$protobufVersion"
//    }
//    plugins {
//        create("grpc") {
//            artifact = "io.grpc:protoc-gen-grpc-java:$grpcVersion"
//        }
//    }
//    generateProtoTasks {
//        all().configureEach {
//            plugins {
//                create("grpc")
//            }
//        }
//    }
//}
//
//// Указываем, где лежат .proto файлы
//sourceSets {
//    main {
//        proto {
//            srcDir("src/main/proto")
//        }
//    }
//}


tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<ProcessResources> {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}
