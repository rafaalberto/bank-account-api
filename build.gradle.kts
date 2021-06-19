import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.3.1.RELEASE"
	id("io.spring.dependency-management") version "1.0.9.RELEASE"
	id ("org.jetbrains.kotlin.plugin.jpa") version "1.3.41"
	kotlin("jvm") version "1.3.72"
	kotlin("plugin.spring") version "1.3.72"
	id ("org.sonarqube") version "3.0"
	jacoco
}

group = "br.com"
version = "1.0.0"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("com.google.code.gson:gson:2.8.5")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	runtimeOnly("org.postgresql:postgresql")
	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
		exclude(module = "junit")
		exclude(module = "mockito-core")
	}
	testImplementation("io.mockk:mockk:1.9.3")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

object Constants {
	val exclusionsForJacoco: List<String> =
		listOf("**BankAccountApplication.kt", "**ApiExceptionHandler.kt")
}

apply(plugin = "jacoco")

sonarqube {
	properties {
		property ("sonar.sourceEncoding", "UTF-8")
		property ("sonar.projectName", project.name)
		property ("sonar.projectKey", project.name)
		property ("sonar.host.url", "http://localhost:9000")
		property ("sonar.login", "22259ea51ddf48b8fce1b7a741551a25287a8245")
		property ("sonar.jacoco.reportPaths", "${buildDir}/jacoco/test.exec")
		property ("sonar.coverage.exclusions", Constants.exclusionsForJacoco)
	}
}

tasks.jacocoTestReport {
	reports {
		html.required.set(true)
		xml.required.set(true)
		csv.required.set(false)
		html.outputLocation.set(layout.buildDirectory.dir("jacocoHtml"))
	}
}

tasks.test {
	finalizedBy("jacocoTestReport")
}