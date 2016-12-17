package es.juanlsanchez.bm.web.rest;

import java.net.URISyntaxException;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import es.juanlsanchez.bm.config.Constants;
import es.juanlsanchez.bm.manager.SectionManager;
import es.juanlsanchez.bm.web.dto.SectionDTO;
import es.juanlsanchez.bm.web.util.pagination.PaginationUtil;

@RestController
@RequestMapping(Constants.START_URL_API + "/section")
public class SectionResource {

  private final Logger log = LoggerFactory.getLogger(this.getClass());

  private final SectionManager sectionManager;

  @Inject
  public SectionResource(final SectionManager sectionManager) {
    this.sectionManager = sectionManager;
  }

  @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<SectionDTO>> findAllByPrincipal(Pageable pageable)
      throws URISyntaxException {
    log.debug("REST request to list section");

    Page<SectionDTO> page = sectionManager.findAllByPrincipal(pageable);
    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/section");

    return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
  }

}
