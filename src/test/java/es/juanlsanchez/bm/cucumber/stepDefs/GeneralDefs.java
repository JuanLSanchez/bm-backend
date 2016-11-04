package es.juanlsanchez.bm.cucumber.stepDefs;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultMatcher;

import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class GeneralDefs extends StepDefs {

  private ContainerDefs containerDefs;

  @Before
  public void setup() {
    containerDefs = ContainerDefs.getInstance();
  }

  // Steps
  @When("^I make a get request to the URL '(.*)'$")
  public void i_make_a_request_to_the_url(String url) throws Exception {
    containerDefs.setAction(
        containerDefs.getRestUserMockMvc().perform(get(url).accept(MediaType.APPLICATION_JSON)));

  }

  @Then("^http status is unauthorized$")
  public void the_status_is_not_unauthorized() throws Exception {
    checkStatus(status().isUnauthorized());
  }

  @Then("^http status is forbidden$")
  public void the_status_is_not_forbidden() throws Exception {
    checkStatus(status().isForbidden());
  }

  @Then("^http status is (\\d*)$")
  public void http_status(int status) throws Exception {
    checkStatus(status().is(status));

  }

  // Utilities
  private void checkStatus(ResultMatcher status) throws Exception {
    containerDefs.getAction().andExpect(status);
  }

}
