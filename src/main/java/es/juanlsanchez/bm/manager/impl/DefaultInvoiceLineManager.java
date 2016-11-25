package es.juanlsanchez.bm.manager.impl;

import java.util.Optional;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import es.juanlsanchez.bm.manager.InvoiceLineManager;
import es.juanlsanchez.bm.mapper.InvoiceLineMapper;
import es.juanlsanchez.bm.service.InvoiceLineService;
import es.juanlsanchez.bm.web.dto.InvoiceLineDTO;
import javassist.NotFoundException;

@Component
public class DefaultInvoiceLineManager implements InvoiceLineManager {

  private final InvoiceLineMapper invoiceLineMapper;
  private final InvoiceLineService invoiceLineService;

  @Inject
  public DefaultInvoiceLineManager(final InvoiceLineMapper invoiceLineMapper,
      final InvoiceLineService invoiceLineService) {
    this.invoiceLineMapper = invoiceLineMapper;
    this.invoiceLineService = invoiceLineService;
  }

  @Override
  public Page<InvoiceLineDTO> findAllByPrincipal(Pageable pageable) {
    return invoiceLineService.findAllByPrincipal(pageable)
        .map(invoiceLine -> invoiceLineMapper.invoiceLineToInvoiceLineDTO(invoiceLine));
  }

  @Override
  public Optional<InvoiceLineDTO> findOne(Long id) {
    return invoiceLineService.findOne(id)
        .map(invoiceLine -> invoiceLineMapper.invoiceLineToInvoiceLineDTO(invoiceLine));
  }

  @Override
  public InvoiceLineDTO create(InvoiceLineDTO invoiceLine) throws NotFoundException {
    return invoiceLineMapper.invoiceLineToInvoiceLineDTO(invoiceLineService.create(
        invoiceLineMapper.invoiceLineDTOToInvoiceLine(invoiceLine), invoiceLine.getInvoiceId()));
  }

  @Override
  public InvoiceLineDTO update(InvoiceLineDTO invoiceLine) throws NotFoundException {
    return invoiceLineMapper.invoiceLineToInvoiceLineDTO(
        this.invoiceLineService.update(invoiceLineMapper.invoiceLineDTOToInvoiceLine(invoiceLine),
            invoiceLine.getId(), invoiceLine.getInvoiceId()));
  }

  @Override
  public void delete(Long id) throws NotFoundException {
    invoiceLineService.delete(id);
  }

  @Override
  public InvoiceLineDTO getOne(Long id) throws NotFoundException {
    return invoiceLineMapper.invoiceLineToInvoiceLineDTO(invoiceLineService.getOne(id));
  }

  @Override
  public Page<InvoiceLineDTO> findAllByInvoice(Long invoiceId, Pageable pageable)
      throws NotFoundException {
    return invoiceLineService.findAllByInvoice(invoiceId, pageable)
        .map(invoiceLine -> invoiceLineMapper.invoiceLineToInvoiceLineDTO(invoiceLine));
  }

}
