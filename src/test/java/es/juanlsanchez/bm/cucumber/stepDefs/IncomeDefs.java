package es.juanlsanchez.bm.cucumber.stepDefs;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import javax.inject.Inject;

import org.mockito.MockitoAnnotations;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import es.juanlsanchez.bm.web.dto.IncomeDTO;
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

  @Given("^a good incomeDTO$")
  public void a_good_incomeDTO() {
    Instant incomeDate = Instant.now().plus(-10l, ChronoUnit.DAYS);
    IncomeDTO incomeDTO =
        new IncomeDTO(null, incomeDate, "Income Test Name", "Income Test NIF", 12.2, 21);
    this.containerDefs.setResponseObject(incomeDTO);;
  }

  @Given("^a incomeDTO without name$")
  public void a_incomeDTO_without_name() {
    Instant incomeDate = Instant.now().plus(-10l, ChronoUnit.DAYS);
    IncomeDTO incomeDTO = new IncomeDTO(null, incomeDate, null, "Income Test NIF", 12.2, 21);
    this.containerDefs.setResponseObject(incomeDTO);;
  }

  @Given("^the income resource$")
  public void the_income_resource() {
    this.containerDefs.setRestUserMockMvc(MockMvcBuilders.standaloneSetup(this.incomeResource)
        .setCustomArgumentResolvers(this.pageableArgumentResolver)
        .addFilters(springSecurityFilterChain).build());
    MockitoAnnotations.initMocks(this);
  }

}
