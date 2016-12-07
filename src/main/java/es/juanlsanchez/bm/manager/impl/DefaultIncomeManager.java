package es.juanlsanchez.bm.manager.impl;

import java.util.Optional;

import javax.inject.Inject;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import es.juanlsanchez.bm.manager.IncomeManager;
import es.juanlsanchez.bm.mapper.IncomeMapper;
import es.juanlsanchez.bm.service.DocumentService;
import es.juanlsanchez.bm.service.IncomeService;
import es.juanlsanchez.bm.web.dto.IncomeDTO;
import es.juanlsanchez.bm.web.dto.QuarterDTO;
import es.juanlsanchez.bm.web.dto.RangeDTO;
import javassist.NotFoundException;

@Component
public class DefaultIncomeManager implements IncomeManager {

  private final IncomeMapper incomeMapper;
  private final IncomeService incomeService;
  private final DocumentService documentService;

  @Inject
  public DefaultIncomeManager(final IncomeMapper incomeMapper, final IncomeService incomeService,
      final DocumentService documentService) {
    this.incomeMapper = incomeMapper;
    this.incomeService = incomeService;
    this.documentService = documentService;
  }

  @Override
  public Page<IncomeDTO> findAllByPrincipal(Pageable pageable) {
    return incomeService.findAllByPrincipal(pageable)
        .map(income -> incomeMapper.incomeToIncomeDTO(income));
  }

  @Override
  public Optional<IncomeDTO> findOne(Long id) {
    return incomeService.findOne(id).map(income -> incomeMapper.incomeToIncomeDTO(income));
  }

  @Override
  public IncomeDTO create(IncomeDTO income) {
    return incomeMapper
        .incomeToIncomeDTO(incomeService.create(incomeMapper.incomeDTOToIncome(income)));
  }

  @Override
  public IncomeDTO update(IncomeDTO income, Long incomeId) throws NotFoundException {
    return incomeMapper
        .incomeToIncomeDTO(incomeService.update(incomeMapper.incomeDTOToIncome(income), incomeId));
  }

  @Override
  public void delete(Long id) throws NotFoundException {
    this.incomeService.delete(id);
  }

  @Override
  public RangeDTO getRangeByPrincipal() {
    return this.incomeService.getRangeByPrincipal();
  }

  @Override
  public HSSFWorkbook getDocumen(QuarterDTO quarterDTO) {
    return this.documentService.createIncomeDocument(quarterDTO);
  }

}
