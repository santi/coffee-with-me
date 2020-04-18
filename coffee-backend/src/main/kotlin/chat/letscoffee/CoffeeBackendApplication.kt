package chat.letscoffee

import chat.letscoffee.config.AppProperties
import nl.martijndwars.webpush.PushService
import org.apache.commons.configuration2.Configuration
import org.apache.commons.configuration2.builder.fluent.Configurations
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Profile
import javax.sql.DataSource

@SpringBootApplication
@EnableConfigurationProperties(AppProperties::class)
class CoffeeBackendApplication {

    @Bean
    fun pushService(): PushService {
        return PushService(
            CONFIG.getString("VAPID_PUBLIC_KEY"),
            CONFIG.getString("VAPID_PRIVATE_KEY")
        )
    }

    @Bean
    @Profile("!test")
    fun dataSource(): DataSource {
        return DataSourceBuilder.create()
            .driverClassName("org.postgresql.Driver")
            .url("${CONFIG.getString("POSTGRES_URL")}/${CONFIG.getString("POSTGRES_DATABASENAME")}")
            .username(CONFIG.getString("POSTGRES_USERNAME"))
            .password(CONFIG.getString("POSTGRES_PASSWORD"))
            .build()
    }

    companion object {
        val CONFIG: Configuration = Configurations().properties("credentials/secrets.properties")
    }
}

fun main(args: Array<String>) {
    runApplication<CoffeeBackendApplication>(*args)
}
