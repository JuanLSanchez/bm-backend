package es.juanlsanchez.bm.config;

import javax.inject.Inject;

import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile(Constants.SPRING_PROFILE_LIQUIBASE)
public class LiquibaseConfig extends LiquibaseAutoConfiguration {

    @Inject
    private HibernateJpaAutoConfiguration hibernateJpaAutoConfiguration;

}
