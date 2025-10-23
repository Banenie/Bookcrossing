plugins {
	java
	id("com.google.protobuf") version "0.9.5"
	id("org.springframework.boot") version "3.4.5"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "com.example"
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

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-web")
	compileOnly("org.projectlombok:lombok")
	runtimeOnly("org.postgresql:postgresql")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	implementation("io.grpc:grpc-netty-shaded:1.51.0")
    implementation("io.grpc:grpc-protobuf:1.51.0")
    implementation("io.grpc:grpc-stub:1.51.0")
	implementation("com.google.protobuf:protobuf-java:4.30.2")
	testImplementation("org.apache.httpcomponents.client5:httpclient5")
	implementation("javax.annotation:javax.annotation-api:1.3.2")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.5")
    testImplementation("org.testcontainers:postgresql:1.17.6")
    testImplementation("org.testcontainers:junit-jupiter:1.17.6")
}

protobuf {
    plugins {
        create("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:1.51.0"
        }
    }
    generateProtoTasks {
        all().forEach {
            it.plugins {
                create("grpc")
            }
        }
    }
}

tasks.withType<JavaCompile> {
    if (System.getenv("TESTS_LETSGO") != null) {
        exclude("com/example/book_exchange/controller/**")
		exclude("com/example/book_exchange/repository/**")
		exclude("com/example/book_exchange/service/**")
    }
}

tasks.withType<Test> {
    if (System.getenv("TESTS_LETSGO") != null) {
        exclude("com/example/book_exchange/controller/**")
		exclude("com/example/book_exchange/repository/**")
		exclude("com/example/book_exchange/service/**")
    }

	useJUnitPlatform()
}
