package es.juanlsanchez.bm.cucumber.stepDefs;

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
import es.juanlsanchez.bm.web.error.ExceptionTranslator;
import es.juanlsanchez.bm.web.rest.SectionResource;

public class SectionDefs extends StepDefs {

  @Inject
  private SectionResource sectionResource;
  @Inject
  private FilterChainProxy springSecurityFilterChain;

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
  @Given("^the section resource$")
  public void the_section_resource() {

    final StaticApplicationContext applicationContext = new StaticApplicationContext();
    applicationContext.registerSingleton("exceptionHandler", ExceptionTranslator.class);

    final WebMvcConfigurationSupport webMvcConfigurationSupport = new WebMvcConfigurationSupport();
    webMvcConfigurationSupport.setApplicationContext(applicationContext);

    this.containerDefs.setRestUserMockMvc(MockMvcBuilders.standaloneSetup(this.sectionResource)
        .setCustomArgumentResolvers(this.pageableArgumentResolver)
        .setHandlerExceptionResolvers(webMvcConfigurationSupport.handlerExceptionResolver())
        .addFilters(springSecurityFilterChain).build());
    MockitoAnnotations.initMocks(this);
  }

}
