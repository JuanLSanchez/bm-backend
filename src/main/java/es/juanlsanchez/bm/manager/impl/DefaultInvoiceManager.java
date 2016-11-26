package es.juanlsanchez.bm.manager.impl;

import java.util.Optional;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import es.juanlsanchez.bm.manager.InvoiceManager;
import es.juanlsanchez.bm.mapper.InvoiceMapper;
import es.juanlsanchez.bm.service.InvoiceService;
import es.juanlsanchez.bm.web.dto.InvoiceDTO;
import es.juanlsanchez.bm.web.dto.RangeDTO;
import javassist.NotFoundException;

@Component
public class DefaultInvoiceManager implements InvoiceManager {

  private final InvoiceMapper invoiceMapper;
  private final InvoiceService invoiceService;

  @Inject
  public DefaultInvoiceManager(final InvoiceMapper invoiceMapper,
      final InvoiceService invoiceService) {
    this.invoiceMapper = invoiceMapper;
    this.invoiceService = invoiceService;
  }

  @Override
  public Page<InvoiceDTO> findAllByPrincipal(Pageable pageable) {
    return invoiceService.findAllByPrincipal(pageable)
        .map(invoice -> invoiceMapper.invoiceToInvoiceDTO(invoice));
  }

  @Override
  public Optional<InvoiceDTO> findOne(Long id) {
    return invoiceService.findOne(id).map(invoice -> invoiceMapper.invoiceToInvoiceDTO(invoice));
  }

  @Override
  public InvoiceDTO create(InvoiceDTO invoice) throws NotFoundException {
    return invoiceMapper
        .invoiceToInvoiceDTO(invoiceService.create(invoiceMapper.invoiceDTOToInvoice(invoice),
            invoice.getOperationId(), invoice.getSupplierId()));
  }

  @Override
  public InvoiceDTO update(InvoiceDTO invoice, Long invoiceId) throws NotFoundException {
    return invoiceMapper
        .invoiceToInvoiceDTO(invoiceService.update(invoiceMapper.invoiceDTOToInvoice(invoice),
            invoiceId, invoice.getOperationId(), invoice.getSupplierId()));
  }

  @Override
  public void delete(Long id) throws NotFoundException {
    invoiceService.delete(id);
  }

  @Override
  public RangeDTO getRangeByPrincipal() {
    return this.invoiceService.getRangeByPrincipal();
  }

}
