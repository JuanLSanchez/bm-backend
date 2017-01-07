package es.juanlsanchez.bm.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import es.juanlsanchez.bm.domain.Invoice;
import es.juanlsanchez.bm.domain.User;
import es.juanlsanchez.bm.mapper.InvoiceMapper;
import es.juanlsanchez.bm.repository.InvoiceRepository;
import es.juanlsanchez.bm.service.InvoiceService;
import es.juanlsanchez.bm.service.OperationService;
import es.juanlsanchez.bm.service.SupplierService;
import es.juanlsanchez.bm.service.UserService;
import es.juanlsanchez.bm.web.dto.RangeDTO;
import javassist.NotFoundException;

@Service
@Transactional
public class DefaultInvoiceService implements InvoiceService {

  private final InvoiceRepository invoiceRepository;
  private final UserService userService;
  private final InvoiceMapper invoiceMapper;

  private final OperationService operationService;
  private final SupplierService supplierService;

  @Inject
  public DefaultInvoiceService(final InvoiceRepository invoiceRepository,
      final UserService userService, final InvoiceMapper invoiceMapper,
      final OperationService operationService, final SupplierService supplierService) {
    this.invoiceRepository = invoiceRepository;
    this.userService = userService;
    this.invoiceMapper = invoiceMapper;
    this.operationService = operationService;
    this.supplierService = supplierService;
  }

  @Override
  public Page<Invoice> findAllByPrincipal(Pageable pageable) {
    return this.invoiceRepository.findAllByPrincipal(pageable);
  }

  @Override
  public Optional<Invoice> findOne(Long id) {
    User principal = userService.getPrincipal();
    return this.invoiceRepository.findOneByIdAndPrincipal(id, principal);
  }

  @Override
  public Invoice create(Invoice invoice, Long operationId, Long supplierId)
      throws NotFoundException {
    User principal = this.userService.getPrincipal();
    invoice.setPrincipal(principal);

    invoice.setOperation(this.operationService.getOne(operationId));
    invoice.setSupplier(this.supplierService.getOne(supplierId));

    Invoice result = this.invoiceRepository.save(invoice);


    return result;
  }

  @Override
  public Invoice update(Invoice invoice, Long invoiceId, Long operationId, Long supplierId)
      throws NotFoundException {
    Invoice invoiceTarget = this.getOne(invoiceId);

    if (!invoiceTarget.getSupplier().getId().equals(supplierId)) {
      invoiceTarget.setSupplier(this.supplierService.getOne(supplierId));
    }

    if (!invoiceTarget.getOperation().getId().equals(operationId)) {
      invoiceTarget.setOperation(this.operationService.getOne(operationId));
    }

    this.invoiceMapper.updateInvoice(invoice, invoiceTarget);

    Invoice result = this.invoiceRepository.save(invoiceTarget);
    return result;
  }

  @Override
  public void delete(Long id) throws NotFoundException {
    this.getOne(id);
    invoiceRepository.delete(id);
  }

  @Override
  public Invoice getOne(Long id) throws NotFoundException {
    return this.findOne(id).orElseThrow(() -> new NotFoundException("Not found the invoice " + id));
  }

  @Override
  public RangeDTO getRangeByPrincipal() {
    User principal = this.userService.getPrincipal();
    return this.invoiceRepository.getRangeByPrincipal(principal);
  }

  @Override
  public List<Invoice> findAllByPrincipalAndDateBuyGreaterThanEqualAndDateBuyLessThan(
      LocalDate start, LocalDate finish) {
    User principal = this.userService.getPrincipal();
    return this.invoiceRepository
        .findAllByPrincipalAndDateBuyGreaterThanEqualAndDateBuyLessThanOrderByDateBuyAsc(principal,
            start, finish);
  }

}
