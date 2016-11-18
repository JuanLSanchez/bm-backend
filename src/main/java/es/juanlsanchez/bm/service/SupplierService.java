package es.juanlsanchez.bm.service;

import java.util.Optional;

import es.juanlsanchez.bm.domain.Supplier;
import javassist.NotFoundException;

public interface SupplierService {

  public Optional<Supplier> findOne(Long supplierId);

  public Supplier getOne(Long supplierId) throws NotFoundException;

}
