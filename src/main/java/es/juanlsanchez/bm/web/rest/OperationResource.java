package es.juanlsanchez.bm.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import es.juanlsanchez.bm.config.Constants;
import es.juanlsanchez.bm.manager.OperationManager;
import es.juanlsanchez.bm.web.dto.OperationDTO;
import es.juanlsanchez.bm.web.util.pagination.HeaderUtil;
import es.juanlsanchez.bm.web.util.pagination.PaginationUtil;
import javassist.NotFoundException;

@RestController
@RequestMapping(Constants.START_URL_API + "/operation")
public class OperationResource {

  private final Logger log = LoggerFactory.getLogger(this.getClass());

  private final OperationManager operationManager;

  @Inject
  public OperationResource(final OperationManager operationManager) {
    this.operationManager = operationManager;
  }

  @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<OperationDTO>> findAllByPrincipal(Pageable pageable)
      throws URISyntaxException {
    log.debug("REST request to list operation");

    Page<OperationDTO> page = operationManager.findAllByPrincipal(pageable);
    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/operation");

    return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
  }

  @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<OperationDTO> create(@Valid @RequestBody OperationDTO operation)
      throws URISyntaxException, NotFoundException {
    log.debug("REST request to create operation: {}", operation);

    OperationDTO operationCreated = operationManager.create(operation);

    return ResponseEntity.created(new URI("/api/operation/" + operationCreated.getId()))
        .headers(HeaderUtil.createAlert("operation", operationCreated.getId().toString()))
        .body(operationCreated);
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<OperationDTO> findOne(@PathVariable Long id)
      throws URISyntaxException, NotFoundException {
    log.debug("REST request to find operation: {}", id);
    return ResponseEntity.ok(operationManager.getOne(id));
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.PUT,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<OperationDTO> update(@Valid @RequestBody OperationDTO operation,
      @PathVariable Long id) throws URISyntaxException, NotFoundException {
    log.debug("REST request to update operation '{}' with: {}", id, operation);

    OperationDTO operationCreated = operationManager.update(operation, id);

    return ResponseEntity.ok()
        .headers(HeaderUtil.createAlert("operation", operationCreated.getId().toString()))
        .body(operationCreated);
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Void> delete(@PathVariable Long id)
      throws URISyntaxException, NotFoundException {
    log.debug("REST request to delete operation '{}' with: {}", id);

    operationManager.delete(id);

    return ResponseEntity.ok()
        .headers(HeaderUtil.createEntityDeletionAlert("schedule", id.toString())).build();
  }

}
