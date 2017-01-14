package es.juanlsanchez.bm.manager.impl;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import es.juanlsanchez.bm.manager.SupplierManager;
import es.juanlsanchez.bm.mapper.SupplierMapper;
import es.juanlsanchez.bm.service.SupplierService;
import es.juanlsanchez.bm.util.StatisticsUtil;
import es.juanlsanchez.bm.web.dto.SupplierDTO;
import es.juanlsanchez.bm.web.dto.SupplierWithEvolutionDTO;
import javassist.NotFoundException;

@Component
public class DefaultSupplierManager implements SupplierManager {

  private final SupplierMapper supplierMapper;
  private final SupplierService supplierService;
  private final StatisticsUtil statisticsUtil;

  @Inject
  public DefaultSupplierManager(final SupplierMapper supplierMapper,
      final SupplierService supplierService, final StatisticsUtil statisticsUtil) {
    this.supplierMapper = supplierMapper;
    this.supplierService = supplierService;
    this.statisticsUtil = statisticsUtil;
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

  @Override
  public List<SupplierWithEvolutionDTO> evolutionInDaysInTheRange(LocalDate start, LocalDate end) {
    return this.supplierService.evolutionInDaysInTheRange(start, end).entrySet().stream()
        .map(entry -> this.supplierMapper.supplierToSupplierWithEvolutionDTO(entry.getKey(),
            statisticsUtil.fillInterval(entry.getValue(), start, end, 0., 1, ChronoUnit.DAYS)))
        .collect(Collectors.toList());
  }

}
