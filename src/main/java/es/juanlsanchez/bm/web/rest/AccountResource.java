package es.juanlsanchez.bm.web.rest;

import java.net.URISyntaxException;

import javax.inject.Inject;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.juanlsanchez.bm.config.Constants;
import es.juanlsanchez.bm.manager.AccountManager;
import es.juanlsanchez.bm.web.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping(Constants.START_URL_API + "/account")
@Slf4j
public class AccountResource {

  private final AccountManager accountManager;

  @Inject
  public AccountResource(final AccountManager accountManager) {
    this.accountManager = accountManager;
  }

  @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<UserDTO> getPrincipal() throws URISyntaxException {
    log.debug("REST request to get account");

    return ResponseEntity.ok(accountManager.getPrincipal());
  }

}
