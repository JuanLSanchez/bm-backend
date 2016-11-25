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
import es.juanlsanchez.bm.manager.InvoiceLineManager;
import es.juanlsanchez.bm.web.dto.InvoiceLineDTO;
import es.juanlsanchez.bm.web.util.pagination.HeaderUtil;
import es.juanlsanchez.bm.web.util.pagination.PaginationUtil;
import javassist.NotFoundException;

@RestController
@RequestMapping(Constants.START_URL_API + "/invoice_line")
public class InvoiceLineResource {

  private final Logger log = LoggerFactory.getLogger(this.getClass());

  private final InvoiceLineManager invoiceLineManager;

  @Inject
  public InvoiceLineResource(final InvoiceLineManager invoiceLineManager) {
    this.invoiceLineManager = invoiceLineManager;
  }

  @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<InvoiceLineDTO>> findAllByPrincipal(Pageable pageable)
      throws URISyntaxException {
    log.debug("REST request to list invoiceLine");

    Page<InvoiceLineDTO> page = invoiceLineManager.findAllByPrincipal(pageable);
    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/invoiceLine");

    return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
  }

  @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<InvoiceLineDTO> create(@Valid @RequestBody InvoiceLineDTO invoiceLine)
      throws URISyntaxException, NotFoundException {
    log.debug("REST request to create invoiceLine: {}", invoiceLine);

    InvoiceLineDTO invoiceLineCreated = invoiceLineManager.create(invoiceLine);

    return ResponseEntity.created(new URI("/api/invoiceLine/" + invoiceLineCreated.getId()))
        .headers(HeaderUtil.createAlert("invoiceLine", invoiceLineCreated.getId().toString()))
        .body(invoiceLineCreated);
  }

  @RequestMapping(value = "/invoice/{invoiceId}", method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<InvoiceLineDTO>> findAllByInvoice(@PathVariable Long invoiceId,
      Pageable pageable) throws URISyntaxException, NotFoundException {
    log.debug("REST request to list invoiceLine");

    Page<InvoiceLineDTO> page = invoiceLineManager.findAllByInvoice(invoiceId, pageable);
    HttpHeaders headers =
        PaginationUtil.generatePaginationHttpHeaders(page, "/api/invoiceLine/invoice/" + invoiceId);

    return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<InvoiceLineDTO> findOne(@PathVariable Long id)
      throws URISyntaxException, NotFoundException {
    log.debug("REST request to find invoiceLine: {}", id);
    return ResponseEntity.ok(invoiceLineManager.getOne(id));
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.PUT,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<InvoiceLineDTO> update(@Valid @RequestBody InvoiceLineDTO invoiceLine,
      @PathVariable Long id) throws URISyntaxException, NotFoundException {
    log.debug("REST request to update invoiceLine '{}' with: {}", id, invoiceLine);

    invoiceLine.setId(id);
    InvoiceLineDTO invoiceLineCreated = invoiceLineManager.update(invoiceLine);

    return ResponseEntity.ok()
        .headers(HeaderUtil.createAlert("invoiceLine", invoiceLineCreated.getId().toString()))
        .body(invoiceLineCreated);
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Void> delete(@PathVariable Long id)
      throws URISyntaxException, NotFoundException {
    log.debug("REST request to delete invoiceLine '{}' with: {}", id);

    invoiceLineManager.delete(id);

    return ResponseEntity.ok()
        .headers(HeaderUtil.createEntityDeletionAlert("schedule", id.toString())).build();
  }



}
