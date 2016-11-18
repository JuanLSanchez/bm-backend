package es.juanlsanchez.bm.service.impl;

import java.util.Optional;

import javax.inject.Inject;
import javax.transaction.Transactional;

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

  @Inject
  public DefaultSupplierService(final SupplierRepository supplierRepository,
      final UserService userService, final SupplierMapper supplierMapper) {
    this.supplierRepository = supplierRepository;
    this.userService = userService;
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

}
