import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot")
	kotlin("jvm")
	kotlin("plugin.spring")
	kotlin("plugin.allopen")

}

java.sourceCompatibility = JavaVersion.VERSION_11


repositories {
	mavenCentral()
	jcenter()

}



dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-jdbc")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("io.jsonwebtoken:jjwt:0.2")
	implementation("com.h2database:h2")
	implementation("org.springframework.security.oauth.boot:spring-security-oauth2-autoconfigure")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

	// Web Push API implementation
	implementation("nl.martijndwars:web-push:5.1.0")
	implementation("org.bouncycastle:bcprov-jdk15on:1.64")

	// HTTP client library
	implementation("com.github.kittinunf.fuel:fuel:2.2.1") //Core package
	implementation("com.github.kittinunf.fuel:fuel-json:2.2.1") //Core package
	implementation("com.github.kittinunf.fuel:fuel-gson:2.2.1") //Fuel Gson
	implementation("com.google.code.gson:gson:2.8.5") //Gson
	implementation("com.google.firebase:firebase-admin:6.11.0") // Firebase login



	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
