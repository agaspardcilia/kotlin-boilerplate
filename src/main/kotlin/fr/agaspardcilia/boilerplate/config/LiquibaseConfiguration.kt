package fr.agaspardcilia.boilerplate.config

import liquibase.integration.spring.SpringLiquibase
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource


@Configuration
open class LiquibaseConfiguration {

    private val log: Logger = LoggerFactory.getLogger(LiquibaseConfiguration::class.java)

    @Bean
    open fun liquibase(dataSource: DataSource, liquibaseProperties: LiquibaseProperties): SpringLiquibase {
        val liquibase = SpringLiquibase()

        liquibase.dataSource = dataSource
        liquibase.changeLog = "classpath:liquibase/dbchangelog.xml"
        liquibase.contexts = liquibaseProperties.contexts
        log.debug("Configuring Liquibase")

        return liquibase
    }
}
