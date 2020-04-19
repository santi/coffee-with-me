package chat.letscoffee.config


import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Profile
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.spock.Testcontainers

import javax.sql.DataSource

@Testcontainers
@TestConfiguration
class SpringTestConfig {

    @Bean
    @Profile("test")
    DataSource dataSource() {

        def postgres = new PostgreSQLContainer("postgres:12.2")
            .withDatabaseName("coffee")
        postgres.start()

        return DataSourceBuilder.create()
            .driverClassName("org.postgresql.Driver")
            .url(postgres.jdbcUrl)
            .username(postgres.username)
            .password(postgres.password)
            .build()
    }


}
