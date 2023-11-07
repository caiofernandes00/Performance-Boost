package org.acme.adapter.out.hibernate.config

import io.quarkus.runtime.StartupEvent
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.event.Observes
import org.eclipse.microprofile.config.inject.ConfigProperty
import org.flywaydb.core.Flyway

@ApplicationScoped
class Flyway(
    @ConfigProperty(name = "quarkus.flyway.migrate-at-start")
    var runMigration: Boolean,
    @ConfigProperty(name = "quarkus.datasource.reactive.url")
    var datasourceUrl: String,
    @ConfigProperty(name = "quarkus.datasource.username")
    var datasourceUsername: String,
    @ConfigProperty(name = "quarkus.datasource.password")
    var datasourcePassword: String
) {
    fun execute(@Observes event: StartupEvent?) {
        if (runMigration) {
            Flyway.configure().dataSource("jdbc:$datasourceUrl", datasourceUsername, datasourcePassword)
                .load()
                .migrate()
        }
    }
}

