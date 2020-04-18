package chat.letscoffee

import chat.letscoffee.config.AppProperties
import nl.martijndwars.webpush.PushService
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import java.security.Security

@SpringBootApplication
@EnableConfigurationProperties(AppProperties::class)
class CoffeeBackendApplication {

    @Bean
    fun pushService(): PushService {


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
