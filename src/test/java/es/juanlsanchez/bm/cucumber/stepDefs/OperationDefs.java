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
import es.juanlsanchez.bm.domain.Operation;
import es.juanlsanchez.bm.repository.OperationRepository;
import es.juanlsanchez.bm.web.dto.OperationDTO;
import es.juanlsanchez.bm.web.error.ExceptionTranslator;
import es.juanlsanchez.bm.web.rest.OperationResource;

public class OperationDefs extends StepDefs {

  @Inject
  private OperationResource operationResource;
  @Inject
  private FilterChainProxy springSecurityFilterChain;
  @Inject
  private OperationRepository operationRepository;

  private long numOfOperations;

  @Before
  public void setup() {
    this.containerDefs = ContainerDefs.getInstance();
  }


  // Given --------------------------------------
  @Given("^a good operationDTO for the user001$")
  public void a_good_operationDTO_for_the_user001() {
    OperationDTO operationDTO = new OperationDTO(null, "pinasñdifn", 1L);
    this.containerDefs.setResponseObject(operationDTO);;
  }

  @Given("^a operationDTO for the user001 without name$")
  public void a_operationDTO_for_the_user001_without_name() {
    OperationDTO operationDTO = new OperationDTO(null, null, 1L);
    this.containerDefs.setResponseObject(operationDTO);;
  }

  @Given("^a operationDTO for the user001 with the section of other user$")
  public void a_operationDTO_for_the_user001_with_the_section_of_other_user() {
    OperationDTO operationDTO = new OperationDTO(null, "srtgesdferg", 20L);
    this.containerDefs.setResponseObject(operationDTO);;
  }

  @Given("^the operation resource$")
  public void the_operation_resource() {

    final StaticApplicationContext applicationContext = new StaticApplicationContext();
    applicationContext.registerSingleton("exceptionHandler", ExceptionTranslator.class);

    final WebMvcConfigurationSupport webMvcConfigurationSupport = new WebMvcConfigurationSupport();
    webMvcConfigurationSupport.setApplicationContext(applicationContext);

    this.containerDefs.setRestUserMockMvc(MockMvcBuilders.standaloneSetup(this.operationResource)
        .setCustomArgumentResolvers(this.pageableArgumentResolver)
        .setHandlerExceptionResolvers(webMvcConfigurationSupport.handlerExceptionResolver())
        .addFilters(springSecurityFilterChain).build());
    MockitoAnnotations.initMocks(this);
  }

  @Given("^count the user's operations$")
  public void count_the_user_operations() {
    this.numOfOperations = operationRepository.findAll().stream().filter(operation -> operation
        .getPrincipal().getLogin().equals(this.containerDefs.getLoginDTO().getUsername())).count();
  }

  // Then ----------------------------------

  @Then("^the operation is creating$")
  public void the_operation_is_creating() {
    List<Operation> operations = operationRepository.findAll();
    Operation operationInDB = operations.get(operations.size() - 1);
    OperationDTO operation;
    if (this.containerDefs.getResponseObject() instanceof OperationDTO) {
      operation = (OperationDTO) this.containerDefs.getResponseObject();
    } else {
      throw new IllegalArgumentException("Not found OperationDTO in the container");
    }

    assertThat(operationInDB).isNotNull();
    assertThat(operationInDB.getName()).isEqualTo(operation.getName());
    assertThat(operationInDB.getSection().getId()).isEqualTo(operation.getSectionId());
  }

  @Then("^the operation (\\d*) is updating$")
  public void the_operation_is_updating(Long id) {
    Operation operationInDB = operationRepository.findOne(id);
    OperationDTO operation;
    if (this.containerDefs.getResponseObject() instanceof OperationDTO) {
      operation = (OperationDTO) this.containerDefs.getResponseObject();
    } else {
      throw new IllegalArgumentException("Not found OperationDTO in the container");
    }

    assertThat(operationInDB).isNotNull();
    assertThat(operationInDB.getName()).isEqualTo(operation.getName());
    assertThat(operationInDB.getSection().getId()).isEqualTo(operation.getSectionId());
  }

  @Then("^the operation (\\d*) is not updating$")
  public void the_opera_is_not_updating(long id) {
    Operation operationInDB = operationRepository.findOne(id);
    OperationDTO operation;
    if (this.containerDefs.getResponseObject() instanceof OperationDTO) {
      operation = (OperationDTO) this.containerDefs.getResponseObject();
    } else {
      throw new IllegalArgumentException("Not found OperationDTO in the container");
    }

    assertThat(operationInDB).isNotNull();
    assertThat(operationInDB.getName()).isNotEqualTo(operation.getName());
    assertThat(operationInDB.getSection().getId()).isNotEqualTo(operation.getSectionId());
  }

  @Then("^count the user's operations and it has increse (-?\\d*)$")
  public void count_the_user_operations_and_it_has_increase(long increment) {
    long now = operationRepository.findAll().stream().filter(operation -> operation.getPrincipal()
        .getLogin().equals(this.containerDefs.getLoginDTO().getUsername())).count();

    assertThat(now).isEqualTo(this.numOfOperations + increment);
  }

  @Then("^the operation (\\d*) is delete$")
  public void the_operation_is_delete(long id) {
    Operation operation = operationRepository.findOne(id);

    assertThat(operation).isNull();
  }

  @Then("^the operation (\\d*) is not delete$")
  public void the_operation_is_not_delete(long id) {
    Operation operation = operationRepository.findOne(id);

    assertThat(operation).isNotNull();
  }

}
