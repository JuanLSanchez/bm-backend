package es.juanlsanchez.bm.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import es.juanlsanchez.bm.domain.Income;
import es.juanlsanchez.bm.web.dto.RangeDTO;
import javassist.NotFoundException;

public interface IncomeService {

  public Page<Income> findAllByPrincipal(Pageable pageable);

  public Optional<Income> findOne(Long id);

  public Income create(Income income);

  public Income update(Income income, Long incomeId) throws NotFoundException;

  public void delete(Long id) throws NotFoundException;

  public RangeDTO getRangeByPrincipal();

  public List<Income> findAllByPrincipalAndIncomeDateGreaterThanEqualAndIncomeDateLessThan(
      LocalDate start, LocalDate finish);

}
