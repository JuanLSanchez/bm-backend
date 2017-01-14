package es.juanlsanchez.bm.manager;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import es.juanlsanchez.bm.web.dto.SupplierDTO;
import es.juanlsanchez.bm.web.dto.SupplierWithEvolutionDTO;
import javassist.NotFoundException;

public interface SupplierManager {

  public Page<SupplierDTO> findAllByPrincipal(Pageable pageable);

  public SupplierDTO create(SupplierDTO supplier);

  public SupplierDTO getOne(Long id) throws NotFoundException;

  public SupplierDTO update(SupplierDTO supplier, Long id) throws NotFoundException;

  public void delete(Long id) throws NotFoundException;

  public Optional<SupplierDTO> findOne(Long id);

  public List<SupplierWithEvolutionDTO> evolutionInDaysInTheRange(LocalDate start, LocalDate end);

}
