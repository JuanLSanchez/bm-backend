package es.juanlsanchez.bm.cucumber.stepDefs;

import org.springframework.http.HttpHeaders;
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
  private HttpHeaders httpHeaders;

  // Constructors
  private ContainerDefs() {}

  // Instance
  private static ContainerDefs instance = null;

  public static ContainerDefs getInstance() {
    if (instance == null) {
      instance = new ContainerDefs();
    }
    return instance;
  }

  // Get and Set

  public HttpHeaders getHttpHeaders() {
    if (this.httpHeaders == null) {
      this.httpHeaders = new HttpHeaders();
    }
    return this.httpHeaders;
  }

}
