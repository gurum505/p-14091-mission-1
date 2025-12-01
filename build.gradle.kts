import org.gradle.kotlin.dsl.implementation

plugins {
	java
	id("org.springframework.boot") version "3.5.7"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "com.mysite"
version = "0.0.1-SNAPSHOT"
description = "Demo project for Spring Boot"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
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
	implementation("org.springframework.boot:spring-boot-starter-web")
	compileOnly("org.projectlombok:lombok")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    runtimeOnly("com.h2database:h2")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    //testImplementation("org.springframework.boot:spring-boot-starter-thymeleaf-test")
    implementation("org.commonmark:commonmark:0.27.0")
    implementation("nz.net.ultraq.thymeleaf:thymeleaf-layout-dialect")
    implementation("org.springframework.boot:spring-boot-starter-security")

    // Thymeleaf와 Spring Security 통합을 위한 의존성 (Security 기능 사용 시 필수)
    implementation("org.thymeleaf.extras:thymeleaf-extras-springsecurity6")

}

tasks.withType<Test> {
	useJUnitPlatform()
}
