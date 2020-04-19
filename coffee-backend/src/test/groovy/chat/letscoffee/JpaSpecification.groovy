package chat.letscoffee

import chat.letscoffee.config.SpringTestConfig
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import org.testcontainers.spock.Testcontainers
import spock.lang.Specification

@DataJpaTest
@Testcontainers
@Import(SpringTestConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
abstract class JpaSpecification extends Specification {}
