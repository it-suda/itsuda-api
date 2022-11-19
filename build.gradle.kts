import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.github.jengelman.gradle.plugins.shadow.transformers.*
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
	id("org.springframework.boot") version "2.6.6"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	// shadow 플러그인 추가
	id("com.github.johnrengelman.shadow") version "7.0.0"
	kotlin("jvm") version "1.6.10"
	kotlin("plugin.spring") version "1.6.10"
}

group = "org.community.itsuda"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
	mavenCentral()
}
val springCloudVersion = "2020.0.3"
val awsLambdaCore = "1.2.1"
val awsLambdaEvents = "3.10.0"

dependencies {
	// webflux reactive programming
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")

	implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

	// Spring Cloud Function 을 위한 의존성
	implementation("org.springframework.cloud:spring-cloud-function-web:3.2.7")
	implementation("org.springframework.cloud:spring-cloud-function-adapter-aws:3.2.7")

	// aws 관련 의존성 추가
	implementation("com.amazonaws:aws-lambda-java-core:${awsLambdaCore}")
	implementation("com.amazonaws:aws-lambda-java-events:${awsLambdaEvents}")
	implementation("com.google.code.gson:gson")

	runtimeOnly("com.amazonaws:aws-lambda-java-log4j2:1.2.0")
	// aws github 참조
	implementation("com.amazonaws.serverless:aws-serverless-java-container-springboot2:1.6")
	implementation("io.symphonia:lambda-logging:1.0.3")


	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

dependencyManagement {
	imports {
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:${springCloudVersion}")
	}
}


tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "1.8"
	}
}

// shadowJar로 빌드하도록.
tasks.assemble {
	dependsOn("shadowJar")
}

/**
 * Jar 파일을 위한 테스크 추가?
 */
//tasks.withType<Jar> {
//	manifest {
//		attributes["Start-Class"] = "org.community.itsuda.handler.ItsudaHandler"
////		attributes["Start-Class"] = "org.community.itsuda.ItsudaHandler.kt"
//	}
//}

tasks.withType<ShadowJar> {
	archiveFileName.set("awsLamdaSample.jar")
	dependencies {
		exclude("org.springframework.cloud:spring-cloud-function-web")
	}
	// Required for Spring
	mergeServiceFiles()
	append("META-INF/spring.handlers")
	append("META-INF/spring.schemas")
	append("META-INF/spring.tooling")
	transform(PropertiesFileTransformer::class.java) {
		paths.add("META-INF/spring.factories")
		mergeStrategy = "append"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}


