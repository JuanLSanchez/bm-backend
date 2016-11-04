package es.juanlsanchez.bm.cucumber.stepDefs;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

import javax.inject.Inject;

import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import es.juanlsanchez.bm.web.rest.IncomeResource;

public class IncomeDefs extends StepDefs {

  @Inject
  private IncomeResource incomeResource;
  @Inject
  private FilterChainProxy springSecurityFilterChain;
  @Inject
  private ApplicationContext context;
  private Authentication authentication;

  private ContainerDefs containerDefs;

  @Before
  public void setup() {
    containerDefs = ContainerDefs.getInstance();
  }

  @Given("^the income resource$")
  public void the_income_resource() {
    AuthenticationManager authenticationManager = this.context.getBean(AuthenticationManager.class);
    this.authentication = authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken("user001", "password"));
    SecurityContextHolder.getContext().setAuthentication(this.authentication);
    MockMvc restUserMockMvc = MockMvcBuilders.standaloneSetup(incomeResource)
        .apply(springSecurity(springSecurityFilterChain)).build();
    containerDefs.setRestUserMockMvc(restUserMockMvc);
  }

}
