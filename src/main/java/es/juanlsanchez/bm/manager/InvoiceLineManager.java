package es.juanlsanchez.bm.manager;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import es.juanlsanchez.bm.web.dto.InvoiceLineDTO;
import javassist.NotFoundException;

public interface InvoiceLineManager {

  public Page<InvoiceLineDTO> findAllByPrincipal(Pageable pageable);

  public InvoiceLineDTO create(InvoiceLineDTO invoiceLine) throws NotFoundException;

  public InvoiceLineDTO getOne(Long id) throws NotFoundException;

  public InvoiceLineDTO update(InvoiceLineDTO invoiceLine) throws NotFoundException;

  public void delete(Long id) throws NotFoundException;

  public Optional<InvoiceLineDTO> findOne(Long id);

  public Page<InvoiceLineDTO> findAllByInvoice(Long invoiceId, Pageable pageable)
      throws NotFoundException;

  public Map<LocalDate, Double> evolutionInDaysInTheRange(LocalDate start, LocalDate end);

}
