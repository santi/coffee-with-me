package chat.letscoffee

import nl.martijndwars.webpush.PushService
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import java.security.Security
import chat.letscoffee.config.AppProperties

@SpringBootApplication
@EnableConfigurationProperties(AppProperties::class)
class CoffeeBackendApplication {

	@Bean
	fun pushService(): PushService  {
		// Add BouncyCastle as an algorithm provider
		if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
			Security.addProvider(BouncyCastleProvider())
		}

		val vapidPublicKey = this::class.java.getResource(
			"/credentials/VapidPublicKey").readText()
		val vapidPrivateKey = this::class.java.getResource(
			"/credentials/VapidPrivateKey").readText()

		return PushService(
			vapidPublicKey,
			vapidPrivateKey
		)
	}
}

fun main(args: Array<String>) {
	runApplication<CoffeeBackendApplication>(*args)
}
