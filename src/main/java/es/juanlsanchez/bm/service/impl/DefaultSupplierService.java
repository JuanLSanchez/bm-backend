package es.juanlsanchez.bm.service.impl;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Optional;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import es.juanlsanchez.bm.domain.Supplier;
import es.juanlsanchez.bm.domain.User;
import es.juanlsanchez.bm.mapper.SupplierMapper;
import es.juanlsanchez.bm.repository.SupplierRepository;
import es.juanlsanchez.bm.service.SupplierService;
import es.juanlsanchez.bm.service.UserService;
import javassist.NotFoundException;

@Service
@Transactional
public class DefaultSupplierService implements SupplierService {

  private final SupplierRepository supplierRepository;
  private final UserService userService;
  private final SupplierMapper supplierMapper;

  @Inject
  public DefaultSupplierService(final SupplierRepository supplierRepository,
      final UserService userService, final SupplierMapper supplierMapper) {
    this.supplierRepository = supplierRepository;
    this.userService = userService;
    this.supplierMapper = supplierMapper;
  }

  @Override
  public Supplier getOne(Long id) throws NotFoundException {
    return this.findOne(id)
        .orElseThrow(() -> new NotFoundException("Not found the supplier " + id));
  }

  @Override
  public Optional<Supplier> findOne(Long id) {
    User principal = userService.getPrincipal();
    return this.supplierRepository.findOneByIdAndPrincipal(id, principal);
  }

  @Override
  public Page<Supplier> findAllByPrincipal(Pageable pageable) {
    return this.supplierRepository.findAllByPrincipal(pageable);
  }

  @Override
  public Supplier create(Supplier supplier) {
    User principal = this.userService.getPrincipal();

    supplier.setPrincipal(principal);

    return this.supplierRepository.save(supplier);
  }

  @Override
  public Supplier update(Supplier supplier, Long supplierId) throws NotFoundException {
    Supplier supplierTarget = this.getOne(supplierId);

    supplierMapper.update(supplier, supplierTarget);

    return this.supplierRepository.save(supplierTarget);
  }

  @Override
  public void delete(Long id) throws NotFoundException {
    Supplier supplier = this.getOne(id);
    assertThat("The supplier cannot has invoices", supplier.getInvoices().isEmpty(),
        is(equalTo(true)));
    supplierRepository.delete(id);

  }

}
