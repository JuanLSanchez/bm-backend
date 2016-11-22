package es.juanlsanchez.bm.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.juanlsanchez.bm.config.Constants;

@RestController
@RequestMapping(Constants.START_URL_API + "/operation")
public class OperationResource {

  private final Logger log = LoggerFactory.getLogger(this.getClass());

}
