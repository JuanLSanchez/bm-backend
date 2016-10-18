package es.juanlsanchez.bm.config;


import javax.inject.Inject;

import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * 
 * Enforce the order FlyWay after HibernateKaz
 *
 */
@Configuration
@Profile(Constants.SPRING_PROFILE_FLYWAY)
public class FlywayConfig extends FlywayAutoConfiguration{

	@Inject
	private HibernateJpaAutoConfiguration hibernateJpaAutoConfiguration;

}
