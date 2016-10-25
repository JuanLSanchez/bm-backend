package es.juanlsanchez.bm.cucumber.stepDefs;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import es.juanlsanchez.bm.BmApplication;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = BmApplication.class)
public abstract class StepDefs {

    protected ResultActions actions;
    protected MockMvc restUserMockMvc;

}
