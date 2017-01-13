package es.juanlsanchez.bm.service.impl;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import es.juanlsanchez.bm.domain.InvoiceLine;
import es.juanlsanchez.bm.domain.User;
import es.juanlsanchez.bm.mapper.InvoiceLineMapper;
import es.juanlsanchez.bm.repository.InvoiceLineRepository;
import es.juanlsanchez.bm.service.InvoiceLineService;
import es.juanlsanchez.bm.service.InvoiceService;
import es.juanlsanchez.bm.service.UserService;
import es.juanlsanchez.bm.util.object.Pair;
import javassist.NotFoundException;

@Service
@Transactional
public class DefaultInvoiceLineService implements InvoiceLineService {

  private final InvoiceLineRepository invoiceLineRepository;
  private final UserService userService;
  private final InvoiceLineMapper invoiceLineMapper;

  private final InvoiceService invoiceService;

  @Inject
  public DefaultInvoiceLineService(final InvoiceLineRepository invoiceLineRepository,
      final UserService userService, final InvoiceLineMapper invoiceLineMapper,
      final InvoiceService invoiceService) {
    this.invoiceLineRepository = invoiceLineRepository;
    this.userService = userService;
    this.invoiceLineMapper = invoiceLineMapper;
    this.invoiceService = invoiceService;
  }

  @Override
  public Page<InvoiceLine> findAllByPrincipal(Pageable pageable) {
    return this.invoiceLineRepository.findAllByInvoicePrincipal(pageable);
  }

  @Override
  public Optional<InvoiceLine> findOne(Long id) {
    User principal = userService.getPrincipal();
    return this.invoiceLineRepository.findOneByIdAndInvoicePrincipal(id, principal);
  }

  @Override
  public InvoiceLine create(InvoiceLine invoiceLine, Long invoiceId) throws NotFoundException {
    invoiceLine.setInvoice(invoiceService.getOne(invoiceId));

    InvoiceLine result = this.invoiceLineRepository.save(invoiceLine);

    return result;
  }

  @Override
  public InvoiceLine update(InvoiceLine invoice, Long invoiceLineId, Long invoiceId)
      throws NotFoundException {
    InvoiceLine invoiceTarget = this.getOne(invoiceLineId);

    if (!invoiceTarget.getInvoice().getId().equals(invoiceId)) {
      invoiceTarget.setInvoice(this.invoiceService.getOne(invoiceId));
    }

    this.invoiceLineMapper.updateInvoiceLine(invoice, invoiceTarget);

    return this.invoiceLineRepository.save(invoiceTarget);
  }

  @Override
  public void delete(Long id) throws NotFoundException {
    this.getOne(id);
    invoiceLineRepository.delete(id);
  }

  @Override
  public InvoiceLine getOne(Long id) throws NotFoundException {
    return this.findOne(id)
        .orElseThrow(() -> new NotFoundException("Not found the invoice line" + id));
  }

  @Override
  public Page<InvoiceLine> findAllByInvoice(Long invoiceId, Pageable pageable)
      throws NotFoundException {
    this.invoiceService.getOne(invoiceId);
    return this.invoiceLineRepository.findAllByInvoiceId(invoiceId, pageable);
  }

  @Override
  public Map<LocalDate, Double> evolutionInDaysInTheRange(LocalDate start, LocalDate end) {
    return this.invoiceLineRepository.evolutionInDaysInTheRange(start, end).stream()
        .collect(Collectors.toMap(Pair::getFirst, Pair::getSecond));
  }

}
