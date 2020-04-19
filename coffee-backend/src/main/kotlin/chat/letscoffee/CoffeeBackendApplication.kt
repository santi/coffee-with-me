package chat.letscoffee

import chat.letscoffee.config.AppProperties
import chat.letscoffee.config.EnvironmentConfig
import nl.martijndwars.webpush.PushService
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
            EnvironmentConfig.getString("VAPID_PUBLIC_KEY"),
            EnvironmentConfig.getString("VAPID_PRIVATE_KEY")
        )
    }

    @Bean
    @Profile("!test")
    fun dataSource(): DataSource {
        return DataSourceBuilder.create()
            .driverClassName("org.postgresql.Driver")
            .url("${EnvironmentConfig.getString("POSTGRES_URL")}/${EnvironmentConfig.getString("POSTGRES_DATABASENAME")}")
            .username(EnvironmentConfig.getString("POSTGRES_USERNAME"))
            .password(EnvironmentConfig.getString("POSTGRES_PASSWORD"))
            .build()
    }
}

fun main(args: Array<String>) {
    runApplication<CoffeeBackendApplication>(*args)
}
