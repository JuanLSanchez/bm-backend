package es.juanlsanchez.bm.cucumber.stepDefs;

import javax.inject.Inject;

import org.mockito.MockitoAnnotations;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import es.juanlsanchez.bm.web.rest.IncomeResource;

public class IncomeDefs extends StepDefs {

  @Inject
  private IncomeResource incomeResource;
  @Inject
  private FilterChainProxy springSecurityFilterChain;

  @Before
  public void setup() {
    this.containerDefs = ContainerDefs.getInstance();
  }

  @Given("^the income resource$")
  public void the_income_resource() {
    this.containerDefs.setRestUserMockMvc(MockMvcBuilders.standaloneSetup(this.incomeResource)
        .setCustomArgumentResolvers(this.pageableArgumentResolver)
        .addFilters(springSecurityFilterChain).build());
    MockitoAnnotations.initMocks(this);
  }

}
