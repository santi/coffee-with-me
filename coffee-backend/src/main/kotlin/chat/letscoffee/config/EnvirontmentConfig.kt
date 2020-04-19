package chat.letscoffee.config

import org.apache.commons.configuration2.Configuration
import org.apache.commons.configuration2.EnvironmentConfiguration
import org.apache.commons.configuration2.builder.fluent.Configurations
import org.apache.commons.configuration2.ex.ConfigurationException

object EnvironmentConfig {
    private val CONFIG: Configuration = try {
        Configurations().properties("credentials/environment.properties")
    } catch (ex: ConfigurationException) {
        EnvironmentConfiguration()
    }

    fun getString(property: String): String {
        return System.getenv()[property] ?: System.getProperty(property) ?: CONFIG.getString(property)
    }
}
