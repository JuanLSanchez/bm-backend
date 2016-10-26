package es.juanlsanchez.bm.cucumber.stepDefs;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContainerDefs {

    // Attributes
    private ResultActions action;
    private MockMvc restUserMockMvc;

    // Constructors
    private ContainerDefs() {
    }

    // Instance
    private static ContainerDefs instance = null;

    public static ContainerDefs getInstance() {
	if (instance == null) {
	    instance = new ContainerDefs();
	}
	return instance;
    }

}
