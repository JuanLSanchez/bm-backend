package es.juanlsanchez.bm.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.juanlsanchez.bm.config.Constants;
import es.juanlsanchez.bm.manager.SupplierManager;
import es.juanlsanchez.bm.web.dto.SupplierDTO;
import es.juanlsanchez.bm.web.dto.SupplierWithEvolutionDTO;
import es.juanlsanchez.bm.web.util.pagination.HeaderUtil;
import es.juanlsanchez.bm.web.util.pagination.PaginationUtil;
import javassist.NotFoundException;

@RestController
@RequestMapping(Constants.START_URL_API + "/supplier")
public class SupplierResource {

  private final Logger log = LoggerFactory.getLogger(this.getClass());

  private final SupplierManager supplierManager;

  @Inject
  public SupplierResource(final SupplierManager supplierManager) {
    this.supplierManager = supplierManager;
  }

  @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<SupplierDTO>> findAllByPrincipal(Pageable pageable)
      throws URISyntaxException {
    log.debug("REST request to list supplier");

    Page<SupplierDTO> page = supplierManager.findAllByPrincipal(pageable);
    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/supplier");

    return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
  }

  @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<SupplierDTO> create(@Valid @RequestBody SupplierDTO supplier)
      throws URISyntaxException {
    log.debug("REST request to create supplier: {}", supplier);

    SupplierDTO supplierCreated = supplierManager.create(supplier);

    return ResponseEntity.created(new URI("/api/supplier/" + supplierCreated.getId()))
        .headers(HeaderUtil.createAlert("supplier", supplierCreated.getId().toString()))
        .body(supplierCreated);
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<SupplierDTO> findOne(@PathVariable Long id)
      throws URISyntaxException, NotFoundException {
    log.debug("REST request to find supplier: {}", id);
    return ResponseEntity.ok(supplierManager.getOne(id));
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.PUT,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<SupplierDTO> update(@Valid @RequestBody SupplierDTO supplier,
      @PathVariable Long id) throws URISyntaxException, NotFoundException {
    log.debug("REST request to update supplier '{}' with: {}", id, supplier);

    SupplierDTO supplierCreated = supplierManager.update(supplier, id);

    return ResponseEntity.ok()
        .headers(HeaderUtil.createAlert("supplier", supplierCreated.getId().toString()))
        .body(supplierCreated);
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Void> delete(@PathVariable Long id)
      throws URISyntaxException, NotFoundException {
    log.debug("REST request to delete supplier '{}' with: {}", id);

    supplierManager.delete(id);

    return ResponseEntity.ok()
        .headers(HeaderUtil.createEntityDeletionAlert("schedule", id.toString())).build();
  }

  // Statistics

  @RequestMapping(value = "/statistic/evolution", method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<SupplierWithEvolutionDTO>> evolutionInDaysInTheRange(
      @RequestParam(required = true,
          name = "start") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate start,
      @RequestParam(required = true,
          name = "end") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {
    log.debug("REST request to get evolition in the range {} <-> {}", start, end);
    return ResponseEntity.ok(this.supplierManager.evolutionInDaysInTheRange(start, end));
  }


}
