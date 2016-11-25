package es.juanlsanchez.bm.cucumber.stepDefs;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import cucumber.api.java.Before;
import cucumber.api.java.en.Then;

public class RangeDefs extends StepDefs {

  @Before
  public void setup() {
    this.containerDefs = ContainerDefs.getInstance();
  }

  @Then("^the minimum is '(\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2})'$")
  public void the_minimum_is(String instant) throws Exception {
    this.containerDefs.getAction().andExpect(jsonPath("$.min").value(instant));
  }

  @Then("^the maximum is '(\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2})'$")
  public void the_maximum_is(String instant) throws Exception {
    this.containerDefs.getAction().andExpect(jsonPath("$.max").value(instant));
  }

}
