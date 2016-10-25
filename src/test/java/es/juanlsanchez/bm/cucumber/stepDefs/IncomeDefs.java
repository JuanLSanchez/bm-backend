package es.juanlsanchez.bm.cucumber.stepDefs;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.inject.Inject;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import es.juanlsanchez.bm.web.rest.IncomeResource;

public class IncomeDefs extends StepDefs {

    @Inject
    private IncomeResource incomeResource;

    @Before
    public void setup() {
	this.restUserMockMvc = MockMvcBuilders.standaloneSetup(incomeResource)
		.build();
    }

    @When("^I search my recipes$")
    public void i_make_a_get_request_to_the_url() throws Exception {
	actions = restUserMockMvc.perform(get("/api/incomes").accept(MediaType.APPLICATION_JSON));
    }

    @Then("not found recipes")
    public void the_status_is_not_found() throws Exception {
	actions.andExpect(status().isNotFound());
    }

}
