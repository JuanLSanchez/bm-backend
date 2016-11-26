package es.juanlsanchez.bm.cucumber.stepDefs;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.time.Instant;

import cucumber.api.java.Before;
import cucumber.api.java.en.Then;

public class RangeDefs extends StepDefs {

  @Before
  public void setup() {
    this.containerDefs = ContainerDefs.getInstance();
  }

  @Then("^the minimum is '(\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}Z)'$")
  public void the_minimum_is(String instantString) throws Exception {
    Instant instant = Instant.parse(instantString);
    this.containerDefs.getAction().andExpect(jsonPath("$.min").value(instant.getEpochSecond()));
  }

  @Then("^the maximum is '(\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}Z)'$")
  public void the_maximum_is(String instantString) throws Exception {
    Instant instant = Instant.parse(instantString);
    this.containerDefs.getAction().andExpect(jsonPath("$.max").value(instant.getEpochSecond()));
  }

}
