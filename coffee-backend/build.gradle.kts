
plugins {
	id("org.springframework.boot")
	id("org.jetbrains.kotlin.plugin.jpa")
	kotlin("jvm")
	kotlin("plugin.spring")
	groovy
}

java.sourceCompatibility = JavaVersion.VERSION_11


repositories {
	mavenCentral()
	jcenter()
}


val FUEL_VERSION = "2.2.1"
val SPOCK_VERSION = "1.3-groovy-2.5"
val TESTCONTAINERS_VERSION = "1.13.0"

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-jdbc")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.security:spring-security-oauth2-client")

	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

	implementation("io.jsonwebtoken:jjwt:0.5.1")
	implementation("org.postgresql:postgresql:42.2.12")

	implementation("org.apache.commons:commons-configuration2:2.7")
	implementation("commons-beanutils:commons-beanutils:1.9.4")

	// Web Push API implementation
	implementation("nl.martijndwars:web-push:5.1.0")
	implementation("org.bouncycastle:bcprov-jdk15on:1.64")

	// HTTP client library
	implementation("com.github.kittinunf.fuel:fuel:$FUEL_VERSION") //Core package
	implementation("com.github.kittinunf.fuel:fuel-json:$FUEL_VERSION") //Core package
	implementation("com.github.kittinunf.fuel:fuel-gson:$FUEL_VERSION") //Fuel Gson
	implementation("com.google.code.gson:gson:2.8.5") //Gson


	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.codehaus.groovy:groovy-all:2.5.7")
	testImplementation("org.spockframework:spock-core:$SPOCK_VERSION")
	testImplementation("org.spockframework:spock-spring:$SPOCK_VERSION")
	testImplementation("org.testcontainers:spock:$TESTCONTAINERS_VERSION")
	testImplementation("org.testcontainers:postgresql:$TESTCONTAINERS_VERSION")
}
