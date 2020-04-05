import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot")
	kotlin("jvm")
	kotlin("plugin.spring")
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
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("com.google.firebase:firebase-admin:6.12.2")
	implementation("com.github.kittinunf.fuel:fuel:2.2.1") //Core package
	implementation("com.github.kittinunf.fuel:fuel-json:2.2.1") //Core package
	implementation("com.github.kittinunf.fuel:fuel-gson:2.2.1") //Fuel Gson
	implementation("com.google.code.gson:gson:2.8.5") //Gson
	implementation("com.beust:klaxon:5.0.1")


	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
