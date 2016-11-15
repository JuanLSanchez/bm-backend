package es.juanlsanchez.bm.cucumber.stepDefs;

import javax.inject.Inject;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import es.juanlsanchez.bm.BmApplication;
import es.juanlsanchez.bm.config.SecurityConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = {BmApplication.class, SecurityConfiguration.class})
public abstract class StepDefs {

  protected ContainerDefs containerDefs;
  @Inject
  protected PageableHandlerMethodArgumentResolver pageableArgumentResolver;

}
