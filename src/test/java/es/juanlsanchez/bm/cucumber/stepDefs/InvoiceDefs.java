package es.juanlsanchez.bm.cucumber.stepDefs;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
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
import es.juanlsanchez.bm.domain.Invoice;
import es.juanlsanchez.bm.repository.InvoiceRepository;
import es.juanlsanchez.bm.web.dto.InvoiceDTO;
import es.juanlsanchez.bm.web.error.ExceptionTranslator;
import es.juanlsanchez.bm.web.rest.InvoiceResource;

public class InvoiceDefs extends StepDefs {

  @Inject
  private InvoiceResource invoiceResource;
  @Inject
  private FilterChainProxy springSecurityFilterChain;
  @Inject
  private InvoiceRepository invoiceRepository;

  private long numOfInvoices;

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
  @Given("^a good invoiceDTO for user001$")
  public void a_good_invoiceDTO_for_user001() {
    LocalDate dateBuy = LocalDate.now().plusDays(-10);
    InvoiceDTO invoiceDTO = new InvoiceDTO(null, "1adf1a323153asd", dateBuy, 1L, 1L);
    this.containerDefs.setResponseObject(invoiceDTO);;
  }

  @Given("^a invoiceDTO for user001 with the supplier of other user$")
  public void a_invoiceDTO_for_user001_with_the_supplier_of_other_user() {
    LocalDate dateBuy = LocalDate.now().plusDays(-11);
    InvoiceDTO invoiceDTO = new InvoiceDTO(null, "asdfsdf", dateBuy, 40L, 1L);
    this.containerDefs.setResponseObject(invoiceDTO);;
  }

  @Given("^a invoiceDTO for user001 with the operation of other user$")
  public void a_invoiceDTO_for_user001_with_the_operation_of_other_user() {
    LocalDate dateBuy = LocalDate.now().plusDays(-12);
    InvoiceDTO invoiceDTO = new InvoiceDTO(null, "asdfsafd", dateBuy, 1L, 6L);
    this.containerDefs.setResponseObject(invoiceDTO);;
  }

  @Given("^the invoice resource$")
  public void the_invoice_resource() {

    final StaticApplicationContext applicationContext = new StaticApplicationContext();
    applicationContext.registerSingleton("exceptionHandler", ExceptionTranslator.class);

    final WebMvcConfigurationSupport webMvcConfigurationSupport = new WebMvcConfigurationSupport();
    webMvcConfigurationSupport.setApplicationContext(applicationContext);

    this.containerDefs.setRestUserMockMvc(MockMvcBuilders.standaloneSetup(this.invoiceResource)
        .setCustomArgumentResolvers(this.pageableArgumentResolver)
        .setHandlerExceptionResolvers(webMvcConfigurationSupport.handlerExceptionResolver())
        .addFilters(springSecurityFilterChain).build());
    MockitoAnnotations.initMocks(this);
  }

  @Given("^count the user's invoices$")
  public void count_the_user_invoices() {
    this.numOfInvoices = invoiceRepository.findAll().stream().filter(invoice -> invoice
        .getPrincipal().getLogin().equals(this.containerDefs.getLoginDTO().getUsername())).count();
  }

  // Then ----------------------------------

  @Then("^the invoice is creating$")
  public void the_invoice_is_creating() {
    List<Invoice> invoices = invoiceRepository.findAll();
    Invoice invoiceInDB = invoices.get(invoices.size() - 1);
    InvoiceDTO invoice;
    if (this.containerDefs.getResponseObject() instanceof InvoiceDTO) {
      invoice = (InvoiceDTO) this.containerDefs.getResponseObject();
    } else {
      throw new IllegalArgumentException("Not found InvoiceDTO in the container");
    }

    assertThat(invoiceInDB).isNotNull();
    assertThat(invoiceInDB).isEqualToComparingOnlyGivenFields(invoice, "number", "dateBuy");
    assertThat(invoiceInDB.getSupplier().getId()).isEqualTo(invoice.getSupplierId());
    assertThat(invoiceInDB.getOperation().getId()).isEqualTo(invoice.getOperationId());
  }

  @Then("^the invoice (\\d*) is updating$")
  public void the_invoice_is_updating(Long id) {
    Invoice invoiceInDB = invoiceRepository.findOne(id);
    InvoiceDTO invoice;
    if (this.containerDefs.getResponseObject() instanceof InvoiceDTO) {
      invoice = (InvoiceDTO) this.containerDefs.getResponseObject();
    } else {
      throw new IllegalArgumentException("Not found InvoiceDTO in the container");
    }

    assertThat(invoiceInDB).isNotNull();
    assertThat(invoiceInDB).isEqualToComparingOnlyGivenFields(invoice, "number", "dateBuy");
    assertThat(invoiceInDB.getSupplier().getId()).isEqualTo(invoice.getSupplierId());
    assertThat(invoiceInDB.getOperation().getId()).isEqualTo(invoice.getOperationId());
  }

  @Then("^the invoice (\\d*) is not updating$")
  public void the_invoice_is_not_updating(Long id) {
    Invoice invoiceInDB = invoiceRepository.findOne(id);
    InvoiceDTO invoice;
    if (this.containerDefs.getResponseObject() instanceof InvoiceDTO) {
      invoice = (InvoiceDTO) this.containerDefs.getResponseObject();
    } else {
      throw new IllegalArgumentException("Not found InvoiceDTO in the container");
    }

    assertThat(invoiceInDB).isNotNull();
    assertThat(invoiceInDB.getNumber()).isNotEqualTo(invoice.getNumber());
    assertThat(invoiceInDB.getDateBuy()).isNotEqualTo(invoice.getDateBuy());
  }

  @Then("^count the user's invoices and it has increse (-?\\d*)$")
  public void count_the_user_invoices_and_it_has_increase(long increment) {
    long now = invoiceRepository.findAll().stream().filter(invoice -> invoice.getPrincipal()
        .getLogin().equals(this.containerDefs.getLoginDTO().getUsername())).count();

    assertThat(now).isEqualTo(this.numOfInvoices + increment);
  }

  @Then("^the invoice (\\d*) is delete$")
  public void the_invoice_is_delete(long id) {
    Invoice invoice = invoiceRepository.findOne(id);

    assertThat(invoice).isNull();
  }

}
