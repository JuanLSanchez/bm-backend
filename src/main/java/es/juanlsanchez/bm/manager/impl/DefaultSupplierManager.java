package es.juanlsanchez.bm.manager.impl;

import java.util.Optional;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import es.juanlsanchez.bm.manager.SupplierManager;
import es.juanlsanchez.bm.mapper.SupplierMapper;
import es.juanlsanchez.bm.service.SupplierService;
import es.juanlsanchez.bm.web.dto.SupplierDTO;
import javassist.NotFoundException;

@Component
public class DefaultSupplierManager implements SupplierManager {

  private final SupplierMapper supplierMapper;
  private final SupplierService supplierService;

  @Inject
  public DefaultSupplierManager(final SupplierMapper supplierMapper,
      final SupplierService supplierService) {
    this.supplierMapper = supplierMapper;
    this.supplierService = supplierService;
  }

  @Override
  public Page<SupplierDTO> findAllByPrincipal(Pageable pageable) {
    return supplierService.findAllByPrincipal(pageable)
        .map(supplier -> supplierMapper.supplierToSupplierDTO(supplier));
  }

  @Override
  public Optional<SupplierDTO> findOne(Long id) {
    return supplierService.findOne(id)
        .map(supplier -> supplierMapper.supplierToSupplierDTO(supplier));
  }

  @Override
  public SupplierDTO create(SupplierDTO supplier) {
    return supplierMapper.supplierToSupplierDTO(
        supplierService.create(supplierMapper.supplierDTOToSupplier(supplier)));
  }

  @Override
  public SupplierDTO update(SupplierDTO supplier, Long supplierId) throws NotFoundException {
    return supplierMapper.supplierToSupplierDTO(
        supplierService.update(supplierMapper.supplierDTOToSupplier(supplier), supplierId));
  }

  @Override
  public void delete(Long id) throws NotFoundException {
    supplierService.delete(id);
  }

  @Override
  public SupplierDTO getOne(Long id) throws NotFoundException {
    return supplierMapper.supplierToSupplierDTO(supplierService.getOne(id));
  }

}
