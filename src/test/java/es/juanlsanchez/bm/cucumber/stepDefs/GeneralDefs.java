package es.juanlsanchez.bm.cucumber.stepDefs;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.inject.Inject;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultMatcher;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import es.juanlsanchez.bm.manager.UserJWTManager;
import es.juanlsanchez.bm.security.jwt.JWTConfigurer;
import es.juanlsanchez.bm.unit.web.rest.TestUtil;
import es.juanlsanchez.bm.web.dto.JWTTokenDTO;
import es.juanlsanchez.bm.web.dto.LoginDTO;

public class GeneralDefs extends StepDefs {

  @Inject
  private UserJWTManager userJWTManager;

  @Before
  public void setup() {
    this.containerDefs = ContainerDefs.getInstance();
    this.containerDefs.setHttpHeaders(null);
  }

  // Steps
  @Given("with the user '(.*)' and password '(.*)'$")
  public void with_the_user_and_password(String login, String password) {

    LoginDTO loginDTO = new LoginDTO(login, password, true);
    this.containerDefs.setLoginDTO(loginDTO);
    JWTTokenDTO jwt = this.userJWTManager.authorize(loginDTO);
    this.containerDefs.getHttpHeaders().add(JWTConfigurer.AUTHORIZATION_HEADER,
        "Bearer " + jwt.getIdToken());

  }

  // When ------------------------------
  @When("^I make a get request to the URL '(.*)'$")
  public void i_make_a_request_to_the_url(String url) throws Exception {
    containerDefs.setAction(this.containerDefs.getRestUserMockMvc()
        .perform(get(url).accept(MediaType.APPLICATION_JSON_UTF8)
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .headers(this.containerDefs.getHttpHeaders())));

  }

  @When("^I make a post request to the URL '(.*)'$")
  public void i_make_a_post_request_to_the_url(String url) throws Exception {
    containerDefs.setAction(this.containerDefs.getRestUserMockMvc()
        .perform(post(url).accept(MediaType.APPLICATION_JSON_UTF8)
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(this.containerDefs.getResponseObject()))
            .headers(this.containerDefs.getHttpHeaders())));
  }

  @When("^I make a put request to the URL '(.*)'$")
  public void i_make_a_put_request_to_the_url(String url) throws Exception {
    containerDefs.setAction(this.containerDefs.getRestUserMockMvc()
        .perform(put(url).accept(MediaType.APPLICATION_JSON_UTF8)
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(this.containerDefs.getResponseObject()))
            .headers(this.containerDefs.getHttpHeaders())));
  }

  @When("I make a delete request to the URL '(.*)'$")
  public void i_make_a_delte_request_to_the_url(String url) throws Exception {
    containerDefs.setAction(this.containerDefs.getRestUserMockMvc()
        .perform(delete(url).accept(MediaType.APPLICATION_JSON_UTF8)
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .headers(this.containerDefs.getHttpHeaders())));
  }

  // Then ---------------------------------------
  @Then("^http status is unauthorized$")
  public void the_status_is_not_unauthorized() throws Exception {
    checkStatus(status().isUnauthorized());
  }

  @Then("^http status is forbidden$")
  public void the_status_is_not_forbidden() throws Exception {
    checkStatus(status().isForbidden());
  }

  @Then("^http status is bad request$")
  public void the_status_is_bad_request() throws Exception {
    checkStatus(status().isBadRequest());
  }

  @Then("^http status is ok$")
  public void the_status_is_ok() throws Exception {
    checkStatus(status().isOk());
  }

  @Then("^http status is created$")
  public void the_status_is_created() throws Exception {
    checkStatus(status().isCreated());
  }

  @Then("^http status is no content$")
  public void the_status_is_no_content() throws Exception {
    checkStatus(status().isNoContent());
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
