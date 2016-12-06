package es.juanlsanchez.bm.service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import es.juanlsanchez.bm.domain.Invoice;
import es.juanlsanchez.bm.web.dto.RangeDTO;
import javassist.NotFoundException;

public interface InvoiceService {

  public Page<Invoice> findAllByPrincipal(Pageable pageable);

  public Optional<Invoice> findOne(Long id);

  public void delete(Long id) throws NotFoundException;

  public Invoice update(Invoice invoiceDTOToInvoice, Long invoiceId, Long operationId,
      Long supplierId) throws NotFoundException;

  public Invoice create(Invoice invoice, Long operationId, Long supplierId)
      throws NotFoundException;

  public Invoice getOne(Long id) throws NotFoundException;

  public RangeDTO getRangeByPrincipal();

  public List<Invoice> findAllByPrincipalAndDateBuyGreaterThanEqualAndDateBuyLessThan(Instant start,
      Instant finish);

}
