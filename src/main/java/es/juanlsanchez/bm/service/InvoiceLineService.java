package es.juanlsanchez.bm.service;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import es.juanlsanchez.bm.domain.InvoiceLine;
import javassist.NotFoundException;

public interface InvoiceLineService {

  public Page<InvoiceLine> findAllByPrincipal(Pageable pageable);

  public Optional<InvoiceLine> findOne(Long id);

  public InvoiceLine create(InvoiceLine invoiceLine, Long invoiceId) throws NotFoundException;

  public InvoiceLine update(InvoiceLine invoiceLine, Long invoiceLineId, Long invoiceId)
      throws NotFoundException;

  public void delete(Long id) throws NotFoundException;

  public InvoiceLine getOne(Long id) throws NotFoundException;

  public Page<InvoiceLine> findAllByInvoice(Long invoiceId, Pageable pageable)
      throws NotFoundException;

  public Map<LocalDate, Double> evolutionInDaysInTheRange(LocalDate start, LocalDate end);

}
