package es.juanlsanchez.bm.service;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import es.juanlsanchez.bm.domain.Supplier;
import javassist.NotFoundException;

public interface SupplierService {

  public Optional<Supplier> findOne(Long id);

  public Supplier getOne(Long id) throws NotFoundException;

  public Page<Supplier> findAllByPrincipal(Pageable pageable);

  public Supplier create(Supplier supplier);

  public Supplier update(Supplier supplier, Long supplierId) throws NotFoundException;

  public void delete(Long id) throws NotFoundException;

  public Map<Supplier, Map<LocalDate, Double>> evolutionInDaysInTheRange(LocalDate start,
      LocalDate end);

}
