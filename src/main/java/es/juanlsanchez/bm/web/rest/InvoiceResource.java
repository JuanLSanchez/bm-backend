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
import es.juanlsanchez.bm.manager.InvoiceManager;
import es.juanlsanchez.bm.web.dto.InvoiceDTO;
import es.juanlsanchez.bm.web.dto.RangeDTO;
import es.juanlsanchez.bm.web.util.pagination.HeaderUtil;
import es.juanlsanchez.bm.web.util.pagination.PaginationUtil;
import javassist.NotFoundException;

@RestController
@RequestMapping(Constants.START_URL_API + "/invoice")
public class InvoiceResource {

  private final Logger log = LoggerFactory.getLogger(this.getClass());

  private final InvoiceManager invoiceManager;

  @Inject
  public InvoiceResource(final InvoiceManager invoiceManager) {
    this.invoiceManager = invoiceManager;
  }

  @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<InvoiceDTO>> findAllByPrincipal(Pageable pageable)
      throws URISyntaxException {
    log.debug("REST request to list invoice");

    Page<InvoiceDTO> page = invoiceManager.findAllByPrincipal(pageable);
    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/invoice");

    return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
  }

  @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<InvoiceDTO> create(@Valid @RequestBody InvoiceDTO invoice)
      throws URISyntaxException, NotFoundException {
    log.debug("REST request to create invoice: {}", invoice);

    InvoiceDTO invoiceCreated = invoiceManager.create(invoice);

    return ResponseEntity.created(new URI("/api/invoice/" + invoiceCreated.getId()))
        .headers(HeaderUtil.createAlert("invoice", invoiceCreated.getId().toString()))
        .body(invoiceCreated);
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<InvoiceDTO> findOne(@PathVariable Long id) throws URISyntaxException {
    log.debug("REST request to find invoice: {}", id);
    return invoiceManager.findOne(id)
        .map(invoiceDto -> new ResponseEntity<>(invoiceDto, HttpStatus.OK))
        .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.PUT,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<InvoiceDTO> update(@Valid @RequestBody InvoiceDTO invoice,
      @PathVariable Long id) throws URISyntaxException, NotFoundException {
    log.debug("REST request to update invoice '{}' with: {}", id, invoice);

    InvoiceDTO invoiceCreated = invoiceManager.update(invoice, id);

    return ResponseEntity.ok()
        .headers(HeaderUtil.createAlert("invoice", invoiceCreated.getId().toString()))
        .body(invoiceCreated);
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Void> delete(@PathVariable Long id)
      throws URISyntaxException, NotFoundException {
    log.debug("REST request to delete invoice '{}' with: {}", id);

    invoiceManager.delete(id);

    return ResponseEntity.ok()
        .headers(HeaderUtil.createEntityDeletionAlert("schedule", id.toString())).build();
  }

  @RequestMapping(value = "/range", method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<RangeDTO> range() {
    log.debug("REST request to get range");
    return ResponseEntity.ok(this.invoiceManager.getRangeByPrincipal());
  }

}
