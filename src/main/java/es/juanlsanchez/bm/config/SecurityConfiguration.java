package es.juanlsanchez.bm.config;

import javax.inject.Inject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.data.repository.query.SecurityEvaluationContextExtension;

import es.juanlsanchez.bm.security.AuthoritiesConstants;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Inject
    private UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
	return new BCryptPasswordEncoder();
    }

    @Inject
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
	auth.userDetailsService(userDetailsService)
		.passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
	http.httpBasic();
	http.headers()
		.frameOptions()
		.disable();

	http.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

	http.authorizeRequests()
		.antMatchers(Constants.START_URL_API
			+ "/**")
		.hasAuthority(AuthoritiesConstants.USER);
	http.authorizeRequests()
		.antMatchers(Constants.MANAGE_URL
			+ "/**")
		.hasAnyAuthority(AuthoritiesConstants.MANAGE);
	http.authorizeRequests()
		.antMatchers(Constants.ADMIN_URL
			+ "/**")
		.hasAuthority(AuthoritiesConstants.ADMIN);
	http.authorizeRequests()
		.anyRequest()
		.hasAuthority(AuthoritiesConstants.ADMIN);
	http.csrf()
		.disable();

    }

    /**
     * Bean to use ?#{princiap} annotation y JPA
     */
    @Bean
    public SecurityEvaluationContextExtension securityEvaluationContextExtension() {
	return new SecurityEvaluationContextExtension();
    }
}
