package es.juanlsanchez.bm.cucumber.stepDefs;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.inject.Inject;

import org.mockito.MockitoAnnotations;
import org.springframework.context.support.StaticApplicationContext;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import es.juanlsanchez.bm.domain.Income;
import es.juanlsanchez.bm.repository.IncomeRepository;
import es.juanlsanchez.bm.web.dto.IncomeDTO;
import es.juanlsanchez.bm.web.error.ExceptionTranslator;
import es.juanlsanchez.bm.web.rest.IncomeResource;

public class IncomeDefs extends StepDefs {

  @Inject
  private IncomeResource incomeResource;
  @Inject
  private FilterChainProxy springSecurityFilterChain;
  @Inject
  private IncomeRepository incomeRepository;

  private long numOfIncomes;

  @Before
  public void setup() {
    this.containerDefs = ContainerDefs.getInstance();
  }


  // Given --------------------------------------
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

    final StaticApplicationContext applicationContext = new StaticApplicationContext();
    applicationContext.registerSingleton("exceptionHandler", ExceptionTranslator.class);

    final WebMvcConfigurationSupport webMvcConfigurationSupport = new WebMvcConfigurationSupport();
    webMvcConfigurationSupport.setApplicationContext(applicationContext);

    this.containerDefs.setRestUserMockMvc(MockMvcBuilders.standaloneSetup(this.incomeResource)
        .setCustomArgumentResolvers(this.pageableArgumentResolver)
        .setHandlerExceptionResolvers(webMvcConfigurationSupport.handlerExceptionResolver())
        .addFilters(springSecurityFilterChain).build());
    MockitoAnnotations.initMocks(this);
  }

  @Given("^count the user's incomes$")
  public void count_the_user_incomes() {
    this.numOfIncomes = incomeRepository.findAll().stream().filter(income -> income.getPrincipal()
        .getLogin().equals(this.containerDefs.getLoginDTO().getUsername())).count();
  }

  // Then ----------------------------------

  @Then("^the income is creating$")
  public void the_income_is_creating() {
    List<Income> incomes = incomeRepository.findAll();
    Income incomeInDB = incomes.get(incomes.size() - 1);
    IncomeDTO income;
    if (this.containerDefs.getResponseObject() instanceof IncomeDTO) {
      income = (IncomeDTO) this.containerDefs.getResponseObject();
    } else {
      throw new IllegalArgumentException("Not found IncomeDTO in the container");
    }

    assertThat(incomeInDB).isEqualToComparingOnlyGivenFields(income, "base", "incomeDate", "name",
        "nif", "iva");
  }

  @Then("^the income (\\d*) is updating$")
  public void the_income_is_updating(Long id) {
    Income incomeInDB = incomeRepository.findOne(id);
    IncomeDTO income;
    if (this.containerDefs.getResponseObject() instanceof IncomeDTO) {
      income = (IncomeDTO) this.containerDefs.getResponseObject();
    } else {
      throw new IllegalArgumentException("Not found IncomeDTO in the container");
    }

    assertThat(incomeInDB).isNotNull();
    assertThat(incomeInDB).isEqualToComparingOnlyGivenFields(income, "base", "incomeDate", "name",
        "nif", "iva");
  }

  @Then("^count the user's incomes and it has increse (-?\\d*)$")
  public void count_the_user_incomes_and_it_has_increase(long increment) {
    long now = incomeRepository.findAll().stream().filter(income -> income.getPrincipal().getLogin()
        .equals(this.containerDefs.getLoginDTO().getUsername())).count();

    assertThat(now).isEqualTo(this.numOfIncomes + increment);
  }

  @Then("^the income (\\d*) is delete$")
  public void the_income_is_delete(long id) {
    Income income = incomeRepository.findOne(id);

    assertThat(income).isNull();
  }

}
