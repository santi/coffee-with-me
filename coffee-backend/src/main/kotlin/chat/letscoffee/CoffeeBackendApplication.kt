package chat.letscoffee

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.messaging.FirebaseMessaging
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean


@SpringBootApplication
class CoffeeBackendApplication {

	@Bean
	fun firebaseMessaging(): FirebaseMessaging {
		val serviceAccount = this::class.java.classLoader.getResourceAsStream(
			"credentials/firebaseMessagingServiceAccount.json"
		)

		val options = FirebaseOptions.Builder()
			.setCredentials(GoogleCredentials.fromStream(serviceAccount))
			.build()

		FirebaseApp.initializeApp(options)
		return FirebaseMessaging.getInstance()
	}
}

fun main(args: Array<String>) {
	runApplication<CoffeeBackendApplication>(*args)
}
