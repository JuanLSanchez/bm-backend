package es.juanlsanchez.bm.cucumber.stepDefs;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import javax.inject.Inject;

import org.mockito.MockitoAnnotations;
import org.springframework.context.support.StaticApplicationContext;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import es.juanlsanchez.bm.domain.InvoiceLine;
import es.juanlsanchez.bm.repository.InvoiceLineRepository;
import es.juanlsanchez.bm.web.dto.InvoiceLineDTO;
import es.juanlsanchez.bm.web.error.ExceptionTranslator;
import es.juanlsanchez.bm.web.rest.InvoiceLineResource;

public class InvoiceLineDefs extends StepDefs {

  @Inject
  private InvoiceLineResource invoiceLineResource;
  @Inject
  private FilterChainProxy springSecurityFilterChain;
  @Inject
  private InvoiceLineRepository invoiceLineRepository;

  private long numOfInvoiceLines;

  @Before
  public void setup() {
    this.containerDefs = ContainerDefs.getInstance();
    transaction = transactionManager.getTransaction(new DefaultTransactionDefinition());
  }

  @After
  public void afterScenario() {
    transactionManager.rollback(transaction);
  }


  // Given --------------------------------------
  @Given("^a good invoiceLineDTO for user001$")
  public void a_good_invoiceLineDTO_for_user001() {
    InvoiceLineDTO invoiceLineDTO = new InvoiceLineDTO(null, 22, 14.5, 1L);
    this.containerDefs.setResponseObject(invoiceLineDTO);
  }

  @Given("^a good invoiceLineDTO for user003$")
  public void a_good_invoiceLineDTO_for_user003() {
    InvoiceLineDTO invoiceLineDTO = new InvoiceLineDTO(null, 22, 14.5, 3L);
    this.containerDefs.setResponseObject(invoiceLineDTO);
  }

  @Given("^a invoiceLineDTO for user001 without iva$")
  public void a_invoiceLineDTO_for_user001_without_iva() {
    InvoiceLineDTO invoiceLineDTO = InvoiceLineDTO.builder().base(14.5).invoiceId(1L).build();
    this.containerDefs.setResponseObject(invoiceLineDTO);
  }

  @Given("^a invoiceLineDTO for user001 without base$")
  public void a_invoiceLineDTO_for_user001_without_base() {
    InvoiceLineDTO invoiceLineDTO = InvoiceLineDTO.builder().iva(21).invoiceId(1L).build();
    this.containerDefs.setResponseObject(invoiceLineDTO);
  }

  @Given("^a invoiceLineDTO without invoice$")
  public void a_invoiceLineDTO_without_invoice() {
    InvoiceLineDTO invoiceLineDTO = new InvoiceLineDTO(null, 21, 14.5, null);
    this.containerDefs.setResponseObject(invoiceLineDTO);
  }

  @Given("^a invoiceLineDTO for user001 with iva great that 100$")
  public void a_invoiceLineDTO_for_user001_with_iva_great_that_100() {
    InvoiceLineDTO invoiceLineDTO = new InvoiceLineDTO(null, 101, 14.5, 1L);
    this.containerDefs.setResponseObject(invoiceLineDTO);
  }

  @Given("^a invoiceLineDTO for user001 with iva less that 0$")
  public void a_invoiceLineDTO_for_user001_with_iva_less_that_0() {
    InvoiceLineDTO invoiceLineDTO = new InvoiceLineDTO(null, -1, 14.5, 1L);
    this.containerDefs.setResponseObject(invoiceLineDTO);
  }

  @Given("^the invoiceLine resource$")
  public void the_invoiceLine_resource() {

    final StaticApplicationContext applicationContext = new StaticApplicationContext();
    applicationContext.registerSingleton("exceptionHandler", ExceptionTranslator.class);

    final WebMvcConfigurationSupport webMvcConfigurationSupport = new WebMvcConfigurationSupport();
    webMvcConfigurationSupport.setApplicationContext(applicationContext);

    this.containerDefs.setRestUserMockMvc(MockMvcBuilders.standaloneSetup(this.invoiceLineResource)
        .setCustomArgumentResolvers(this.pageableArgumentResolver)
        .setHandlerExceptionResolvers(webMvcConfigurationSupport.handlerExceptionResolver())
        .addFilters(springSecurityFilterChain).build());
    MockitoAnnotations.initMocks(this);
  }

  @Given("^count the user's invoiceLines$")
  public void count_the_user_invoiceLines() {
    this.numOfInvoiceLines = invoiceLineRepository.findAll().stream()
        .filter(invoiceLine -> invoiceLine.getInvoice().getPrincipal().getLogin()
            .equals(this.containerDefs.getLoginDTO().getUsername()))
        .count();
  }

  // Then ----------------------------------

  @Then("^the invoiceLine is creating$")
  public void the_invoiceLine_is_creating() {
    List<InvoiceLine> invoiceLines = invoiceLineRepository.findAll();
    InvoiceLine invoiceLineInDB = invoiceLines.get(invoiceLines.size() - 1);
    InvoiceLineDTO invoiceLine;
    if (this.containerDefs.getResponseObject() instanceof InvoiceLineDTO) {
      invoiceLine = (InvoiceLineDTO) this.containerDefs.getResponseObject();
    } else {
      throw new IllegalArgumentException("Not found InvoiceLineDTO in the container");
    }

    assertThat(invoiceLineInDB).isNotNull();
    assertThat(invoiceLineInDB).isEqualToComparingOnlyGivenFields(invoiceLine, "iva", "base");
    assertThat(invoiceLineInDB.getInvoice().getId()).isEqualTo(invoiceLine.getInvoiceId());
  }

  @Then("^the invoiceLine (\\d*) is updating$")
  public void the_invoiceLine_is_updating(Long id) {
    InvoiceLine invoiceLineInDB = invoiceLineRepository.findOne(id);
    InvoiceLineDTO invoiceLine;
    if (this.containerDefs.getResponseObject() instanceof InvoiceLineDTO) {
      invoiceLine = (InvoiceLineDTO) this.containerDefs.getResponseObject();
    } else {
      throw new IllegalArgumentException("Not found InvoiceLineDTO in the container");
    }

    assertThat(invoiceLineInDB).isNotNull();
    assertThat(invoiceLineInDB).isEqualToComparingOnlyGivenFields(invoiceLine, "iva", "base");
    assertThat(invoiceLineInDB.getInvoice().getId()).isEqualTo(invoiceLine.getInvoiceId());
  }

  @Then("^the invoiceLine (\\d*) is not updating$")
  public void the_invoiceLine_is_not_updating(Long id) {
    InvoiceLine invoiceLineInDB = invoiceLineRepository.findOne(id);
    InvoiceLineDTO invoiceLine;
    if (this.containerDefs.getResponseObject() instanceof InvoiceLineDTO) {
      invoiceLine = (InvoiceLineDTO) this.containerDefs.getResponseObject();
    } else {
      throw new IllegalArgumentException("Not found InvoiceLineDTO in the container");
    }

    assertThat(invoiceLineInDB).isNotNull();
    assertThat(invoiceLineInDB).isEqualToComparingOnlyGivenFields(invoiceLine, "iva", "base");
    assertThat(invoiceLineInDB.getBase()).isNotEqualTo(invoiceLine.getBase());
    assertThat(invoiceLineInDB.getIva()).isNotEqualTo(invoiceLine.getIva());
    assertThat(invoiceLineInDB.getInvoice().getId()).isNotEqualTo(invoiceLine.getInvoiceId());
  }

  @Then("^count the user's invoiceLines and it has increse (-?\\d*)$")
  public void count_the_user_invoiceLines_and_it_has_increase(long increment) {
    long now = invoiceLineRepository.findAll().stream()
        .filter(invoiceLine -> invoiceLine.getInvoice().getPrincipal().getLogin()
            .equals(this.containerDefs.getLoginDTO().getUsername()))
        .count();

    assertThat(now).isEqualTo(this.numOfInvoiceLines + increment);
  }

  @Then("^the invoiceLine (\\d*) is delete$")
  public void the_invoiceLine_is_delete(long id) {
    InvoiceLine invoiceLine = invoiceLineRepository.findOne(id);

    assertThat(invoiceLine).isNull();
  }

}
