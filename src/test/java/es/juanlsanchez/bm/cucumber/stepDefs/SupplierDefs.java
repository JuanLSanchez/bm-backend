package es.juanlsanchez.bm.cucumber.stepDefs;

import static org.assertj.core.api.Assertions.assertThat;

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
import es.juanlsanchez.bm.domain.Supplier;
import es.juanlsanchez.bm.repository.SupplierRepository;
import es.juanlsanchez.bm.web.dto.SupplierDTO;
import es.juanlsanchez.bm.web.error.ExceptionTranslator;
import es.juanlsanchez.bm.web.rest.SupplierResource;

public class SupplierDefs extends StepDefs {

  @Inject
  private SupplierResource supplierResource;
  @Inject
  private FilterChainProxy springSecurityFilterChain;
  @Inject
  private SupplierRepository supplierRepository;

  private long numOfSupplier;

  @Before
  public void setup() {
    this.containerDefs = ContainerDefs.getInstance();
  }


  // Given --------------------------------------
  @Given("^a good supplierDTO for the user001$")
  public void a_good_supplierDTO_for_the_user001() {
    SupplierDTO supplierDTO = new SupplierDTO(null, "ljñlk", "jasioas");
    this.containerDefs.setResponseObject(supplierDTO);;
  }

  @Given("^a supplierDTO for the user001 without name$")
  public void a_supplierDTO_for_the_user001_without_name() {
    SupplierDTO supplierDTO = new SupplierDTO(null, null, "jasioas");
    this.containerDefs.setResponseObject(supplierDTO);;
  }

  @Given("^the supplier resource$")
  public void the_supplier_resource() {

    final StaticApplicationContext applicationContext = new StaticApplicationContext();
    applicationContext.registerSingleton("exceptionHandler", ExceptionTranslator.class);

    final WebMvcConfigurationSupport webMvcConfigurationSupport = new WebMvcConfigurationSupport();
    webMvcConfigurationSupport.setApplicationContext(applicationContext);

    this.containerDefs.setRestUserMockMvc(MockMvcBuilders.standaloneSetup(this.supplierResource)
        .setCustomArgumentResolvers(this.pageableArgumentResolver)
        .setHandlerExceptionResolvers(webMvcConfigurationSupport.handlerExceptionResolver())
        .addFilters(springSecurityFilterChain).build());
    MockitoAnnotations.initMocks(this);
  }

  @Given("^count the user's suppliers$")
  public void count_the_user_supplier() {
    this.numOfSupplier = supplierRepository.findAll().stream().filter(supplier -> supplier
        .getPrincipal().getLogin().equals(this.containerDefs.getLoginDTO().getUsername())).count();
  }

  // Then ----------------------------------

  @Then("^the supplier is creating$")
  public void the_supplier_is_creating() {
    List<Supplier> suppliers = supplierRepository.findAll();
    Supplier supplierInDB = suppliers.get(suppliers.size() - 1);
    SupplierDTO supplier;
    if (this.containerDefs.getResponseObject() instanceof SupplierDTO) {
      supplier = (SupplierDTO) this.containerDefs.getResponseObject();
    } else {
      throw new IllegalArgumentException("Not found SupplierDTO in the container");
    }

    assertThat(supplierInDB).isNotNull();
    assertThat(supplierInDB).isEqualToComparingOnlyGivenFields(supplier, "name", "nif");
  }

  @Then("^the supplier (\\d*) is updating$")
  public void the_supplier_is_updating(Long id) {
    Supplier supplierInDB = supplierRepository.findOne(id);
    SupplierDTO supplier;
    if (this.containerDefs.getResponseObject() instanceof SupplierDTO) {
      supplier = (SupplierDTO) this.containerDefs.getResponseObject();
    } else {
      throw new IllegalArgumentException("Not found SupplierDTO in the container");
    }

    assertThat(supplierInDB).isNotNull();
    assertThat(supplierInDB).isEqualToComparingOnlyGivenFields(supplier, "name", "nif");
  }

  @Then("^the supplier (\\d*) is not updating$")
  public void the_opera_is_not_updating(long id) {
    Supplier supplierInDB = supplierRepository.findOne(id);
    SupplierDTO supplier;
    if (this.containerDefs.getResponseObject() instanceof SupplierDTO) {
      supplier = (SupplierDTO) this.containerDefs.getResponseObject();
    } else {
      throw new IllegalArgumentException("Not found SupplierDTO in the container");
    }

    assertThat(supplierInDB).isNotNull();
    assertThat(supplierInDB.getName()).isNotEqualTo(supplier.getName());
    assertThat(supplierInDB.getNif()).isNotEqualTo(supplier.getNif());
  }

  @Then("^count the user's suppliers and it has increse (-?\\d*)$")
  public void count_the_user_supplier_and_it_has_increase(long increment) {
    long now = supplierRepository.findAll().stream().filter(supplier -> supplier.getPrincipal()
        .getLogin().equals(this.containerDefs.getLoginDTO().getUsername())).count();

    assertThat(now).isEqualTo(this.numOfSupplier + increment);
  }

  @Then("^the supplier (\\d*) is delete$")
  public void the_supplier_is_delete(long id) {
    Supplier supplier = supplierRepository.findOne(id);

    assertThat(supplier).isNull();
  }

  @Then("^the supplier (\\d*) is not delete$")
  public void the_supplier_is_not_delete(long id) {
    Supplier supplier = supplierRepository.findOne(id);

    assertThat(supplier).isNotNull();
  }

}
