package es.juanlsanchez.bm.manager;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import es.juanlsanchez.bm.web.dto.InvoiceDTO;
import es.juanlsanchez.bm.web.dto.RangeDTO;
import javassist.NotFoundException;

public interface InvoiceManager {

  public Page<InvoiceDTO> findAllByPrincipal(Pageable pageable);

  public InvoiceDTO create(InvoiceDTO invoice) throws NotFoundException;

  public Optional<InvoiceDTO> findOne(Long id);

  public InvoiceDTO update(InvoiceDTO invoice, Long id) throws NotFoundException;

  public void delete(Long id) throws NotFoundException;

  public RangeDTO getRangeByPrincipal();

}
